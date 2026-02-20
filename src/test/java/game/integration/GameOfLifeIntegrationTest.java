//package game.integration;
//
//import org.junit.jupiter.api.AfterEach;
//import static org.junit.jupiter.api.Assertions.assertArrayEquals;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import game.board.Board;
//import game.board.StandardBoard;
//import game.core.GameOfLife;
//import game.neighbors.NeighborCounter;
//import game.neighbors.TorusNeighborCounter;
//import game.rules.ConwayRules;
//import game.rules.GameRules;
//
//class GameOfLifeIntegrationTest {
//    private GameRules rules;
//    private NeighborCounter counter;
//    private GameOfLife game;
//
//    @BeforeEach
//    void setUp() {
//        rules = new ConwayRules();
//        counter = new TorusNeighborCounter();
//    }
//
//    @AfterEach
//    void tearDown() {
//        if (game != null) {
//            game.shutdown();
//        }
//    }
//
//    @Test
//    void testOscillatorOnTorus() {
//        Board currentBoard = new StandardBoard(4, 4);
//        Board nextBoard = new StandardBoard(4, 4);
//        game = new GameOfLife(currentBoard, nextBoard, rules, counter, 2);
//
//        boolean[][] initialState = {
//            {false, false, false, true},
//            {false, false, false, true},
//            {false, false, false, true},
//            {false, false, false, false}
//        };
//        game.reset(initialState);
//
//        game.computeNextGeneration();
//        boolean[][] expectedState1 = {
//            {false, false, false, false},
//            {true, false, true, true},
//            {false, false, false, false},
//            {false, false, false, false}
//        };
//        assertArrayEquals(expectedState1, game.getCurrentState(), "First generation failed");
//
//        game.computeNextGeneration();
//        assertArrayEquals(initialState, game.getCurrentState(), "Second generation failed");
//    }
//
//    @Test
//    void testMultiThreadedEvolution() {
//        Board currentBoard = new StandardBoard(6, 6);
//        Board nextBoard = new StandardBoard(6, 6);
//        game = new GameOfLife(currentBoard, nextBoard, rules, counter, 3);
//
//        boolean[][] initialState = {
//            {false, false, false, false, false, false},
//            {false, true, true, true, false, false},
//            {false, false, false, false, false, false},
//            {false, false, false, false, false, false},
//            {false, true, true, true, false, false},
//            {false, false, false, false, false, false}
//        };
//        game.reset(initialState);
//
//        game.computeNextGeneration();
//
//        boolean[][] expectedState = {
//            {false, false, true, false, false, false},
//            {false, false, true, false, false, false},
//            {false, false, true, false, false, false},
//            {false, false, true, false, false, false},
//            {false, false, true, false, false, false},
//            {false, false, true, false, false, false}
//        };
//        assertArrayEquals(expectedState, game.getCurrentState(),
//            "Multi-threaded evolution failed");
//    }
//}