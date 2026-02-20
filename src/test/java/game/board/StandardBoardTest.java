package game.board;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class StandardBoardTest {

    @Test
    void testSetAndGetCell() {
        Board board = new StandardBoard(3, 3);
        board.setCell(1, 1, true);
        assertTrue(board.getCell(1, 1));
        assertFalse(board.getCell(0, 0));
    }

    @Test
    void testSetStateAndGetState() {
        Board board = new StandardBoard(2, 2);
        boolean[][] initialState = {
            {true, false},
            {false, true}
        };
        board.setState(initialState);
        boolean[][] state = board.getState();
        assertArrayEquals(initialState, state);
    }

    @Test
    void testGetRowsAndCols() {
        Board board = new StandardBoard(4, 5);
        assertEquals(4, board.getRows());
        assertEquals(5, board.getCols());
    }

    @Test
    void testInvalidDimensions() {
        assertThrows(IllegalArgumentException.class, () -> new StandardBoard(0, 5));
        assertThrows(IllegalArgumentException.class, () -> new StandardBoard(5, 0));
    }

    @Test
    void testInvalidStateDimensions() {
        Board board = new StandardBoard(2, 2);
        boolean[][] invalidState = {
            {true, false, true},
            {false, true, false}
        };
        assertThrows(IllegalArgumentException.class, () -> board.setState(invalidState));
    }
} 