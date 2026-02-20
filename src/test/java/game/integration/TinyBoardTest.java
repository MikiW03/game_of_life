//package game.integration;
//
//import static org.junit.jupiter.api.Assertions.assertArrayEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import org.junit.jupiter.api.Test;
//
//import game.board.Board;
//import game.board.StandardBoard;
//import game.core.GameOfLife;
//import game.neighbors.NeighborCounter;
//import game.neighbors.TorusNeighborCounter;
//import game.rules.ConwayRules;
//import game.rules.GameRules;
//
//class TinyBoardTest {
//
//    @Test
//    void testBoard1x1() {
//        Board currentBoard = new StandardBoard(1, 1);
//        Board nextBoard = new StandardBoard(1, 1);
//        GameRules rules = new ConwayRules();
//        NeighborCounter counter = new TorusNeighborCounter();
//
//        GameOfLife game = new GameOfLife(currentBoard, nextBoard, rules, counter, 1);
//
//        boolean[][] aliveState = {{true}};
//        game.reset(aliveState);
//        game.computeNextGeneration();
//        assertFalse(game.getCurrentState()[0][0]);
//
//        boolean[][] deadState = {{false}};
//        game.reset(deadState);
//        game.computeNextGeneration();
//        assertFalse(game.getCurrentState()[0][0]);
//
//        game.shutdown();
//    }
//
//    @Test
//    void testBoard2x2() {
//        Board currentBoard = new StandardBoard(2, 2);
//        Board nextBoard = new StandardBoard(2, 2);
//        GameRules rules = new ConwayRules();
//        NeighborCounter counter = new TorusNeighborCounter();
//
//        GameOfLife game = new GameOfLife(currentBoard, nextBoard, rules, counter, 2);
//
//        boolean[][] allAlive = {
//            {true, true},
//            {true, true}
//        };
//        game.reset(allAlive);
//        game.computeNextGeneration();
//        boolean[][] allDead = {
//            {false, false},
//            {false, false}
//        };
//        assertArrayEquals(allDead, game.getCurrentState());
//
//        game.shutdown();
//    }
//}