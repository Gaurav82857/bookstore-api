# bookstore-api
CURD operations on Books 
# Book Store API with Spring Boot, Java8

## Configure Spring Datasource, JPA, App properties
-------------------------------------------------------------------------------------
Open `src/main/resources/application.properties`

- spring.datasource.url=jdbc:h2:mem:bookStoreDB
- spring.datasource.username= admin
- spring.datasource.password= 

- spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
- spring.jpa.hibernate.ddl-auto= update


## Access details
-------------------------------------------------------------------------------------
To access REST apis : [http://localhost:8080/bookstore-api/]

This application is swagger enabled.
Shows the list of Endpoints in the current REST webservice using link : [http://localhost:8080/swagger-ui.html]

This application uses H2 (in-memory database).
To access the database of the applicaion using link : [http://localhost:8080/h2-console/]

# DB Credentials
db-url : jdbc:h2:mem:bookStoreDB

user : admin

password :


## Unit Test and Locally Run
-------------------------------------------------------------------------------------
```
Test programs : AssertServiceTest, BooksStoreServiceTest and CheckoutServiceTest attached in src.
To unit test all the apis.
```
```
To run application locally sample data available in application src.
```


## Run Application
-------------------------------------------------------------------------------------
```
mvn install
```
```
java -jar ./bookstore-api.jar
```
