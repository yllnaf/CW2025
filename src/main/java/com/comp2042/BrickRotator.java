package com.comp2042;

import com.comp2042.logic.bricks.Brick;

/**
 * Brick rotator class responsible for managing brick rotation state.
 * Provides functionality to get current shape, next shape, and set brick.
 */
public class BrickRotator {

    private Brick brick;
    private int currentShape = 0;

    /**
     * Gets the next rotated shape.
     * 
     * @return next shape information
     */
    public NextShapeInfo getNextShape() {
        int nextShapeIndex = (currentShape + 1) % brick.getShapeMatrix().size();
        return new NextShapeInfo(brick.getShapeMatrix().get(nextShapeIndex), nextShapeIndex);
    }

    /**
     * Gets the current shape matrix.
     * 
     * @return current shape as a two-dimensional array
     */
    public int[][] getCurrentShape() {
        return brick.getShapeMatrix().get(currentShape);
    }

    /**
     * Sets the current shape index.
     * 
     * @param shapeIndex shape index
     */
    public void setCurrentShape(int shapeIndex) {
        this.currentShape = shapeIndex;
    }

    /**
     * Sets a new brick and resets rotation state.
     * 
     * @param brick new brick object
     */
    public void setBrick(Brick brick) {
        this.brick = brick;
        currentShape = 0;
    }

    /**
     * Gets the active brick instance.
     *
     * @return current brick
     */
    public Brick getCurrentBrick() {
        return brick;
    }

    /**
     * Gets the current rotation index.
     *
     * @return rotation index
     */
    public int getCurrentShapeIndex() {
        return currentShape;
    }
}
