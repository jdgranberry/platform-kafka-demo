package com.jdgranberry.platformkafkademo.kafka;

import com.jdgranberry.platformkafkademo.model.CreateUserRecord;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Component
public class CreateUserConsumer {
    Consumer<String, CreateUserRecord> consumer;

    CreateUserConsumer(@Autowired Consumer<String, CreateUserRecord> consumer) {
        this.consumer = consumer;
    }

    public ArrayList<String> consumeWithCountryFilter(String country) {
        ArrayList<String> users = new ArrayList<String>();

        consumer.subscribe(Set.of("create-user"), new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                consumer.seekToBeginning(partitions);
            }
        });

        consumer.seekToBeginning(consumer.assignment());

        /* We limit the polling to 30s. This works for small datasets as a process of the REST transaction, but needs
         * to be reworked for larger datasets.
         */
        ConsumerRecords<String, CreateUserRecord> records = consumer.poll(Duration.ofMillis(1_000));

        records.forEach(record -> {
            System.out.println(record.value());
            if (record.value().addressRecord().address().country().equals(country)) {
                users.add(record.value().userRecord().user().firstName() + " " + record.value().userRecord().user().lastName());
            }
        });

        return users;
    }
}