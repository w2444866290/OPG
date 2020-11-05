# -- Dockerfile --

FROM openjdk:8-alpine

COPY ./* /app/

WORKDIR /app/
RUN javac ./opg.java
