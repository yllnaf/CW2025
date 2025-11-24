package com.comp2042;

import com.comp2042.save.GameSaveData;
import com.comp2042.save.GameSaveManager;
import com.comp2042.save.GameSaveMetadata;
import com.comp2042.util.GameConstants;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Game controller class responsible for coordinating game logic and user interface.
 * Implements InputEventListener interface to handle user input events.
 */
public class GameController implements InputEventListener {

    private final SimpleBoard board;
    private final GuiController viewGuiController;

    /**
     * Constructor to initialize the game controller.
     * 
     * @param guiController GUI controller instance
     */
    public GameController(GuiController guiController) {
        this.viewGuiController = guiController;
        this.board = new SimpleBoard(GameConstants.BOARD_HEIGHT, GameConstants.BOARD_WIDTH);
        board.createNewBrick();
        viewGuiController.setEventListener(this);
        viewGuiController.initGameView(board.getBoardMatrix(), board.getViewData());
        viewGuiController.bindScore(board.getScore().scoreProperty());
        viewGuiController.bindLines(board.getScore().linesProperty());
        viewGuiController.bindLevel(board.getScore().levelProperty());
        viewGuiController.bindHighScore(board.getScore().highScoreProperty());
        board.getScore().levelProperty().addListener((observable, oldValue, newValue) ->
                viewGuiController.updateGameSpeed(calculateIntervalForLevel(newValue.intValue())));
        viewGuiController.updateGameSpeed(calculateIntervalForLevel(board.getScore().getLevel()));
        viewGuiController.promptForStartupChoice();
    }

    /**
     * Handles brick down movement event.
     * 
     * @param event movement event
     * @return down data containing cleared row information and view data
     */
    @Override
    public DownData onDownEvent(MoveEvent event) {
        boolean canMove = board.moveBrickDown();
        ClearRow clearRow = null;
        
        if (!canMove) {
            // Brick cannot move down further, merge to background and check for clearing
            board.mergeBrickToBackground();
            clearRow = board.clearRows();
            
            // Only add score when lines are actually cleared
            if (clearRow.getLinesRemoved() > 0) {
                board.getScore().add(clearRow.getScoreBonus());
                board.getScore().addLines(clearRow.getLinesRemoved());
            }
            
            // Create new brick, if failed then game over
            if (board.createNewBrick()) {
                Score currentScore = board.getScore();
                viewGuiController.gameOver(
                        currentScore.getValue(),
                        currentScore.getHighScore(),
                        currentScore.isNewHighScoreAchieved());
            }

            viewGuiController.refreshGameBackground(board.getBoardMatrix());
        }
        
        return new DownData(clearRow, board.getViewData());
    }

    /**
     * Handles brick left movement event.
     * 
     * @param event movement event
     * @return updated view data
     */
    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        board.moveBrickLeft();
        return board.getViewData();
    }

    /**
     * Handles brick right movement event.
     * 
     * @param event movement event
     * @return updated view data
     */
    @Override
    public ViewData onRightEvent(MoveEvent event) {
        board.moveBrickRight();
        return board.getViewData();
    }

    /**
     * Handles brick rotation event.
     * 
     * @param event movement event
     * @return updated view data
     */
    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateLeftBrick();
        return board.getViewData();
    }

    /**
     * Creates a new game.
     */
    @Override
    public void createNewGame() {
        board.newGame();
        viewGuiController.refreshGameBackground(board.getBoardMatrix());
        // Refresh current brick and next brick display
        viewGuiController.refreshBrick(board.getViewData());
    }

    @Override
    public boolean saveGame(String saveName) {
        String displayName = (saveName == null || saveName.isBlank())
                ? GameSaveManager.generateDefaultDisplayName()
                : saveName.trim();
        String fileSafeName = GameSaveManager.toFileSafeName(displayName);
        GameSaveData saveData = board.captureState(displayName, fileSafeName);
        try {
            GameSaveManager.saveGame(saveData);
            return true;
        } catch (IOException exception) {
            System.err.println("Failed to save game: " + exception.getMessage());
            return false;
        }
    }

    @Override
    public boolean loadGame(String fileSafeName) {
        try {
            Optional<GameSaveData> gameSaveData = GameSaveManager.loadGame(fileSafeName);
            if (gameSaveData.isEmpty()) {
                return false;
            }
            board.restoreState(gameSaveData.get());
            viewGuiController.refreshGameBackground(board.getBoardMatrix());
            viewGuiController.refreshBrick(board.getViewData());
            viewGuiController.updateGameSpeed(calculateIntervalForLevel(board.getScore().getLevel()));
            viewGuiController.onGameLoaded();
            return true;
        } catch (IOException | ClassNotFoundException exception) {
            System.err.println("Failed to load game: " + exception.getMessage());
            return false;
        }
    }

    @Override
    public List<GameSaveMetadata> listSavedGames() {
        return GameSaveManager.listSaves();
    }

    private int calculateIntervalForLevel(int level) {
        int interval = GameConstants.GAME_LOOP_INTERVAL_MS -
                (Math.max(0, level - 1) * GameConstants.GAME_LOOP_INTERVAL_DECREMENT_MS);
        return Math.max(interval, GameConstants.GAME_LOOP_MIN_INTERVAL_MS);
    }
}
