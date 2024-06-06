FROM openjdk:20 AS build
EXPOSE 8080:8080
RUN mkdir /app
COPY ./build/libs/*-all.jar /app/back-tussam.jar
ENTRYPOINT ["java","-jar","/app/back-tussam.jar"]