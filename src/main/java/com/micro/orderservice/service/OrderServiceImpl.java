package com.micro.orderservice.service;

import java.time.Instant;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.micro.orderservice.entity.Order;
import com.micro.orderservice.external.client.PaymentService;
import com.micro.orderservice.external.client.ProductService;
import com.micro.orderservice.model.OrderRequest;
import com.micro.orderservice.model.OrderResponse;
import com.micro.orderservice.repository.OrderRepository;
import com.micro.orderservice.external.request.PaymentRequest;
import com.micro.orderservice.external.response.PaymentResponse;
import com.micro.orderservice.external.response.ProductResponse;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2

public class OrderServiceImpl implements OrderService{
    
    @Autowired
    private OrderRepository orderRepository ;

    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RestTemplate restTemplate ;

    @Override
    public OrderResponse getOrderDetails(long orderId) {
        // 
        log.info("Get Order Details for Order id");

        Order order = orderRepository.findById(orderId).orElseThrow(()-> new RuntimeException("order not found exception"));


        //

        log.info("Invoking product service to fetch the product for id :{}",order.getProductId());
        ProductResponse productResponse = restTemplate.getForObject("http://PRODUCT-SERVICE/product/"+ order.getProductId(),ProductResponse.class);

        log.info("Getting payment information for the payment service || order id se payment ki transaction details ko call krna hai ");

        PaymentResponse paymentResponse = restTemplate.getForObject("http://PAYMENT-SERVICE/payment/order/"+ order.getId(), PaymentResponse.class);

        OrderResponse.PaymentDetails paymentDetails = OrderResponse.PaymentDetails.builder()
        .paymentId(paymentResponse.getPaymentId())
        .paymentDate(paymentResponse.getPaymentDate())
        .amount(paymentResponse.getAmount())
        .paymentMode(paymentResponse.getPaymentMode())
        .orderId(paymentResponse.getOrderId())
        .build();

        OrderResponse.ProductDetails productDetails = OrderResponse.ProductDetails.builder()
        .productName(productResponse.getProductName())
        .productId(productResponse.getProductId())
        .quantity(productResponse.getQuantity())
        .price(productResponse.getPrice())
        .build();

        OrderResponse orderResponse = OrderResponse.builder()
        .orderid(order.getId())
        .orderStatus(order.getOrderStatus())
        .amount(order.getAmount())
        .orderDate(order.getOrderDate())
        .productDetails(productDetails)
        .paymentDetails(paymentDetails)
        .build();
        return orderResponse;
    }

    @Override
    public long placeOrder(OrderRequest orderRequest) {
        
        // order entity -> save the data with status order created 
        // product service -> block products (Reduce the Quantity)
        // Payment Service -> Payments -> Success > Complete , Else Cancelled

        log.info("Placing order reqest :{}",orderRequest);
//feign client call start reduce quantity
        productService.reduceQuantity(orderRequest.getProductid(), orderRequest.getQuantity());
        log.info("Creating Order with status CREATED");

//end


        Order order = Order.builder()
        .amount(orderRequest.getTotalAmount())
        .orderStatus("CREATED")
        .productId(orderRequest.getProductid())
        .quantity(orderRequest.getQuantity())
        .orderDate(Instant.now())
        .build() ;

        order = orderRepository.save(order);

        //feign client call start payment complete
        
            log.info("Calling payment service to complete the payment");

            PaymentRequest paymentRequest = PaymentRequest.builder()
            .orderId(order.getId())
            .paymentMode(orderRequest.getPaymentMode())
            .amount(orderRequest.getTotalAmount())
            .build();

            String orderStatus = null;
            try {
                paymentService.doPayment(paymentRequest);
                log.info("Payment Done Successfully . Changing order status to placed ");
                orderStatus = "PLACED";
            } catch (Exception e) {
                // TODO: handle exception

                log.error("Error occured in payment. Changing order status to Payment Failed");
                orderStatus = "PAYMENT_FAILED";
            }

            order.setOrderStatus(orderStatus);
            orderRepository.save(order);
        //

        log.info("Order places successfully with order Id : {}", order.getId());
        return order.getId();
    }
    
}
