package dot.and.boxes;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel {

    private Board board;

    // Board position
    private final int START_X = 150;
    private final int START_Y = 100;

    // Distance between dots
    private final int DOT_SPACING = 100;

    // Dot radius
    private final int DOT_RADIUS = 6;

    // Area in which mouse click is accepted
    private final int CLICK_TOLERANCE = 20;

    public GamePanel() {

        board = new Board();

        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                handleMouseClick(e.getX(), e.getY());
            }
        });
    }

    // Handle mouse click
    private void handleMouseClick(int mouseX, int mouseY) {
        
        System.out.println("Mouse X: " + mouseX);
        System.out.println("Mouse Y: " + mouseY);

        // First check horizontal lines
        boolean lineSelected = checkHorizontalLine(mouseX, mouseY);

        // If horizontal line was not selected,
        // check vertical lines
        if (!lineSelected) {

            lineSelected = checkVerticalLine(mouseX, mouseY);
        }

        // Redraw the board
        if (lineSelected) {

            repaint();
        }
    }

    // Check horizontal lines
    private boolean checkHorizontalLine(int mouseX, int mouseY) {

        for (int row = 0; row < board.getRows(); row++) {

            for (int col = 0; col < board.getCols() - 1; col++) {

                int x1 = START_X + col * DOT_SPACING;
                int x2 = START_X + (col + 1) * DOT_SPACING;

                int y = START_Y + row * DOT_SPACING;

                // Check if mouse is near the horizontal line
                if (mouseX >= x1 - CLICK_TOLERANCE
                        && mouseX <= x2 + CLICK_TOLERANCE
                        && mouseY >= y - CLICK_TOLERANCE
                        && mouseY <= y + CLICK_TOLERANCE) {

                    return board.selectHorizontalLine(row, col);
                }
            }
        }

        return false;
    }

    // Check vertical lines
    private boolean checkVerticalLine(int mouseX, int mouseY) {

        for (int row = 0; row < board.getRows() - 1; row++) {

            for (int col = 0; col < board.getCols(); col++) {

                int x = START_X + col * DOT_SPACING;

                int y1 = START_Y + row * DOT_SPACING;
                int y2 = START_Y + (row + 1) * DOT_SPACING;

                // Check if mouse is near the vertical line
                if (mouseX >= x - CLICK_TOLERANCE
                        && mouseX <= x + CLICK_TOLERANCE
                        && mouseY >= y1 - CLICK_TOLERANCE
                        && mouseY <= y2 + CLICK_TOLERANCE) {

                    return board.selectVerticalLine(row, col);
                }
            }
        }

        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        // Make lines smoother
        g2.setStroke(new BasicStroke(4));

        // Draw selected horizontal lines
        drawHorizontalLines(g2);

        // Draw selected vertical lines
        drawVerticalLines(g2);

        // Draw dots
        drawDots(g2);

        g2.dispose();
    }

    // Draw horizontal lines
    private void drawHorizontalLines(Graphics2D g2) {

        g2.setColor(Color.BLUE);

        for (int row = 0; row < board.getRows(); row++) {

            for (int col = 0; col < board.getCols() - 1; col++) {

                if (board.isHorizontalLineSelected(row, col)) {

                    int x1 = START_X + col * DOT_SPACING;
                    int x2 = START_X + (col + 1) * DOT_SPACING;

                    int y = START_Y + row * DOT_SPACING;

                    g2.drawLine(x1, y, x2, y);
                }
            }
        }
    }

    // Draw vertical lines
    private void drawVerticalLines(Graphics2D g2) {

        g2.setColor(Color.BLUE);

        for (int row = 0; row < board.getRows() - 1; row++) {

            for (int col = 0; col < board.getCols(); col++) {

                if (board.isVerticalLineSelected(row, col)) {

                    int x = START_X + col * DOT_SPACING;

                    int y1 = START_Y + row * DOT_SPACING;
                    int y2 = START_Y + (row + 1) * DOT_SPACING;

                    g2.drawLine(x, y1, x, y2);
                }
            }
        }
    }

    // Draw dots
    private void drawDots(Graphics2D g2) {

        g2.setColor(Color.BLACK);

        for (int row = 0; row < board.getRows(); row++) {

            for (int col = 0; col < board.getCols(); col++) {

                int x = START_X + col * DOT_SPACING;
                int y = START_Y + row * DOT_SPACING;

                g2.fillOval(
                        x - DOT_RADIUS,
                        y - DOT_RADIUS,
                        DOT_RADIUS * 2,
                        DOT_RADIUS * 2
                );
            }
        }
    }
}