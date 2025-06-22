from typing import Final

class Constants:
    """
    This class contains constants used throughout the application.
    """
    BOOTSTRAP_SERVER_VALUE : Final[str] = "kafka:9092"
    BOOTSTRAP_SERVER_ID : Final[str] = "bootstrap_servers"
    KAFKA_GROUP_ID : Final[str] = "group_id"
    ALARM_GROUP_ID_VALUE : Final[str] = "alarm_group"
    KAFKA_OFFSET_RESET_ID : Final[str] = "auto_offset_reset"
    KAFKA_OFFSET_RESET_VALUE : Final[str] = "earliest"
    ALARM_CRITICAL_TOPIC : Final[str] = "com.reddy.alarm.critical"
    ALARM_MAJOR_TOPIC : Final[str] = "com.reddy.alarm.major"
    ALARM_MINOR_TOPIC : Final[str] = "com.reddy.alarm.minor"
    ALARM_NORMAL_TOPIC : Final[str] = "com.reddy.alarm.normal"