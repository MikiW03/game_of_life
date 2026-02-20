package input;

import java.io.IOException;

public interface GameConfigParser {
    GameConfig parse(String filename) throws IOException;
} 