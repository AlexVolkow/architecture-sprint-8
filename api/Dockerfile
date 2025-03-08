FROM bellsoft/liberica-openjdk-alpine:17.0.8

WORKDIR /app

COPY build/libs/sprint8-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8000

ENTRYPOINT ["java","-jar","app.jar"]