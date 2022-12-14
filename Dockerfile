FROM eclipse-temurin:11-jdk-jammy

RUN ["chmod",  "+x", "mvnw"]

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN mkdir /opt/app

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]