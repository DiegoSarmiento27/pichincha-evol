package com.evol.pichincha.config;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.evol.pichincha.dto.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {

	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private String expireTime;
	
	private Key key;

	public String generateToken(User user) {

		Long expirationTimeLong = Long.parseLong(expireTime);
		final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);
		Map<String, Object> claim = new HashMap<>();
		claim.put("alg", "HS256");
		claim.put("typ", "JWT");
		
		return Jwts.builder()
                .setClaims(claim)
                .setSubject(user.getUserName())
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
	}
	
	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(secret.getBytes());
	}
	
	@SuppressWarnings("deprecation")
	public Claims getClaimsToken(String token) {
		return Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(secret.getBytes()))
				.parseClaimsJws(token)
				.getBody();
	}
	
	public String getUsernameFromToken(String token) {
		String newToken = token.substring(1, token.length() - 1);
		return getClaimsToken(newToken).getSubject();
	}
	
	
	public Date getExpirationDate(String token) {
		return getClaimsToken(token).getExpiration();
	}

}
