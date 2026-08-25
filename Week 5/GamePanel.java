package dot.and.boxes;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel {

    private Board board;

    // Current player
    // 1 = Player 1
    // 2 = Player 2
    private int currentPlayer = 1;

    // Blink status
    private boolean blinkOn = true;

    // Blink timer
    private Timer blinkTimer;

    // Distance between dots
    private final int DOT_SPACING = 100;

    // Dot radius
    private final int DOT_RADIUS = 6;

    // Mouse click tolerance
    private final int CLICK_TOLERANCE = 20;

    public GamePanel() {

        board = new Board();

        setBackground(Color.WHITE);

        // Start blinking timer
        blinkTimer = new Timer(500, e -> {

            blinkOn = !blinkOn;

            repaint();
        });

        blinkTimer.start();

        // Mouse listener
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                handleMouseClick(
                        e.getX(),
                        e.getY()
                );
            }
        });
    }

    // Calculate board starting X
    private int getStartX() {

        int boardWidth =
                (board.getCols() - 1)
                * DOT_SPACING;

        return (getWidth() - boardWidth) / 2;
    }

    // Calculate board starting Y
    private int getStartY() {

        int boardHeight =
                (board.getRows() - 1)
                * DOT_SPACING;

        return (getHeight() - boardHeight) / 2
                + 30;
    }

    // Handle mouse click
    private void handleMouseClick(
            int mouseX,
            int mouseY) {

        System.out.println(
                "Player "
                + currentPlayer
                + " clicked: X = "
                + mouseX
                + ", Y = "
                + mouseY
        );

        // Check horizontal line
        boolean lineSelected =
                checkHorizontalLine(
                        mouseX,
                        mouseY
                );

        // If not horizontal,
        // check vertical
        if (!lineSelected) {

            lineSelected =
                    checkVerticalLine(
                            mouseX,
                            mouseY
                    );
        }

        // Valid move
        if (lineSelected) {

            // Check completed boxes
            boolean boxCompleted =
                    checkCompletedBoxes();

            /*
             * If no box is completed,
             * change player.
             *
             * If box is completed,
             * same player continues.
             */
            if (!boxCompleted) {

                switchPlayer();
            }

            repaint();
        }
    }

    // Check horizontal lines
    private boolean checkHorizontalLine(
            int mouseX,
            int mouseY) {

        int startX = getStartX();
        int startY = getStartY();

        for (int row = 0;
                row < board.getRows();
                row++) {

            for (int col = 0;
                    col < board.getCols() - 1;
                    col++) {

                int x1 =
                        startX
                        + col * DOT_SPACING;

                int x2 =
                        startX
                        + (col + 1)
                        * DOT_SPACING;

                int y =
                        startY
                        + row * DOT_SPACING;

                if (mouseX >=
                        x1 - CLICK_TOLERANCE
                        && mouseX <=
                        x2 + CLICK_TOLERANCE
                        && mouseY >=
                        y - CLICK_TOLERANCE
                        && mouseY <=
                        y + CLICK_TOLERANCE) {

                    return board
                            .selectHorizontalLine(
                                    row,
                                    col,
                                    currentPlayer
                            );
                }
            }
        }

        return false;
    }

    // Check vertical lines
    private boolean checkVerticalLine(
            int mouseX,
            int mouseY) {

        int startX = getStartX();
        int startY = getStartY();

        for (int row = 0;
                row < board.getRows() - 1;
                row++) {

            for (int col = 0;
                    col < board.getCols();
                    col++) {

                int x =
                        startX
                        + col * DOT_SPACING;

                int y1 =
                        startY
                        + row * DOT_SPACING;

                int y2 =
                        startY
                        + (row + 1)
                        * DOT_SPACING;

                if (mouseX >=
                        x - CLICK_TOLERANCE
                        && mouseX <=
                        x + CLICK_TOLERANCE
                        && mouseY >=
                        y1 - CLICK_TOLERANCE
                        && mouseY <=
                        y2 + CLICK_TOLERANCE) {

                    return board
                            .selectVerticalLine(
                                    row,
                                    col,
                                    currentPlayer
                            );
                }
            }
        }

        return false;
    }

    // Check all boxes
    private boolean checkCompletedBoxes() {

        boolean boxCompleted = false;

        for (int row = 0;
                row < board.getRows() - 1;
                row++) {

            for (int col = 0;
                    col < board.getCols() - 1;
                    col++) {

                if (board.isBoxCompleted(
                        row,
                        col)) {

                    // Check box owner
                    if (board.getBoxOwner(
                            row,
                            col) == 0) {

                        board.assignBox(
                                row,
                                col,
                                currentPlayer
                        );

                        System.out.println(
                                "Player "
                                + currentPlayer
                                + " completed box at row "
                                + row
                                + ", column "
                                + col
                        );

                        boxCompleted = true;
                    }
                }
            }
        }

        return boxCompleted;
    }

    // Change player
    private void switchPlayer() {

        if (currentPlayer == 1) {

            currentPlayer = 2;

        } else {

            currentPlayer = 1;
        }

        System.out.println(
                "Now Player "
                + currentPlayer
                + "'s turn."
        );
    }

    @Override
    protected void paintComponent(
            Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 =
                (Graphics2D) g.create();

        // Smooth lines
        g2.setStroke(
                new BasicStroke(4)
        );

        // Current player
        drawCurrentPlayer(g2);

        // Completed boxes
        drawCompletedBoxes(g2);

        // Horizontal lines
        drawHorizontalLines(g2);

        // Vertical lines
        drawVerticalLines(g2);

        // Dots
        drawDots(g2);

        g2.dispose();
    }

    // Display current player
    private void drawCurrentPlayer(
            Graphics2D g2) {

        g2.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );

        if (currentPlayer == 1) {

            g2.setColor(Color.BLUE);

            g2.drawString(
                    "Current Turn: Player 1",
                    300,
                    50
            );

        } else {

            g2.setColor(Color.RED);

            g2.drawString(
                    "Current Turn: Player 2",
                    300,
                    50
            );
        }
    }

    // Draw completed boxes
    private void drawCompletedBoxes(
            Graphics2D g2) {

        for (int row = 0;
                row < board.getRows() - 1;
                row++) {

            for (int col = 0;
                    col < board.getCols() - 1;
                    col++) {

                int owner =
                        board.getBoxOwner(
                                row,
                                col
                        );

                int x =
                        getStartX()
                        + col * DOT_SPACING
                        + 5;

                int y =
                        getStartY()
                        + row * DOT_SPACING
                        + 5;

                // Player 1 box
                if (owner == 1) {

                    g2.setColor(
                            Color.LIGHT_GRAY
                    );

                    g2.fillRect(
                            x,
                            y,
                            DOT_SPACING - 10,
                            DOT_SPACING - 10
                    );

                    g2.setColor(
                            Color.BLUE
                    );

                    g2.setFont(
                            new Font(
                                    "Arial",
                                    Font.BOLD,
                                    20
                            )
                    );

                    g2.drawString(
                            "P1",
                            x + 40,
                            y + 55
                    );
                }

                // Player 2 box
                else if (owner == 2) {

                    g2.setColor(
                            Color.GRAY
                    );

                    g2.fillRect(
                            x,
                            y,
                            DOT_SPACING - 10,
                            DOT_SPACING - 10
                    );

                    g2.setColor(
                            Color.RED
                    );

                    g2.setFont(
                            new Font(
                                    "Arial",
                                    Font.BOLD,
                                    20
                            )
                    );

                    g2.drawString(
                            "P2",
                            x + 40,
                            y + 55
                    );
                }
            }
        }
    }

    // Draw horizontal lines
    private void drawHorizontalLines(
            Graphics2D g2) {

        int startX = getStartX();
        int startY = getStartY();

        for (int row = 0;
                row < board.getRows();
                row++) {

            for (int col = 0;
                    col < board.getCols() - 1;
                    col++) {

                if (board.isHorizontalLineSelected(
                        row,
                        col)) {

                    int x1 =
                            startX
                            + col * DOT_SPACING;

                    int x2 =
                            startX
                            + (col + 1)
                            * DOT_SPACING;

                    int y =
                            startY
                            + row * DOT_SPACING;

                    int owner =
                            board.getHorizontalLineOwner(
                                    row,
                                    col
                            );

                    // Last move
                    if (board.isLastHorizontalLine(
                            row,
                            col)) {

                        if (owner == 1) {

                            if (blinkOn) {

                                g2.setColor(
                                        Color.BLUE
                                );

                            } else {

                                g2.setColor(
                                        new Color(
                                                150,
                                                180,
                                                255
                                        )
                                );
                            }

                        } else if (owner == 2) {

                            if (blinkOn) {

                                g2.setColor(
                                        Color.RED
                                );

                            } else {

                                g2.setColor(
                                        new Color(
                                                255,
                                                150,
                                                150
                                        )
                                );
                            }

                        }

                    } else {

                        // Normal player color
                        if (owner == 1) {

                            g2.setColor(
                                    Color.BLUE
                            );

                        } else if (owner == 2) {

                            g2.setColor(
                                    Color.RED
                            );
                        }
                    }

                    g2.drawLine(
                            x1,
                            y,
                            x2,
                            y
                    );
                }
            }
        }
    }

    // Draw vertical lines
    private void drawVerticalLines(
            Graphics2D g2) {

        int startX = getStartX();
        int startY = getStartY();

        for (int row = 0;
                row < board.getRows() - 1;
                row++) {

            for (int col = 0;
                    col < board.getCols();
                    col++) {

                if (board.isVerticalLineSelected(
                        row,
                        col)) {

                    int x =
                            startX
                            + col * DOT_SPACING;

                    int y1 =
                            startY
                            + row * DOT_SPACING;

                    int y2 =
                            startY
                            + (row + 1)
                            * DOT_SPACING;

                    int owner =
                            board.getVerticalLineOwner(
                                    row,
                                    col
                            );

                    // Last move
                    if (board.isLastVerticalLine(
                            row,
                            col)) {

                        if (owner == 1) {

                            if (blinkOn) {

                                g2.setColor(
                                        Color.BLUE
                                );

                            } else {

                                g2.setColor(
                                        new Color(
                                                150,
                                                180,
                                                255
                                        )
                                );
                            }

                        } else if (owner == 2) {

                            if (blinkOn) {

                                g2.setColor(
                                        Color.RED
                                );

                            } else {

                                g2.setColor(
                                        new Color(
                                                255,
                                                150,
                                                150
                                        )
                                );
                            }

                        }

                    } else {

                        // Normal player color
                        if (owner == 1) {

                            g2.setColor(
                                    Color.BLUE
                            );

                        } else if (owner == 2) {

                            g2.setColor(
                                    Color.RED
                            );
                        }
                    }

                    g2.drawLine(
                            x,
                            y1,
                            x,
                            y2
                    );
                }
            }
        }
    }

    // Draw dots
    private void drawDots(
            Graphics2D g2) {

        int startX = getStartX();
        int startY = getStartY();

        g2.setColor(Color.BLACK);

        for (int row = 0;
                row < board.getRows();
                row++) {

            for (int col = 0;
                    col < board.getCols();
                    col++) {

                int x =
                        startX
                        + col * DOT_SPACING;

                int y =
                        startY
                        + row * DOT_SPACING;

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