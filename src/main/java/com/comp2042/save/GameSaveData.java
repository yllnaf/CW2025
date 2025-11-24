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

    public String getDisplayName() {
        return displayName;
    }

    public String getFileSafeName() {
        return fileSafeName;
    }

    public long getSavedAt() {
        return savedAt;
    }

    public int[][] getBoardMatrix() {
        return deepCopy(boardMatrix);
    }

    public int getCurrentOffsetX() {
        return currentOffsetX;
    }

    public int getCurrentOffsetY() {
        return currentOffsetY;
    }

    public String getCurrentBrickType() {
        return currentBrickType;
    }

    public int getCurrentRotationIndex() {
        return currentRotationIndex;
    }

    public List<String> getQueuedBrickTypes() {
        return new ArrayList<>(queuedBrickTypes);
    }

    public int getScoreValue() {
        return scoreValue;
    }

    public int getLinesCleared() {
        return linesCleared;
    }

    public int getLevel() {
        return level;
    }

    public int getHighScoreValue() {
        return highScoreValue;
    }

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

        public Builder displayName(String name) {
            this.displayName = name;
            return this;
        }

        public Builder fileSafeName(String name) {
            this.fileSafeName = name;
            return this;
        }

        public Builder savedAt(long timestamp) {
            this.savedAt = timestamp;
            return this;
        }

        public Builder boardMatrix(int[][] matrix) {
            this.boardMatrix = matrix;
            return this;
        }

        public Builder currentOffset(int x, int y) {
            this.currentOffsetX = x;
            this.currentOffsetY = y;
            return this;
        }

        public Builder currentBrickType(String type) {
            this.currentBrickType = type;
            return this;
        }

        public Builder currentRotationIndex(int rotationIndex) {
            this.currentRotationIndex = rotationIndex;
            return this;
        }

        public Builder queuedBrickTypes(List<String> queue) {
            this.queuedBrickTypes = new ArrayList<>(queue);
            return this;
        }

        public Builder scoreValue(int score) {
            this.scoreValue = score;
            return this;
        }

        public Builder linesCleared(int lines) {
            this.linesCleared = lines;
            return this;
        }

        public Builder level(int levelValue) {
            this.level = levelValue;
            return this;
        }

        public Builder highScoreValue(int value) {
            this.highScoreValue = value;
            return this;
        }

        public GameSaveData build() {
            return new GameSaveData(this);
        }
    }
}

