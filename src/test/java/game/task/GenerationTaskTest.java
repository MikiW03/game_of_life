package game.task;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.board.Board;
import game.board.StandardBoard;
import game.neighbors.NeighborCounter;
import game.neighbors.TorusNeighborCounter;
import game.rules.ConwayRules;
import game.rules.GameRules;

class GenerationTaskTest {
    private Board currentBoard;
    private Board nextBoard;
    private GameRules rules;
    private NeighborCounter neighborCounter;
    private CountDownLatch latch;

    @BeforeEach
    void setUp() {
        currentBoard = new StandardBoard(6, 6);
        nextBoard = new StandardBoard(6, 6);
        rules = new ConwayRules();
        neighborCounter = new TorusNeighborCounter();
        latch = new CountDownLatch(1);
    }

    @Test
    void testProcessSection() {
        boolean[][] initialState = {
            {false, false, false, false, false, false},
            {false, false, false, false, false, false},
            {false, true, true, false, false, false},
            {false, false, false, false, false, false},
            {false, false, false, false, false, false},
            {false, false, false, false, false, false}
        };
        currentBoard.setState(initialState);

        GenerationTask task = new GenerationTask(
            currentBoard, nextBoard, rules, neighborCounter, 2, 3, latch);
        
        task.run();
        
        boolean[][] expectedRow = {
            {false, false, false, false, false, false}
        };
        
        boolean[][] actualState = nextBoard.getState();
        assertArrayEquals(
            expectedRow[0], 
            actualState[2],
            "Row was not processed correctly"
        );
    }

//    @Test
//    void testProcessEdgeSection() {
//        boolean[][] initialState = {
//            {true, true, false, false, false, false},  // Dwie komórki w górnym wierszu
//            {false, false, false, false, false, false},
//            {false, false, false, false, false, false},
//            {false, false, false, false, false, false},
//            {false, false, false, false, false, false},
//            {true, false, false, false, false, false}   // Jedna komórka w dolnym wierszu
//        };
//        currentBoard.setState(initialState);
//
//        GenerationTask task = new GenerationTask(
//            currentBoard, nextBoard, rules, neighborCounter, 0, 1, latch);
//
//        task.run();
//
//        boolean[][] expectedRow = {
//            {false, false, false, false, false, false}
//        };
//
//        boolean[][] actualState = nextBoard.getState();
//        assertArrayEquals(
//            expectedRow[0],
//            actualState[0],
//            "Row was not processed correctly"
//        );
//    }
} 