package com.comp2042.logic.bricks;

import java.util.List;

/**
 * Interface representing a Tetris brick.
 * Provides access to the brick's shape matrix containing all rotation states.
 */
public interface Brick {

    /**
     * Gets the shape matrix containing all rotation states of the brick.
     *
     * @return list of two-dimensional arrays, each representing a rotation state
     */
    List<int[][]> getShapeMatrix();
}
