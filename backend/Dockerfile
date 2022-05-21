FROM openjdk:17-slim-bullseye
USER root

COPY . /opt/app/backend

RUN apt update && apt upgrade -y

WORKDIR /opt/app/backend
RUN ./mvnw package


CMD ["java", "-jar", "/opt/app/backend/target/crypto-tokens.jar"]