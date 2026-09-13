package dot.and.boxes;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    public GameFrame() {

        setTitle("Dots and Boxes");

        setSize(800, 650);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(false);

        showPlayerSetup();

        setVisible(true);
    }


    // =========================
    // PLAYER SETUP
    // =========================

    public void showPlayerSetup() {

        getContentPane().removeAll();

        JPanel panel = new JPanel(null);

        panel.setBackground(
                new Color(245, 247, 250)
        );


        // =========================
        // TITLE
        // =========================

        JLabel title = new JLabel(
                "DOTS & BOXES",
                SwingConstants.CENTER
        );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        32
                )
        );

        title.setForeground(
                new Color(35, 45, 65)
        );

        title.setBounds(
                200,
                25,
                400,
                40
        );

        panel.add(title);


        JLabel subtitle = new JLabel(
                "Enter player information to start",
                SwingConstants.CENTER
        );

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        subtitle.setForeground(
                new Color(100, 105, 115)
        );

        subtitle.setBounds(
                200,
                65,
                400,
                25
        );

        panel.add(subtitle);


        // =========================
        // PLAYER 1 PANEL
        // =========================

        JPanel player1Panel =
                new JPanel(null);

        player1Panel.setBackground(
                Color.WHITE
        );

        player1Panel.setBorder(
                BorderFactory.createLineBorder(
                        new Color(220, 224, 230),
                        1
                )
        );

        player1Panel.setBounds(
                40,
                115,
                330,
                190
        );

        panel.add(player1Panel);


        JLabel p1Title =
                new JLabel("PLAYER 1");

        p1Title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        19
                )
        );

        p1Title.setForeground(
                new Color(45, 100, 220)
        );

        p1Title.setBounds(
                25,
                15,
                200,
                30
        );

        player1Panel.add(p1Title);


        JLabel p1NameLabel =
                new JLabel("Name:");

        p1NameLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        p1NameLabel.setBounds(
                25,
                60,
                60,
                25
        );

        player1Panel.add(p1NameLabel);


        JTextField p1Name =
                new JTextField();

        p1Name.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        p1Name.setBounds(
                90,
                57,
                210,
                30
        );

        player1Panel.add(p1Name);


        JLabel p1ColorLabel =
                new JLabel("Color:");

        p1ColorLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        p1ColorLabel.setBounds(
                25,
                110,
                60,
                25
        );

        player1Panel.add(p1ColorLabel);


        String[] colors = {
                "Blue",
                "Red",
                "Green",
                "Orange",
                "Purple",
                "Pink"
        };


        JComboBox<String> p1Color =
                new JComboBox<>(colors);

        p1Color.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        p1Color.setBounds(
                90,
                107,
                210,
                30
        );

        player1Panel.add(p1Color);


        // =========================
        // PLAYER 2 PANEL
        // =========================

        JPanel player2Panel =
                new JPanel(null);

        player2Panel.setBackground(
                Color.WHITE
        );

        player2Panel.setBorder(
                BorderFactory.createLineBorder(
                        new Color(220, 224, 230),
                        1
                )
        );

        player2Panel.setBounds(
                430,
                115,
                330,
                190
        );

        panel.add(player2Panel);


        JLabel p2Title =
                new JLabel("PLAYER 2");

        p2Title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        19
                )
        );

        p2Title.setForeground(
                new Color(220, 65, 65)
        );

        p2Title.setBounds(
                25,
                15,
                200,
                30
        );

        player2Panel.add(p2Title);


        JLabel p2NameLabel =
                new JLabel("Name:");

        p2NameLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        p2NameLabel.setBounds(
                25,
                60,
                60,
                25
        );

        player2Panel.add(p2NameLabel);


        JTextField p2Name =
                new JTextField();

        p2Name.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        p2Name.setBounds(
                90,
                57,
                210,
                30
        );

        player2Panel.add(p2Name);


        JLabel p2ColorLabel =
                new JLabel("Color:");

        p2ColorLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        p2ColorLabel.setBounds(
                25,
                110,
                60,
                25
        );

        player2Panel.add(p2ColorLabel);


        JComboBox<String> p2Color =
                new JComboBox<>(colors);

        p2Color.setSelectedItem("Red");

        p2Color.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        p2Color.setBounds(
                90,
                107,
                210,
                30
        );

        player2Panel.add(p2Color);


        // =========================
        // START GAME BUTTON
        // =========================

        JButton startButton =
                new JButton("START GAME");

        startButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        startButton.setForeground(
                Color.WHITE
        );

        startButton.setBackground(
        new Color(35, 100, 220)
);

startButton.setForeground(Color.WHITE);

startButton.setOpaque(true);

startButton.setContentAreaFilled(true);

startButton.setBorder(
        BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(25, 75, 180),
                        2
                ),
                BorderFactory.createEmptyBorder(
                        5, 15, 5, 15
                )
        )
);

        startButton.setFocusPainted(false);

        startButton.setBorderPainted(false);

        startButton.setBounds(
                275,
                345,
                250,
                50
        );

        panel.add(startButton);


        JLabel instruction =
                new JLabel(
                        "Choose different colors for both players.",
                        SwingConstants.CENTER
                );

        instruction.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        instruction.setForeground(
                new Color(110, 115, 125)
        );

        instruction.setBounds(
                200,
                405,
                400,
                25
        );

        panel.add(instruction);


        // =========================
        // START BUTTON ACTION
        // =========================

        startButton.addActionListener(e -> {

            String name1 =
                    p1Name.getText().trim();

            String name2 =
                    p2Name.getText().trim();


            if (name1.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter Player 1 name.",
                        "Name Required",
                        JOptionPane.WARNING_MESSAGE
                );

                p1Name.requestFocus();

                return;
            }


            if (name2.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter Player 2 name.",
                        "Name Required",
                        JOptionPane.WARNING_MESSAGE
                );

                p2Name.requestFocus();

                return;
            }


            String color1 =
                    (String) p1Color.getSelectedItem();

            String color2 =
                    (String) p2Color.getSelectedItem();


            if (color1.equals(color2)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Player 1 and Player 2 cannot have the same color.",
                        "Color Conflict",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            Player player1 =
                    new Player(
                            1,
                            name1,
                            color1
                    );


            Player player2 =
                    new Player(
                            2,
                            name2,
                            color2
                    );


            // =========================
            // START MAIN GAME
            // =========================

            getContentPane().removeAll();


            add(
                    new GamePanel(
                            player1,
                            player2
                    )
            );


            revalidate();

            repaint();
        });


        add(panel);

        revalidate();

        repaint();
    }
}

