package com.customerorder.controller;

import com.customerorder.entity.customer.Customers;
import com.customerorder.entity.order.Order;
import com.customerorder.producer.KafkaProducer;
import com.customerorder.repository.customer.CustomerRepository;
import com.customerorder.repository.order.OrderRepository;
import com.customerorder.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class CustomerController {
    @Autowired
    private ItemService itemService;
    @Autowired
    KafkaProducer kafkaProducer;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/customers")
    public ResponseEntity<String> saveCustomer(@RequestBody Customers customers) {
        log.info("customers: {}", customers);
        kafkaProducer.sendMessage("item-topic", customers);
        Customers response = customerRepository.save(customers);
        log.info("Customers response: {}", response);
        return ResponseEntity.ok("Customer created");
    }

    @PostMapping("/orders")
    public ResponseEntity<String> saveOrder(@RequestBody Order order) {
        log.info("order: {}", order);
        Order response = orderRepository.save(order);
        log.info("order response: {}", response);
        return ResponseEntity.ok("Order created");
    }
}
