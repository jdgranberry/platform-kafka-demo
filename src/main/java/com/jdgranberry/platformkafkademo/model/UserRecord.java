package com.jdgranberry.platformkafkademo.model;

import java.io.Serializable;

public record UserRecord(Long id, User user) implements Serializable {
}
