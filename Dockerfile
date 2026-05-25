FROM eclipse-temurin:17-jdk-alpine
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
WORKDIR /app
COPY --chown=appuser:appgroup target/soaventas-1.0.0.jar app.jar
EXPOSE 8085
USER appuser
ENTRYPOINT ["java", "-jar", "app.jar"]