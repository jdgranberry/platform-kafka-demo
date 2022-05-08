package com.jdgranberry.platformkafkademo.model;

import java.io.Serializable;

public record CreateUserRecord(UserRecord userRecord, AddressRecord addressRecord) implements Serializable {
}
