package dot.and.boxes;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePanel extends JPanel {

    private Board board;

    private Player player1;
    private Player player2;

    private int currentPlayer = 1;

    // =====================================
    // GAME SETTINGS
    // =====================================

    private int boardSize;

    private boolean singlePlayer;

    private String difficulty;

    private Random random =
            new Random();

    // =====================================
    // BOARD POSITION
    // =====================================

    private int START_X;

    private int START_Y;

    private int DOT_SPACING;

    private final int DOT_RADIUS = 6;

    private final int CLICK_TOLERANCE = 18;

    // =====================================
    // LAST MOVE
    // =====================================

    private boolean lastLineHorizontal =
            false;

    private int lastLineRow = -1;

    private int lastLineCol = -1;

    private boolean blinkVisible = true;

    private Timer blinkTimer;

    // =====================================
    // GAME FINISHED
    // =====================================

    private boolean gameFinished = false;

    private Timer winnerTimer;

    private int animationStep = 0;

    private int[] confettiX =
            new int[70];

    private int[] confettiY =
            new int[70];

    private int[] confettiSpeed =
            new int[70];

    // =====================================
    // AI TIMER
    // =====================================

    private Timer aiTimer;

    // =====================================
    // CONSTRUCTOR
    // =====================================

    public GamePanel(
            Player player1,
            Player player2,
            int boardSize,
            boolean singlePlayer,
            String difficulty) {

        this.player1 = player1;

        this.player2 = player2;

        this.boardSize = boardSize;

        this.singlePlayer =
                singlePlayer;

        this.difficulty =
                difficulty;

        board =
                new Board(boardSize);

        calculateBoardPosition();

        setBackground(
                new Color(
                        245,
                        247,
                        250
                )
        );

        // =====================================
        // BLINK TIMER
        // =====================================

        blinkTimer =
                new Timer(
                        400,
                        e -> {

                            if (!gameFinished) {

                                blinkVisible =
                                        !blinkVisible;

                                repaint();
                            }
                        }
                );

        blinkTimer.start();

        // =====================================
        // CONFETTI
        // =====================================

        for (int i = 0;
                i < confettiX.length;
                i++) {

            confettiX[i] =
                    random.nextInt(800);

            confettiY[i] =
                    random.nextInt(650);

            confettiSpeed[i] =
                    2 + random.nextInt(4);
        }

        // =====================================
        // MOUSE
        // =====================================

        addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            MouseEvent e) {

                        if (gameFinished) {
                            return;
                        }

                        if (singlePlayer &&
                                currentPlayer == 2) {

                            return;
                        }

                        handleMouseClick(
                                e.getX(),
                                e.getY()
                        );
                    }
                }
        );
    }

    // =====================================
    // CALCULATE BOARD POSITION
    // =====================================

    private void calculateBoardPosition() {

        /*
         * 5x5 = 4 gaps × 60 = 240
         * 4x4 = 3 gaps × 80 = 240
         * 3x3 = 2 gaps × 80 = 160
         */

        if (boardSize == 5) {

            DOT_SPACING = 60;

        } else {

            DOT_SPACING = 80;
        }

        int boardWidth =
                (boardSize - 1)
                * DOT_SPACING;

        /*
         * Board area:
         *
         * X = 180 to 620
         * Y = 190 to 490
         *
         * Height = 300
         *
         * For 5x5 / 4x4:
         * board height = 240
         *
         * (300 - 240) / 2 = 30
         *
         * 190 + 30 = 220
         *
         * Therefore board is perfectly
         * centered vertically.
         */

        START_X =
                180
                + (440 - boardWidth) / 2;

        START_Y =
                190
                + (300 - boardWidth) / 2;
    }

    // =====================================
    // SOUND SYSTEM
    // =====================================

    private void playSound(
            double frequency,
            int duration) {

        try {

            float sampleRate =
                    44100;

            byte[] buffer =
                    new byte[
                            (int)
                            (
                                    sampleRate
                                    * duration
                                    / 1000
                            )
                    ];

            for (int i = 0;
                    i < buffer.length;
                    i++) {

                double angle =
                        2.0
                        * Math.PI
                        * frequency
                        * i
                        / sampleRate;

                double envelope =
                        1.0
                        - (
                                (double) i
                                / buffer.length
                        );

                buffer[i] =
                        (byte)
                        (
                                Math.sin(angle)
                                * 70
                                * envelope
                        );
            }

            AudioFormat format =
                    new AudioFormat(
                            sampleRate,
                            8,
                            1,
                            true,
                            false
                    );

            SourceDataLine line =
                    AudioSystem
                            .getSourceDataLine(
                                    format
                            );

            line.open(format);

            line.start();

            line.write(
                    buffer,
                    0,
                    buffer.length
            );

            line.drain();

            line.stop();

            line.close();

        } catch (Exception ignored) {
        }
    }

    private void playLineSound() {

        new Thread(() ->
                playSound(520, 80)
        ).start();
    }

    private void playErrorSound() {

        new Thread(() ->
                playSound(180, 120)
        ).start();
    }

    private void playBoxSound() {

        new Thread(() -> {

            playSound(700, 100);

            try {
                Thread.sleep(60);
            } catch (InterruptedException ignored) {
            }

            playSound(900, 130);

        }).start();
    }

    private void playWinnerSound() {

        new Thread(() -> {

            playSound(600, 120);

            try {
                Thread.sleep(80);
            } catch (InterruptedException ignored) {
            }

            playSound(800, 120);

            try {
                Thread.sleep(80);
            } catch (InterruptedException ignored) {
            }

            playSound(1000, 180);

        }).start();
    }

    // =====================================
    // HANDLE CLICK
    // =====================================

    private void handleMouseClick(
            int mouseX,
            int mouseY) {

        boolean lineSelected =
                checkHorizontalLine(
                        mouseX,
                        mouseY
                );

        if (!lineSelected) {

            lineSelected =
                    checkVerticalLine(
                            mouseX,
                            mouseY
                    );
        }

        if (!lineSelected) {

            playErrorSound();
        }
    }

    // =====================================
    // HORIZONTAL LINE
    // =====================================

    private boolean checkHorizontalLine(
            int mouseX,
            int mouseY) {

        for (int row = 0;
                row < board.getRows();
                row++) {

            for (int col = 0;
                    col < board.getCols() - 1;
                    col++) {

                int x1 =
                        START_X
                        + col * DOT_SPACING;

                int x2 =
                        START_X
                        + (col + 1)
                        * DOT_SPACING;

                int y =
                        START_Y
                        + row * DOT_SPACING;

                if (
                        mouseX >=
                                x1 - CLICK_TOLERANCE

                        && mouseX <=
                                x2 + CLICK_TOLERANCE

                        && mouseY >=
                                y - CLICK_TOLERANCE

                        && mouseY <=
                                y + CLICK_TOLERANCE
                ) {

                    boolean selected =
                            board.selectHorizontalLine(
                                    row,
                                    col
                            );

                    if (!selected) {

                        playErrorSound();

                        return true;
                    }

                    board.setHorizontalLinePlayer(
                            row,
                            col,
                            currentPlayer
                    );

                    lastLineHorizontal =
                            true;

                    lastLineRow =
                            row;

                    lastLineCol =
                            col;

                    playLineSound();

                    int completedBoxes =
                            checkCompletedBoxes(
                                    row,
                                    col,
                                    true
                            );

                    if (completedBoxes == 0 &&
                            !gameFinished) {

                        switchPlayer();

                        startComputerTurnIfNeeded();
                    }

                    repaint();

                    return true;
                }
            }
        }

        return false;
    }

    // =====================================
    // VERTICAL LINE
    // =====================================

    private boolean checkVerticalLine(
            int mouseX,
            int mouseY) {

        for (int row = 0;
                row < board.getRows() - 1;
                row++) {

            for (int col = 0;
                    col < board.getCols();
                    col++) {

                int x =
                        START_X
                        + col * DOT_SPACING;

                int y1 =
                        START_Y
                        + row * DOT_SPACING;

                int y2 =
                        START_Y
                        + (row + 1)
                        * DOT_SPACING;

                if (
                        mouseX >=
                                x - CLICK_TOLERANCE

                        && mouseX <=
                                x + CLICK_TOLERANCE

                        && mouseY >=
                                y1 - CLICK_TOLERANCE

                        && mouseY <=
                                y2 + CLICK_TOLERANCE
                ) {

                    boolean selected =
                            board.selectVerticalLine(
                                    row,
                                    col
                            );

                    if (!selected) {

                        playErrorSound();

                        return true;
                    }

                    board.setVerticalLinePlayer(
                            row,
                            col,
                            currentPlayer
                    );

                    lastLineHorizontal =
                            false;

                    lastLineRow =
                            row;

                    lastLineCol =
                            col;

                    playLineSound();

                    int completedBoxes =
                            checkCompletedBoxes(
                                    row,
                                    col,
                                    false
                            );

                    if (completedBoxes == 0 &&
                            !gameFinished) {

                        switchPlayer();

                        startComputerTurnIfNeeded();
                    }

                    repaint();

                    return true;
                }
            }
        }

        return false;
    }

    // =====================================
    // BOX DETECTION
    // =====================================

    private int checkCompletedBoxes(
            int row,
            int col,
            boolean horizontal) {

        int completedBoxes = 0;

        if (horizontal) {

            if (row > 0) {

                if (
                        board.isBoxCompleted(
                                row - 1,
                                col
                        )
                ) {

                    if (
                            board.getBoxOwner(
                                    row - 1,
                                    col
                            ) == 0
                    ) {

                        board.assignBox(
                                row - 1,
                                col,
                                currentPlayer
                        );

                        addScore();

                        completedBoxes++;
                    }
                }
            }

            if (
                    row < board.getRows() - 1
            ) {

                if (
                        board.isBoxCompleted(
                                row,
                                col
                        )
                ) {

                    if (
                            board.getBoxOwner(
                                    row,
                                    col
                            ) == 0
                    ) {

                        board.assignBox(
                                row,
                                col,
                                currentPlayer
                        );

                        addScore();

                        completedBoxes++;
                    }
                }
            }

        } else {

            if (col > 0) {

                if (
                        board.isBoxCompleted(
                                row,
                                col - 1
                        )
                ) {

                    if (
                            board.getBoxOwner(
                                    row,
                                    col - 1
                            ) == 0
                    ) {

                        board.assignBox(
                                row,
                                col - 1,
                                currentPlayer
                        );

                        addScore();

                        completedBoxes++;
                    }
                }
            }

            if (
                    col < board.getCols() - 1
            ) {

                if (
                        board.isBoxCompleted(
                                row,
                                col
                        )
                ) {

                    if (
                            board.getBoxOwner(
                                    row,
                                    col
                            ) == 0
                    ) {

                        board.assignBox(
                                row,
                                col,
                                currentPlayer
                        );

                        addScore();

                        completedBoxes++;
                    }
                }
            }
        }

        if (completedBoxes > 0) {

            playBoxSound();
        }

        if (isGameFinished()) {

            showWinner();
        }

        repaint();

        return completedBoxes;
    }

    // =====================================
    // SCORE
    // =====================================

    private void addScore() {

        if (currentPlayer == 1) {

            player1.addScore();

        } else {

            player2.addScore();
        }
    }

    // =====================================
    // SWITCH PLAYER
    // =====================================

    private void switchPlayer() {

        currentPlayer =
                currentPlayer == 1
                ? 2
                : 1;

        repaint();
    }

    // =====================================
    // COMPUTER TURN START
    // =====================================

    private void startComputerTurnIfNeeded() {

        if (!singlePlayer) {
            return;
        }

        if (gameFinished) {
            return;
        }

        if (currentPlayer != 2) {
            return;
        }

        if (aiTimer != null &&
                aiTimer.isRunning()) {

            return;
        }

        aiTimer =
                new Timer(
                        550,
                        e -> {

                            aiTimer.stop();

                            if (!gameFinished &&
                                    currentPlayer == 2) {

                                computerMove();
                            }
                        }
                );

        aiTimer.setRepeats(false);

        aiTimer.start();
    }

    // =====================================
    // COMPUTER MOVE
    // =====================================

    private void computerMove() {

        if (gameFinished ||
                currentPlayer != 2) {

            return;
        }

        AIMove move =
                chooseComputerMove();

        if (move == null) {
            return;
        }

        boolean selected;

        if (move.horizontal) {

            selected =
                    board.selectHorizontalLine(
                            move.row,
                            move.col
                    );

            if (!selected) {
                return;
            }

            board.setHorizontalLinePlayer(
                    move.row,
                    move.col,
                    2
            );

            lastLineHorizontal =
                    true;

        } else {

            selected =
                    board.selectVerticalLine(
                            move.row,
                            move.col
                    );

            if (!selected) {
                return;
            }

            board.setVerticalLinePlayer(
                    move.row,
                    move.col,
                    2
            );

            lastLineHorizontal =
                    false;
        }

        lastLineRow =
                move.row;

        lastLineCol =
                move.col;

        playLineSound();

        int completedBoxes =
                checkCompletedBoxes(
                        move.row,
                        move.col,
                        move.horizontal
                );

        if (gameFinished) {
            return;
        }

        /*
         * IMPORTANT:
         *
         * If computer completes a box,
         * it keeps its turn.
         *
         * Otherwise Player 1 gets the turn.
         */

        if (completedBoxes > 0) {

            currentPlayer = 2;

        } else {

            currentPlayer = 1;
        }

        repaint();

        if (!gameFinished &&
                currentPlayer == 2) {

            startComputerTurnIfNeeded();
        }
    }

    // =====================================
    // AI MOVE SELECTION
    // =====================================

    private AIMove chooseComputerMove() {

        List<AIMove> moves =
                getAvailableMoves();

        if (moves.isEmpty()) {
            return null;
        }

        // =====================================
        // ALL LEVELS:
        // ALWAYS TAKE A BOX IF AVAILABLE
        // =====================================

        AIMove completingMove =
                findCompletingMove(moves);

        if (completingMove != null) {

            return completingMove;
        }

        // =====================================
        // EASY
        // =====================================

        if ("Easy".equals(difficulty)) {

            return moves.get(
                    random.nextInt(
                            moves.size()
                    )
            );
        }

        // =====================================
        // MEDIUM
        // =====================================

        if ("Medium".equals(difficulty)) {

            List<AIMove> safeMoves =
                    getSafeMoves(moves);

            if (!safeMoves.isEmpty()) {

                return safeMoves.get(
                        random.nextInt(
                                safeMoves.size()
                        )
                );
            }

            return moves.get(
                    random.nextInt(
                            moves.size()
                    )
            );
        }

        // =====================================
        // HARD
        // =====================================

        List<AIMove> safeMoves =
                getSafeMoves(moves);

        if (!safeMoves.isEmpty()) {

            AIMove bestMove =
                    null;

            int bestDanger =
                    Integer.MAX_VALUE;

            for (AIMove move :
                    safeMoves) {

                int danger =
                        calculateDanger(
                                move
                        );

                if (danger < bestDanger) {

                    bestDanger =
                            danger;

                    bestMove =
                            move;
                }
            }

            if (bestMove != null) {

                return bestMove;
            }
        }

        // =====================================
        // IF NO SAFE MOVE EXISTS
        // =====================================

        AIMove bestMove =
                moves.get(0);

        int bestDanger =
                calculateDanger(
                        bestMove
                );

        for (AIMove move :
                moves) {

            int danger =
                    calculateDanger(
                            move
                    );

            if (danger < bestDanger) {

                bestDanger =
                        danger;

                bestMove =
                        move;
            }
        }

        return bestMove;
    }

    // =====================================
    // FIND BOX-COMPLETING MOVE
    // =====================================

    private AIMove findCompletingMove(
            List<AIMove> moves) {

        for (AIMove move :
                moves) {

            if (
                    moveCompletesBox(
                            move
                    )
            ) {

                return move;
            }
        }

        return null;
    }

    // =====================================
    // CHECK MOVE COMPLETES BOX
    // =====================================

    private boolean moveCompletesBox(
            AIMove move) {

        if (move.horizontal) {

            if (move.row > 0) {

                if (
                        board.countBoxSides(
                                move.row - 1,
                                move.col
                        ) == 3
                ) {

                    return true;
                }
            }

            if (
                    move.row <
                            board.getRows() - 1
            ) {

                if (
                        board.countBoxSides(
                                move.row,
                                move.col
                        ) == 3
                ) {

                    return true;
                }
            }

        } else {

            if (move.col > 0) {

                if (
                        board.countBoxSides(
                                move.row,
                                move.col - 1
                        ) == 3
                ) {

                    return true;
                }
            }

            if (
                    move.col <
                            board.getCols() - 1
            ) {

                if (
                        board.countBoxSides(
                                move.row,
                                move.col
                        ) == 3
                ) {

                    return true;
                }
            }
        }

        return false;
    }

    // =====================================
    // SAFE MOVES
    // =====================================

    private List<AIMove> getSafeMoves(
            List<AIMove> moves) {

        List<AIMove> safeMoves =
                new ArrayList<>();

        for (AIMove move :
                moves) {

            if (!createsThreeSidedBox(move)) {

                safeMoves.add(move);
            }
        }

        return safeMoves;
    }

    // =====================================
    // CHECK DANGER
    // =====================================

    private boolean createsThreeSidedBox(
            AIMove move) {

        if (move.horizontal) {

            if (move.row > 0) {

                int sides =
                        board.countBoxSides(
                                move.row - 1,
                                move.col
                        );

                if (sides == 2) {
                    return true;
                }
            }

            if (
                    move.row <
                            board.getRows() - 1
            ) {

                int sides =
                        board.countBoxSides(
                                move.row,
                                move.col
                        );

                if (sides == 2) {
                    return true;
                }
            }

        } else {

            if (move.col > 0) {

                int sides =
                        board.countBoxSides(
                                move.row,
                                move.col - 1
                        );

                if (sides == 2) {
                    return true;
                }
            }

            if (
                    move.col <
                            board.getCols() - 1
            ) {

                int sides =
                        board.countBoxSides(
                                move.row,
                                move.col
                        );

                if (sides == 2) {
                    return true;
                }
            }
        }

        return false;
    }

    // =====================================
    // CALCULATE DANGER
    // =====================================

    private int calculateDanger(
            AIMove move) {

        int danger = 0;

        if (move.horizontal) {

            if (move.row > 0) {

                int sides =
                        board.countBoxSides(
                                move.row - 1,
                                move.col
                        );

                if (sides == 2) {
                    danger++;
                }
            }

            if (
                    move.row <
                            board.getRows() - 1
            ) {

                int sides =
                        board.countBoxSides(
                                move.row,
                                move.col
                        );

                if (sides == 2) {
                    danger++;
                }
            }

        } else {

            if (move.col > 0) {

                int sides =
                        board.countBoxSides(
                                move.row,
                                move.col - 1
                        );

                if (sides == 2) {
                    danger++;
                }
            }

            if (
                    move.col <
                            board.getCols() - 1
            ) {

                int sides =
                        board.countBoxSides(
                                move.row,
                                move.col
                        );

                if (sides == 2) {
                    danger++;
                }
            }
        }

        return danger;
    }

    // =====================================
    // GET AVAILABLE MOVES
    // =====================================

    private List<AIMove> getAvailableMoves() {

        List<AIMove> moves =
                new ArrayList<>();

        // Horizontal
        for (int row = 0;
                row < board.getRows();
                row++) {

            for (int col = 0;
                    col < board.getCols() - 1;
                    col++) {

                if (
                        !board.isHorizontalLineSelected(
                                row,
                                col
                        )
                ) {

                    moves.add(
                            new AIMove(
                                    row,
                                    col,
                                    true
                            )
                    );
                }
            }
        }

        // Vertical
        for (int row = 0;
                row < board.getRows() - 1;
                row++) {

            for (int col = 0;
                    col < board.getCols();
                    col++) {

                if (
                        !board.isVerticalLineSelected(
                                row,
                                col
                        )
                ) {

                    moves.add(
                            new AIMove(
                                    row,
                                    col,
                                    false
                            )
                    );
                }
            }
        }

        return moves;
    }

    // =====================================
    // AI MOVE CLASS
    // =====================================

    private static class AIMove {

        int row;

        int col;

        boolean horizontal;

        AIMove(
                int row,
                int col,
                boolean horizontal) {

            this.row = row;

            this.col = col;

            this.horizontal =
                    horizontal;
        }
    }

    // =====================================
    // GAME FINISHED
    // =====================================

    private boolean isGameFinished() {

        int totalBoxes =
                (board.getRows() - 1)
                * (board.getCols() - 1);

        int totalScore =
                player1.getScore()
                + player2.getScore();

        return totalScore >= totalBoxes;
    }

    // =====================================
    // WINNER
    // =====================================

    private Player getWinner() {

        if (
                player1.getScore()
                >
                player2.getScore()
        ) {

            return player1;
        }

        if (
                player2.getScore()
                >
                player1.getScore()
        ) {

            return player2;
        }

        return null;
    }

    // =====================================
    // SHOW WINNER
    // =====================================

    private void showWinner() {

        if (gameFinished) {
            return;
        }

        gameFinished = true;

        if (blinkTimer != null) {
            blinkTimer.stop();
        }

        if (aiTimer != null) {
            aiTimer.stop();
        }

        playWinnerSound();

        winnerTimer =
                new Timer(
                        60,
                        e -> {

                            animationStep++;

                            updateConfetti();

                            repaint();
                        }
                );

        winnerTimer.start();

        repaint();
    }

    // =====================================
    // CONFETTI
    // =====================================

    private void updateConfetti() {

        for (int i = 0;
                i < confettiY.length;
                i++) {

            confettiY[i] +=
                    confettiSpeed[i];

            if (
                    confettiY[i]
                    > getHeight()
            ) {

                confettiY[i] = -10;

                confettiX[i] =
                        random.nextInt(
                                Math.max(
                                        1,
                                        getWidth()
                                )
                        );
            }
        }
    }

    // =====================================
    // NEW GAME
    // =====================================

    private void newGame() {

        if (winnerTimer != null) {
            winnerTimer.stop();
        }

        Window window =
                SwingUtilities
                        .getWindowAncestor(
                                this
                        );

        if (window instanceof GameFrame) {

            GameFrame frame =
                    (GameFrame) window;

            frame.showPlayerSetup();
        }
    }

    // =====================================
    // PAINT
    // =====================================

    @Override
    protected void paintComponent(
            Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 =
                (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON
        );

        if (gameFinished) {

            drawWinnerScreen(g2);

            g2.dispose();

            return;
        }

        drawHeader(g2);

        drawPlayerCards(g2);

        drawBoardArea(g2);

        drawBoxes(g2);

        drawHorizontalLines(g2);

        drawVerticalLines(g2);

        drawDots(g2);

        drawTurnIndicator(g2);

        drawInstructions(g2);

        drawWatermark(g2);

        g2.dispose();
    }

    // =====================================
    // WATERMARK
    // =====================================

    private void drawWatermark(
            Graphics2D g2) {

        g2.setFont(
                new Font(
                        "Arial",
                        Font.ITALIC,
                        13
                )
        );

        g2.setColor(
                new Color(
                        150,
                        155,
                        165,
                        120
                )
        );

        String watermark =
                "Simin";

        int width =
                g2.getFontMetrics()
                        .stringWidth(
                                watermark
                        );

        g2.drawString(
                watermark,
                getWidth()
                - width
                - 12,
                getHeight()
                - 12
        );
    }

    // =====================================
    // WINNER SCREEN
    // =====================================

    private void drawWinnerScreen(
            Graphics2D g2) {

        g2.setColor(
                new Color(248, 250, 253)
        );

        g2.fillRect(
                0,
                0,
                getWidth(),
                getHeight()
        );

        Color[] colors = {

                new Color(45, 100, 220),
                new Color(220, 65, 65),
                new Color(40, 170, 100),
                new Color(245, 165, 40),
                new Color(150, 80, 190),
                new Color(240, 100, 150)
        };

        for (int i = 0;
                i < confettiX.length;
                i++) {

            g2.setColor(
                    colors[
                            i % colors.length
                    ]
            );

            g2.fillRect(
                    confettiX[i],
                    confettiY[i],
                    7,
                    10
            );
        }

        Player winner =
                getWinner();

        g2.setColor(
                new Color(
                        245,
                        180,
                        45
                )
        );

        g2.fillOval(
                350,
                70,
                100,
                100
        );

        g2.setColor(
                new Color(
                        255,
                        215,
                        70
                )
        );

        g2.fillRect(
                370,
                160,
                60,
                15
        );

        g2.fillRoundRect(
                355,
                175,
                90,
                15,
                8,
                8
        );

        int pulse =
                (int)
                (
                        Math.sin(
                                animationStep * 0.15
                        ) * 3
                );

        g2.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        38 + pulse
                )
        );

        Color winnerColor;

        if (winner != null) {

            winnerColor =
                    getPlayerColor(winner);

        } else {

            winnerColor =
                    new Color(
                            35,
                            45,
                            65
                    );
        }

        g2.setColor(winnerColor);

        String congratulations =
                "CONGRATULATIONS!";

        int width =
                g2.getFontMetrics()
                        .stringWidth(
                                congratulations
                        );

        g2.drawString(
                congratulations,
                (getWidth() - width) / 2,
                235
        );

        g2.setColor(
                new Color(
                        35,
                        45,
                        65
                )
        );

        g2.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        30
                )
        );

        String winnerName;

        if (winner != null) {

            winnerName =
                    winner.getName();

        } else {

            winnerName =
                    "It's a Draw!";
        }

        int winnerWidth =
                g2.getFontMetrics()
                        .stringWidth(
                                winnerName
                        );

        g2.drawString(
                winnerName,
                (getWidth() - winnerWidth) / 2,
                280
        );

        g2.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        17
                )
        );

        g2.setColor(
                new Color(
                        100,
                        105,
                        115
                )
        );

        String text;

        if (winner != null) {

            text =
                    "You are the winner!";

        } else {

            text =
                    "Great game! It's a draw.";
        }

        int textWidth =
                g2.getFontMetrics()
                        .stringWidth(text);

        g2.drawString(
                text,
                (getWidth() - textWidth) / 2,
                310
        );

        g2.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        g2.setColor(
                winnerColor
        );

        String scoreText =
                player1.getName()
                + "  "
                + player1.getScore()
                + "   -   "
                + player2.getScore()
                + "  "
                + player2.getName();

        int scoreWidth =
                g2.getFontMetrics()
                        .stringWidth(
                                scoreText
                        );

        g2.drawString(
                scoreText,
                (getWidth() - scoreWidth) / 2,
                350
        );

        JButton newGameButton =
                new JButton(
                        "NEW GAME"
                );

        newGameButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        17
                )
        );

        newGameButton.setForeground(
                Color.WHITE
        );

        newGameButton.setBackground(
                new Color(
                        35,
                        100,
                        220
                )
        );

        newGameButton.setOpaque(true);

        newGameButton.setContentAreaFilled(true);

        newGameButton.setBorderPainted(false);

        newGameButton.setFocusPainted(false);

        newGameButton.setBounds(
                275,
                400,
                250,
                50
        );

        if (getComponentCount() == 0) {

            add(newGameButton);

            newGameButton.addActionListener(
                    e -> {

                        remove(
                                newGameButton
                        );

                        revalidate();

                        repaint();

                        newGame();
                    }
            );
        }
    }

    // =====================================
    // HEADER
    // =====================================

    private void drawHeader(
            Graphics2D g2) {

        g2.setColor(
                new Color(
                        35,
                        45,
                        65
                )
        );

        g2.fillRoundRect(
                25,
                15,
                750,
                55,
                15,
                15
        );

        g2.setColor(Color.WHITE);

        g2.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        26
                )
        );

        String title =
                "DOTS & BOXES";

        int textWidth =
                g2.getFontMetrics()
                        .stringWidth(title);

        g2.drawString(
                title,
                (getWidth() - textWidth) / 2,
                52
        );
    }

    // =====================================
    // PLAYER CARDS
    // =====================================

    private void drawPlayerCards(
            Graphics2D g2) {

        drawPlayerCard(
                g2,
                player1,
                40,
                85
        );

        drawPlayerCard(
                g2,
                player2,
                565,
                85
        );
    }

    private void drawPlayerCard(
            Graphics2D g2,
            Player player,
            int x,
            int y) {

        boolean active =
                player.getPlayerNumber()
                        == currentPlayer;

        Color playerColor =
                getPlayerColor(player);

        if (active) {

            g2.setColor(
                    playerColor
            );

        } else {

            g2.setColor(
                    new Color(
                            220,
                            224,
                            230
                    )
            );
        }

        g2.fillRoundRect(
                x,
                y,
                195,
                95,
                15,
                15
        );

        g2.setColor(Color.WHITE);

        g2.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        17
                )
        );

        g2.drawString(
                player.getName(),
                x + 15,
                y + 28
        );

        g2.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        g2.drawString(
                "Score: "
                + player.getScore(),
                x + 15,
                y + 58
        );

        g2.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        if (active) {

            g2.drawString(
                    "● YOUR TURN",
                    x + 15,
                    y + 80
            );
        }
    }

    // =====================================
    // BOARD AREA
    // =====================================

    private void drawBoardArea(
            Graphics2D g2) {

        g2.setColor(Color.WHITE);

        g2.fillRoundRect(
                180,
                190,
                440,
                300,
                20,
                20
        );

        g2.setColor(
                new Color(
                        220,
                        224,
                        230
                )
        );

        g2.setStroke(
                new BasicStroke(2)
        );

        g2.drawRoundRect(
                180,
                190,
                440,
                300,
                20,
                20
        );
    }

    // =====================================
    // BOXES
    // =====================================

    private void drawBoxes(
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

                if (owner == 0) {
                    continue;
                }

                Player player =
                        owner == 1
                        ? player1
                        : player2;

                Color color =
                        getPlayerColor(player);

                Color boxColor =
                        new Color(
                                color.getRed(),
                                color.getGreen(),
                                color.getBlue(),
                                50
                        );

                int x =
                        START_X
                        + col * DOT_SPACING
                        + 5;

                int y =
                        START_Y
                        + row * DOT_SPACING
                        + 5;

                g2.setColor(
                        boxColor
                );

                g2.fillRect(
                        x,
                        y,
                        DOT_SPACING - 10,
                        DOT_SPACING - 10
                );
            }
        }
    }

    // =====================================
    // HORIZONTAL LINES
    // =====================================

    private void drawHorizontalLines(
            Graphics2D g2) {

        for (int row = 0;
                row < board.getRows();
                row++) {

            for (int col = 0;
                    col < board.getCols() - 1;
                    col++) {

                if (
                        board.isHorizontalLineSelected(
                                row,
                                col
                        )
                ) {

                    int x1 =
                            START_X
                            + col * DOT_SPACING;

                    int x2 =
                            START_X
                            + (col + 1)
                            * DOT_SPACING;

                    int y =
                            START_Y
                            + row * DOT_SPACING;

                    int player =
                            board.getHorizontalLinePlayer(
                                    row,
                                    col
                            );

                    Color color =
                            player == 1
                            ? getPlayerColor(player1)
                            : getPlayerColor(player2);

                    boolean isLast =
                            lastLineHorizontal
                            &&
                            lastLineRow == row
                            &&
                            lastLineCol == col;

                    g2.setStroke(
                            new BasicStroke(
                                    isLast
                                    && blinkVisible
                                    ? 9
                                    : 5
                            )
                    );

                    g2.setColor(color);

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

    // =====================================
    // VERTICAL LINES
    // =====================================

    private void drawVerticalLines(
            Graphics2D g2) {

        for (int row = 0;
                row < board.getRows() - 1;
                row++) {

            for (int col = 0;
                    col < board.getCols();
                    col++) {

                if (
                        board.isVerticalLineSelected(
                                row,
                                col
                        )
                ) {

                    int x =
                            START_X
                            + col * DOT_SPACING;

                    int y1 =
                            START_Y
                            + row * DOT_SPACING;

                    int y2 =
                            START_Y
                            + (row + 1)
                            * DOT_SPACING;

                    int player =
                            board.getVerticalLinePlayer(
                                    row,
                                    col
                            );

                    Color color =
                            player == 1
                            ? getPlayerColor(player1)
                            : getPlayerColor(player2);

                    boolean isLast =
                            !lastLineHorizontal
                            &&
                            lastLineRow == row
                            &&
                            lastLineCol == col;

                    g2.setStroke(
                            new BasicStroke(
                                    isLast
                                    && blinkVisible
                                    ? 9
                                    : 5
                            )
                    );

                    g2.setColor(color);

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

    // =====================================
    // DOTS
    // =====================================

    private void drawDots(
            Graphics2D g2) {

        for (int row = 0;
                row < board.getRows();
                row++) {

            for (int col = 0;
                    col < board.getCols();
                    col++) {

                int x =
                        START_X
                        + col * DOT_SPACING;

                int y =
                        START_Y
                        + row * DOT_SPACING;

                g2.setColor(
                        new Color(
                                180,
                                185,
                                195
                        )
                );

                g2.fillOval(
                        x - DOT_RADIUS + 2,
                        y - DOT_RADIUS + 2,
                        DOT_RADIUS * 2,
                        DOT_RADIUS * 2
                );

                g2.setColor(Color.BLACK);

                g2.fillOval(
                        x - DOT_RADIUS,
                        y - DOT_RADIUS,
                        DOT_RADIUS * 2,
                        DOT_RADIUS * 2
                );
            }
        }
    }

    // =====================================
    // TURN INDICATOR
    // =====================================

    private void drawTurnIndicator(
            Graphics2D g2) {

        Player player =
                currentPlayer == 1
                ? player1
                : player2;

        g2.setColor(
                getPlayerColor(player)
        );

        g2.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );

        String text;

        if (
                singlePlayer &&
                currentPlayer == 2
        ) {

            text =
                    "Computer is thinking...";

        } else {

            text =
                    player.getName()
                    + "'s Turn";
        }

        int textWidth =
                g2.getFontMetrics()
                        .stringWidth(text);

        g2.drawString(
                text,
                (getWidth() - textWidth) / 2,
                525
        );
    }

    // =====================================
    // INSTRUCTIONS
    // =====================================

    private void drawInstructions(
            Graphics2D g2) {

        g2.setColor(
                new Color(
                        80,
                        85,
                        95
                )
        );

        g2.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        String text =
                "Click a line between two dots to play.";

        int textWidth =
                g2.getFontMetrics()
                        .stringWidth(text);

        g2.drawString(
                text,
                (getWidth() - textWidth) / 2,
                555
        );
    }

    // =====================================
    // PLAYER COLOR
    // =====================================

    private Color getPlayerColor(
            Player player) {

        switch (player.getColor()) {

            case "Blue":
                return Color.BLUE;

            case "Red":
                return Color.RED;

            case "Green":
                return Color.GREEN;

            case "Orange":
                return Color.ORANGE;

            case "Purple":
                return new Color(
                        128,
                        0,
                        128
                );

            case "Pink":
                return Color.PINK;

            default:
                return Color.BLACK;
        }
    }
}
