# Usa una imagen oficial de JDK para construir y ejecutar
FROM eclipse-temurin:17-jdk-alpine

# Crea un directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia para posteriormente construir el proyecto con Maven
COPY . .

# Construye el proyecto con Maven en modo producción (sin tests)
RUN ./mvnw clean package -DskipTests

# Expone el puerto por defecto de Spring Boot
EXPOSE 8080

# Ejecuta el JAR generado
CMD ["java", "-jar", "target/taller-0.0.1-SNAPSHOT.jar"]