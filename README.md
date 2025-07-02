# Building Company

A Java project demonstrating object-oriented programming concepts with a focus on construction company operations.

## Project Structure

```
src/main/java/com/solvd/buildingcompany/
├── exceptions/         # Custom exceptions
├── interfaces/         # Interface definitions
├── models/             # Domain models
│   └── workers/        # Worker-related models
├── services/           # Business services
├── utils/              # Utility classes
└── Main.java           # Application entry point
```

## Features

- Construction project management
- Team composition and management
- Cost and timeline calculations
- Safety inspections
- Various building materials support
- Comprehensive exception handling

## Custom Exceptions

The project includes several custom exceptions:

- `InvalidMaterialException` - Thrown when an unsupported building material is specified
- `InsufficientTeamSizeException` - Thrown when a team does not have enough members for a project
- `SafetyCheckFailedException` - Thrown when a construction site fails safety checks
- `ProjectSizeTooLargeException` - Thrown when a project exceeds size limitations
- `BudgetExceededException` - Thrown when project costs exceed the available budget

## Build and Run

The project uses Maven for dependency management and building.

```shell
# Compile the project
mvn compile

# Run the application
mvn exec:java

# Package the application
mvn package
```

## Logging

The application uses Log4j2 for logging. Configuration can be found in `src/main/resources/log4j2.xml`.

Logs are written to both console and file (`logs/buildingcompany.log`).

## Requirements

- Java 21
- Maven 3.8+
