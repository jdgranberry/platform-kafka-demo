package com.jdgranberry.platformkafkademo.model;

import java.io.Serializable;

public record AddressRecord(Long id, Address address)
        implements Serializable {
}
