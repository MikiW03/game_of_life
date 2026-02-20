package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import gui.controller.GameGuiController;
import gui.panels.ControlPanel;
import gui.panels.GameBoardPanel;
import gui.panels.ThreadStatsPanel;

public class GameWindow extends JFrame {
    private static final String TITLE = "Game of Life";
    private static final Color BACKGROUND_COLOR = new Color(240, 240, 240);
    
    private final GameBoardPanel boardPanel;
    private final ThreadStatsPanel statsPanel;
    private final ControlPanel controlPanel;
    private final GameGuiController controller;
    
    public GameWindow(
            GameBoardPanel boardPanel,
            ThreadStatsPanel statsPanel,
            ControlPanel controlPanel,
            GameGuiController controller) {
            
        this.boardPanel = boardPanel;
        this.statsPanel = statsPanel;
        this.controlPanel = controlPanel;
        this.controller = controller;
        
        initializeWindow();
        layoutComponents();
        setupWindowListeners();
    }
    
    private void initializeWindow() {
        setTitle(TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(BACKGROUND_COLOR);
        setResizable(true);
    }
    
    private void layoutComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(BACKGROUND_COLOR);
        statsPanel.setPreferredSize(new Dimension(300, statsPanel.getPreferredSize().height));
        rightPanel.add(statsPanel, BorderLayout.CENTER);
        
        mainPanel.add(boardPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }
    
    private void setupWindowListeners() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.shutdown();
            }
        });
    }
}