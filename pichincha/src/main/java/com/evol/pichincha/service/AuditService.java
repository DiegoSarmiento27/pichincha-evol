package com.evol.pichincha.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.server.ServerRequest;

import com.evol.pichincha.config.JWTUtil;
import com.evol.pichincha.dto.ChangeResponse;
import com.evol.pichincha.entity.Audit;
import com.evol.pichincha.entity.TypeCurrency;
import com.evol.pichincha.repository.AuditRepository;
import com.evol.pichincha.util.Constants;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
@Transactional
public class AuditService {

	private final AuditRepository auditRepository;
	
	private final JWTUtil jwt;
	
	public Mono<TypeCurrency> createAudit(TypeCurrency type, ServerRequest req){
		DateFormat dateFormat = new SimpleDateFormat(Constants.FORMATDATE);
		String token = req.headers().firstHeader(HttpHeaders.AUTHORIZATION).substring(Constants.BEARER.length());
		String user = jwt.getUsernameFromToken(token);
		Audit auditEntity = new Audit(user, type.getId(), req.uri().toString(), req.methodName().toString(), type.toString(), dateFormat.format(new Date()));
		return auditRepository.save(auditEntity)
				.then(Mono.just(type));
	}
	
	public Mono<ChangeResponse> createAuditProcess(ChangeResponse res, ServerRequest req){
		DateFormat dateFormat = new SimpleDateFormat(Constants.FORMATDATE);
		String token = req.headers().firstHeader(HttpHeaders.AUTHORIZATION).substring(Constants.BEARER.length());
		String user = jwt.getUsernameFromToken(token);
		Audit auditEntity = new Audit(user, (long) 0, req.uri().toString(), req.methodName().toString(), res.toString(), dateFormat.format(new Date()));
		return auditRepository.save(auditEntity)
				.then(Mono.just(res));
	}
	
	public Flux<Audit> findAll(){
		return auditRepository.findAll();
	}
}
