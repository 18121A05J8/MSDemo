import uvicorn
from fastapi import FastAPI

from kafka_consumer.kafka_genesis import start_kafka_thread

app = FastAPI()

def main():
    print("Consumer service starting..")
    HOST = "0.0.0.0"
    PORT = 5000
    start_kafka_thread()
    uvicorn.run("main:app", host=HOST, port=PORT, reload=False)

if __name__ == "__main__":
    main()