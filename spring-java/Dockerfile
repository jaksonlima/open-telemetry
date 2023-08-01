FROM eclipse-temurin:17-jdk-alpine as buildOne

WORKDIR /opt/app

COPY . ./

RUN ./gradlew clean bootJar

FROM eclipse-temurin:17-jdk-alpine as buildTwo

WORKDIR /opt/app

COPY --from=buildOne /opt/app/build/libs/application.jar ./application.jar
COPY ./opentelemetry-javaagent.jar ./opentelemetry-javaagent.jar

ENTRYPOINT ["java", "-javaagent:opentelemetry-javaagent.jar", "-jar", "application.jar"]
#ENTRYPOINT ["java", "-javaagent:opentelemetry-javaagent.jar", "-Dotel.service.name=spring-java-open-telemetry", "-Dotel.traces.exporter=zipkin", "-Dotel.exporter.zipkin.endpoint=http://zipkin:9411/api/v2/spans", "-jar", "application.jar"]