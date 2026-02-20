package game.board;

public class StandardBoard implements Board {
    private final int rows;
    private final int cols;
    private final boolean[][] cells;
    
    public StandardBoard(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Board dimensions must be positive");
        }
        this.rows = rows;
        this.cols = cols;
        this.cells = new boolean[rows][cols];
    }
    
    @Override
    public void setState(boolean[][] state) {
        if (state.length != rows || state[0].length != cols) {
            throw new IllegalArgumentException("State dimensions do not match board dimensions");
        }
        for (int i = 0; i < rows; i++) {
            System.arraycopy(state[i], 0, cells[i], 0, cols);
        }
    }
    
    @Override
    public boolean[][] getState() {
        boolean[][] copy = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            copy[i] = cells[i].clone();
        }
        return copy;
    }
    
    @Override
    public boolean getCell(int row, int col) {
        return cells[row][col];
    }
    
    @Override
    public void setCell(int row, int col, boolean state) {
        cells[row][col] = state;
    }
    
    @Override
    public int getRows() {
        return rows;
    }
    
    @Override
    public int getCols() {
        return cols;
    }
} 