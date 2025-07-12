import logging
import threading
from concurrent.futures import ThreadPoolExecutor

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

def kafka_consumers():
    """Kafka consumer loop"""
    consumer_list : list[ConsumerInterface] = [
    CriticalAlarmConsumer(),
    MajorAlarmConsumer(),
    MinorAlarmConsumer(),
    NormalAlarmConsumer()]

    with ThreadPoolExecutor(max_workers=len(consumer_list)) as executor:
        futures = [executor.submit(consumer.consume) for consumer in consumer_list]

def start_kafka_thread():
    logger.info("Starting Kafka consumers...")
    thread = threading.Thread(target=kafka_consumers, daemon=True)
    thread.start()

@app.get("/health")
def health_check():
    return {"status": "Kafka consumer running"}