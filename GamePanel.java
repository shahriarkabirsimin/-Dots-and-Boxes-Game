package dot.and.box;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel {

    // Constructor
    public GamePanel() {

        // Panel Background Color
        setBackground(Color.WHITE);

        // Mouse Listener
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                System.out.println("Mouse Clicked");
                System.out.println("X Coordinate : " + e.getX());
                System.out.println("Y Coordinate : " + e.getY());

            }

        });

    }

    // Draw the Game Board
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        int size = 4;       // 4 × 4 Dots
        int gap = 100;      // Distance between dots
        int startX = 100;   // Starting X Position
        int startY = 100;   // Starting Y Position

        for (int row = 0; row < size; row++) {

            for (int col = 0; col < size; col++) {

                g.fillOval(
                        startX + (col * gap),
                        startY + (row * gap),
                        10,
                        10
                );

            }

        }

    }

}