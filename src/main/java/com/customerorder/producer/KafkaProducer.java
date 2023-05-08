package com.customerorder.producer;


import com.customerorder.consumer.Consumer;
import com.customerorder.entity.customer.Customers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducer {

    @Autowired
    Consumer consumer;


    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(String topic, Customers customers){
        log.info("Message sent -> {}", customers);
        kafkaTemplate.send(topic, customers);
        consumer.createConsumer(topic);
    }
}