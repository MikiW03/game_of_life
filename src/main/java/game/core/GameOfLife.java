package game.core;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import game.board.Board;
import game.task.GenerationTask;
import game.neighbors.NeighborCounter;
import game.rules.GameRules;
import input.GameConfig;
import input.Point;

public class GameOfLife {
    private Board currentBoard;
    private Board nextBoard;
    private final GameRules rules;
    private final NeighborCounter neighborCounter;
    private final int threadCount;
    private final ExecutorService executor;
    private final GameConfig config;
    private int generation;

    public GameOfLife(
            Board currentBoard,
            Board nextBoard,
            GameRules rules,
            NeighborCounter neighborCounter,
            int threadCount,
            GameConfig config) {
        this.currentBoard = currentBoard;
        this.nextBoard = nextBoard;
        this.rules = rules;
        this.neighborCounter = neighborCounter;
        this.config = config;
        
        if (threadCount <= 0) {
            throw new IllegalArgumentException("Thread count must be positive");
        }
        
        this.threadCount = Math.min(threadCount, currentBoard.getRows());
        this.executor = Executors.newFixedThreadPool(this.threadCount);
        
        initializeBoard();
    }

    public void initializeBoard() {
        boolean[][] initialState = new boolean[config.getRows()][config.getCols()];
        for (Point p : config.getLivingCells()) {
            initialState[p.getRow()][p.getCol()] = true;
        }
        reset(initialState);
        generation = 0;
    }

    public void computeNextGeneration() {
        CountDownLatch latch = new CountDownLatch(threadCount);
        int rowsPerThread = currentBoard.getRows() / threadCount;
        int remainingRows = currentBoard.getRows() % threadCount;

        int startRow = 0;
        for (int i = 0; i < threadCount; i++) {
            int threadRows = rowsPerThread + (i < remainingRows ? 1 : 0);
            int endRow = startRow + threadRows;

            executor.execute(new GenerationTask(
                currentBoard,
                nextBoard,
                rules,
                neighborCounter,
                startRow,
                endRow,
                latch
            ));
            
            startRow = endRow;
        }
        
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Generation computation interrupted", e);
        }
        
        generation++;
        swapBoards();
    }
    
    private void swapBoards() {
        Board temp = currentBoard;
        currentBoard = nextBoard;
        nextBoard = temp;
    }
    
    public boolean[][] getCurrentState() {
        return currentBoard.getState();
    }
    
    public int getRows() {
        return currentBoard.getRows();
    }
    
    public int getCols() {
        return currentBoard.getCols();
    }

    public int getGeneration() {
        return generation;
    }
    
    public void reset(boolean[][] state) {
        currentBoard.setState(state);
        generation = 0;
    }

    public boolean hasReachedGenerationLimit() {
        return generation >= config.getIterations();
    }
    
    public int getMaxGenerations() {
        return config.getIterations();
    }
} 