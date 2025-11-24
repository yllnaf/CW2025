package com.comp2042;

/**
 * Data class containing information about the next rotated shape.
 * Used for brick rotation operations.
 */
public final class NextShapeInfo {

    private final int[][] shape;
    private final int position;

    /**
     * Constructor to create next shape information.
     *
     * @param shape shape matrix for the next rotation
     * @param position rotation index position
     */
    public NextShapeInfo(final int[][] shape, final int position) {
        this.shape = shape;
        this.position = position;
    }

    /**
     * Gets a copy of the shape matrix.
     *
     * @return shape matrix
     */
    public int[][] getShape() {
        return MatrixOperations.copy(shape);
    }

    /**
     * Gets the rotation position index.
     *
     * @return rotation index
     */
    public int getPosition() {
        return position;
    }
}
