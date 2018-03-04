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

At this point you should be able to query the API with the URL `TODO`.

For example, the query: `TODO` should return a response:
```
TODO
```

## Running the tests

The code includes unit (TODO), integration (TODO), smoke and end to end (TODO) tests.

In order to execute all of them run:

```
mvnw test
```

For end to end tests only go with (TODO):
```
mvnw -Dtest=ClassName test
```
## API Reference

GET
 * GET /users/:username/wall
 * GET /users/:username/timeline
 
POST
 * POST /users/:username/tweet
 * POST /users/:username/follow?username=:to_follow_username

## Assumptions

TODO     

## Technology stack

* Java 8
* Spring boot
* Groovy
* Spock
* Cucumber
* Lombok
* TODO: More will come...


## Author
Nikodem Karbowy. Say hello @
nikodem.karbowy@wgoracejwodzie.company
