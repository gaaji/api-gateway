FROM openjdk:17-alpine
VOLUME /tmp
COPY build/libs/api-gateway-0.0.1-SNAPSHOT.jar api.jar
EXPOSE 8000
ENTRYPOINT ["java","-jar", "api.jar"]