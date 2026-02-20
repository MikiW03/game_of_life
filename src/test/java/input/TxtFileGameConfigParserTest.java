package input;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import input.GameConfig;
import input.Point;
import input.TxtFileGameConfigParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TxtFileGameConfigParserTest {
    
    private TxtFileGameConfigParser parser;
    
    @BeforeEach
    void setUp() {
        parser = new TxtFileGameConfigParser();
    }
    
    @Test
    void testParseValidFile() throws IOException {
        GameConfig config = parser.parse("valid.txt");
        
        assertEquals(30, config.getRows());
        assertEquals(30, config.getCols());
        assertEquals(100, config.getIterations());
        assertEquals(5, config.getLivingCells().size());
        
        assertTrue(config.getLivingCells().contains(new Point(29, 1)));
        assertTrue(config.getLivingCells().contains(new Point(28, 2)));
    }
    
    @Test
    void testParseNonexistentFile() {
        assertThrows(IOException.class, 
            () -> parser.parse("nonexistent.txt"));
    }
    
    @Test
    void testParseInvalidFile() {
        assertThrows(IllegalArgumentException.class, 
            () -> parser.parse("invalid_dimensions.txt"));
        assertThrows(IllegalArgumentException.class, 
            () -> parser.parse("invalid_points.txt"));
        assertThrows(IllegalArgumentException.class, 
            () -> parser.parse("too_few_points.txt"));
    }
} 