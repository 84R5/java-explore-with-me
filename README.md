
## [Link to Pull Request for review Feature-Rating.](https://github.com/84R5/java-explore-with-me/pull/5)

# java-explore-with-me
Template repository for ExploreWithMe project.

## Tech stack:
- Java 11;
- Spring MVC;
- Spring Data, SQL, Hibernate;
- Maven(multi-module project), Lombok;
- Docker, Postman.

## Project Structure:
There are 2 microservices made as modules in project:

Ewm-service - contains everything necessary for operation:
- Viewing events without authorization;
- Ability to create and manage categories;
- Events and working with them - creating, moderation;
- User requests to participate in an event - request, confirmation, rejection.
- Creating and managing compilations.
- Adding and deleting rating of events(comment judgment), forming ratings. 
- Runs on port 8080.

Stats-server - stores the number of views and allows to make various selections for analyzing the application usage.
- Separate service for collecting statistics.
- Runs on port 9090.

## Swagger specification for REST API:
- [Ewm-service](ewm-main-service-spec.json)
- [Stats-server](ewm-stats-service-spec.json)

## Main Database description:
![SCHEMA](https://github.com/84R5/java-explore-with-me/assets/114769678/9b78ce9a-e022-45c4-a399-23302d77e090)

## Stat Database description
![stat](https://github.com/84R5/java-explore-with-me/assets/114769678/93e4a6fa-f586-49fa-9634-c4da0beae7a4)
