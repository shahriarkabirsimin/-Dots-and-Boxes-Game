package dot.and.box;

import javax.swing.JFrame;

public class GameFrame extends JFrame {

    public GameFrame() {

        // Window Title
        setTitle("Dots and Boxes");

        // Window Size
        setSize(800, 600);

        // Window Screen-এর মাঝখানে খুলবে
        setLocationRelativeTo(null);

        // X Button চাপলে Program বন্ধ হবে
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Window Resize করা যাবে না
        setResizable(false);

        // Game Panel Window-এর ভিতরে যোগ করা
        add(new GamePanel());

        // Window দেখানো
        setVisible(true);
    }

}