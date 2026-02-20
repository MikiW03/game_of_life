package game.rules;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class ConwayRulesTest {

    @Test
    void testShouldLiveWithTwoNeighbors() {
        GameRules rules = new ConwayRules();
        assertTrue(rules.shouldLive(true, 2), "Alive cell with 2 neighbors should live");
    }

    @Test
    void testShouldLiveWithThreeNeighbors() {
        GameRules rules = new ConwayRules();
        assertTrue(rules.shouldLive(true, 3), "Alive cell with 3 neighbors should live");
        assertTrue(rules.shouldLive(false, 3), "Dead cell with 3 neighbors should become alive");
    }

    @Test
    void testShouldDieWithFewerThanTwoNeighbors() {
        GameRules rules = new ConwayRules();
        assertFalse(rules.shouldLive(true, 1), "Alive cell with 1 neighbor should die");
        assertFalse(rules.shouldLive(true, 0), "Alive cell with 0 neighbors should die");
    }

    @Test
    void testShouldDieWithMoreThanThreeNeighbors() {
        GameRules rules = new ConwayRules();
        assertFalse(rules.shouldLive(true, 4), "Alive cell with 4 neighbors should die");
        assertFalse(rules.shouldLive(true, 5), "Alive cell with 5 neighbors should die");
    }
} 