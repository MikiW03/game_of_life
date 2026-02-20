package input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import input.ConfigLineParser;
import input.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConfigLineParserTest {
    
    private ConfigLineParser parser;
    
    @BeforeEach
    void setUp() {
        parser = new ConfigLineParser();
    }
    
    @Test
    void testParsePositiveNumber() {
        assertEquals(5, parser.parsePositiveNumber("5", "test"));
        assertEquals(100, parser.parsePositiveNumber(" 100 ", "test")); // z whitespace
    }
    
    @Test
    void testParsePositiveNumberInvalid() {
        assertThrows(IllegalArgumentException.class, 
            () -> parser.parsePositiveNumber("-1", "test"));
        assertThrows(IllegalArgumentException.class, 
            () -> parser.parsePositiveNumber("0", "test"));
        assertThrows(IllegalArgumentException.class, 
            () -> parser.parsePositiveNumber("abc", "test"));
        assertThrows(NullPointerException.class, 
            () -> parser.parsePositiveNumber(null, "test"));
    }
    
    @Test
    void testParsePoint() {
        Point point = parser.parsePoint("3 4", 1, 10, 10);
        assertEquals(3, point.getRow());
        assertEquals(4, point.getCol());
        
        point = parser.parsePoint(" 5  6 ", 1, 10, 10);
        assertEquals(5, point.getRow());
        assertEquals(6, point.getCol());
    }
    
    @Test
    void testParsePointInvalid() {
        assertThrows(IllegalArgumentException.class,
            () -> parser.parsePoint("3", 1, 10, 10));
        assertThrows(IllegalArgumentException.class, 
            () -> parser.parsePoint("3 4 5", 1, 10, 10));
        assertThrows(IllegalArgumentException.class, 
            () -> parser.parsePoint("a b", 1, 10, 10));
        
        assertThrows(IllegalArgumentException.class,
            () -> parser.parsePoint("-1 4", 1, 10, 10));
        assertThrows(IllegalArgumentException.class, 
            () -> parser.parsePoint("10 4", 1, 10, 10));
        assertThrows(IllegalArgumentException.class, 
            () -> parser.parsePoint("4 -1", 1, 10, 10));
        assertThrows(IllegalArgumentException.class, 
            () -> parser.parsePoint("4 10", 1, 10, 10));
    }
} 