package com.jdgranberry.platformkafkademo.service;

import com.jdgranberry.platformkafkademo.kafka.CreateUserConsumer;
import com.jdgranberry.platformkafkademo.kafka.CreateUserProducer;
import com.jdgranberry.platformkafkademo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
        System.out.printf("Publish to topic %s with id <TODO>", producer.topic);

        /* TODO figure out id mechanism */
        CreateUserRecord record = new CreateUserRecord(
                new UserRecord(1L, createUserRequest.user()),
                new AddressRecord(1L, createUserRequest.address()));

        producer.send(record);

        return record;
    }

    public ArrayList<String> getUsersByCountry(String country) {
        return consumer.consumeWithCountryFilter(country);
    }
}
