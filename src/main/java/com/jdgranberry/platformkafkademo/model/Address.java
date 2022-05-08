package com.jdgranberry.platformkafkademo.model;

import java.io.Serializable;

public record Address(String address1, String address2, String city, String state, String zip, String country)
        implements Serializable {
}
