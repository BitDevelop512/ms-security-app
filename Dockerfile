# Usa una imagen base con OpenJDK
FROM openjdk:21 as builder

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el archivo pom.xml y las dependencias de Maven (si usas Maven)
COPY . .

# Asegúrate de que el archivo mvnw sea ejecutable
RUN chmod +x ./mvnw

# Ejecutar Maven para construir el proyecto
RUN ./mvnw clean package -DskipTests || (echo "Maven build failed" && exit 1)

# Usar una imagen base más liviana para ejecutar la aplicación
FROM openjdk:21-jdk-slim

# Copiar el archivo JAR construido en la imagen anterior
COPY --from=builder /app/target/*.jar /app.jar

# Exponer el puerto en el que la aplicación escucha (ajusta si es necesario)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]
