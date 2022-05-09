package com.jdgranberry.platformkafkademo.service;

import com.jdgranberry.platformkafkademo.kafka.CreateUserConsumer;
import com.jdgranberry.platformkafkademo.kafka.CreateUserProducer;
import com.jdgranberry.platformkafkademo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    @Value(value = "${kafka.topic}")
    private String topic;
    CreateUserProducer producer;
    CreateUserConsumer consumer;

    UserService(@Autowired CreateUserProducer producer, @Autowired CreateUserConsumer consumer) {
        this.producer = producer;
        this.consumer = consumer;
    }

    public CreateUserRecord createUser(CreateUserRequest createUserRequest) {
        System.out.printf("Publishing new user to topic %s%n", producer.topic);
        CreateUserRecord record = new CreateUserRecord(createUserRequest.user(), createUserRequest.address());
        producer.send(record);
        return record;
    }

    public ArrayList<String> getUsersByCountry(String country) {
        return consumer.processTopicWithCountryFilter(country);
    }
}
