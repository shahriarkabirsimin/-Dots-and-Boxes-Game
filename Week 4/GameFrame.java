package dot.and.boxes;

import javax.swing.JFrame;

public class GameFrame extends JFrame {

    public GameFrame() {

        setTitle("Dots and Boxes");

        setSize(800, 600);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
           setLocationRelativeTo(null);

        setResizable(false);

        add(new GamePanel());

        setVisible(true);

    }

}
