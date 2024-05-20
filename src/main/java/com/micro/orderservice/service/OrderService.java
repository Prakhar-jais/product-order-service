package com.micro.orderservice.service;

import com.micro.orderservice.model.OrderRequest;
import com.micro.orderservice.model.OrderResponse;

public interface OrderService {

    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(long orderId);
    

}
