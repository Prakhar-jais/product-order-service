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
    
    private long oderid;
    private Instant  orderDate;
    private String orderStatus;
    private long amount;
}
