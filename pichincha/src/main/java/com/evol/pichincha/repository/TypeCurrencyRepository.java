package com.evol.pichincha.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.evol.pichincha.entity.TypeCurrency;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface TypeCurrencyRepository extends R2dbcRepository<TypeCurrency, Long> {
	
    @Query("SELECT id, name, code, value_currency, created_at, update_at FROM type_currency WHERE code = :code")
    Mono<TypeCurrency> findByCode(String code);
    
    @Query("SELECT id, name, code, value_currency, created_at, update_at FROM type_currency WHERE id in (:code, :codeChange)")
    Flux<TypeCurrency> findCurrencies(long getCurrencyId, long getCurrencyChangeId);
    
    @Query("SELECT id, name, code, value_currency, created_at, update_at FROM type_currency WHERE id = :id")
    Mono<TypeCurrency> findByIdCurrency(int id);
    
}
