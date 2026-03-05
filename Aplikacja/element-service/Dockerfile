# Etap 1: Budowanie (Maven)
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Kopiujemy plik pom.xml i pobieramy zależności (cache'owanie)
COPY pom.xml .
RUN mvn dependency:go-offline

# Kopiujemy kod źródłowy i budujemy paczkę .jar
COPY src ./src
RUN mvn clean package -DskipTests

# Etap 2: Uruchamianie (JRE)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Kopiujemy zbudowany plik jar z poprzedniego etapu
COPY --from=build /app/target/*.jar app.jar

# Deklarujemy port (zmienimy go potem dynamicznie w Docker Compose)
EXPOSE 8080

# Uruchamiamy aplikację z obsługą zmiennych środowiskowych
ENTRYPOINT ["java", "-jar", "app.jar"]