package gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import gui.util.ThreadColorGenerator;

public class GameBoardPanel extends JPanel {
    private static final int CELL_SIZE = 15;
    private static final int GRID_LINE_WIDTH = 1;
    private static final Color GRID_COLOR = Color.GRAY;
    private static final Color CELL_COLOR = Color.BLACK;
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    
    private final int rows;
    private final int cols;
    private final int threadCount;
    private final Color[] threadColors;
    private BufferedImage buffer;
    private final int width;
    private final int height;
    
    public GameBoardPanel(int rows, int cols, int threadCount) {
        this.rows = rows;
        this.cols = cols;
        this.threadCount = threadCount;
        this.width = cols * CELL_SIZE;
        this.height = rows * CELL_SIZE;
        
        this.threadColors = new Color[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threadColors[i] = ThreadColorGenerator.generateThreadColor(i, threadCount);
        }
        
        setPreferredSize(new Dimension(width, height));
        setBackground(BACKGROUND_COLOR);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (buffer == null || buffer.getWidth() != getWidth() || 
            buffer.getHeight() != getHeight()) {
            createBuffer();
        }
        
        g.drawImage(buffer, 0, 0, null);
    }
    
    private void createBuffer() {
        buffer = new BufferedImage(super.getWidth(), super.getHeight(),
            BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buffer.createGraphics();
        
        g2d.setColor(super.getBackground());
        g2d.fillRect(0, 0, super.getWidth(), super.getHeight());
        
        drawThreadAreas(g2d);
        
        drawGrid(g2d);
        
        g2d.dispose();
    }
    
    private void drawThreadAreas(Graphics2D g2d) {
        int rowsPerThread = rows / threadCount;
        int remainingRows = rows % threadCount;
        int startRow = 0;
        
        for (int i = 0; i < threadCount; i++) {
            int threadRows = rowsPerThread + (i < remainingRows ? 1 : 0);
            int endRow = startRow + threadRows;
            
            g2d.setColor(threadColors[i]);
            g2d.fillRect(0, startRow * CELL_SIZE, 
                width, threadRows * CELL_SIZE);
            
            startRow = endRow;
        }
    }
    
    private void drawGrid(Graphics2D g2d) {
        g2d.setColor(GRID_COLOR);
        
        for (int x = 0; x <= cols; x++) {
            int xPos = x * CELL_SIZE;
            g2d.drawLine(xPos, 0, xPos, height);
        }
        
        for (int y = 0; y <= rows; y++) {
            int yPos = y * CELL_SIZE;
            g2d.drawLine(0, yPos, width, yPos);
        }
    }
    
    public void updateBoard(boolean[][] state) {
        if (buffer == null) {
            createBuffer();
        }
        
        Graphics2D g2d = buffer.createGraphics();
        
        g2d.setColor(BACKGROUND_COLOR);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        drawThreadAreas(g2d);
        
        g2d.setColor(CELL_COLOR);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (state[row][col]) {
                    int x = col * CELL_SIZE + GRID_LINE_WIDTH;
                    int y = row * CELL_SIZE + GRID_LINE_WIDTH;
                    g2d.fillRect(x, y, 
                        CELL_SIZE - 2*GRID_LINE_WIDTH, 
                        CELL_SIZE - 2*GRID_LINE_WIDTH);
                }
            }
        }
        
        drawGrid(g2d);
        
        g2d.dispose();
        repaint();
    }
}