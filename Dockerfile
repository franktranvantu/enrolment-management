FROM openjdk:latest
ADD target/enrolment-management-0.0.1.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]