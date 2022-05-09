package com.jdgranberry.platformkafkademo.kafka;

import com.jdgranberry.platformkafkademo.model.Address;
import com.jdgranberry.platformkafkademo.model.CreateUserRecord;
import com.jdgranberry.platformkafkademo.model.User;
import com.jdgranberry.platformkafkademo.testUtil.TestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
public class FunctionalTests {

    @Autowired CreateUserProducer producer;
    @Autowired CreateUserConsumer consumer;

    @Test
    @DisplayName("When the producer writes a new CreateUserRecord, the consumer can process it")
    void producerCreatesRecord_consumerReceivesRecord() {
        User testUser = new User("testFirstName", "testLastName", "testEmail", "testPassword");
        Address testAddress =
                new Address("testAddress1", "testAddress2", "testCity", "testState", "testZip", "testCountry");


        producer.send(new CreateUserRecord(testUser, testAddress));
        ArrayList<String> expected =
                new ArrayList<>(Collections.singletonList("%s %s".formatted(testUser.firstName(), testUser.lastName())));

       /* A wait here is a code smell and adds uneccessary time to tests, but is used for lack of a better blocking
        * mechanism. In the past, I've created a utility that uses another Spring kafka consumer to ensure the records
        * successfully wrote to Kafka.
        */
        TestUtil.wait(1000);

        ArrayList<String> results = consumer.processTopicWithCountryFilter(testAddress.country());

        assertEquals(expected, results, "Arrays should be equal");
    }

    @Test
    @DisplayName("When the producer writes several new CreateUserRecord, the consumer can filter by country for specific users")
    void producerCreatesRecords_consumerFiltersRecordsByCountry() {
        User testUser1 = new User("testFirstName1", "testLastName1", "testEmail1", "testPassword1");
        User testUser2 = new User("testFirstName2", "testLastName2", "testEmail2", "testPassword2");
        User testUser3 = new User("testFirstName3", "testLastName3", "testEmail3", "testPassword3");
        Address testAddress1 =
                new Address("testAddress1", "testAddress1", "testCity1", "testState1", "testZip1", "testCountry1");
        Address testAddress2 =
                new Address("testAddress2", "testAddress2", "testCity2", "testState2", "testZip2", "testCountry2");
        Address testAddress3 =
                new Address("testAddress3", "testAddress3", "testCity3", "testState3", "testZip3", "testCountry3");

        producer.send(new CreateUserRecord(testUser1, testAddress1));
        producer.send(new CreateUserRecord(testUser2, testAddress2)); /* testFirstName2 testLastName2 should be in results */
        producer.send(new CreateUserRecord(testUser3, testAddress3));
        producer.send(new CreateUserRecord(testUser3, testAddress2)); /* testFirstName3 testLastName3 should be in results */

        TestUtil.wait(1000);

        ArrayList<String> expected =
                new ArrayList<>(Arrays.asList(
                        "%s %s".formatted(testUser2.firstName(), testUser2.lastName()),
                        "%s %s".formatted(testUser3.firstName(), testUser3.lastName())));

        ArrayList<String> results = consumer.processTopicWithCountryFilter(testAddress2.country());

        assertEquals(expected, results, "Arrays should be equal");
    }
}
