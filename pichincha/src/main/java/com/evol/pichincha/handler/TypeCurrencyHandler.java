package com.evol.pichincha.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.evol.pichincha.dto.ChangeRequest;
import com.evol.pichincha.dto.ChangeResponse;
import com.evol.pichincha.dto.ErrorResponse;
import com.evol.pichincha.entity.TypeCurrency;
import com.evol.pichincha.service.AuditService;
import com.evol.pichincha.service.TypeCurrencyService;
import com.evol.pichincha.util.Constants;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TypeCurrencyHandler {
	
	private final TypeCurrencyService typeCurrencyService;
	private final AuditService auditService;
	
	public Mono<ServerResponse> findCurrencies(ServerRequest request){
		Flux<TypeCurrency> typeCurrenciesFlux = typeCurrencyService.findAll();
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(typeCurrenciesFlux, 
				TypeCurrency.class);
	}
	
	public Mono<ServerResponse> findCurrencyById(ServerRequest request){
		try {
			int id = Integer.parseInt(request.pathVariable(Constants.ID).toUpperCase().trim());
			Mono<TypeCurrency> typeCurrenciesFlux = typeCurrencyService.findById(id);
			return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(typeCurrenciesFlux, 
					TypeCurrency.class);
		} catch (Exception e) {
			return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(
					Mono.just(new ErrorResponse(Constants.ERROR, Constants.ERRORID)), 
					ErrorResponse.class);
		}

	}
	
	public Mono<ServerResponse> getTypeCurrencyCode(ServerRequest request){
		String code = request.pathVariable(Constants.CODE).toUpperCase().trim();
		Mono<TypeCurrency> typeCurrencyFlux = typeCurrencyService.findByCode(code);
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(typeCurrencyFlux, 
				TypeCurrency.class);
	}
		
	public Mono<ServerResponse> updateCurrency(ServerRequest request){
		int id = Integer.parseInt(request.pathVariable(Constants.ID));
		Mono<TypeCurrency> updatedCurrency = request.bodyToMono(TypeCurrency.class)
				.flatMap(cur -> this.typeCurrencyService.updateCurrency(id, cur))
				.flatMap(cur -> this.auditService.createAudit(cur, request));
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(updatedCurrency, TypeCurrency.class);
	}
	
	public Mono<ServerResponse> createCurrency(ServerRequest request){
		Mono<TypeCurrency> currency = request.bodyToMono(TypeCurrency.class)
				.flatMap(cur -> this.typeCurrencyService.createCurrency(cur))
				.flatMap(cur -> this.auditService.createAudit(cur, request));
		return ServerResponse.created(request.uri()).contentType(MediaType.APPLICATION_JSON).body(currency, TypeCurrency.class);
	}
	
	public Mono<ServerResponse> process(ServerRequest request){
		Mono<ChangeResponse> processCurrency = request.bodyToMono(ChangeRequest.class)
				.flatMap(cur -> this.typeCurrencyService.process(cur))
				.flatMap(res -> this.auditService.createAuditProcess(res, request));
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(processCurrency, ChangeResponse.class).switchIfEmpty(ServerResponse.notFound().build());
	}
	
}
