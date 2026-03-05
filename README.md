Movie Management Microservices System

Projekt przedstawia architekturę systemu zarządzania bazą filmów i kategorii, opartą na niezależnych mikroserwisach komunikujących się przez API Gateway.
Architektura Systemu

System składa się z następujących komponentów:

    Frontend (Angular): Interfejs użytkownika serwowany przez serwer Nginx.

    API Gateway (Spring Cloud Gateway): Pojedynczy punkt wejścia (Port 8080), odpowiedzialny za routing żądań do odpowiednich serwisów.

    Category Service (Spring Boot): Obsługa logiki biznesowej dotyczącej gatunków i kategorii (Port 8081).

    Movie Service (Spring Boot): Obsługa bazy danych filmów (Port 8082).

    Persystencja: Dwie niezależne bazy danych PostgreSQL, co zapewnia izolację danych dla każdego mikroserwisu.
