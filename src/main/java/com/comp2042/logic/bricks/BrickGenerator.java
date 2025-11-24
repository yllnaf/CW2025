package com.comp2042.logic.bricks;

/**
 * Interface for generating Tetris bricks.
 * Provides methods to get the current brick and preview the next brick.
 */
public interface BrickGenerator {

    /**
     * Gets the current brick and advances to the next one.
     *
     * @return current brick instance
     */
    Brick getBrick();

    /**
     * Gets the next brick without consuming it.
     *
     * @return next brick instance
     */
    Brick getNextBrick();
}
