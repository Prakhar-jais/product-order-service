package com.micro.orderservice.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@CircuitBreaker(name="external",fallbackMethod = "fallback")

@FeignClient(name = "product-service/product")
public interface ProductService {


    @PutMapping("reduceQuantity/{id}")
 
    public ResponseEntity<String> reduceQuantity(@PathVariable("id") long productId,@RequestParam long quantity) ;
    
      
    default void fallback(Exception e){
        throw new RuntimeException("Product Service is Down");
    }

}
