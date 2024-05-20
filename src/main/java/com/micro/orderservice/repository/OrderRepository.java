package com.micro.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.micro.orderservice.entity.Order;


@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    
}
