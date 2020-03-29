FROM gradle:6.3.0-jdk13 AS BUILD
WORKDIR /build
COPY . .
RUN gradle bootJar
RUN cp infrastructure/build/libs/infrastructure-0.0.1-SNAPSHOT.jar app.jar

FROM openjdk:13-alpine
WORKDIR /app
COPY wait-for-it.sh .
COPY --from=BUILD /build/app.jar app.jar