package gui.controller;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import game.core.GameOfLife;
import gui.panels.ControlPanel;
import gui.panels.GameBoardPanel;
import gui.panels.ThreadStatsPanel;

public class GameGuiController {
    private static final int MIN_DELAY = 50;
    private static final int MAX_DELAY = 1000;
    
    private final GameOfLife game;
    private final GameBoardPanel boardPanel;
    private final ThreadStatsPanel statsPanel;
    private final ControlPanel controlPanel;
    private final ScheduledExecutorService executor;
    private ScheduledFuture<?> gameTask;
    private volatile boolean isRunning;
    
    public GameGuiController(
            GameOfLife game,
            GameBoardPanel boardPanel,
            ThreadStatsPanel statsPanel,
            ControlPanel controlPanel) {
        
        this.game = game;
        this.boardPanel = boardPanel;
        this.statsPanel = statsPanel;
        this.controlPanel = controlPanel;
        this.executor = Executors.newSingleThreadScheduledExecutor();
        
        initializeControlListeners();
    }
    
    public void initialize() {
        updateBoard();
    }
    
    private void initializeControlListeners() {
        controlPanel.setStartPauseButtonListener(e -> toggleSimulation());
        
        controlPanel.setResetButtonListener(e -> resetSimulation());
        
        controlPanel.setSpeedSliderListener(e -> {
            if (isRunning) {
                restartSimulation();
            }
        });
    }
    
    private void toggleSimulation() {
        if (isRunning) {
            stopSimulation();
        } else {
            startSimulation();
        }
    }
    
    private void startSimulation() {
        isRunning = true;
        controlPanel.toggleStartPauseButton(true);
        int delay = calculateDelay(controlPanel.getSpeed());
        gameTask = executor.scheduleAtFixedRate(this::nextGeneration, 0, delay, TimeUnit.MILLISECONDS);
    }
    
    private void stopSimulation() {
        isRunning = false;
        controlPanel.toggleStartPauseButton(false);
        if (gameTask != null) {
            gameTask.cancel(false);
        }
    }
    
    private void resetSimulation() {
        game.initializeBoard();
        updateBoard();
        controlPanel.updateGenerationLabel(0);
    }
    
    private void restartSimulation() {
        if (isRunning) {
            stopSimulation();
            startSimulation();
        }
    }
    
    private void nextGeneration() {
        try {
            if (game.hasReachedGenerationLimit()) {
                stopSimulation();
                showSimulationComplete();
                return;
            }
            
            game.computeNextGeneration();
            SwingUtilities.invokeLater(this::updateBoard);
        } catch (Exception e) {
            e.printStackTrace();
            stopSimulation();
            showError("Error during simulation: " + e.getMessage());
        }
    }
    
    private void updateBoard() {
        boardPanel.updateBoard(game.getCurrentState());
        controlPanel.updateGenerationLabel(game.getGeneration());
    }
    
    private int calculateDelay(int speed) {
        return MAX_DELAY - ((speed - 1) * (MAX_DELAY - MIN_DELAY) / 9);
    }
    
    private void showError(String message) {
        SwingUtilities.invokeLater(() -> 
            JOptionPane.showMessageDialog(null, 
                message, 
                "Error", 
                JOptionPane.ERROR_MESSAGE)
        );
    }
    
    private void showSimulationComplete() {
        SwingUtilities.invokeLater(() -> 
            JOptionPane.showMessageDialog(null,
                "Simulation completed after " + game.getMaxGenerations() + " generations.",
                "Simulation Complete",
                JOptionPane.INFORMATION_MESSAGE)
        );
    }
    
    public void shutdown() {
        stopSimulation();
        executor.shutdown();
        try {
            if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
} 