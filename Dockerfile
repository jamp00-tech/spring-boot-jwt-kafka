FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/*.jar app.jar

COPY docker-compose.yaml /tmp/docker-compose.yaml

EXPOSE 8080

ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
