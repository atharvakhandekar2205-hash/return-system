FROM openjdk:17

WORKDIR /app
COPY . .

RUN chmod +x mvnw
RUN ./mvnw clean install -DskipTests

CMD ["java", "-jar", "target/return-system-0.0.1-SNAPSHOT.jar"]