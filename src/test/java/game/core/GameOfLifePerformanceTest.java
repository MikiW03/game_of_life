//package game.core;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import org.junit.jupiter.api.Test;
//
//import game.board.Board;
//import game.board.StandardBoard;
//import game.neighbors.NeighborCounter;
//import game.neighbors.TorusNeighborCounter;
//import game.rules.ConwayRules;
//import game.rules.GameRules;
//
//class GameOfLifePerformanceTest {
//    private static final int BOARD_SIZE = 1000;
//    private static final int GENERATIONS = 100;
//
//    private Board createRandomBoard() {
//        Board board = new StandardBoard(BOARD_SIZE, BOARD_SIZE);
//        boolean[][] state = new boolean[BOARD_SIZE][BOARD_SIZE];
//        for (int i = 0; i < BOARD_SIZE; i++) {
//            for (int j = 0; j < BOARD_SIZE; j++) {
//                state[i][j] = Math.random() < 0.3; // 30% szans na żywą komórkę
//            }
//        }
//        board.setState(state);
//        return board;
//    }
//
//    @Test
//    void compareThreadCounts() {
//        GameRules rules = new ConwayRules();
//        NeighborCounter neighborCounter = new TorusNeighborCounter();
//
//        int[] threadCounts = {1, 2, 4, 8};
//        long[] times = new long[threadCounts.length];
//
//        for (int i = 0; i < threadCounts.length; i++) {
//            Board currentBoard = createRandomBoard();
//            Board nextBoard = new StandardBoard(BOARD_SIZE, BOARD_SIZE);
//
//            GameOfLife game = new GameOfLife(
//                currentBoard,
//                nextBoard,
//                rules,
//                neighborCounter,
//                threadCounts[i]
//            );
//
//            long startTime = System.nanoTime();
//
//            for (int gen = 0; gen < GENERATIONS; gen++) {
//                game.computeNextGeneration();
//            }
//
//            times[i] = System.nanoTime() - startTime;
//            game.shutdown();
//        }
//
//        // Sprawdzamy czy więcej wątków daje przyspieszenie
//        for (int i = 1; i < threadCounts.length; i++) {
//            assertTrue(times[i] < times[0],
//                String.format("Expected %d threads (%.2f ms) to be faster than single thread (%.2f ms)",
//                    threadCounts[i],
//                    times[i] / 1_000_000.0,
//                    times[0] / 1_000_000.0));
//        }
//    }
//
//    @Test
//    void testLargeBoard() {
//        Board currentBoard = createRandomBoard();
//        Board nextBoard = new StandardBoard(BOARD_SIZE, BOARD_SIZE);
//        GameRules rules = new ConwayRules();
//        NeighborCounter neighborCounter = new TorusNeighborCounter();
//
//        GameOfLife game = new GameOfLife(
//            currentBoard,
//            nextBoard,
//            rules,
//            neighborCounter,
//            Runtime.getRuntime().availableProcessors() // Używamy optymalnej liczby wątków
//        );
//
//        long startTime = System.nanoTime();
//
//        for (int gen = 0; gen < GENERATIONS; gen++) {
//            game.computeNextGeneration();
//        }
//
//        long totalTime = System.nanoTime() - startTime;
//
//        // Sprawdzamy czy przetwarzanie dużej planszy nie trwa zbyt długo
//        assertTrue(totalTime < 30_000_000_000L, // 30 sekund
//            String.format("Processing took too long: %.2f seconds",
//                totalTime / 1_000_000_000.0));
//
//        game.shutdown();
//    }
//}