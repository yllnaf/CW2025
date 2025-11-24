package com.comp2042.logic.bricks;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Factory class that creates brick instances based on their simple type names.
 */
public final class BrickFactory {

    private static final Map<String, Supplier<Brick>> REGISTRY = new HashMap<>();

    static {
        REGISTRY.put(IBrick.class.getSimpleName(), IBrick::new);
        REGISTRY.put(JBrick.class.getSimpleName(), JBrick::new);
        REGISTRY.put(LBrick.class.getSimpleName(), LBrick::new);
        REGISTRY.put(OBrick.class.getSimpleName(), OBrick::new);
        REGISTRY.put(SBrick.class.getSimpleName(), SBrick::new);
        REGISTRY.put(TBrick.class.getSimpleName(), TBrick::new);
        REGISTRY.put(ZBrick.class.getSimpleName(), ZBrick::new);
    }

    private BrickFactory() {
        throw new AssertionError("Cannot instantiate utility class");
    }

    /**
     * Creates a new brick instance based on the provided type name.
     *
     * @param typeName simple brick class name, e.g. {@code IBrick}
     * @return brick instance
     * @throws IllegalArgumentException if the requested type is unknown
     */
    public static Brick createBrick(String typeName) {
        Supplier<Brick> supplier = REGISTRY.get(typeName);
        if (supplier == null) {
            throw new IllegalArgumentException("Unsupported brick type: " + typeName);
        }
        return supplier.get();
    }

    /**
     * Gets the simple name for the supplied brick instance.
     *
     * @param brick brick instance
     * @return simple class name
     */
    public static String getTypeName(Brick brick) {
        return Optional.ofNullable(brick)
                .map(value -> value.getClass().getSimpleName())
                .orElse("UNKNOWN");
    }
}

