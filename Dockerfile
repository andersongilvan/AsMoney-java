FROM eclipse-temurin:17

LABEL maitainer="github.com/andersongilvan"

WORKDIR /app

COPY target/AsMoney-0.0.1-SNAPSHOT.jar /app/AsMoney.jar

ENTRYPOINT ["java", "-jar", "AsMoney.jar"]
