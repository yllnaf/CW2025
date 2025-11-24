package com.comp2042.save;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Handles saving, loading and listing game save files on disk.
 */
public final class GameSaveManager {

    private static final String SAVE_FOLDER = ".tetris_saves";
    private static final String EXTENSION = ".sav";
    private static final DateTimeFormatter DEFAULT_NAME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private GameSaveManager() {
        throw new AssertionError("Cannot instantiate utility class");
    }

    /**
     * Persists the provided game state.
     *
     * @param data state to persist
     * @throws IOException if writing to disk fails
     */
    public static void saveGame(GameSaveData data) throws IOException {
        Path filePath = getSaveDirectory().resolve(data.getFileSafeName() + EXTENSION);
        Files.createDirectories(filePath.getParent());
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(filePath))) {
            objectOutputStream.writeObject(data);
        }
    }

    /**
     * Loads a previously saved game state.
     *
     * @param fileSafeName sanitized save identifier
     * @return optional game state
     * @throws IOException if reading fails
     * @throws ClassNotFoundException if the serialized class cannot be found
     */
    public static Optional<GameSaveData> loadGame(String fileSafeName)
            throws IOException, ClassNotFoundException {
        Path filePath = getSaveDirectory().resolve(fileSafeName + EXTENSION);
        if (!Files.exists(filePath)) {
            return Optional.empty();
        }
        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(filePath))) {
            Object object = inputStream.readObject();
            if (object instanceof GameSaveData saveData) {
                return Optional.of(saveData);
            }
        }
        return Optional.empty();
    }

    /**
     * Lists all saved games sorted by most recent first.
     *
     * @return metadata list
     */
    public static List<GameSaveMetadata> listSaves() {
        List<GameSaveMetadata> metadataList = new ArrayList<>();
        Path directory = getSaveDirectory();
        if (!Files.exists(directory)) {
            return metadataList;
        }
        try (var pathStream = Files.list(directory)) {
            pathStream
                .filter(path -> path.toString().endsWith(EXTENSION))
                .forEach(path -> loadMetadata(path).ifPresent(metadataList::add));
        } catch (IOException exception) {
            System.err.println("Failed to enumerate saves: " + exception.getMessage());
        }
        metadataList.sort(Comparator.comparingLong(GameSaveMetadata::getSavedAt).reversed());
        return metadataList;
    }

    /**
     * Checks if a save with the provided name already exists.
     *
     * @param fileSafeName sanitized name
     * @return true if the save exists
     */
    public static boolean saveExists(String fileSafeName) {
        return Files.exists(getSaveDirectory().resolve(fileSafeName + EXTENSION));
    }

    /**
     * Generates a default save display name using the current timestamp.
     *
     * @return default display name
     */
    public static String generateDefaultDisplayName() {
        return "Save-" + LocalDateTime.now().format(DEFAULT_NAME_FORMATTER);
    }

    /**
     * Sanitizes user provided names for file system usage.
     *
     * @param userProvidedName raw name
     * @return sanitized name
     */
    public static String toFileSafeName(String userProvidedName) {
        String sanitized = userProvidedName.trim().replaceAll("[^a-zA-Z0-9-_]", "_");
        if (sanitized.isBlank()) {
            sanitized = "save_" + System.currentTimeMillis();
        }
        return sanitized;
    }

    private static Optional<GameSaveMetadata> loadMetadata(Path path) {
        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(path))) {
            Object object = inputStream.readObject();
            if (object instanceof GameSaveData saveData) {
                return Optional.of(new GameSaveMetadata(
                        saveData.getFileSafeName(),
                        saveData.getDisplayName(),
                        saveData.getSavedAt()));
            }
        } catch (IOException | ClassNotFoundException exception) {
            System.err.println("Failed to read save metadata: " + exception.getMessage());
        }
        return Optional.empty();
    }

    private static Path getSaveDirectory() {
        String userHome = System.getProperty("user.home");
        return Paths.get(userHome, SAVE_FOLDER);
    }
}

