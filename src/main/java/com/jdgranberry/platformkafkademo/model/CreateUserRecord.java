package com.jdgranberry.platformkafkademo.model;

import java.io.Serializable;

public record CreateUserRecord(User user, Address address) implements Serializable {
}
