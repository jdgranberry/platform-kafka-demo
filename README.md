Platform Kafka Demo
------
## Installation

## Usage
Running locally
*  `docker-compose up -d`
* If you encounter errors re-running docker-compose, run `rm -rf /tmp/kafka` to remove all local kafka data

## Design
* Publishes to and audits create-user Kafka topic. Presumably downstream services
consume this topic to store the user & address data.
  
## TODO
* Structured logging
* Metrics
* User Jackson ser/de