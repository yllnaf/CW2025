package com.comp2042.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Utility class responsible for persisting and loading the game high score.
 */
public final class HighScoreStorage {

    private static final String FILE_NAME = ".tetris_highscore.properties";
    private static final String PROPERTY_KEY = "highScore";

    private HighScoreStorage() {
        throw new AssertionError("Cannot instantiate utility class");
    }

    /**
     * Loads the stored high score from disk.
     *
     * @return stored high score, or 0 if not available
     */
    public static int loadHighScore() {
        Path filePath = getFilePath();
        Properties properties = new Properties();
        if (Files.exists(filePath)) {
            try (InputStream inputStream = Files.newInputStream(filePath)) {
                properties.load(inputStream);
                return Integer.parseInt(properties.getProperty(PROPERTY_KEY, "0"));
            } catch (IOException | NumberFormatException exception) {
                System.err.println("Failed to load high score: " + exception.getMessage());
            }
        }
        return 0;
    }

    /**
     * Saves the provided high score to disk.
     *
     * @param highScore current high score
     */
    public static void saveHighScore(int highScore) {
        Path filePath = getFilePath();
        Properties properties = new Properties();
        properties.setProperty(PROPERTY_KEY, String.valueOf(highScore));
        try {
            if (filePath.getParent() != null) {
                Files.createDirectories(filePath.getParent());
            }
            try (OutputStream outputStream = Files.newOutputStream(filePath)) {
                properties.store(outputStream, "Tetris High Score");
            }
        } catch (IOException exception) {
            System.err.println("Failed to save high score: " + exception.getMessage());
        }
    }

    private static Path getFilePath() {
        String userHome = System.getProperty("user.home");
        return Paths.get(userHome, FILE_NAME);
    }
}

