# Building Company

A Java project demonstrating object-oriented programming concepts with a focus on construction company operations, collections, and generics.

## Project Structure

```
src/main/java/com/solvd/buildingcompany/
├── collections/        # Custom collection implementations
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
- Generic classes for type safety
- Custom LinkedList implementation
- Multiple collection types (List, Set, Map, Queue)
- Proper encapsulation with getters and setters

## Collections and Generics

The project demonstrates the use of various collection types and generic classes:

- `CustomLinkedList<T>` - A custom implementation of a linked list with basic operations
  - Contains a private inner `Node<T>` class with encapsulated data
  - Provides methods like add, get, remove, size, and isEmpty
  - Full encapsulation with proper getters and setters
- `MaterialInventory<T extends BuildingMaterial>` - Generic inventory management using various collections:
  - HashMap for material catalog
  - HashSet for suppliers
  - LinkedList for order queue
  - ArrayList for stock items
- `ProjectSchedule<T extends Project>` - Generic project scheduling using TreeMap
  - Manages projects with date-based scheduling
  - Provides methods to add, retrieve, and reschedule projects
- `ConstructionTeam<T extends AbstractConstructionTeamMember>` - Generic team management
  - Organizes team members by position and specialization

## Custom Exceptions

The project includes several custom exceptions:

- `InvalidMaterialException` - Thrown when an unsupported building material is specified
- `InsufficientTeamSizeException` - Thrown when a team does not have enough members for a project
- `SafetyCheckFailedException` - Thrown when a construction site fails safety checks
- `ProjectSizeTooLargeException` - Thrown when a project exceeds size limitations
- `BudgetExceededException` - Thrown when project costs exceed the available budget

## Encapsulation

All classes follow proper encapsulation principles:
- Private fields with public getters and setters
- Inner classes (like Node in CustomLinkedList) properly encapsulated
- Immutable data where appropriate
- Logger instances are private and static final

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
