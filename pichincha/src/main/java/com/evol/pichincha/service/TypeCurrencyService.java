package com.evol.pichincha.service;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evol.pichincha.dto.ChangeRequest;
import com.evol.pichincha.dto.ChangeResponse;
import com.evol.pichincha.entity.TypeCurrency;
import com.evol.pichincha.repository.TypeCurrencyRepository;
import com.evol.pichincha.util.Constants;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
@Transactional
public class TypeCurrencyService {
	
	private final TypeCurrencyRepository typeCurrencyRepository;
		
	public Mono<TypeCurrency> createCurrency(TypeCurrency typeCurrency){
	    return typeCurrencyRepository.save(typeCurrency);
	}
	
    public Mono<TypeCurrency> findById(long id){
        return typeCurrencyRepository.findById(id);
    }
    
    public Flux<TypeCurrency> findAll(){
        return typeCurrencyRepository.findAll();
    }
    
    public Mono<TypeCurrency> findByCode(String code){
        return typeCurrencyRepository.findByCode(code);
    }
    
    public Mono<TypeCurrency> findById(int id){
        return typeCurrencyRepository.findByIdCurrency(id);
    }
    
    public Mono<TypeCurrency> updateCurrency(long id,  TypeCurrency typeCurrency){
        return typeCurrencyRepository.findById(id)
                .flatMap(dbCurrency -> {
            		DateFormat dateFormat = new SimpleDateFormat(Constants.FORMATDATE);
                	dbCurrency.setName(typeCurrency.getName());
                	dbCurrency.setCode(typeCurrency.getCode());
                	dbCurrency.setValueCurrency(typeCurrency.getValueCurrency());
                	dbCurrency.setUpdateAt(dateFormat.format(new Date()));
                    return typeCurrencyRepository.save(dbCurrency);
                });
    }
    
    public Mono<TypeCurrency> deleteCurrency(long id){
        return typeCurrencyRepository.findById(id)
                .flatMap(existingCurrency -> typeCurrencyRepository.delete(existingCurrency)
                        .then(Mono.just(existingCurrency)));
    }
    
    public Mono<ChangeResponse> process(ChangeRequest typeCurrency){
       Flux<TypeCurrency> listCurrency = typeCurrencyRepository.findCurrencies(typeCurrency.getCurrencyId(), 
    		   typeCurrency.getCurrencyChangeId());
       return listCurrency.collectList().map(res -> convert(res, typeCurrency));
    }
    
    public ChangeResponse convert(List<TypeCurrency> res, ChangeRequest req){
    	
       	if(req.getValue() <= 0) {
    		ChangeResponse errorResponse = new ChangeResponse();
    		errorResponse.setMessage(Constants.ERRORMONEDA);
    		return errorResponse;
    	}
    	ChangeResponse changeResponse = new ChangeResponse();
    	double currency = 0;
    	double currencyChange = 0;
    	for(TypeCurrency typeCurrency : res) {
    		if(typeCurrency.getId().equals(req.getCurrencyId())) {
    			currency = typeCurrency.getValueCurrency();
    			changeResponse.setCurrencyOriginal(typeCurrency.getCode());
    			changeResponse.setCurrencyOriginalId(typeCurrency.getId().toString());
    		}
    		if(typeCurrency.getId().equals(req.getCurrencyChangeId())) {
    			currencyChange = typeCurrency.getValueCurrency();
    			changeResponse.setCurrency(typeCurrency.getCode());
    			changeResponse.setCurrencyId(typeCurrency.getId().toString());
    		}
    	}
    	if(currency <= 0 || currencyChange <= 0) {
    		ChangeResponse errorResponse = new ChangeResponse();
    		errorResponse.setMessage(Constants.ERRORMONEDA);
    		return errorResponse;
    	}else {
    		double value = req.getValue()*currency/currencyChange;
    		DecimalFormat dec = new DecimalFormat(Constants.DECIMALFORMAT);
    		changeResponse.setValueOriginal(dec.format(req.getValue()));
    		changeResponse.setValue(dec.format(value));
    	}
    	return changeResponse;
    }
}
