Platform Kafka Demo
------

## Usage

Running the API locally

* Requires Java 17
* `./gradlew build`
* `./gradlew bootRun`

Running the local Kafka services

* `docker-compose up -d`
* If you encounter errors re-running docker-compose, run `rm -rf /tmp/kafka` to remove all local kafka data and all
  Docker volumes associated with the containers

## Design

* Publishes to and audits create-user Kafka topic. Presumably a downstream service consumes this topic to peristently 
  store the user & address data tied by an id together.

## Endpoints

POST localhost:8080/v1/user Post body example:

```json
{
  "user": {
    "firstName": "Josh",
    "lastName": "Granberry",
    "email": "jdgranberry@gmail.com",
    "password": "password"
  },
  "address": {
    "address1": "Josh's House",
    "address2": "",
    "city": "Minneapolis",
    "state": "MN",
    "zip": "55123",
    "country": "USA"
  }
}
```

GET localhost:8080/v1/audit/users?country=<country>

An Insomnia configuration is included for interacting with the API.

## TODO

* Tests :)
* Error handling and proper status code responses
* Jackson potentially for object ser/de (conversion of CamelCase to/from snake_case)
* Structured logging & metrics
* Kafka Streams for audit endpoints would probably be the proper way to query the `create-user` topic

