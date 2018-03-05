# Twitter 
>a Twitter alternative that does the same and less

Twitter is a simple social networking application, similar to Twitter.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine.

### Prerequisites

You'll only need JDK 1.8 installed on your machine. If you review the code in Intellij and have no [Lombok plugin](https://plugins.jetbrains.com/plugin/6317) installed, you might want to get one.

### Installing

To launch the app run the following command from the project's home directory.

For Unix:
```
mvnw spring-boot:run
```

For Windows:

```
mvnw.cmd spring-boot:run
```

At this point you should be able to query the API. 

For example, the query to `localhost:8080/users/donald/tweet` with `Content-Type` header set to `application-json` and body:
```json
{
  "message": "Despite the constant negative press covfefe"
}
```
should get you 200 response code:
```
OK
```

## Running the tests

The code includes unit, integration, smoke and end to end tests.

In order to execute all of them run:

```
mvnw test
```

For end to end tests only go with:
```
mvnw -Dtest=ApplicationE2ETest test
```
## API Reference
Method      | Http request                                                   | Description
----------- | -------------------------------------------------------------- | -------------------------------------------------------------------------------------------
tweet       | POST /users/***user***/tweet                                   | Saves the tweet from the body (value of the *message* key) and registers ***user*** if necessary. 
follow      | POST /users/***user***/follow?followee_username=***followee*** | Starts following ***followee***. Both ***user*** and ***followee*** have to be already registered prior to *follow*
getWall     | GET /users/***user***/wall                                     | Returns the list of the tweets posted by the ***user*** in reverse chronological order. 
getTimeline | GET /users/***user***/timeline                                 | Returns the list of the tweets posted by users followed by the ***user*** in reverse chronological order.

## Assumptions/Documentation

System handles several edge cases. All of them are well documented under `src\test\resources\cucumber` folder. Please refer to that documentation for more details.    

## Technology stack

* Java 8
* Spring boot
* Groovy
* Spock
* Cucumber
* JSONAssert
* Lombok


## Author
Nikodem Karbowy 🤙 

Say hello @ nikodem.karbowy///a/t///wgoracejwodzie.company 
