package com.micro.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    
    private long productid;
    private long totalAmount;
    private long quantity;
    private PaymentMode paymentMode ;
}
