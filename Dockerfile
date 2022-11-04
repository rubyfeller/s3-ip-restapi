FROM openjdk:16

EXPOSE 8080

WORKDIR /applications

COPY target/docker-restapi-0.0.1.jar /applications/restapi.jar

ENTRYPOINT ["java","-jar", "sample-application.jar"]