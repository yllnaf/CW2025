# Tetris Game - COMP2042 Coursework

## GitHub Repository

[GitHub Repository Link](https://github.com/YOUR_USERNAME/CW2025)  
*Note: Please replace with your actual GitHub repository link*

## Compilation Instructions

### Prerequisites

- **Java Development Kit (JDK)**: Version 23 or higher
- **Maven**: Version 3.6+ (or use the included Maven wrapper)
- **IDE**: IntelliJ IDEA, Eclipse, or any Java IDE (optional)

### Step-by-Step Compilation Guide

1. **Set JAVA_HOME Environment Variable** (if not already set):
   ```bash
   # Windows PowerShell
   $env:JAVA_HOME = "D:\Program Files\Java\jdk-23.0.2"
   
   # Linux/Mac
   export JAVA_HOME=/path/to/jdk-23
   ```

2. **Navigate to Project Directory**:
   ```bash
   cd CW2025
   ```

3. **Compile the Project**:
   ```bash
   # Using Maven wrapper (Windows)
   .\mvnw.cmd clean compile
   
   # Using Maven wrapper (Linux/Mac)
   ./mvnw clean compile
   
   # Or using system Maven
   mvn clean compile
   ```

4. **Run the Application**:
   ```bash
   # Using Maven wrapper
   .\mvnw.cmd javafx:run
   
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

## Implemented and Working Features

### Core Game Features

1. **Basic Tetris Gameplay**
   - ✅ Block falling mechanics
   - ✅ Block rotation (UP key or W key)
   - ✅ Block movement (LEFT/RIGHT keys or A/D keys)
   - ✅ Block acceleration (DOWN key or S key)
   - ✅ Line clearing when rows are filled
   - ✅ Score calculation and display
   - ✅ Game over detection

2. **User Interface**
   - ✅ Game board visualization
   - ✅ Current block display
   - ✅ Next block preview
   - ✅ Score display
   - ✅ Game over panel
   - ✅ Keyboard controls

3. **Code Refactoring (Maintenance)**
   - ✅ Magic number elimination through constants
   - ✅ Code organization improvements
   - ✅ Design pattern implementation (Strategy pattern for color mapping)
   - ✅ Complete Javadoc documentation
   - ✅ Improved code encapsulation
   - ✅ Method extraction to reduce code duplication

## Implemented but Not Working Features

*None - All implemented features are working correctly.*

## Unimplemented Features

1. **Score Binding in GUI**
   - **Location**: `GuiController.bindScore()` method
   - **Reason**: The method is currently empty (marked with TODO). The score property binding functionality was not fully implemented, though the score system itself works correctly.

2. **Pause/Resume Functionality**
   - **Location**: `GuiController.pauseGame()` method
   - **Reason**: The pause game method exists but only requests focus. Full pause/resume functionality with timeline control was not implemented.

## New Java Classes

### 1. `GameConstants` (Utility Class)
- **Location**: `com.comp2042.util.GameConstants`
- **Purpose**: Centralizes all magic numbers and configuration values in the game
- **Key Features**:
  - Board dimensions (width, height)
  - Initial brick positions
  - Display-related constants (brick size, window dimensions)
  - Game loop interval
  - Score calculation constants
  - Color index constants
- **Benefits**: Improves code readability and maintainability by eliminating magic numbers

### 2. `ColorMapper` (Utility Class)
- **Location**: `com.comp2042.util.ColorMapper`
- **Purpose**: Implements Strategy pattern for color mapping, separating color logic from GUI controller
- **Key Features**:
  - Static method `getColor(int colorIndex)` that returns corresponding Paint objects
  - Uses Java 17 switch expressions for clean code
- **Benefits**: 
  - Follows Single Responsibility Principle
  - Centralized color management, easy to extend
  - Improves code reusability

## Modified Java Classes

### 1. `MatrixOperations`
- **Location**: `com.comp2042.MatrixOperations`
- **Modifications**:
  - Added complete Javadoc documentation for all public methods
  - Improved method naming and logic:
    - Simplified `checkOutOfBound()` boolean logic
    - Improved variable naming in `copy()` (myInt → copy, aMatrix → row)
    - Improved variable naming in `merge()` (copy → merged)
    - Improved variable naming in `checkRemoving()` (tmp → newMatrix, newRows → remainingRows)
  - Replaced magic number `50` with `GameConstants.BASE_SCORE_PER_LINE`
  - Added clearer comments explaining algorithm logic
  - Made class `final` to prevent inheritance
  - Improved constructor to prevent instantiation using `AssertionError`
- **Reason**: Improve code readability, eliminate magic numbers, and enhance code quality

### 2. `SimpleBoard`
- **Location**: `com.comp2042.SimpleBoard`
- **Modifications**:
  - Added Javadoc documentation for class and constructor
  - Replaced hardcoded values `4, 10` with `GameConstants.INITIAL_BRICK_X` and `INITIAL_BRICK_Y` in `createNewBrick()` method
- **Reason**: Improve code readability and facilitate configuration management

### 3. `GuiController`
- **Location**: `com.comp2042.GuiController`
- **Modifications**:
  - Replaced all hardcoded values (BRICK_SIZE, -42, 400, etc.) with constants from `GameConstants`
  - Unified all color retrieval to use `ColorMapper.getColor()`
  - Marked old `getFillColor()` method as `@Deprecated`
  - Extracted methods to eliminate code duplication:
    - `updateBrickPanelPosition()` - eliminates repeated position calculation code
    - `handleGameKeyPress()` - simplifies key handling logic
    - `showScoreNotification()` - improves code readability
  - Improved boolean comparisons (using `!isPause.getValue()` instead of `isPause.getValue() == Boolean.FALSE`)
  - Added Javadoc documentation for all public methods and important private methods
  - Added constant `displayStartRow = 2` to improve readability
- **Reason**: Improve code structure, reduce duplication, and enhance maintainability

### 4. `GameController`
- **Location**: `com.comp2042.GameController`
- **Modifications**:
  - Changed `board` field to `final`, initialized in constructor
  - Replaced hardcoded board dimensions with `GameConstants.BOARD_HEIGHT` and `BOARD_WIDTH`
  - Improved constructor parameter naming (c → guiController)
  - Added Javadoc documentation for class and all methods
  - Added logic explanation comments in `onDownEvent()`
  - Replaced hardcoded score value with `GameConstants.BASE_SCORE_PER_DOWN`
- **Reason**: Improve code readability, enhance encapsulation, and facilitate understanding of business logic

### 5. `Main`
- **Location**: `com.comp2042.Main`
- **Modifications**:
  - Added class-level Javadoc with author and version information
  - Replaced hardcoded window dimensions with `GameConstants.GAME_WINDOW_WIDTH` and `GAME_WINDOW_HEIGHT`
  - Improved variable naming (c → guiController)
  - Removed unused import (`ResourceBundle`)
  - Added comments for key steps
- **Reason**: Make code more professional and maintainable

### 6. `Score`
- **Location**: `com.comp2042.Score`
- **Modifications**:
  - Added complete Javadoc documentation for class and methods
  - Improved method parameter naming (i → points)
  - Added `getValue()` method to retrieve current score
- **Reason**: Improve API completeness and code readability

### 7. `BrickRotator`
- **Location**: `com.comp2042.BrickRotator`
- **Modifications**:
  - Added complete Javadoc documentation for class and methods
  - Simplified `getNextShape()` method implementation
  - Improved parameter naming (currentShape → shapeIndex in setter)
- **Reason**: Make code clearer and logic easier to understand

## Core Modifications Summary

### System Maintenance and Extension

#### 1. Code Refactoring (Maintenance)

**Location**: Multiple files across the codebase

**Modifications**:
- **Magic Number Elimination**: Created `GameConstants` class to centralize all configuration values
  - Affected files: `SimpleBoard.java`, `GameController.java`, `GuiController.java`, `Main.java`, `MatrixOperations.java`
  - Reason: Improve code readability and maintainability

- **Design Pattern Application**: Implemented Strategy pattern for color mapping
  - Created `ColorMapper` class
  - Affected files: `GuiController.java`
  - Reason: Separate concerns, improve code organization, and enable easy extension

- **Code Organization**: Extracted methods to reduce duplication
  - `GuiController.updateBrickPanelPosition()`
  - `GuiController.handleGameKeyPress()`
  - `GuiController.showScoreNotification()`
  - Reason: Follow DRY principle and improve maintainability

- **Documentation**: Added complete Javadoc documentation
  - All public classes and main methods now have comprehensive documentation
  - Reason: Improve code understandability and maintainability

- **Code Quality Improvements**:
  - Improved variable and parameter naming
  - Enhanced encapsulation (final classes, private constructors)
  - Simplified boolean logic
  - Reason: Follow best practices and improve code quality

## Unexpected Issues and Solutions

### Issue 1: Java Version Compatibility
- **Problem**: The project initially required Java 23, but the system had Java 8 installed
- **Solution**: 
  - Installed JDK 23.0.2
  - Set JAVA_HOME environment variable to point to JDK 23
  - Verified compilation with `mvn clean compile`
- **Location**: `pom.xml` (Java version configuration)

### Issue 2: Maven Javadoc Plugin Configuration
- **Problem**: Javadoc generation required proper plugin configuration
- **Solution**: 
  - Added `maven-javadoc-plugin` configuration to `pom.xml`
  - Configured source/target version, encoding, and documentation title
  - Successfully generated Javadoc documentation
- **Location**: `pom.xml` (build plugins section)

### Issue 3: Code Comments Language
- **Problem**: Initial code comments were in Chinese, but project requirements specified English
- **Solution**: 
  - Converted all Javadoc comments and inline comments to English
  - Ensured all documentation follows English language standards
- **Location**: All Java source files

## Project Structure

```
CW2025/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── comp2042/
│       │           ├── util/          # New utility classes
│       │           │   ├── GameConstants.java
│       │           │   └── ColorMapper.java
│       │           ├── logic/
│       │           │   └── bricks/    # Brick classes
│       │           └── [Other game classes]
│       └── resources/
│           ├── gameLayout.fxml
│           ├── window_style.css
│           └── [Other resources]
├── Javadoc/                           # Generated API documentation
├── pom.xml
├── README.md                          # This file
└── [Other project files]
```

## Design Patterns Applied

### Strategy Pattern
- **Implementation**: `ColorMapper` class
- **Purpose**: Separate color mapping logic from GUI controller
- **Benefits**: 
  - Single Responsibility Principle
  - Easy to extend with new color mapping strategies
  - Improved code reusability

## Design Principles Followed

1. **Single Responsibility Principle (SRP)**: 
   - `ColorMapper` handles only color mapping
   - `GameConstants` handles only constant management

2. **Open-Closed Principle (OCP)**: 
   - Color mapping can be extended without modifying existing code

3. **DRY Principle (Don't Repeat Yourself)**: 
   - Extracted repeated code into methods (e.g., `updateBrickPanelPosition()`)

4. **Encapsulation**: 
   - Utility classes use private constructors to prevent instantiation
   - Improved field access control

## Testing

The refactored code maintains all original functionality:
- ✅ All game features work correctly
- ✅ Code compiles without errors
- ✅ Application runs successfully
- ✅ No regressions introduced

## Future Improvements

1. **Package Restructuring**: Consider reorganizing packages following MVC pattern
2. **Unit Testing**: Add JUnit tests for critical classes
3. **Further Refactoring**: Consider splitting `SimpleBoard` to separate game state management and brick management
4. **Exception Handling**: Add more comprehensive exception handling mechanisms
5. **Feature Implementation**: Complete score binding and pause/resume functionality

## Conclusion

This coursework focused on code maintenance and refactoring, significantly improving code quality:
- ✅ Eliminated all magic numbers
- ✅ Added complete Javadoc documentation
- ✅ Applied design patterns (Strategy pattern)
- ✅ Improved code organization and naming
- ✅ Enhanced code maintainability and readability

All refactoring work maintains the integrity of original functionality, and the code compiles and runs successfully.

