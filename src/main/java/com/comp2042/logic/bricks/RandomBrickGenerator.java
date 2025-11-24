package com.comp2042.logic.bricks;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class RandomBrickGenerator implements BrickGenerator {

    private final List<Brick> brickList;

    private final Deque<Brick> nextBricks = new ArrayDeque<>();

    public RandomBrickGenerator() {
        brickList = new ArrayList<>();
        brickList.add(new IBrick());
        brickList.add(new JBrick());
        brickList.add(new LBrick());
        brickList.add(new OBrick());
        brickList.add(new SBrick());
        brickList.add(new TBrick());
        brickList.add(new ZBrick());
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
    }

    @Override
    public Brick getBrick() {
        ensureQueueSize();
        return nextBricks.poll();
    }

    @Override
    public Brick getNextBrick() {
        return nextBricks.peek();
    }

    /**
     * Exports the pending bricks as their simple type names for persistence.
     *
     * @return list of queued brick type names
     */
    public List<String> exportQueuedBrickTypes() {
        return nextBricks.stream()
                .map(BrickFactory::getTypeName)
                .collect(Collectors.toList());
    }

    /**
     * Replaces the pending brick queue with the provided list.
     *
     * @param queuedTypes queued brick type names
     */
    public void importQueuedBrickTypes(List<String> queuedTypes) {
        nextBricks.clear();
        if (queuedTypes == null || queuedTypes.isEmpty()) {
            refillDefaults();
            return;
        }
        queuedTypes.forEach(type -> nextBricks.add(createBrickSafely(type)));
        ensureQueueSize();
    }

    private void ensureQueueSize() {
        if (nextBricks.size() <= 1) {
            nextBricks.add(randomBrick());
        }
    }

    private void refillDefaults() {
        nextBricks.add(randomBrick());
        nextBricks.add(randomBrick());
    }

    private Brick createBrickSafely(String type) {
        try {
            return BrickFactory.createBrick(type);
        } catch (IllegalArgumentException exception) {
            return randomBrick();
        }
    }

    private Brick randomBrick() {
        return brickList.get(ThreadLocalRandom.current().nextInt(brickList.size()));
    }
}
