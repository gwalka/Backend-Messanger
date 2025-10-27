FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY gradlew .
COPY gradle ./gradle/
COPY secrets ./secrets/
COPY build.gradle.kts settings.gradle.kts ./

COPY src ./src/

RUN chmod +x ./gradlew

RUN ./gradlew bootJar


COPY build/libs/*.jar app.jar

CMD ["java", "-jar", "app.jar"]