# ============================
# Etapa 1: Build con Maven
# ============================
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copiar pom.xml y descargar dependencias (cache)
COPY pom.xml .
RUN mvn -q dependency:go-offline

# Copiar el código fuente
COPY src ./src

# Construir el JAR (perfil prod)
RUN mvn -q clean package -DskipTests -Pprod


# ============================
# Etapa 2: Imagen final ligera
# ============================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copiar el JAR desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Render asigna dinámicamente este puerto
ENV PORT=8080

# Hacer que Spring Boot lo use (muy importante)
ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
