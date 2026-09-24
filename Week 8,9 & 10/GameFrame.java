package dot.and.boxes;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    public GameFrame() {

        setTitle("Dots and Boxes");

        setSize(800, 650);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setResizable(false);

        showPlayerSetup();

        setVisible(true);
    }

    // =========================
    // PLAYER SETUP
    // =========================

    public void showPlayerSetup() {

        getContentPane().removeAll();

        JPanel panel =
                new JPanel(null);

        panel.setBackground(
                new Color(
                        245,
                        247,
                        250
                )
        );

        // =========================
        // TITLE
        // =========================

        JLabel title =
                new JLabel(
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
                new Color(
                        35,
                        45,
                        65
                )
        );

        title.setBounds(
                200,
                20,
                400,
                40
        );

        panel.add(title);

        JLabel subtitle =
                new JLabel(
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
                new Color(
                        100,
                        105,
                        115
                )
        );

        subtitle.setBounds(
                200,
                60,
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
                        new Color(
                                220,
                                224,
                                230
                        ),
                        1
                )
        );

        player1Panel.setBounds(
                30,
                105,
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
                new Color(
                        45,
                        100,
                        220
                )
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

        p1NameLabel.setBounds(
                25,
                60,
                60,
                25
        );

        player1Panel.add(p1NameLabel);

        JTextField p1Name =
                new JTextField();

        p1Name.setBounds(
                90,
                57,
                210,
                30
        );

        player1Panel.add(p1Name);

        JLabel p1ColorLabel =
                new JLabel("Color:");

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
                        new Color(
                                220,
                                224,
                                230
                        ),
                        1
                )
        );

        player2Panel.setBounds(
                440,
                105,
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
                new Color(
                        220,
                        65,
                        65
                )
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

        p2NameLabel.setBounds(
                25,
                60,
                60,
                25
        );

        player2Panel.add(p2NameLabel);

        JTextField p2Name =
                new JTextField();

        p2Name.setBounds(
                90,
                57,
                210,
                30
        );

        player2Panel.add(p2Name);

        JLabel p2ColorLabel =
                new JLabel("Color:");

        p2ColorLabel.setBounds(
                25,
                110,
                60,
                25
        );

        player2Panel.add(p2ColorLabel);

        JComboBox<String> p2Color =
                new JComboBox<>(colors);

        p2Color.setSelectedItem(
                "Red"
        );

        p2Color.setBounds(
                90,
                107,
                210,
                30
        );

        player2Panel.add(p2Color);

        // =========================
        // GAME SETTINGS
        // =========================

        JLabel boardLabel =
                new JLabel("Board Size:");

        boardLabel.setBounds(
                80,
                320,
                100,
                25
        );

        panel.add(boardLabel);

        String[] boardSizes = {
                "3 x 3",
                "4 x 4",
                "5 x 5"
        };

        JComboBox<String> boardSize =
                new JComboBox<>(
                        boardSizes
                );

        boardSize.setSelectedItem(
                "4 x 4"
        );

        boardSize.setBounds(
                175,
                317,
                110,
                30
        );

        panel.add(boardSize);

        JLabel modeLabel =
                new JLabel("Game Mode:");

        modeLabel.setBounds(
                310,
                320,
                100,
                25
        );

        panel.add(modeLabel);

        String[] modes = {
                "Two Players",
                "Single Player"
        };

        JComboBox<String> gameMode =
                new JComboBox<>(modes);

        gameMode.setBounds(
                405,
                317,
                130,
                30
        );

        panel.add(gameMode);

        JLabel difficultyLabel =
                new JLabel("Difficulty:");

        difficultyLabel.setBounds(
                555,
                320,
                80,
                25
        );

        panel.add(difficultyLabel);

        String[] difficulties = {
                "Easy",
                "Medium",
                "Hard"
        };

        JComboBox<String> difficulty =
                new JComboBox<>(
                        difficulties
                );

        difficulty.setBounds(
                630,
                317,
                100,
                30
        );

        panel.add(difficulty);

        // =========================
        // START BUTTON
        // =========================

        JButton startButton =
                new JButton(
                        "START GAME"
                );

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
                new Color(
                        35,
                        100,
                        220
                )
        );

        startButton.setOpaque(true);

        startButton.setContentAreaFilled(true);

        startButton.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                25,
                                75,
                                175
                        ),
                        2
                )
        );

        startButton.setFocusPainted(false);

        startButton.setRolloverEnabled(true);

        startButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        startButton.setBounds(
                275,
                370,
                250,
                50
        );

        panel.add(startButton);

        // =========================
        // INSTRUCTION
        // =========================

        JLabel instruction =
                new JLabel(
                        "Choose board size and game mode.",
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
                new Color(
                        110,
                        115,
                        125
                )
        );

        instruction.setBounds(
                200,
                430,
                400,
                25
        );

        panel.add(instruction);

        // =========================
        // GAME MODE ACTION
        // =========================

        gameMode.addActionListener(e -> {

            boolean singlePlayer =
                    gameMode
                            .getSelectedItem()
                            .equals(
                                    "Single Player"
                            );

            p2Name.setEnabled(
                    !singlePlayer
            );

            p2Color.setEnabled(
                    !singlePlayer
            );

            difficulty.setEnabled(
                    singlePlayer
            );

            if (singlePlayer) {

                p2Name.setText(
                        "Computer"
                );

            } else {

                p2Name.setText("");
            }
        });

        difficulty.setEnabled(false);

        // =========================
        // START BUTTON ACTION
        // =========================

        startButton.addActionListener(e -> {

            String name1 =
                    p1Name
                            .getText()
                            .trim();

            String name2 =
                    p2Name
                            .getText()
                            .trim();

            boolean singlePlayer =
                    gameMode
                            .getSelectedItem()
                            .equals(
                                    "Single Player"
                            );

            // Player 1 name
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

            // Player 2 name
            if (!singlePlayer
                    && name2.isEmpty()) {

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
                    (String)
                    p1Color.getSelectedItem();

            String color2 =
                    (String)
                    p2Color.getSelectedItem();

            // Color conflict
            if (!singlePlayer
                    && color1.equals(color2)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Player 1 and Player 2 cannot have the same color.",
                        "Color Conflict",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            // Computer setup
            if (singlePlayer) {

                name2 = "Computer";

                if (color1.equals(color2)) {

                    p2Color.setSelectedItem(
                            "Red"
                    );

                    color2 =
                            (String)
                            p2Color
                                    .getSelectedItem();

                    if (color1.equals(color2)) {

                        p2Color.setSelectedItem(
                                "Green"
                        );

                        color2 =
                                (String)
                                p2Color
                                        .getSelectedItem();
                    }
                }
            }

            // =========================
            // BOARD SIZE
            // =========================

            int size = 4;

            String selectedSize =
                    (String)
                    boardSize
                            .getSelectedItem();

            if (selectedSize.equals(
                    "3 x 3")) {

                size = 3;

            } else if (
                    selectedSize.equals(
                            "5 x 5")) {

                size = 5;
            }

            // =========================
            // DIFFICULTY
            // =========================

            String selectedDifficulty =
                    (String)
                    difficulty
                            .getSelectedItem();

            if (selectedDifficulty == null) {

                selectedDifficulty =
                        "Easy";
            }

            // =========================
            // CREATE PLAYERS
            // =========================

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
            // START GAME
            // =========================

            getContentPane()
                    .removeAll();

            add(
                    new GamePanel(
                            player1,
                            player2,
                            size,
                            singlePlayer,
                            selectedDifficulty
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