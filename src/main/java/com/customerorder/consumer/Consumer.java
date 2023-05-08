package com.customerorder.consumer;

import com.customerorder.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Component
@Slf4j
public class Consumer {

    @Autowired
    private ItemService itemService;
    @Value("${spring.kafka.bootstrap-servers}")
    String bootstrapServer;

    private boolean isPaused = false;

    public KafkaConsumer<String, Object> createConsumer(String topic) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServer);
        props.put("group.id", "group-id-json-1");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", StringDeserializer.class);
        props.put("value.deserializer", JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.customerorder.entity.customer");

        try(KafkaConsumer<String, Object> consumer = new KafkaConsumer<>(props)) {

            consumer.subscribe(Collections.singletonList(topic));
            log.info("isPaused: {}", isPaused);
            while (true) {
                if(!isPaused) {
                    ConsumerRecords<String, Object> consumerRecords = consumer.poll(Duration.ofMillis(100));
                    log.info("consumerRecords: {}", consumerRecords);
                    for (ConsumerRecord<String, Object> consumerRecord : consumerRecords) {
                        log.info("topicName is : {}, Consumed message: {}", consumerRecord.topic(), consumerRecord.value());
                        //itemService.addCustomer((Customers) consumerRecord.value());
                    }
                }
                break;
            }
        } catch (Exception e) {e.printStackTrace();}
        return null;
    }
    public void consumerPause() {
        isPaused = true;
        log.info("consumerPause status: {}", isPaused);
    }
    public void consumerResume() {
        isPaused = false;
        log.info("consumerResume status: {}", isPaused);
    }

}

