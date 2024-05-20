package com.micro.orderservice.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service/product")
public interface ProductService {


    @PutMapping("reduceQuantity/{id}")
 
    public ResponseEntity<String> reduceQuantity(@PathVariable("id") long productId,@RequestParam long quantity) ;
    

}
