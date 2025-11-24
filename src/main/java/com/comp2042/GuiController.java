package com.comp2042;

import com.comp2042.save.GameSaveManager;
import com.comp2042.save.GameSaveMetadata;
import com.comp2042.util.ColorMapper;
import com.comp2042.util.GameConstants;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.Reflection;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * GUI controller class responsible for handling user interface interactions and game view updates.
 * Implements Initializable interface for FXML initialization.
 */
public class GuiController implements Initializable {

    @FXML
    private GridPane gamePanel;

    @FXML
    private Group groupNotification;

    @FXML
    private GridPane brickPanel;

    @FXML
    private GameOverPanel gameOverPanel;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label linesLabel;

    @FXML
    private Label levelLabel;

    @FXML
    private Label highScoreLabel;

    @FXML
    private GridPane nextBrickPanel;

    @FXML
    private Label pauseLabel;

    private Rectangle[][] displayMatrix;

    private InputEventListener eventListener;

    private Rectangle[][] rectangles;

    private Rectangle[][] nextBrickRectangles;

    private Timeline timeLine;

    private final BooleanProperty isPause = new SimpleBooleanProperty();

    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

    private boolean startupPromptDisplayed;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        gamePanel.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.P) {
                    pauseGame(null);
                    keyEvent.consume();
                    return;
                }
                if (!isPause.getValue() && !isGameOver.getValue()) {
                    handleGameKeyPress(keyEvent);
                }
                if (keyEvent.getCode() == KeyCode.N) {
                    newGame(null);
                }
            }
        });
        gameOverPanel.setVisible(false);
        showPauseIndicator(false);

        final Reflection reflection = new Reflection();
        reflection.setFraction(0.8);
        reflection.setTopOpacity(0.9);
        reflection.setTopOffset(-12);
    }

    /**
     * Initializes the game view.
     * 
     * @param boardMatrix game board matrix
     * @param brick current brick data
     */
    public void initGameView(int[][] boardMatrix, ViewData brick) {
        // Initialize game board display matrix
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        final int displayStartRow = 2; // Start displaying from row 2 (first 2 rows as buffer)
        for (int i = displayStartRow; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(GameConstants.BRICK_SIZE, GameConstants.BRICK_SIZE);
                rectangle.setFill(ColorMapper.getColor(GameConstants.COLOR_EMPTY));
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i - displayStartRow);
            }
        }

        // Initialize current brick display
        rectangles = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(GameConstants.BRICK_SIZE, GameConstants.BRICK_SIZE);
                rectangle.setFill(ColorMapper.getColor(brick.getBrickData()[i][j]));
                rectangles[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
            }
        }
        
        // Set brick panel position
        updateBrickPanelPosition(brick);

        // Initialize next brick preview display
        initNextBrickDisplay(brick);

        // Initialize game loop timeline
        timeLine = new Timeline(new KeyFrame(
                Duration.millis(GameConstants.GAME_LOOP_INTERVAL_MS),
                ae -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
    }

    /**
     * Displays a startup dialog that lets the player choose between a new game or loading a save.
     */
    public void promptForStartupChoice() {
        if (startupPromptDisplayed || eventListener == null) {
            return;
        }
        startupPromptDisplayed = true;
        Platform.runLater(() -> {
            boolean wasPaused = pauseForDialog();
            ChoiceDialog<String> dialog = new ChoiceDialog<>("New Game",
                    FXCollections.observableArrayList("New Game", "Load Save"));
            dialog.setTitle("Start Tetris");
            dialog.setHeaderText("Choose how you would like to begin");
            dialog.setContentText("Select an option:");
            Optional<String> choice = dialog.showAndWait();
            if (choice.isPresent() && "Load Save".equals(choice.get())) {
                boolean loaded = showLoadSelectionDialog();
                if (!loaded) {
                    eventListener.createNewGame();
                    onGameLoaded();
                }
            } else {
                eventListener.createNewGame();
                onGameLoaded();
            }
            resumeAfterDialog(wasPaused);
        });
    }
    
    /**
     * Updates the brick panel position.
     * 
     * @param brick brick data
     */
    private void updateBrickPanelPosition(ViewData brick) {
        brickPanel.setLayoutX(gamePanel.getLayoutX() + 
            brick.getxPosition() * brickPanel.getVgap() + 
            brick.getxPosition() * GameConstants.BRICK_SIZE);
        brickPanel.setLayoutY(GameConstants.DISPLAY_OFFSET_Y + 
            gamePanel.getLayoutY() + 
            brick.getyPosition() * brickPanel.getHgap() + 
            brick.getyPosition() * GameConstants.BRICK_SIZE);
    }

    /**
     * Gets fill color based on color index (deprecated, use ColorMapper instead).
     * 
     * @deprecated Use {@link ColorMapper#getColor(int)} instead
     * @param colorIndex color index
     * @return corresponding Paint object
     */
    @Deprecated
    private Paint getFillColor(int colorIndex) {
        return ColorMapper.getColor(colorIndex);
    }


    /**
     * Refreshes the current brick display.
     * 
     * @param brick brick data
     */
    public void refreshBrick(ViewData brick) {
        updateBrickPanelPosition(brick);
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                setRectangleData(brick.getBrickData()[i][j], rectangles[i][j]);
            }
        }
        // Update next brick preview
        refreshNextBrickDisplay(brick);
    }

    /**
     * Initializes the next brick preview display.
     * 
     * @param brick brick data containing next brick information
     */
    private void initNextBrickDisplay(ViewData brick) {
        // Clear existing children if any
        nextBrickPanel.getChildren().clear();
        
        int[][] nextBrickData = brick.getNextBrickData();
        if (nextBrickData != null && nextBrickData.length > 0) {
            nextBrickRectangles = new Rectangle[nextBrickData.length][nextBrickData[0].length];
            for (int i = 0; i < nextBrickData.length; i++) {
                for (int j = 0; j < nextBrickData[i].length; j++) {
                    Rectangle rectangle = new Rectangle(GameConstants.BRICK_SIZE, GameConstants.BRICK_SIZE);
                    setRectangleData(nextBrickData[i][j], rectangle);
                    nextBrickRectangles[i][j] = rectangle;
                    nextBrickPanel.add(rectangle, j, i);
                }
            }
        }
    }

    /**
     * Refreshes the next brick preview display.
     * If the next brick size has changed, reinitialize the display.
     * 
     * @param brick brick data containing next brick information
     */
    private void refreshNextBrickDisplay(ViewData brick) {
        int[][] nextBrickData = brick.getNextBrickData();
        if (nextBrickData != null && nextBrickData.length > 0) {
            // Check if size has changed, if so reinitialize
            if (nextBrickRectangles == null || 
                nextBrickRectangles.length != nextBrickData.length ||
                (nextBrickRectangles.length > 0 && nextBrickRectangles[0].length != nextBrickData[0].length)) {
                initNextBrickDisplay(brick);
            } else {
                // Update existing rectangles
                for (int i = 0; i < nextBrickData.length && i < nextBrickRectangles.length; i++) {
                    for (int j = 0; j < nextBrickData[i].length && j < nextBrickRectangles[i].length; j++) {
                        setRectangleData(nextBrickData[i][j], nextBrickRectangles[i][j]);
                    }
                }
            }
        }
    }

    /**
     * Refreshes the game background display.
     * 
     * @param board game board matrix
     */
    public void refreshGameBackground(int[][] board) {
        final int displayStartRow = 2;
        for (int i = displayStartRow; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
    }

    /**
     * Sets rectangle display data.
     * 
     * @param colorIndex color index
     * @param rectangle rectangle object
     */
    private void setRectangleData(int colorIndex, Rectangle rectangle) {
        rectangle.setFill(ColorMapper.getColor(colorIndex));
        rectangle.setArcHeight(GameConstants.RECTANGLE_ARC_HEIGHT);
        rectangle.setArcWidth(GameConstants.RECTANGLE_ARC_WIDTH);
    }

    /**
     * Handles brick down movement event.
     * 
     * @param event movement event
     */
    private void moveDown(MoveEvent event) {
        if (!isPause.getValue()) {
            DownData downData = eventListener.onDownEvent(event);
            if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
                showScoreNotification(downData.getClearRow().getScoreBonus());
            }
            refreshBrick(downData.getViewData());
        }
        gamePanel.requestFocus();
    }
    
    /**
     * Shows score notification.
     * 
     * @param scoreBonus score bonus
     */
    private void showScoreNotification(int scoreBonus) {
        NotificationPanel notificationPanel = new NotificationPanel("+" + scoreBonus);
        groupNotification.getChildren().add(notificationPanel);
        notificationPanel.showScore(groupNotification.getChildren());
    }

    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
    }

    /**
     * Binds score property to the score label for real-time display.
     * 
     * @param integerProperty score property
     */
    public void bindScore(IntegerProperty integerProperty) {
        if (scoreLabel != null && integerProperty != null) {
            // Bind the label text to the score property, updating automatically when score changes
            scoreLabel.textProperty().bind(integerProperty.asString());
        }
    }

    /**
     * Binds cleared lines property to the lines label for real-time display.
     *
     * @param integerProperty cleared lines property
     */
    public void bindLines(IntegerProperty integerProperty) {
        if (linesLabel != null && integerProperty != null) {
            linesLabel.textProperty().bind(integerProperty.asString());
        }
    }

    /**
     * Binds level property to the level label for real-time display.
     *
     * @param integerProperty level property
     */
    public void bindLevel(IntegerProperty integerProperty) {
        if (levelLabel != null && integerProperty != null) {
            levelLabel.textProperty().bind(integerProperty.asString());
        }
    }

    /**
     * Binds high score property to the high score label.
     *
     * @param integerProperty high score property
     */
    public void bindHighScore(IntegerProperty integerProperty) {
        if (highScoreLabel != null && integerProperty != null) {
            highScoreLabel.textProperty().bind(integerProperty.asString());
        }
    }

    /**
     * Updates the auto-drop speed by adjusting the timeline playback rate.
     *
     * @param intervalMillis desired drop interval in milliseconds
     */
    public void updateGameSpeed(int intervalMillis) {
        if (timeLine == null || intervalMillis <= 0) {
            return;
        }
        double rate = (double) GameConstants.GAME_LOOP_INTERVAL_MS / intervalMillis;
        timeLine.setRate(rate);
    }
    
    /**
     * Handles game key press events.
     * 
     * @param keyEvent key event
     */
    private void handleGameKeyPress(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        if (code == KeyCode.LEFT || code == KeyCode.A) {
            refreshBrick(eventListener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER)));
            keyEvent.consume();
        } else if (code == KeyCode.RIGHT || code == KeyCode.D) {
            refreshBrick(eventListener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER)));
            keyEvent.consume();
        } else if (code == KeyCode.UP || code == KeyCode.W) {
            refreshBrick(eventListener.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER)));
            keyEvent.consume();
        } else if (code == KeyCode.DOWN || code == KeyCode.S) {
            moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
            keyEvent.consume();
        }
    }

    /**
     * Handles game over.
     */
    public void gameOver(int finalScore, int highScore, boolean isNewRecord) {
        timeLine.stop();
        gameOverPanel.updateScores(finalScore, highScore, isNewRecord);
        gameOverPanel.setVisible(true);
        showPauseIndicator(false);
        isPause.setValue(false);
        isGameOver.setValue(true);
    }

    /**
     * Starts a new game.
     * 
     * @param actionEvent action event (can be null)
     */
    public void newGame(ActionEvent actionEvent) {
        timeLine.stop();
        gameOverPanel.setVisible(false);
        eventListener.createNewGame();
        gamePanel.requestFocus();
        timeLine.play();
        isPause.setValue(false);
        showPauseIndicator(false);
        isGameOver.setValue(false);
    }

    /**
     * Pauses the game (currently not fully implemented).
     * 
     * @param actionEvent action event
     */
    public void pauseGame(ActionEvent actionEvent) {
        if (timeLine == null) {
            return;
        }
        if (isPause.getValue()) {
            resumeGame();
            return;
        }
        if (isGameOver.getValue()) {
            return;
        }
        timeLine.pause();
        isPause.setValue(true);
        showPauseIndicator(true);
        gamePanel.requestFocus();
    }

    /**
     * Resumes the game if it is currently paused.
     */
    private void resumeGame() {
        if (timeLine == null) {
            return;
        }
        timeLine.play();
        isPause.setValue(false);
        showPauseIndicator(false);
        gamePanel.requestFocus();
    }

    /**
     * Exits the game and closes the application window.
     *
     * @param actionEvent action event
     */
    public void exitGame(ActionEvent actionEvent) {
        if (timeLine != null) {
            timeLine.stop();
        }
        Platform.exit();
    }

    /**
     * Saves the current game state after asking the user for a save name.
     *
     * @param actionEvent action event
     */
    @FXML
    public void saveGame(ActionEvent actionEvent) {
        if (eventListener == null) {
            return;
        }
        boolean wasPaused = pauseForDialog();
        TextInputDialog dialog = new TextInputDialog(GameSaveManager.generateDefaultDisplayName());
        dialog.setTitle("Save Game");
        dialog.setHeaderText("Enter a name for your save");
        dialog.setContentText("Save name:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            String trimmedName = name.trim();
            if (trimmedName.isEmpty()) {
                showErrorAlert("Invalid Name", "Save name cannot be empty.");
                return;
            }
            String fileSafeName = GameSaveManager.toFileSafeName(trimmedName);
            if (GameSaveManager.saveExists(fileSafeName)) {
                Alert overwriteAlert = new Alert(Alert.AlertType.CONFIRMATION,
                        "A save with this name already exists. Overwrite it?",
                        ButtonType.CANCEL,
                        ButtonType.OK);
                overwriteAlert.setTitle("Overwrite Existing Save");
                overwriteAlert.setHeaderText("Confirm overwrite");
                Optional<ButtonType> confirmation = overwriteAlert.showAndWait();
                if (!confirmation.isPresent() || confirmation.get() != ButtonType.OK) {
                    showInformationAlert("Save Cancelled", "Existing save was not overwritten.");
                    return;
                }
            }
            boolean success = eventListener.saveGame(trimmedName);
            if (success) {
                showInformationAlert("Game Saved", "Progress saved as \"" + trimmedName + "\".");
            } else {
                showErrorAlert("Save Failed", "An unexpected error prevented saving.");
            }
        });
        resumeAfterDialog(wasPaused);
        gamePanel.requestFocus();
    }

    /**
     * Deletes the selected save after user confirmation.
     *
     * @param actionEvent action event
     */
    @FXML
    public void deleteSave(ActionEvent actionEvent) {
        if (eventListener == null) {
            return;
        }
        boolean wasPaused = pauseForDialog();
        List<GameSaveMetadata> saves = eventListener.listSavedGames();
        if (saves.isEmpty()) {
            showInformationAlert("No Saves Found", "There are no saves to delete.");
            resumeAfterDialog(wasPaused);
            return;
        }

        ChoiceDialog<GameSaveMetadata> dialog = new ChoiceDialog<>(saves.get(0), saves);
        dialog.setTitle("Delete Save");
        dialog.setHeaderText("Select a save to delete");
        dialog.setContentText("Saved games:");
        Optional<GameSaveMetadata> selected = dialog.showAndWait();
        if (selected.isPresent()) {
            Alert confirm = new Alert(Alert.AlertType.WARNING,
                    "This action cannot be undone. Delete the selected save?",
                    ButtonType.CANCEL,
                    ButtonType.OK);
            confirm.setTitle("Confirm Delete");
            confirm.setHeaderText(selected.get().getDisplayName());
            Optional<ButtonType> response = confirm.showAndWait();
            if (response.isPresent() && response.get() == ButtonType.OK) {
                boolean deleted = eventListener.deleteSave(selected.get().getFileSafeName());
                if (deleted) {
                    showInformationAlert("Save Deleted", "Selected save has been deleted.");
                } else {
                    showErrorAlert("Delete Failed", "Unable to delete the selected save.");
                }
            }
        }
        resumeAfterDialog(wasPaused);
        gamePanel.requestFocus();
    }

    /**
     * Resets overlays and focus after a game has been loaded.
     */
    public void onGameLoaded() {
        gameOverPanel.setVisible(false);
        isGameOver.setValue(false);
        showPauseIndicator(false);
        gamePanel.requestFocus();
    }

    /**
     * Shows or hides the pause indicator label.
     *
     * @param visible true to show, false to hide
     */
    private void showPauseIndicator(boolean visible) {
        if (pauseLabel != null) {
            pauseLabel.setVisible(visible);
        }
    }

    private boolean showLoadSelectionDialog() {
        List<GameSaveMetadata> saves = eventListener.listSavedGames();
        if (saves.isEmpty()) {
            showInformationAlert("No Saves Found", "You have not created any saves yet.");
            return false;
        }
        ChoiceDialog<GameSaveMetadata> dialog = new ChoiceDialog<>(saves.get(0), saves);
        dialog.setTitle("Load Game");
        dialog.setHeaderText("Select a saved game to load");
        dialog.setContentText("Saved games:");
        Optional<GameSaveMetadata> selected = dialog.showAndWait();
        if (selected.isEmpty()) {
            return false;
        }
        boolean success = eventListener.loadGame(selected.get().getFileSafeName());
        if (!success) {
            showErrorAlert("Load Failed", "Unable to load the selected save.");
        }
        return success;
    }

    private boolean pauseForDialog() {
        boolean alreadyPaused = isPause.getValue();
        if (!alreadyPaused && timeLine != null) {
            timeLine.pause();
            isPause.setValue(true);
        }
        showPauseIndicator(false);
        return alreadyPaused;
    }

    private void resumeAfterDialog(boolean wasPaused) {
        if (!wasPaused && timeLine != null) {
            timeLine.play();
            isPause.setValue(false);
        }
    }

    private void showInformationAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
