package com.comp2042;

/**
 * Data class containing view information for rendering the current game state.
 * Includes current brick data, position, and next brick preview.
 */
public final class ViewData {

    private final int[][] brickData;
    private final int xPosition;
    private final int yPosition;
    private final int[][] nextBrickData;

    /**
     * Constructor to create view data.
     *
     * @param brickData current brick shape matrix
     * @param xPosition current brick X position
     * @param yPosition current brick Y position
     * @param nextBrickData next brick shape matrix for preview
     */
    public ViewData(int[][] brickData, int xPosition, int yPosition, int[][] nextBrickData) {
        this.brickData = brickData;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.nextBrickData = nextBrickData;
    }

    /**
     * Gets a copy of the current brick data.
     *
     * @return brick shape matrix
     */
    public int[][] getBrickData() {
        return MatrixOperations.copy(brickData);
    }

    /**
     * Gets the current brick X position.
     *
     * @return X coordinate
     */
    public int getxPosition() {
        return xPosition;
    }

    /**
     * Gets the current brick Y position.
     *
     * @return Y coordinate
     */
    public int getyPosition() {
        return yPosition;
    }

    /**
     * Gets a copy of the next brick data for preview.
     *
     * @return next brick shape matrix
     */
    public int[][] getNextBrickData() {
        return MatrixOperations.copy(nextBrickData);
    }
}
