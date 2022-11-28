package com.evol.pichincha.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.evol.pichincha.config.JWTUtil;
import com.evol.pichincha.dto.AuthResponse;
import com.evol.pichincha.dto.User;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@AllArgsConstructor
public class UserHandler {

	@Autowired
	private JWTUtil jwtUtil;
	
	public Mono<ServerResponse> login(ServerRequest request){
		Mono<User> user = request.bodyToMono(User.class);
		return user.flatMap(userDetails -> ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(jwtUtil.generateToken(userDetails)), AuthResponse.class));
	}
}
