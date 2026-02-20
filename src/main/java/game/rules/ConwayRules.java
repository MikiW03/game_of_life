package game.rules;

public class ConwayRules implements GameRules {
    @Override
    public boolean shouldLive(boolean isAlive, int liveNeighbors) {
        if (isAlive) {
            return liveNeighbors == 2 || liveNeighbors == 3;
        }
        return liveNeighbors == 3;
    }
} 