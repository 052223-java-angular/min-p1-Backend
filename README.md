# Min He P1 - Full Stack Pokémon

## Introduction

View Pokémons and their info. Build your own Pokémon and assemble a team. Share your builds and have discussions with fellow trainers.
Pokémon app is an Angular and Spring Boot fullstack application. The application will be primarily be written using Java and TypeScript and will utilize a PostgreSQL database to store user information, dicussions and team recommendations. 

## User Stories

- **As a user**, I want to be able to view the Pokédex
- **As a user**, I want to be able to view information about any Pokémon.
- **As a user**, I want to be able to search Pokémon by various categories.
- **As a user**, I want to be able build my own team of Pokémons.
- **As a user**, I want to be able to discuss about Pokémons with other users.
- **As a user**, I want to be able to recommend and see recommended Pokémon/team builds.

## MVP (Minimum Viable Product)

- User registration and login.
- Browsing and searching for Pokémons.
- Build Pokémons.
- Build Pokémon teams.
- Modifying Pokémon builds.
- Modifying Pokémon teams.
- Recommend Pokémon/teams.
- Rate and discuss Pokémon/teams builds.

## Stretch Goals

- Implementing battle simulation
- Implement other parts of PokéAPI


## Feature
- Registration
    - Username:
      - 8-20 alphanumeric characters or underscores or dots but not starting or ending with an underscore or dot or having consecutive underscores or dots. 
      - must not be already in use.
    - Password:
      - at least 8 alphanumeric characters with at least one letter and one number.
      - salted and hashed.
- Login
  - Must use correct combination of username and password.
- Posts
  - create post
  - edit post
  - vote post
  - comments
    - add comment
    - edit comment
    - vote comment
- Profile
  - overview of the user:
    - comment counts
    - post counts
    - build counts
    - team counts
  - user signature
- Builds
    - Build from 1010 Pokémon:
        - 4 moves
        - 1 ability
        - 1 nature
    - Edit build
    - Delete build
- Teams
  - Assemble a team:
    - Up to 6 builds
    - pick from your builds
  - Edit team
  - Delete team
- Pokédex
  - 1010 Pokémons
  - Noramal sprite
  - Shiny sprite
  - click to view Pokémon info
- Pokémon info
  - names
  - abilities
  - moves
  - pokédex numbers
  - base stat
  - evolution family
  - forms

## Tech Stacks

- **Java/Spring**: The main programming language used for backend.
- **TypeScript/Angular**: The main programming language used for frontend.
- **PostgreSQL**: Used as the database to store user, product, and order data.
- **Node.js/NPM**:Used for managing project frontend dependencies.
- **Maven or Gradle**: Used for managing project backend dependencies.
- **JUnit**: A testing framework for Java applications, used to ensure our code works as expected.
- **Log4j**: A logging utility for debugging purposes.
- **JDBC (Java Database Connectivity)**: An API for connecting and executing queries on the database.
- **BCrypt**: A Java library for hashing and checking passwords for security.
- **JUnit, Mockito, and PowerMock**: Used for unit and integration testing.
- **Git and GitHub**: Used for version control.

## Requirements

- **Clean Codebase**: All code should be clean and well-documented. The repository should not include any unnecessary files or folders such as the `target/`, `.DS_Store`, etc. All files and directories should be appropriately named and organized.

- **Database Design**: The database should be designed following the principles of the 3rd Normal Form (3NF) to ensure data integrity and efficiency. An Entity Relationship Diagram (ERD) should be included in the documentation.

- **Secure**: All sensitive user data such as passwords must be securely hashed before storing it in the database. The application should not display any sensitive information in error messages.

- **Error Handling**: The application should handle potential errors gracefully and provide clear and helpful error messages to the users.

- **Testing**: The application should have a high test coverage. Unit tests and integration tests should be implemented using JUnit, Mockito, and PowerMock.

- **Version Control**: The application should be developed using a version control system, preferably Git, with regular commits denoting progress.

- **Documentation**: The repository should include a README file with clear instructions on how to run the application. Code should be well-commented to allow for easy understanding and maintenance.

- **Scalable**: The design of the application should be scalable, allowing for easy addition of new features or modifications in the future.
