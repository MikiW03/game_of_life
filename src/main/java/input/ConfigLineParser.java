package input;

import java.util.Objects;

public class ConfigLineParser {
    
    public int parsePositiveNumber(String line, String valueName) {
        Objects.requireNonNull(line, valueName + " line cannot be null");
        try {
            int value = Integer.parseInt(line.trim());
            if (value <= 0) {
                throw new IllegalArgumentException(
                    valueName + " must be positive, got: " + value
                );
            }
            return value;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                "Invalid " + valueName + " format: " + line
            );
        }
    }
    
    public Point parsePoint(String line, int lineNumber, int maxRows, int maxCols) {
        String[] parts = line.trim().split("\\s+");
        if (parts.length != 2) {
            throw new IllegalArgumentException(
                "Invalid coordinate format at line " + lineNumber + ": " + line
            );
        }
        
        try {
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);
            
            if (row < 0 || row >= maxRows || col < 0 || col >= maxCols) {
                throw new IllegalArgumentException(
                    "Coordinates out of bounds at line " + lineNumber + 
                    ": " + row + " " + col
                );
            }
            
            return new Point(row, col);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                "Invalid coordinate values at line " + lineNumber + ": " + line
            );
        }
    }
} 