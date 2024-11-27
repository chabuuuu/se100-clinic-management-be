FROM openjdk:21-jdk-slim AS build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME

# Install Maven
RUN apt-get update && apt-get install -y maven

# Build the Spring Boot app using Maven
RUN mvn clean
RUN mvn package -DskipTests=true

FROM openjdk:21-jdk-slim

ARG JAR_FILE=/usr/app/target/*.jar

VOLUME /tmp

COPY --from=build $JAR_FILE app.jar


#ADD $JAR_FILE app.jar
ENTRYPOINT exec java -jar /app.jar
EXPOSE 8080