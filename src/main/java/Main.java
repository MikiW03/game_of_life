import javax.swing.SwingUtilities;

import game.board.Board;
import game.board.StandardBoard;
import game.core.GameOfLife;
import game.neighbors.NeighborCounter;
import game.neighbors.TorusNeighborCounter;
import game.rules.ConwayRules;
import game.rules.GameRules;
import gui.GameWindow;
import gui.controller.GameGuiController;
import gui.panels.ControlPanel;
import gui.panels.GameBoardPanel;
import gui.panels.ThreadStatsPanel;
import input.GameConfig;
import input.GameConfigParser;
import input.TxtFileGameConfigParser;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java Main <input_file> <number_of_threads>");
            System.exit(1);
        }

        try {
            String inputFile = args[0];
            int numberOfThreads = Integer.parseInt(args[1]);

            GameConfigParser parser = new TxtFileGameConfigParser();
            GameConfig config = parser.parse(inputFile);
            numberOfThreads = Math.min(config.getRows(), numberOfThreads);
            
            Board currentBoard = new StandardBoard(config.getRows(), config.getCols());
            Board nextBoard = new StandardBoard(config.getRows(), config.getCols());
            GameRules rules = new ConwayRules();
            NeighborCounter neighborCounter = new TorusNeighborCounter();
            
            GameOfLife game = new GameOfLife(
                currentBoard,
                nextBoard,
                rules,
                neighborCounter, 
                numberOfThreads,
                config
            );

            GameBoardPanel boardPanel = new GameBoardPanel(config.getRows(), config.getCols(), numberOfThreads);
            ThreadStatsPanel statsPanel = new ThreadStatsPanel(numberOfThreads, config.getRows(), config.getCols());
            ControlPanel controlPanel = new ControlPanel();

            GameGuiController guiController = new GameGuiController(
                game,
                boardPanel,
                statsPanel,
                controlPanel
            );

            SwingUtilities.invokeLater(() -> {
                GameWindow window = new GameWindow(
                    boardPanel,
                    statsPanel,
                    controlPanel,
                    guiController
                );
                window.setVisible(true);
                guiController.initialize();
            });

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
} 