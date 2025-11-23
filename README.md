# Tetris Game - COMP2042 Coursework

## GitHub Repository

[GitHub Repository Link](https://github.com/YOUR_USERNAME/CW2025)  
*Note: Please replace with your actual GitHub repository link*

## Compilation and Running Instructions

### Prerequisites

- **Java Development Kit (JDK)**: Version 23 or higher
- **Maven**: Version 3.6+ (or use the included Maven wrapper)
- **IDE**: IntelliJ IDEA, Eclipse, or any Java IDE (optional)

### Step-by-Step Guide

#### 1. Set JAVA_HOME Environment Variable

If not already set:

```bash
# Windows PowerShell
$env:JAVA_HOME = "D:\Program Files\Java\jdk-23.0.2"

# Linux/Mac
export JAVA_HOME=/path/to/jdk-23
```

#### 2. Navigate to Project Directory

```bash
cd CW2025
```

#### 3. Compile the Project

```bash
# Using Maven wrapper (Windows)
.\mvnw.cmd clean compile

# Using Maven wrapper (Linux/Mac)
./mvnw clean compile

# Or using system Maven
mvn clean compile
```

#### 4. Run the Application

```bash
# Using Maven wrapper (Windows)
.\mvnw.cmd javafx:run

# Using Maven wrapper (Linux/Mac)
./mvnw javafx:run

# Or using system Maven
mvn javafx:run
```

### Dependencies

All dependencies are managed by Maven and will be automatically downloaded:
- JavaFX Controls 21.0.6
- JavaFX FXML 21.0.6
- JUnit 5.12.1 (for testing)

### Special Settings

- The project uses Java 23 as the source and target version
- JavaFX modules are included via Maven dependencies
- No additional configuration required beyond setting JAVA_HOME

### Quick Start

For a quick start, simply run:

```bash
# Windows
.\mvnw.cmd clean javafx:run

# Linux/Mac
./mvnw clean javafx:run
```

This will compile and run the application in one command.

## Project Structure

```
CW2025/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/comp2042/
│       │       ├── util/          # Utility classes
│       │       ├── logic/         # Game logic
│       │       └── [Other classes]
│       └── resources/
│           ├── gameLayout.fxml
│           └── window_style.css
├── Javadoc/                       # Generated API documentation
├── pom.xml
└── README.md
```

## Controls

- **Arrow Keys / WASD**: Move and rotate blocks
  - LEFT/A: Move left
  - RIGHT/D: Move right
  - UP/W: Rotate block
  - DOWN/S: Accelerate fall
- **N**: Start new game

## Documentation

- **Javadoc**: See `Javadoc/index.html` for complete API documentation
- **Refactoring Report**: See `报告.md` for detailed refactoring documentation

