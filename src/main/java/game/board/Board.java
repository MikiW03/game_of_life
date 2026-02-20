package game.board;

public interface Board {
    void setState(boolean[][] state);
    boolean[][] getState();
    boolean getCell(int row, int col);
    void setCell(int row, int col, boolean state);
    int getRows();
    int getCols();
} 