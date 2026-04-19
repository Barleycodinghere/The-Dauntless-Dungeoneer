# The Dauntless Dungeoneer

A turn-based dungeon crawler built with libGDX, focused on strategic combat and a flexible entity-component architecture.

## Description

The Dauntless Dungeoneer is a Java-based game project developed using libGDX. It features a turn-based combat system where players choose a class and engage in strategic battles against enemies. The project is structured using a hybrid Entity Component System (ECS) architecture, allowing for modular and scalable game design. Entities act as containers, components define data such as stats, and higher-level classes like Player and Enemy provide structure and default behavior.

## Getting Started

### Dependencies

* Java 17 or higher
* Gradle (wrapper included in project)
* Compatible OS (Windows, macOS, or Linux)

### Installing

* Clone the repository:

```
git clone https://github.com/your-username/dauntless-dungeoneer.git
```

* Navigate into the project directory:

```
cd dauntless-dungeoneer
```

* No additional setup required since the Gradle wrapper is included.

### Executing program

* Run the desktop version using Gradle:

On macOS/Linux:

```
./gradlew lwjgl3:run
```

On Windows:

```
gradlew.bat lwjgl3:run
```

## Help

If Gradle dependencies fail to load or the project does not run:

```
./gradlew clean build
```

If using an IDE, ensure the project is imported as a Gradle project and dependencies are synced.

## Authors

Team Five XD

## Version History

* 0.1

  * Initial libGDX project setup
  * Refactored codebase into Hybrid ECS-style architecture
