package com.comp2042;

import com.comp2042.util.ColorMapper;
import com.comp2042.util.GameConstants;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.effect.Reflection;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.net.URL;
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

    private Rectangle[][] displayMatrix;

    private InputEventListener eventListener;

    private Rectangle[][] rectangles;

    private Timeline timeLine;

    private final BooleanProperty isPause = new SimpleBooleanProperty();

    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        gamePanel.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (!isPause.getValue() && !isGameOver.getValue()) {
                    handleGameKeyPress(keyEvent);
                }
                if (keyEvent.getCode() == KeyCode.N) {
                    newGame(null);
                }
            }
        });
        gameOverPanel.setVisible(false);

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

        // Initialize game loop timeline
        timeLine = new Timeline(new KeyFrame(
                Duration.millis(GameConstants.GAME_LOOP_INTERVAL_MS),
                ae -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
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
    private void refreshBrick(ViewData brick) {
        if (!isPause.getValue()) {
            updateBrickPanelPosition(brick);
            for (int i = 0; i < brick.getBrickData().length; i++) {
                for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                    setRectangleData(brick.getBrickData()[i][j], rectangles[i][j]);
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
    public void gameOver() {
        timeLine.stop();
        gameOverPanel.setVisible(true);
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
        isGameOver.setValue(false);
    }

    /**
     * Pauses the game (currently not fully implemented).
     * 
     * @param actionEvent action event
     */
    public void pauseGame(ActionEvent actionEvent) {
        gamePanel.requestFocus();
        // TODO: Implement pause/resume functionality
    }
}
