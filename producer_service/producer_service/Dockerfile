# Use an official OpenJDK runtime as a parent image
FROM amazoncorretto:11

# Set the working directory
WORKDIR /app

# Copy the built jar file into the container
COPY target/*.jar app.jar

EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]

# need to run mvn clean package to build the jar file before running this Dockerfile