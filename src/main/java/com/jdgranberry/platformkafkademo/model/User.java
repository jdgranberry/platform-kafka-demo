package com.jdgranberry.platformkafkademo.model;

/* TODO non-nullable fields? */
public record User(String firstName, String lastName, String email,
                   String password) {
}
