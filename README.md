# Building Company
# Building Company

A Java project demonstrating object-oriented programming concepts.

## Project Structure

```
src/main/java/com/solvd/buildingcompany/
├── models/
│   ├── workers/
│   │   ├── AbstractWorker.java
│   │   ├── AbstractConstructionTeamMember.java
│   │   ├── Builder.java
│   │   ├── Electrician.java
│   │   └── Plumber.java
│   ├── BuildingMaterial.java
│   ├── ConstructionResult.java
│   ├── ConstructionSite.java
│   ├── ConstructionTeam.java
│   └── Project.java
├── interfaces/
│   ├── Buildable.java
│   ├── ConstructionWorker.java
│   ├── CostCalculatable.java
│   ├── Inspectable.java
│   └── Reportable.java
├── services/
│   └── ConstructionCalculator.java
└── Main.java
```

## Features
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
- Construction project management
- Team building with specialized workers
- Cost and time estimation
- Building materials handling
- Construction site inspection
- Logging with Log4j2

## Requirements

- Java 21
- Maven 3.8+

## Running the Project

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.solvd.buildingcompany.Main"
```
Это учебный проект для демонстрации концепций объектно-ориентированного программирования на Java.

## Структура проекта

Проект имеет следующую структуру пакетов:

- `com.solvd.buildingcompany` - корневой пакет
  - `models` - модели данных
    - `workers` - классы работников
  - `interfaces` - интерфейсы
  - `services` - сервисные классы

## Функциональность

Проект моделирует работу строительной компании, включая:
- Управление строительными проектами
- Формирование строительных команд из разных специалистов
- Расчет стоимости и времени выполнения строительных работ
- Учет строительных материалов
- Инспекцию строительных площадок

## Логирование

В проекте используется Log4j2 для логирования. Настройки логирования находятся в файле `src/main/resources/log4j2.xml`.

## Запуск проекта

Для запуска проекта используйте Maven:

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.solvd.buildingcompany.Main"
```

## Требования

- Java 21
- Maven 3.8+
