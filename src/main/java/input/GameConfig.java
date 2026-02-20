package input;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class GameConfig {
    private final int rows;
    private final int cols;
    private final int iterations;
    private final List<Point> livingCells;
    
    public GameConfig(int rows, int cols, int iterations, List<Point> livingCells) {
        this.rows = rows;
        this.cols = cols;
        this.iterations = iterations;
        this.livingCells = new ArrayList<>(livingCells);
    }
    
    public int getRows() { 
        return rows; 
    }
    
    public int getCols() { 
        return cols; 
    }
    
    public int getIterations() { 
        return iterations; 
    }
    
    public List<Point> getLivingCells() { 
        return Collections.unmodifiableList(livingCells); 
    }
} 