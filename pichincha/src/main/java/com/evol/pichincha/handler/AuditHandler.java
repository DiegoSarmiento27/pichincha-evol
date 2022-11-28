package com.evol.pichincha.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.evol.pichincha.entity.Audit;
import com.evol.pichincha.service.AuditService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuditHandler {

	private final AuditService auditService;
	
	public Mono<ServerResponse> findAudits(ServerRequest request){
		Flux<Audit> auditFlux = auditService.findAll();
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(auditFlux, 
				Audit.class);
	}
}