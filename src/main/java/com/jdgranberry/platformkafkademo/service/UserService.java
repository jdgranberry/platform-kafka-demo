package com.jdgranberry.platformkafkademo.service;

import com.jdgranberry.platformkafkademo.kafka.CreateUserProducer;
import com.jdgranberry.platformkafkademo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    CreateUserProducer producer;

    UserService(@Autowired CreateUserProducer producer) {
        this.producer = producer;
    }

    public CreateUserRecord createUser(CreateUserRequest createUserRequest) {
        System.out.println("Publish to User topic: " + createUserRequest);

        /* TODO figure out id mechanism */
        CreateUserRecord record = new CreateUserRecord(
                new UserRecord(1L, createUserRequest.user()),
                new AddressRecord(1L, createUserRequest.address()));

        producer.send(record);

        return record;
    }
}
