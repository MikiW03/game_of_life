package gui.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.util.ThreadColorGenerator;

public class ThreadStatsPanel extends JPanel {
    private static final int PANEL_WIDTH = 300;
    private static final Color BACKGROUND_COLOR = new Color(240, 240, 240);
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 14);
    private static final Font STATS_FONT = new Font("Monospace", Font.PLAIN, 12);
    
    private final int threadCount;
    private final int rowsPerThread;
    private final int remainingRows;
    private final JLabel[] threadLabels;
    private final int cols;
    
    public ThreadStatsPanel(int threadCount, int totalRows, int totalCols) {
        this.threadCount = threadCount;
        this.rowsPerThread = totalRows / threadCount;
        this.remainingRows = totalRows % threadCount;
        this.cols = totalCols;
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(BACKGROUND_COLOR);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        setPreferredSize(new Dimension(PANEL_WIDTH, -1));
        setMinimumSize(new Dimension(PANEL_WIDTH, 100));
        
        JLabel titleLabel = new JLabel("Thread Statistics");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(titleLabel);
        add(Box.createVerticalStrut(10));
        
        threadLabels = new JLabel[threadCount];
        for (int i = 0; i < threadCount; i++) {
            Color threadColor = ThreadColorGenerator.generateThreadColor(i, threadCount);
            int threadRows = rowsPerThread + (i < remainingRows ? 1 : 0);
            
            JPanel threadPanel = createThreadPanel(i, threadColor, threadRows);
            threadPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            add(threadPanel);
            add(Box.createVerticalStrut(5));
        }
    }
    
    private JPanel createThreadPanel(int threadId, Color color, int rows) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panel.setBackground(BACKGROUND_COLOR);
        
        JPanel colorSquare = new JPanel();
        colorSquare.setPreferredSize(new Dimension(15, 15));
        colorSquare.setBackground(color);
        colorSquare.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        int startRow = threadId * rowsPerThread + Math.min(threadId, remainingRows);
        int endRow = startRow + rowsPerThread + (threadId < remainingRows ? 1 : 0) - 1;
        
        String threadInfo = String.format("Thread#%2d: rows:%2d-%-2d(%d) cols:0-%d(%d)",
            threadId, 
            startRow, 
            endRow,
            endRow - startRow + 1,
            cols - 1,
            cols
        );
        
        JLabel infoLabel = new JLabel(threadInfo);
        infoLabel.setFont(STATS_FONT);
        
        panel.add(colorSquare);
        panel.add(infoLabel);
        
        threadLabels[threadId] = infoLabel;
        return panel;
    }
} 