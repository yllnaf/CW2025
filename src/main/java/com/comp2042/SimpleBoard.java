package com.comp2042;

import com.comp2042.logic.bricks.Brick;
import com.comp2042.logic.bricks.BrickFactory;
import com.comp2042.logic.bricks.RandomBrickGenerator;
import com.comp2042.save.GameSaveData;
import com.comp2042.util.GameConstants;

import java.awt.*;
import java.util.List;

/**
 * Game board implementation class responsible for managing game state, brick movement and clearing logic.
 * Implements the Board interface to provide core game functionality.
 */
public class SimpleBoard implements Board {

    private final int width;
    private final int height;
    private final RandomBrickGenerator brickGenerator;
    private final BrickRotator brickRotator;
    private int[][] currentGameMatrix;
    private Point currentOffset;
    private final Score score;

    /**
     * Constructor to initialize the game board.
     * 
     * @param width board width
     * @param height board height
     */
    public SimpleBoard(int width, int height) {
        this.width = width;
        this.height = height;
        currentGameMatrix = new int[width][height];
        brickGenerator = new RandomBrickGenerator();
        brickRotator = new BrickRotator();
        score = new Score();
    }

    @Override
    public boolean moveBrickDown() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(0, 1);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }


    @Override
    public boolean moveBrickLeft() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(-1, 0);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    @Override
    public boolean moveBrickRight() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(1, 0);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    @Override
    public boolean rotateLeftBrick() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        NextShapeInfo nextShape = brickRotator.getNextShape();
        boolean conflict = MatrixOperations.intersect(currentMatrix, nextShape.getShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
        if (conflict) {
            return false;
        } else {
            brickRotator.setCurrentShape(nextShape.getPosition());
            return true;
        }
    }

    @Override
    public boolean createNewBrick() {
        Brick currentBrick = brickGenerator.getBrick();
        brickRotator.setBrick(currentBrick);
        currentOffset = new Point(GameConstants.INITIAL_BRICK_X, GameConstants.INITIAL_BRICK_Y);
        
        // Check if the new brick can be placed at the initial position
        // Game over only if the brick cannot be placed at the top (spawn position)
        boolean cannotPlace = MatrixOperations.intersect(currentGameMatrix, brickRotator.getCurrentShape(), 
                (int) currentOffset.getX(), (int) currentOffset.getY());
        
        // Game over only if the brick overlaps at the spawn position (top of board)
        // This means blocks have reached the top
        return cannotPlace;
    }

    @Override
    public int[][] getBoardMatrix() {
        return currentGameMatrix;
    }

    @Override
    public ViewData getViewData() {
        return new ViewData(brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY(), brickGenerator.getNextBrick().getShapeMatrix().get(0));
    }

    @Override
    public void mergeBrickToBackground() {
        currentGameMatrix = MatrixOperations.merge(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    @Override
    public ClearRow clearRows() {
        ClearRow clearRow = MatrixOperations.checkRemoving(currentGameMatrix);
        currentGameMatrix = clearRow.getNewMatrix();
        return clearRow;

    }

    @Override
    public Score getScore() {
        return score;
    }


    @Override
    public void newGame() {
        currentGameMatrix = new int[width][height];
        score.reset();
        createNewBrick();
    }

    /**
     * Captures the current game state for persistence.
     *
     * @param displayName user provided save name
     * @param fileSafeName sanitized file name
     * @return save data snapshot
     */
    public GameSaveData captureState(String displayName, String fileSafeName) {
        int[][] boardCopy = MatrixOperations.copy(currentGameMatrix);
        String currentBrickType = BrickFactory.getTypeName(brickRotator.getCurrentBrick());
        List<String> queuedTypes = brickGenerator.exportQueuedBrickTypes();
        int offsetX = currentOffset != null ? (int) currentOffset.getX() : GameConstants.INITIAL_BRICK_X;
        int offsetY = currentOffset != null ? (int) currentOffset.getY() : GameConstants.INITIAL_BRICK_Y;

        return GameSaveData.builder()
                .displayName(displayName)
                .fileSafeName(fileSafeName)
                .savedAt(System.currentTimeMillis())
                .boardMatrix(boardCopy)
                .currentOffset(offsetX, offsetY)
                .currentBrickType(currentBrickType)
                .currentRotationIndex(brickRotator.getCurrentShapeIndex())
                .queuedBrickTypes(queuedTypes)
                .scoreValue(score.getValue())
                .linesCleared(score.getLinesCleared())
                .level(score.getLevel())
                .highScoreValue(score.getHighScore())
                .build();
    }

    /**
     * Restores the board state from persisted data.
     *
     * @param saveData saved state
     */
    public void restoreState(GameSaveData saveData) {
        currentGameMatrix = MatrixOperations.copy(saveData.getBoardMatrix());
        currentOffset = new Point(saveData.getCurrentOffsetX(), saveData.getCurrentOffsetY());
        Brick loadedBrick = BrickFactory.createBrick(saveData.getCurrentBrickType());
        brickRotator.setBrick(loadedBrick);
        brickRotator.setCurrentShape(saveData.getCurrentRotationIndex());
        brickGenerator.importQueuedBrickTypes(saveData.getQueuedBrickTypes());
        score.applyState(
                saveData.getScoreValue(),
                saveData.getLinesCleared(),
                saveData.getLevel(),
                saveData.getHighScoreValue());
    }
}
