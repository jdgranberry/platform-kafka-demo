package com.jdgranberry.platformkafkademo.kafka;

import com.jdgranberry.platformkafkademo.model.CreateUserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CreateUserProducer {
    private final KafkaTemplate<String, CreateUserRecord> producer;

    CreateUserProducer(@Autowired KafkaTemplate<String, CreateUserRecord> kafkaTemplate) {
        producer = kafkaTemplate;
    }

    public void send(CreateUserRecord record) {
        producer.send("create-user", record);
    }
}
