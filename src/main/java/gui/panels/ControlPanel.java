package gui.panels;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    private static final Color BACKGROUND_COLOR = new Color(240, 240, 240);
    private static final int MIN_SPEED = 1;
    private static final int MAX_SPEED = 10;
    private static final int DEFAULT_SPEED = 5;
    
    private final JButton startPauseButton;
    private final JButton resetButton;
    private final JSlider speedSlider;
    private final JLabel generationLabel;
    
    public ControlPanel() {
        setBackground(BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
        
        startPauseButton = new JButton("Start");
        
        resetButton = new JButton("Reset");
        
        JPanel speedPanel = new JPanel(new BorderLayout(5, 0));
        speedPanel.setBackground(BACKGROUND_COLOR);
        
        JLabel speedLabel = new JLabel("Speed:");
        speedSlider = new JSlider(JSlider.HORIZONTAL, MIN_SPEED, MAX_SPEED, DEFAULT_SPEED);
        speedSlider.setBackground(BACKGROUND_COLOR);
        speedSlider.setMajorTickSpacing(1);
        speedSlider.setPaintTicks(true);
        speedSlider.setSnapToTicks(true);
        
        speedPanel.add(speedLabel, BorderLayout.WEST);
        speedPanel.add(speedSlider, BorderLayout.CENTER);
        
        generationLabel = new JLabel("Generation: 0");
        
        add(startPauseButton);
        add(resetButton);
        add(speedPanel);
        add(generationLabel);
    }
    
    public void setStartPauseButtonListener(ActionListener listener) {
        startPauseButton.addActionListener(listener);
    }
    
    public void setResetButtonListener(ActionListener listener) {
        resetButton.addActionListener(listener);
    }
    
    public void setSpeedSliderListener(ChangeListener listener) {
        speedSlider.addChangeListener(listener);
    }
    
    public void updateGenerationLabel(int generation) {
        generationLabel.setText("Generation: " + generation);
    }
    
    public void toggleStartPauseButton(boolean isRunning) {
        startPauseButton.setText(isRunning ? "Pause" : "Start");
    }
    
    public int getSpeed() {
        return speedSlider.getValue();
    }
    
    public void setSpeed(int speed) {
        speedSlider.setValue(speed);
    }
    
    public void enableControls(boolean enabled) {
        startPauseButton.setEnabled(enabled);
        resetButton.setEnabled(enabled);
        speedSlider.setEnabled(enabled);
    }
} 