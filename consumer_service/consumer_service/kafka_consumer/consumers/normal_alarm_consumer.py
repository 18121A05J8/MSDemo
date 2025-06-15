import logging

from kafka import KafkaConsumer

from kafka_consumer.consumers.abstract_alarm_consumer import ConsumerInterface
from utils.constants import Constants

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger("normal_alarm_consumer")

class NormalAlarmConsumer(ConsumerInterface):
    def __init(self):
        self.TOPIC = Constants.ALARM_NORMAL_TOPIC
        self.consumer_config = {
            Constants.BOOTSTRAP_SERVER_ID: Constants.BOOTSTRAP_SERVER_VALUE,  # Change to 'localhost:9092' if not in Docker
            Constants.KAFKA_GROUP_ID: Constants.ALARM_GROUP_ID_VALUE,         # Configurations need to be moved to a file
            Constants.KAFKA_OFFSET_RESET_ID: Constants.KAFKA_OFFSET_RESET_VALUE,
            "enable_auto_commit": True,  # Enable auto commit for offsets
        }

    def consume(self):
        """Kafka consumer loop"""
        consumer = None
        try:
            consumer = KafkaConsumer(
                self.TOPIC,
                **self.consumer_config
            )
            logger.info(f"Subscribed to topic: {self.TOPIC}")

            for message in consumer:
                logger.info(f"Received: {message.value.decode('utf-8')}")

        except Exception as e:
            logger.error(f"Exception: {e}")
        finally:
            if consumer:
                consumer.close()