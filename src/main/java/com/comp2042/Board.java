package com.comp2042;

/**
 * Interface representing the game board functionality.
 * Defines core operations for brick movement, rotation, and game state management.
 */
public interface Board {

    /**
     * Moves the current brick down by one unit.
     *
     * @return true if the brick was moved successfully, false if blocked
     */
    boolean moveBrickDown();

    /**
     * Moves the current brick left by one unit.
     *
     * @return true if the brick was moved successfully, false if blocked
     */
    boolean moveBrickLeft();

    /**
     * Moves the current brick right by one unit.
     *
     * @return true if the brick was moved successfully, false if blocked
     */
    boolean moveBrickRight();

    /**
     * Rotates the current brick to the left (counter-clockwise).
     *
     * @return true if the rotation was successful, false if blocked
     */
    boolean rotateLeftBrick();

    /**
     * Creates a new brick at the top of the board.
     *
     * @return true if a new brick was created successfully, false if game over
     */
    boolean createNewBrick();

    /**
     * Gets the current game board matrix.
     *
     * @return two-dimensional array representing the board state
     */
    int[][] getBoardMatrix();

    /**
     * Gets the current view data including brick position and shape.
     *
     * @return view data object
     */
    ViewData getViewData();

    /**
     * Merges the current falling brick into the background board.
     */
    void mergeBrickToBackground();

    /**
     * Clears completed rows from the board.
     *
     * @return clear row information including number of rows cleared and score bonus
     */
    ClearRow clearRows();

    /**
     * Gets the score management object.
     *
     * @return score object
     */
    Score getScore();

    /**
     * Resets the game to initial state.
     */
    void newGame();
}
