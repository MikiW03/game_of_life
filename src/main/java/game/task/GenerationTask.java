package game.task;

import java.util.concurrent.CountDownLatch;

import game.board.Board;
import game.neighbors.NeighborCounter;
import game.rules.GameRules;

public class GenerationTask implements Runnable {
    private final Board currentBoard;
    private final Board nextBoard;
    private final GameRules rules;
    private final NeighborCounter neighborCounter;
    private final int startRow;
    private final int endRow;
    private final CountDownLatch latch;
    
    public GenerationTask(
            Board currentBoard,
            Board nextBoard,
            GameRules rules,
            NeighborCounter neighborCounter,
            int startRow,
            int endRow,
            CountDownLatch latch) {
        this.currentBoard = currentBoard;
        this.nextBoard = nextBoard;
        this.rules = rules;
        this.neighborCounter = neighborCounter;
        this.startRow = startRow;
        this.endRow = endRow;
        this.latch = latch;
    }
    
    @Override
    public void run() {
        try {
            for (int row = startRow; row < endRow; row++) {
                for (int col = 0; col < currentBoard.getCols(); col++) {
                    boolean isAlive = currentBoard.getCell(row, col);
                    int liveNeighbors = neighborCounter.countLiveNeighbors(currentBoard, row, col);
                    boolean willLive = rules.shouldLive(isAlive, liveNeighbors);
                    nextBoard.setCell(row, col, willLive);
                }
            }
        } finally {
            latch.countDown();
        }
    }
} 