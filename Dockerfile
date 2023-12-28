FROM maven:latest as stage1
WORKDIR /app
COPY pom.xml /app
RUN mvn dependency:resolve
COPY . /app
RUN mvn clean install

FROM openjdk:21 as final
WORKDIR /app
COPY --from=stage1 /app/target/Habr_Parser_Project-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]