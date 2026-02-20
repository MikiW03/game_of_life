//package game.core;
//
//import org.junit.jupiter.api.AfterEach;
//import static org.junit.jupiter.api.Assertions.assertArrayEquals;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import game.board.Board;
//import game.board.StandardBoard;
//import game.neighbors.NeighborCounter;
//import game.neighbors.TorusNeighborCounter;
//import game.rules.ConwayRules;
//import game.rules.GameRules;
//
//class GameOfLifeTest {
//    private GameOfLife game;
//    private Board currentBoard;
//    private Board nextBoard;
//    private GameRules rules;
//    private NeighborCounter neighborCounter;
//
//    @BeforeEach
//    void setUp() {
//        currentBoard = new StandardBoard(4, 4);
//        nextBoard = new StandardBoard(4, 4);
//        rules = new ConwayRules();
//        neighborCounter = new TorusNeighborCounter();
//        game = new GameOfLife(currentBoard, nextBoard, rules, neighborCounter, 2);
//    }
//
//    @AfterEach
//    void tearDown() {
//        game.shutdown();
//    }
//
//    @Test
//    void testBlinker() {
//        boolean[][] initialState = {
//            {false, false, false, false},
//            {false, true, false, false},
//            {false, true, false, false},
//            {false, true, false, false}
//        };
//        game.reset(initialState);
//
//        game.computeNextGeneration();
//        boolean[][] expectedState = {
//            {false, false, false, false},
//            {false, false, false, false},
//            {true, true, true, false},
//            {false, false, false, false}
//        };
//        assertArrayEquals(expectedState, game.getCurrentState());
//
//        game.computeNextGeneration();
//        assertArrayEquals(initialState, game.getCurrentState());
//    }
//
//    @Test
//    void testStableSquareOnTorus() {
//        boolean[][] squareState = {
//            {true, true, false, false},
//            {true, true, false, false},
//            {false, false, false, false},
//            {false, false, false, false}
//        };
//        game.reset(squareState);
//
//        game.computeNextGeneration();
//        assertArrayEquals(squareState, game.getCurrentState());
//    }
//
//    @Test
//    void testWraparoundNeighbors() {
//        boolean[][] state = {
//            {true, false, false, true},
//            {false, false, false, false},
//            {false, false, false, false},
//            {true, false, false, true}
//        };
//        game.reset(state);
//
//        game.computeNextGeneration();
//        boolean[][] expectedState = {
//            {true, false, false, true},
//            {false, false, false, false},
//            {false, false, false, false},
//            {true, false, false, true}
//        };
//        assertArrayEquals(expectedState, game.getCurrentState());
//    }
//
//    @Test
//    void testAllDead() {
//        boolean[][] deadState = new boolean[4][4];
//        game.reset(deadState);
//
//        game.computeNextGeneration();
//        assertArrayEquals(deadState, game.getCurrentState());
//    }
//
//    @Test
//    void testInvalidReset() {
//        boolean[][] invalidState = new boolean[5][5];
//        assertThrows(IllegalArgumentException.class, () -> game.reset(invalidState));
//    }
//
//    @Test
//    void testDimensions() {
//        assertEquals(4, game.getRows());
//        assertEquals(4, game.getCols());
//    }
//}