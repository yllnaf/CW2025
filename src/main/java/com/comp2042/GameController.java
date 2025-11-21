package com.comp2042;

import com.comp2042.util.GameConstants;

/**
 * Game controller class responsible for coordinating game logic and user interface.
 * Implements InputEventListener interface to handle user input events.
 */
public class GameController implements InputEventListener {

    private final Board board;
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
            
            if (clearRow.getLinesRemoved() > 0) {
                board.getScore().add(clearRow.getScoreBonus());
            }
            
            // Create new brick, if failed then game over
            if (board.createNewBrick()) {
                viewGuiController.gameOver();
            }

            viewGuiController.refreshGameBackground(board.getBoardMatrix());
        } else {
            // Give score reward when user manually moves down
            if (event.getEventSource() == EventSource.USER) {
                board.getScore().add(GameConstants.BASE_SCORE_PER_DOWN);
            }
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
    }
}
