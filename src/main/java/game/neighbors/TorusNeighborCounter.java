package game.neighbors;

import game.board.Board;

public class TorusNeighborCounter implements NeighborCounter {
    @Override
    public int countLiveNeighbors(Board board, int row, int col) {
        int count = 0;
        int rows = board.getRows();
        int cols = board.getCols();
        
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                
                int neighborRow = (row + i + rows) % rows;
                int neighborCol = (col + j + cols) % cols;
                
                if (board.getCell(neighborRow, neighborCol)) {
                    count++;
                }
            }
        }
        return count;
    }
} 