package com.comp2042.save;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Serializable representation of the entire game state for persistence.
 */
public final class GameSaveData implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String displayName;
    private final String fileSafeName;
    private final long savedAt;
    private final int[][] boardMatrix;
    private final int currentOffsetX;
    private final int currentOffsetY;
    private final String currentBrickType;
    private final int currentRotationIndex;
    private final List<String> queuedBrickTypes;
    private final int scoreValue;
    private final int linesCleared;
    private final int level;
    private final int highScoreValue;

    private GameSaveData(Builder builder) {
        this.displayName = builder.displayName;
        this.fileSafeName = builder.fileSafeName;
        this.savedAt = builder.savedAt;
        this.boardMatrix = deepCopy(builder.boardMatrix);
        this.currentOffsetX = builder.currentOffsetX;
        this.currentOffsetY = builder.currentOffsetY;
        this.currentBrickType = builder.currentBrickType;
        this.currentRotationIndex = builder.currentRotationIndex;
        this.queuedBrickTypes = new ArrayList<>(builder.queuedBrickTypes);
        this.scoreValue = builder.scoreValue;
        this.linesCleared = builder.linesCleared;
        this.level = builder.level;
        this.highScoreValue = builder.highScoreValue;
    }

    private int[][] deepCopy(int[][] source) {
        if (source == null) {
            return null;
        }
        int[][] copy = new int[source.length][];
        for (int i = 0; i < source.length; i++) {
            copy[i] = Arrays.copyOf(source[i], source[i].length);
        }
        return copy;
    }

    /**
     * Gets the display name of the save.
     *
     * @return display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Gets the file-safe name of the save.
     *
     * @return file-safe name
     */
    public String getFileSafeName() {
        return fileSafeName;
    }

    /**
     * Gets the timestamp when the save was created.
     *
     * @return timestamp in milliseconds
     */
    public long getSavedAt() {
        return savedAt;
    }

    /**
     * Gets a deep copy of the board matrix.
     *
     * @return board matrix
     */
    public int[][] getBoardMatrix() {
        return deepCopy(boardMatrix);
    }

    /**
     * Gets the current brick X offset.
     *
     * @return X coordinate
     */
    public int getCurrentOffsetX() {
        return currentOffsetX;
    }

    /**
     * Gets the current brick Y offset.
     *
     * @return Y coordinate
     */
    public int getCurrentOffsetY() {
        return currentOffsetY;
    }

    /**
     * Gets the current brick type name.
     *
     * @return brick type name
     */
    public String getCurrentBrickType() {
        return currentBrickType;
    }

    /**
     * Gets the current brick rotation index.
     *
     * @return rotation index
     */
    public int getCurrentRotationIndex() {
        return currentRotationIndex;
    }

    /**
     * Gets a copy of the queued brick types list.
     *
     * @return list of queued brick type names
     */
    public List<String> getQueuedBrickTypes() {
        return new ArrayList<>(queuedBrickTypes);
    }

    /**
     * Gets the saved score value.
     *
     * @return score
     */
    public int getScoreValue() {
        return scoreValue;
    }

    /**
     * Gets the number of cleared lines.
     *
     * @return lines cleared
     */
    public int getLinesCleared() {
        return linesCleared;
    }

    /**
     * Gets the game level.
     *
     * @return level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the high score value.
     *
     * @return high score
     */
    public int getHighScoreValue() {
        return highScoreValue;
    }

    /**
     * Creates a new builder instance for constructing GameSaveData.
     *
     * @return builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link GameSaveData}.
     */
    public static final class Builder {
        private String displayName;
        private String fileSafeName;
        private long savedAt;
        private int[][] boardMatrix;
        private int currentOffsetX;
        private int currentOffsetY;
        private String currentBrickType;
        private int currentRotationIndex;
        private List<String> queuedBrickTypes = new ArrayList<>();
        private int scoreValue;
        private int linesCleared;
        private int level;
        private int highScoreValue;

        private Builder() {
        }

        /**
         * Sets the display name.
         *
         * @param name display name
         * @return this builder
         */
        public Builder displayName(String name) {
            this.displayName = name;
            return this;
        }

        /**
         * Sets the file-safe name.
         *
         * @param name file-safe name
         * @return this builder
         */
        public Builder fileSafeName(String name) {
            this.fileSafeName = name;
            return this;
        }

        /**
         * Sets the save timestamp.
         *
         * @param timestamp timestamp in milliseconds
         * @return this builder
         */
        public Builder savedAt(long timestamp) {
            this.savedAt = timestamp;
            return this;
        }

        /**
         * Sets the board matrix.
         *
         * @param matrix board matrix
         * @return this builder
         */
        public Builder boardMatrix(int[][] matrix) {
            this.boardMatrix = matrix;
            return this;
        }

        /**
         * Sets the current brick offset.
         *
         * @param x X coordinate
         * @param y Y coordinate
         * @return this builder
         */
        public Builder currentOffset(int x, int y) {
            this.currentOffsetX = x;
            this.currentOffsetY = y;
            return this;
        }

        /**
         * Sets the current brick type.
         *
         * @param type brick type name
         * @return this builder
         */
        public Builder currentBrickType(String type) {
            this.currentBrickType = type;
            return this;
        }

        /**
         * Sets the current rotation index.
         *
         * @param rotationIndex rotation index
         * @return this builder
         */
        public Builder currentRotationIndex(int rotationIndex) {
            this.currentRotationIndex = rotationIndex;
            return this;
        }

        /**
         * Sets the queued brick types.
         *
         * @param queue list of queued brick type names
         * @return this builder
         */
        public Builder queuedBrickTypes(List<String> queue) {
            this.queuedBrickTypes = new ArrayList<>(queue);
            return this;
        }

        /**
         * Sets the score value.
         *
         * @param score score
         * @return this builder
         */
        public Builder scoreValue(int score) {
            this.scoreValue = score;
            return this;
        }

        /**
         * Sets the lines cleared count.
         *
         * @param lines lines cleared
         * @return this builder
         */
        public Builder linesCleared(int lines) {
            this.linesCleared = lines;
            return this;
        }

        /**
         * Sets the game level.
         *
         * @param levelValue level
         * @return this builder
         */
        public Builder level(int levelValue) {
            this.level = levelValue;
            return this;
        }

        /**
         * Sets the high score value.
         *
         * @param value high score
         * @return this builder
         */
        public Builder highScoreValue(int value) {
            this.highScoreValue = value;
            return this;
        }

        /**
         * Builds the GameSaveData instance.
         *
         * @return GameSaveData instance
         */
        public GameSaveData build() {
            return new GameSaveData(this);
        }
    }
}

