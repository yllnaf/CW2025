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

    public GameSaveMetadata(String fileSafeName, String displayName, long savedAt) {
        this.fileSafeName = fileSafeName;
        this.displayName = displayName;
        this.savedAt = savedAt;
    }

    public String getFileSafeName() {
        return fileSafeName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public long getSavedAt() {
        return savedAt;
    }

    @Override
    public String toString() {
        return displayName + " (" + DISPLAY_FORMATTER.format(Instant.ofEpochMilli(savedAt)) + ")";
    }
}
