import socket
import time

import uvicorn
from fastapi import FastAPI

from kafka_consumer.kafka_genesis import start_kafka_thread

app = FastAPI()

def wait_for_kafka(host: str, port: int, timeout: int = 60):
    """Wait for Kafka broker to be available."""
    start_time = time.time()
    while time.time() - start_time < timeout:
        try:
            with socket.create_connection((host, port), timeout=2):
                print(f"Kafka is available at {host}:{port}")
                return
        except (OSError, ConnectionRefusedError):
            print(f"Waiting for Kafka at {host}:{port}...")
            time.sleep(2)
    raise TimeoutError(f"Kafka not available after {timeout} seconds")

def main():
    print("Consumer service starting..")
    HOST = "0.0.0.0"
    PORT = 5000
    wait_for_kafka("kafka", 9092, 240)  # Adjust host and port as needed
    start_kafka_thread()
    uvicorn.run("main:app", host=HOST, port=PORT, reload=False)

if __name__ == "__main__":
    main()