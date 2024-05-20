package com.micro.orderservice.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.micro.orderservice.external.request.PaymentRequest;

@FeignClient(name="payment-service/payment")
public interface PaymentService {

    @PostMapping("/")
    public ResponseEntity<Long> doPayment(@RequestBody  PaymentRequest paymentRequest);

    
}
