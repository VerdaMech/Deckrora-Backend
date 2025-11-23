# Etapa 1: construir el JAR con Maven
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copiamos pom.xml y resolvemos dependencias (para aprovechar cache)
COPY pom.xml .
RUN mvn -q dependency:go-offline

# Copiamos el código fuente
COPY src ./src

# Compilamos el proyecto y generamos el JAR
RUN mvn -q clean package -DskipTests

# Etapa 2: imagen liviana solo con el JAR
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copiamos el JAR desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Puerto interno donde corre Spring Boot
EXPOSE 8080

# Comando de arranque
ENTRYPOINT ["java", "-jar", "app.jar"]
