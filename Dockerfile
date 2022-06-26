From openjdk:8-jdk-alpine
copy ./target/bookstore-api-0.0.1-SNAPSHOT.jar bookstore-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","/bookstore-api-0.0.1-SNAPSHOT.jar"]