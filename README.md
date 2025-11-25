# Tetris Game - COMP2042 Coursework

## Table of Contents

1. [GitHub Repository](#github-repository)
2. [Compilation Instructions](#compilation-instructions)
3. [Implemented and Working Features](#implemented-and-working-features)
4. [Implemented but Malfunctioning Features](#implemented-but-malfunctioning-features)
5. [Unimplemented Features](#unimplemented-features)
6. [New Java Classes](#new-java-classes)
7. [Modified Java Classes](#modified-java-classes)
8. [Unexpected Issues](#unexpected-issues)

---

## GitHub Repository

https://github.com/yllnaf/CW2025

## Compilation Instructions

### Environment Requirements

- **Java Development Kit (JDK)**: Version 23 or higher
- **IntelliJ IDEA**: Version 2023.1 or higher (latest version recommended)
- **Maven**: Version 3.6+ 

### Step-by-Step Compilation Guide

#### Step 1: Import Project into IntelliJ IDEA

1. Launch IntelliJ IDEA and select **File** → **Open** (or **Open or Import**)
2. Browse and select the project root directory (the `CW2025` folder containing `pom.xml`)
3. Click **OK** to import the project
4. IDEA will automatically detect the Maven project and prompt to import it. Click **Import Maven Project** or **Trust Project**

#### Step 2: Configure JDK

1. Open **File** → **Project Structure**
2. Select **Project** from the left sidebar
3. Choose JDK 23 from the **Project SDK** dropdown menu
4. Ensure **Project language level** is set to **23**
5. Select **Modules** from the left sidebar and confirm the module's Language level is also 23
6. Click **OK** to save settings

#### Step 3: Configure Maven

1. Open **File** → **Settings**
2. Navigate to **Build, Execution, Deployment** → **Build Tools** → **Maven**
3. Verify Maven settings:
   - **Maven home path**: Use IDEA's built-in Maven or specify system Maven path
   - **User settings file**: Use default or custom settings.xml
   - **Local repository**: Use default or custom local repository path
4. Click **OK** to save settings

#### Step 4: Download Dependencies

1. Locate the **Maven** tool window in IDEA's right sidebar (if not visible, open via **View** → **Tool Windows** → **Maven**)
2. Expand the project name and locate **Lifecycle**
3. Double-click **validate** or **compile** to automatically download all Maven dependencies
4. Wait for dependencies to download (may take several minutes on first download)

#### Step 5: Compile Project

1. In the **Maven** tool window, expand **Lifecycle**
2. Double-click **clean** to remove previous compilation files
3. Double-click **compile** to compile the project
4. Check the bottom **Build** tool window to confirm successful compilation

#### Step 6: Run Application

1. In the **Maven** tool window, expand **Plugins** → **javafx**
2. Double-click **javafx:run** to run the application

## Implemented and Working Features

### I. Original Core Features

#### 1. Basic Gameplay

The game implements complete Tetris core gameplay. Blocks automatically fall at regular intervals. Players can rotate blocks using the UP key or W key, move blocks horizontally using LEFT/RIGHT keys or A/D keys, and accelerate block descent using the DOWN key or S key. When a row is completely filled, the system automatically clears that row and calculates score based on the number of cleared rows. The game ends when blocks stack to the top of the game board and new blocks cannot be placed.

#### 2. Basic User Interface

The game provides a clear and intuitive user interface. The game board visually displays the board state in real-time, with the currently falling block highlighted. When the game ends, a game over panel appears at the center of the screen to notify the player. All operations support keyboard control, providing a smooth gaming experience.

### II. New Features

#### 1. UI Display Enhancements

**1.1 Real-time Score Display**

Implemented real-time score display functionality using JavaFX property binding for automatic updates. The score display area is located in the information panel on the right side of the game board, using a digital font and prominent yellow color to ensure players can clearly see score changes.

**1.2 Real-time Lines Cleared Statistics**

Added a lines cleared statistics display to the right of the score. The system uses JavaFX property binding mechanism, automatically updating the interface to show the total number of cleared lines when players successfully clear rows. The recording logic ensures accumulation only occurs after actual row clearing, guaranteeing data accuracy.

**1.3 Level Display**

Implemented a level system that automatically increases level based on cumulative cleared lines (one level per 5 lines cleared). Level information is displayed in real-time in the top-right information panel. As the level increases, the game automatically speeds up block descent, while setting a minimum speed limit to prevent the game from becoming unplayable due to excessive speed.

**1.4 High Score Display**

Added high score recording and display functionality. The system uses local properties files to persist high scores, ensuring historical high scores are retained across multiple game sessions. The top-right information panel displays the current high score in real-time. When players achieve a new record, a "NEW RECORD!" notification is displayed, enhancing the sense of achievement.

**1.5 Next Block Preview**

Implemented next block preview functionality to help players plan their moves in advance. The preview area is located below the score display, showing the next upcoming block in real-time. The preview automatically updates when blocks change, supports blocks of different sizes, and maintains consistent styling with the game board for a good visual experience.

**1.6 Welcome Information Panel**

The game displays a welcome information panel at the center of the game area upon startup, containing the game title and operation instructions to guide new users on how to use the NEW or LOAD buttons. The panel uses a semi-transparent dark background consistent with the overall game style. The panel automatically hides after the game starts, not interfering with normal gameplay.

#### 2. Game Control Features

**2.1 Pause/Resume Functionality**

Implemented pause/resume functionality, allowing players to pause or resume the game at any time by pressing the `P` key. When paused, the system automatically stops the game timeline and displays a prominent "PAUSED" indicator on screen, ensuring players are aware of the current game state. The system intelligently manages pause state, automatically clearing pause state when a new game starts or the game ends, preventing state residue.

**2.2 Game Exit Functionality**

Added an EXIT button in the right control area, providing a convenient way to exit the game. After clicking the button, the system safely stops the timeline and exits the application, ensuring proper resource release. The button's UI style is consistent with other controls, maintaining overall interface aesthetics.

#### 3. Data Persistence Features

**3.1 Game Save/Load Functionality**

Implemented complete game save and load functionality. Added a SAVE button in the right control area, allowing players to input a custom save name to save current game progress at any time. The system automatically validates save names and handles duplicate names, ensuring standardized save management. All save files are stored in the `.tetris_saves` folder in the user directory, supporting multiple game records. Upon game startup, a welcome information panel is displayed, allowing users to choose between starting a new game or loading an existing save via the NEW or LOAD buttons. After loading a save, the system can fully restore board state, current block, next block, score, level, and all other game states, ensuring players can seamlessly continue their previous game progress.

**3.2 Save Deletion Functionality**

Added save deletion functionality for convenient save management. Added a DELETE button in the right control area, allowing players to select saves to delete from a save list. To ensure operation safety, the system displays a confirmation warning dialog before deletion to prevent accidental deletion. After the deletion operation completes, the system immediately provides feedback on the operation result (success or failure), keeping players informed of the operation status.

#### 4. Control Panel Features

**4.1 Quick Action Buttons**

Added three quick action buttons to the control panel, enhancing game operation convenience. The NEW button allows players to restart the current round and clear all notifications with one click, quickly starting a new game. The LOAD button can pop up the save list at any time, allowing players to select and load existing saves. The PAUSE/START button dynamically displays text based on current game state, automatically showing "START" when paused and returning to "PAUSE" when resumed, maintaining complete synchronization with pause indicators and timeline state to ensure interface state consistency.

### III. Code Maintenance Work

#### 1. Code Refactoring

Comprehensive code refactoring was performed, significantly improving code quality. By creating the `GameConstants` class to centrally manage all configuration values, magic numbers were eliminated from the code, improving maintainability. Repeated code was extracted into independent methods, improving code organization. The Strategy pattern was applied to handle color mapping logic, separating color management from the GUI controller, improving code extensibility. Complete Javadoc documentation was added to all public classes and methods, enhancing code comprehensibility. Access control was improved, enhancing code encapsulation. Variable naming was optimized, logic structure was simplified, and overall code readability and maintainability were improved.

## Implemented but Malfunctioning Features

*None - All implemented features are working correctly.*

## Unimplemented Features

*None - All planned features for the current phase have been completed.*

## New Java Classes

### 1. `GameConstants` (Utility Class)

**Location**: `com.comp2042.util.GameConstants`

This class centrally manages all magic numbers and configuration values in the game, including board dimensions (width, height), initial block positions, display-related constants (block size, window dimensions), game loop intervals, score calculation constants, and color index constants. By eliminating magic numbers from the code, it significantly improves code readability and maintainability, facilitating unified configuration modifications.

### 2. `ColorMapper` (Utility Class)

**Location**: `com.comp2042.util.ColorMapper`

Implements color mapping functionality using the Strategy pattern, separating color logic from the GUI controller. This class provides a static method `getColor(int colorIndex)` that returns the corresponding Paint object based on color index, using Java 17 switch expressions to simplify code. This design follows the Single Responsibility Principle, centralizing color management and making it easy to extend, while improving code reusability.

### 3. `BrickFactory` (Factory Class)

**Location**: `com.comp2042.logic.bricks.BrickFactory`

Creates corresponding block instances based on string type names and provides a unified type naming method. This class provides the `createBrick(typeName)` method to create specific `Brick` instances, and the `getTypeName(Brick)` method to convert instances to type names for serialization. It internally registers all seven standard block types, ensuring accurate shape restoration when loading saves. This factory class eliminates hardcoded `new IBrick()` logic, making block serialization/deserialization more reliable.

### 4. `GameSaveData` (Serializable Entity)

**Location**: `com.comp2042.save.GameSaveData`

Used for serializing and saving the entire game state, including board, current block, next block queue, score, lines cleared, level, and other information. This class uses the Builder pattern to encapsulate save fields, avoiding reference sharing issues through deep copying of board matrices and queues. It also stores display names and file-safe names, balancing user experience and file system requirements. This design provides a single carrier to restore the complete game scenario, making it easy to extend new save fields.

### 5. `GameSaveManager` (Save Manager)

**Location**: `com.comp2042.save.GameSaveManager`

Responsible for save file operations including saving, loading, listing, and naming. This class serializes `GameSaveData` to `%USERPROFILE%/.tetris_saves/*.sav` files, reads saves and returns `Optional<GameSaveData>`. It can enumerate all saves and return a `GameSaveMetadata` list (sorted by time in descending order), while providing methods for generating default save names, validating duplicate names, and filename normalization. This manager abstracts file I/O logic, allowing controllers to persist progress by simply calling a unified interface.

### 6. `GameSaveMetadata` (Save Metadata)

**Location**: `com.comp2042.save.GameSaveMetadata`

Provides a lightweight view for save lists, containing display name, filename, and timestamp. This class encapsulates save identifiers and save time, formats time in the `toString()` method, and can be directly used for `ChoiceDialog` display. This design avoids loading complete board matrices when listing saves, significantly improving response speed.

### 7. `HighScoreStorage` (Utility Class)

**Location**: `com.comp2042.util.HighScoreStorage`

Responsible for persistent storage and loading of high scores. This class uses `Properties` files to persist high scores (default path: `%USERPROFILE%/.tetris_highscore.properties`), providing `loadHighScore()` and `saveHighScore()` static methods to unify read/write logic. When IO exceptions occur, it outputs error messages without interrupting the game. This class supports the high score recording feature, ensuring historical scores are retained across multiple game sessions.

## Modified Java Classes

### 1. `SimpleBoard`

**Location**: `com.comp2042.SimpleBoard`

Added Javadoc documentation for the class and constructors, improving code readability. Replaced hardcoded `4, 10` with `GameConstants.INITIAL_BRICK_X` and `INITIAL_BRICK_Y` in the `createNewBrick()` method for easier configuration management. Fixed the premature game over bug by improving the game over judgment logic in `createNewBrick()`, adding comments explaining game over conditions to ensure the game only ends when blocks truly stack to the top. Added `captureState()` and `restoreState()` methods for generating and restoring `GameSaveData`, synchronizing board matrix, current block position, rotation state, random queue, and `Score`, supporting complete game save functionality.

### 2. `MatrixOperations`

**Location**: `com.comp2042.MatrixOperations`

Added complete Javadoc documentation for all public methods, improving code comprehensibility. Improved method naming and logic, simplified boolean logic in `checkOutOfBound()`, and optimized variable naming in `copy()`, `merge()`, and `checkRemoving()` methods (e.g., myInt → copy, aMatrix → row, tmp → newMatrix, newRows → remainingRows) for better readability. Replaced hardcoded `50` with constant `GameConstants.BASE_SCORE_PER_LINE`, eliminating magic numbers. Added clearer comments explaining algorithm logic. Declared the class as `final` to prevent inheritance, and improved the constructor to use `AssertionError` to prevent instantiation, enhancing code quality and encapsulation.

### 3. `GuiController`

**Location**: `com.comp2042.GuiController`

Performed comprehensive code refactoring and feature expansion. Replaced all hardcoded values (BRICK_SIZE, -42, 400, etc.) with constants from `GameConstants`, unified color retrieval using `ColorMapper.getColor()`, and marked the old `getFillColor()` method as `@Deprecated`. Extracted methods such as `updateBrickPanelPosition()`, `handleGameKeyPress()`, and `showScoreNotification()` to eliminate code duplication. Improved boolean comparison methods, added Javadoc documentation for all public methods and important private methods, and added constant `displayStartRow = 2` to improve readability.

Implemented multiple new features: real-time score display by adding `scoreLabel` field and `bindScore()` method using JavaFX property binding for automatic score updates; next block preview by adding `nextBrickPanel` field and `nextBrickRectangles` array, implementing `initNextBrickDisplay()` and `refreshNextBrickDisplay()` methods, and changing `refreshBrick()` method to `public` for external calls; real-time lines display by adding `linesLabel` field and `bindLines()` method, adjusting FXML layout to `HBox` to display SCORE and LINES side by side; pause/resume control by listening to `KeyCode.P` in keyboard events, implementing `pauseGame()`/`resumeGame()` logic to control `Timeline`, and adding `pauseLabel` and `showPauseIndicator()` for state indication; level display and dynamic speed by adding `levelLabel` field and `bindLevel()` method, adding `updateGameSpeed()` to adjust automatic descent speed based on level; real-time high score display by adding `highScoreLabel` field and `bindHighScore()` method, adding BEST column in FXML HBox; game exit control by adding `exitGame(ActionEvent)` method using JavaFX `Platform.exit()` for safe exit; game save UI by implementing `saveGame()`, `showLoadSelectionDialog()`, `pauseForDialog()/resumeAfterDialog()`, and `onGameLoaded()` methods supporting complete save management; welcome information panel through `startupOverlay` VBox field and `showStartupOverlay()`/`hideStartupOverlay()` methods; control panel quick actions by adding `loadGame()`, `deleteSave()`, and `pauseButton` related methods, and adding FXCollections adapters for compatibility with older JDK versions. These modifications improved code structure, reduced duplication, and enhanced maintainability.

### 4. `GameController`

**Location**: `com.comp2042.GameController`

Changed the `board` field to `final` and initialized it in the constructor, replaced hardcoded board dimensions with `GameConstants.BOARD_HEIGHT` and `BOARD_WIDTH`, and improved constructor parameter naming (c → guiController). Added Javadoc documentation for classes and methods, and added logic explanation comments in `onDownEvent()`. Fixed score accumulation logic error by removing score accumulation logic when pressing the down key, ensuring score only accumulates after clearing rows. Called `refreshBrick()` in `createNewGame()` to update next block preview. Implemented lines statistics by binding `Score.linesProperty()` in the constructor and calling `addLines()` when rows are successfully cleared to keep the interface synchronized. Implemented level-driven speed adjustment by binding `Score.levelProperty()` and adding listeners, using `calculateIntervalForLevel()` to reduce automatic descent interval based on level with a lower limit, and calling `updateGameSpeed()` to link with the interface for real-time speed adjustment. Implemented high score binding by binding `Score.highScoreProperty()` and displaying it in real-time in the top-right corner, passing current score, high score, and whether it's a new record when the game ends to drive GameOver panel display. Implemented game save logic by adding `saveGame(String)`, `loadGame(String)`, and `listSavedGames()` methods, coordinating with `GameSaveManager`/`SimpleBoard` to save and restore state, refreshing board, current block, next block, and automatic descent speed after loading saves to ensure consistency with the saved state. These modifications improved code readability, enhanced encapsulation, and facilitated understanding of business logic.

### 5. `Main`

**Location**: `com.comp2042.Main`

Added class-level Javadoc containing author and version information, replaced hardcoded window dimensions with `GameConstants.GAME_WINDOW_WIDTH` and `GAME_WINDOW_HEIGHT`, improved variable naming (c → guiController), removed unused imports (ResourceBundle), and added comments for key steps. These modifications made the code more professional and improved maintainability.

### 6. `Score`

**Location**: `com.comp2042.Score`

Added complete Javadoc documentation for classes and methods, improved method parameter naming (i → points), and added `getValue()` method to get current score. Added `linesProperty()`, `addLines()`, and `getLinesCleared()` methods for recording and binding cleared lines, synchronizing score and lines reset in `reset()`. Added `levelProperty()`, `getLevel()`, and `updateLevel()` methods to automatically calculate level based on `LINES_PER_LEVEL`. Added `highScoreProperty()`, `getHighScore()`, and local persistence logic to support high score recording. Added `applyState()` method to restore score, lines cleared, level, and high score from saves, automatically handling high score markers. These modifications improved API completeness and code readability.

### 7. `BrickRotator`

**Location**: `com.comp2042.BrickRotator`

Added complete Javadoc documentation for classes and methods, simplified the implementation of `getNextShape()` method, and improved parameter naming (in setter: currentShape → shapeIndex). New functionality exposes `getCurrentBrick()` and `getCurrentShapeIndex()` methods to provide current block type and rotation state for save functionality. These modifications made the code clearer and the logic easier to understand.

### 8. `RandomBrickGenerator`

**Location**: `com.comp2042.logic.bricks.RandomBrickGenerator`

Added `exportQueuedBrickTypes()` and `importQueuedBrickTypes()` methods for exporting/importing queued block types when saving. Added private methods such as `ensureQueueSize()` and `randomBrick()` to ensure at least two candidate blocks remain after queue reconstruction. Introduced `BrickFactory` to ensure no block shapes are missed when restoring blocks from type names. These modifications ensure saves contain the actual "next block" and subsequent queue, avoiding different block sequences after loading compared to the original.

### 9. `InputEventListener`

**Location**: `com.comp2042.InputEventListener`

Added interface methods `saveGame(String)`, `loadGame(String)`, and `listSavedGames()`, enabling the GUI to communicate with the controller through a unified interface, avoiding direct dependency on persistence implementation. These modifications extend the input listener's capabilities, allowing the UI to trigger save/load operations while maintaining decoupling.

### 10. `gameLayout.fxml`

**Location**: `src/main/resources/gameLayout.fxml`

Added a SAVE button in the control button `VBox`, bound to `GuiController.saveGame`. Subsequently extended with five operation buttons: `NEW`, `LOAD`, `PAUSE/START`, `DELETE`, and `EXIT`, unified with `prefWidth=90` and left-aligned with the Score area. Adjusted the position of the NEXT preview and button area to ensure new buttons do not overlap with preview/statistics information. Added `startupOverlay` VBox to display welcome information at the center of the game area, containing game title and operation instructions. The welcome information panel uses a semi-transparent background, supports automatic text wrapping, and has a width limit of 180px to ensure it does not exceed the game area. These modifications provide users with an explicit save entry point, meeting the requirement of "being able to input a save name when saving."

### 11. Delete Functionality Related Class Updates

**Location**: `GuiController.java`, `GameController.java`, `InputEventListener.java`, `gameLayout.fxml`, `GameSaveManager.java`

Added a DELETE button in `gameLayout.fxml`, bound to `GuiController.deleteSave`. `GuiController.deleteSave()` calls `ChoiceDialog` to let users select a save and displays a confirmation dialog before deletion. `InputEventListener` and `GameController` added `deleteSave(String)` interface/implementation, calling `GameSaveManager.deleteSave()`. `GameSaveManager` added a `deleteSave()` helper method to uniformly delete disk files. These modifications allow users to directly manage saves within the game, eliminating the need for manual file system operations.

## Unexpected Issues

### Issue 1: Premature Game Over Bug

During testing, it was discovered that the game ended prematurely before blocks reached the top of the game board, ending when blocks stacked to the middle position. Analysis revealed that the initial block generation Y coordinate was set to 10 (middle position of the game board). When blocks stacked near Y=10, new blocks would collide with already stacked blocks at the initial position, causing premature game over. The solution was to change the initial Y coordinate to 0 (top of the game board) and improve the game over judgment logic to ensure the game only ends when blocks cannot be placed at the top. Modifications were made in `GameConstants.INITIAL_BRICK_Y` and the `SimpleBoard.createNewBrick()` method.

### Issue 2: Java Version Compatibility

The project initially required Java 23, but the system had Java 8 installed. To resolve this, JDK 23.0.2 was installed, the JAVA_HOME environment variable was set to point to JDK 23, and compilation was verified using `mvn clean compile`. Related configuration is in the Java version configuration section of `pom.xml`.

### Issue 3: Maven Javadoc Plugin Configuration

Javadoc generation requires correct plugin configuration. Added `maven-javadoc-plugin` configuration in `pom.xml`, configured source/target version, encoding, and document title, and successfully generated Javadoc documentation. Related configuration is in the build plugins section of `pom.xml`.

### Issue 4: Code Comment Language

Initial code comments were in Chinese, but the project required English. All Javadoc comments and inline comments were converted to English, ensuring all documentation follows English language standards. Modifications involved all Java source files.

### Issue 5: Score Accumulation Logic Error

After implementing the score display feature, testing revealed that pressing the down key would increase the score even without clearing rows, which violates game rules. Analysis found that in the `GameController.onDownEvent()` method, when a block could continue moving down, if the event source was the user (USER), it would accumulate `BASE_SCORE_PER_DOWN` points, causing the user to gain points every time the down key was pressed, which does not conform to Tetris game rules. The solution was to remove the score accumulation code in the `else` branch of the `onDownEvent()` method, ensuring score only accumulates after clearing rows (`clearRow.getLinesRemoved() > 0`). The modification was made in the `GameController.onDownEvent()` method. After the fix, score now only increases after correctly clearing rows, conforming to game rules.

### Issue 6: Score Display Position Overlap

During testing, it was discovered that the score display position was too far left and overlapped with the game area. The score was displayed at layoutX="200", overlapping with the game panel. Analysis revealed that the game panel was located at layoutX="40", with a width of approximately 10 blocks (20 pixels each) = 200 pixels, and the game area actually occupied 40-240 pixels in width. The score display at 200 pixels would overlap with the right side of the game area. The solution was to first adjust the score display position from `layoutX="200", layoutY="30"` to `layoutX="260", layoutY="10"`, and then, after adding lines and level displays, adjust the position again to `layoutX="280", layoutY="10"`, and gradually increase the window width from 300 to 420 to accommodate the complete right-side information panel. Modifications were made in `gameLayout.fxml` and `GameConstants.GAME_WINDOW_WIDTH`. After the fix, the score, lines, level, and next block panels are all located on the right side of the window, with coordinated layout and no clipping.
