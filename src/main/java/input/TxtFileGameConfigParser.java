package input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TxtFileGameConfigParser implements GameConfigParser {
    
    private static final String SCENARIOS_PATH = "scenarios/";
    private final ConfigLineParser lineParser;
    
    public TxtFileGameConfigParser() {
        this.lineParser = new ConfigLineParser();
    }
    
    @Override
    public GameConfig parse(String filename) throws IOException {
        String resourcePath = SCENARIOS_PATH + filename;
        
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IOException("Could not find scenario file: " + filename);
            }
            
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                return parseConfig(reader);
            }
        }
    }
    
    private GameConfig parseConfig(BufferedReader reader) throws IOException {
        int rows = lineParser.parsePositiveNumber(reader.readLine(), "rows");
        int cols = lineParser.parsePositiveNumber(reader.readLine(), "columns");
        int iterations = lineParser.parsePositiveNumber(reader.readLine(), "iterations");
        int numLivingCells = lineParser.parsePositiveNumber(reader.readLine(), "number of living cells");
        
        List<Point> livingCells = new ArrayList<>();
        for (int i = 0; i < numLivingCells; i++) {
            String line = reader.readLine();
            if (line == null) {
                throw new IllegalArgumentException(
                    "File ended too early, expected " + numLivingCells + " living cells"
                );
            }
            Point point = lineParser.parsePoint(line, i + 5, rows, cols);
            livingCells.add(point);
        }
        
        return new GameConfig(rows, cols, iterations, livingCells);
    }
} 