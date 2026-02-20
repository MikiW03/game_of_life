package game.rules;

public interface GameRules {
    boolean shouldLive(boolean isAlive, int liveNeighbors);
} 