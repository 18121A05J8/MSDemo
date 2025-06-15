import logging
import threading

from kafka_consumer.consumers.abstract_alarm_consumer import ConsumerInterface
from kafka_consumer.consumers.critical_alarm_consumer import CriticalAlarmConsumer
from kafka_consumer.consumers.major_alarm_consumer import MajorAlarmConsumer
from kafka_consumer.consumers.minor_alarm_consumer import MinorAlarmConsumer
from kafka_consumer.consumers.normal_alarm_consumer import NormalAlarmConsumer
from utils.constants import Constants
from fastapi import FastAPI

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger("KafkaConsumer")

app = FastAPI()

# Kafka configuration
consumer_config = {
    Constants.BOOTSTRAP_SERVER_ID: Constants.BOOTSTRAP_SERVER_VALUE,  # Change to 'localhost:9092' if not in Docker
    Constants.KAFKA_GROUP_ID: Constants.ALARM_GROUP_ID_VALUE,         #Configurations need to moved to a file
    Constants.KAFKA_OFFSET_RESET_ID: Constants.KAFKA_OFFSET_RESET_VALUE
}

TOPIC = Constants.ALARM_CRITICAL_TOPIC

def kafka_consumers():
    """Kafka consumer loop"""
    consumer_list : list[ConsumerInterface] = [
    CriticalAlarmConsumer(),
    MajorAlarmConsumer(),
    MinorAlarmConsumer(),
    NormalAlarmConsumer()]

    try:
        for consumer in consumer_list:
            consumer.consume()
    except Exception as e:
        logger.error(f"Exception in Kafka consumer thread: {e}")


def start_kafka_thread():
    logger.info("Starting Kafka consumers...")
    thread = threading.Thread(target=kafka_consumers, daemon=True)
    thread.start()

@app.get("/health")
def health_check():
    return {"status": "Kafka consumer running"}