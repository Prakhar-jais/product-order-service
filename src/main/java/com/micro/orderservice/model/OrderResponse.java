package com.micro.orderservice.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class OrderResponse {
    
    private long orderid;
    private Instant  orderDate;
    private String orderStatus;
    private long amount;
    private ProductDetails productDetails;
    private PaymentDetails paymentDetails;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor

    public static class ProductDetails {
        
        private long productId ;
        private String productName;
        private long price;
        private long quantity;
    }


 



    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder

    public static class PaymentDetails {
        
        private long paymentId;
        private String status ;
        private PaymentMode paymentMode;
        private long amount;
        private Instant paymentDate;
        private long orderId;
    }


}
