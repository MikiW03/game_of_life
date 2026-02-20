package game.neighbors;

import game.board.Board;

public interface NeighborCounter {
    int countLiveNeighbors(Board board, int row, int col);
} 