package com.comp2042.save;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Lightweight view of a saved game, used when listing options for the user.
 */
public final class GameSaveMetadata {

    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault());

    private final String fileSafeName;
    private final String displayName;
    private final long savedAt;

    /**
     * Constructor to create save metadata.
     *
     * @param fileSafeName sanitized file name
     * @param displayName user-friendly display name
     * @param savedAt timestamp when the save was created
     */
    public GameSaveMetadata(String fileSafeName, String displayName, long savedAt) {
        this.fileSafeName = fileSafeName;
        this.displayName = displayName;
        this.savedAt = savedAt;
    }

    /**
     * Gets the file-safe name.
     *
     * @return file-safe name
     */
    public String getFileSafeName() {
        return fileSafeName;
    }

    /**
     * Gets the display name.
     *
     * @return display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Gets the save timestamp.
     *
     * @return timestamp in milliseconds
     */
    public long getSavedAt() {
        return savedAt;
    }

    /**
     * Returns a formatted string representation with display name and timestamp.
     *
     * @return formatted string
     */
    @Override
    public String toString() {
        return displayName + " (" + DISPLAY_FORMATTER.format(Instant.ofEpochMilli(savedAt)) + ")";
    }
}
