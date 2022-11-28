package com.evol.pichincha.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.evol.pichincha.handler.AuditHandler;
import com.evol.pichincha.handler.TypeCurrencyHandler;
import com.evol.pichincha.handler.UserHandler;


@Configuration
public class ApplicationRouter {
	
	@Autowired
	private TypeCurrencyHandler handler;
	
	@Autowired
	private AuditHandler handlerAudit;
	
	@Autowired
	private UserHandler handlerUser;
    
	@Bean
    public RouterFunction<ServerResponse> routeTypeCurrency() {
    	return RouterFunctions.route()
    			.GET("/currency", handler::findCurrencies)
    			.GET("/currency/{id}", handler::findCurrencyById)
    			.GET("/currency/code/{code}", handler::getTypeCurrencyCode)
    			.PUT("/currency/update/{id}", handler::updateCurrency)
    			.POST("/currency/create", handler::createCurrency)
    			.POST("/currency/process", handler::process)
    			.build();
    }
	
	@Bean
    public RouterFunction<ServerResponse> routeAudit() {
    	return RouterFunctions.route()
    			.GET("/audit", handlerAudit::findAudits)
    			.build();
    }
	
	@Bean
    public RouterFunction<ServerResponse> routeLogin() {
    	return RouterFunctions.route()
    			.POST("/login", handlerUser::login)
    			.build();
    }
}
