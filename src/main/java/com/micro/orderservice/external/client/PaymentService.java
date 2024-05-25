package com.micro.orderservice.external.client;

import javax.management.RuntimeErrorException;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.micro.orderservice.external.request.PaymentRequest;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@CircuitBreaker(name="external",fallbackMethod = "fallback")
@FeignClient(name="payment-service/payment")
public interface PaymentService {

    @PostMapping("/")
    public ResponseEntity<Long> doPayment(@RequestBody  PaymentRequest paymentRequest);

    
    default void fallback(Exception e){
        throw new RuntimeException("Payment Service is Down");
    }
}
