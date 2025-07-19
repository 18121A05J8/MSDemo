# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-slim AS build

# Install Maven
RUN apt-get update && apt-get install -y \
    curl \
    unzip \
    ca-certificates \
    && update-ca-certificates \
    && curl -fsSL --insecure https://downloads.apache.org/maven/maven-3/3.8.9/binaries/apache-maven-3.8.9-bin.tar.gz -o /tmp/apache-maven-3.8.9-bin.tar.gz \
    && tar -xvzf /tmp/apache-maven-3.8.9-bin.tar.gz -C /opt/ \
    && ln -s /opt/apache-maven-3.8.9/bin/mvn /usr/bin/mvn \
    && rm /tmp/apache-maven-3.8.9-bin.tar.gz

# Set the Maven environment variables
ENV MAVEN_HOME /opt/apache-maven-3.8.9
ENV PATH $MAVEN_HOME/bin:$PATH

COPY . /app

# Set the working directory
WORKDIR /app

RUN mvn clean package -DskipTests

FROM openjdk:11-slim
# Copy the built jar file into the container
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8081

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=dev"]
# you can change the profile as needed between dev or prod or you can remove overall to run the default profile