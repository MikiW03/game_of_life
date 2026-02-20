package game.neighbors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.board.Board;
import game.board.StandardBoard;

class TorusNeighborCounterTest {
    private NeighborCounter counter;
    private Board board;

    @BeforeEach
    void setUp() {
        counter = new TorusNeighborCounter();
        board = new StandardBoard(3, 3);
    }

    @Test
    void testCenterCell() {
        boolean[][] state = {
            {true,  true,  true},
            {true,  false, true},
            {true,  true,  true}
        };
        board.setState(state);
        assertEquals(8, counter.countLiveNeighbors(board, 1, 1));
    }

    @Test
    void testCornerCell() {
        boolean[][] state = {
            {false, true,  false},
            {true,  true,  true},
            {false, true,  false}
        };
        board.setState(state);
        assertEquals(5, counter.countLiveNeighbors(board, 0, 0));
    }

    @Test
    void testEdgeCell() {
        boolean[][] state = {
            {true,  true,  true},
            {false, false, false},
            {true,  true,  true}
        };
        board.setState(state);
        assertEquals(6, counter.countLiveNeighbors(board, 1, 0));
    }

    @Test
    void testEmptyBoard() {
        boolean[][] state = {
            {false, false, false},
            {false, false, false},
            {false, false, false}
        };
        board.setState(state);
        assertEquals(0, counter.countLiveNeighbors(board, 1, 1));
    }

    @Test
    void testFullBoard() {
        boolean[][] state = {
            {true, true, true},
            {true, true, true},
            {true, true, true}
        };
        board.setState(state);
        assertEquals(8, counter.countLiveNeighbors(board, 1, 1));
    }
} 