# syntax=docker/dockerfile:1
FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /build
COPY pom.xml .
COPY prs-common/pom.xml prs-common/pom.xml
COPY prs-platform/pom.xml prs-platform/pom.xml
COPY prs-auth/pom.xml prs-auth/pom.xml
COPY prs-shadow/pom.xml prs-shadow/pom.xml
COPY prs-project/pom.xml prs-project/pom.xml
COPY prs-common/src prs-common/src
COPY prs-platform/src prs-platform/src
COPY prs-auth/src prs-auth/src
COPY prs-shadow/src prs-shadow/src
COPY prs-project/src prs-project/src
RUN mvn -q -B -DskipTests -pl prs-auth -am package
ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v2.30.0/opentelemetry-javaagent.jar /app/ot-agent.jar
FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /build/prs-auth/target/prs-auth-*.jar /app/app.jar
COPY --from=build /app/ot-agent.jar /app/ot-agent.jar
RUN chown -R 65532:65532 /app
USER 65532:65532
ENV OTEL_SERVICE_NAME=prs-auth \
    OTEL_TRACES_EXPORTER=none \
    OTEL_METRICS_EXPORTER=none \
    OTEL_LOGS_EXPORTER=none \
    JAVA_TOOL_OPTIONS="-javaagent:/app/ot-agent.jar"
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
