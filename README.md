# MSDemo
A Demo project for Micro services using docker, K8s, python, Java, Spring boot, FastApi and kafka

# Pending Implementation

*) Implement observer pattern to measure thresold of alarm comming in consumer and decorater to notifyy via mail/whatsapp.
*) spring functionalities
*) Values from property file and Autowired(Completed)
*) Implement Nagios
*) metrics implementation
*) postgres db

# steps to build

0) run kafka as docker container on port 9092
1) need to run mvn clean package in producer_service
2) docker build -t producer_service:1.0.0 .
3) docker run producer_service:1.0.0
4) cd to consumer_service
5) docker build -t consumer_service:1.0.0 .
6) docker run consumer_service

