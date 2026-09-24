package dot.and.boxes;

public class Board {

    private final int rows;
    private final int cols;

    private Dot[][] dots;

    private boolean[][] horizontalLines;
    private boolean[][] verticalLines;

    private int[][] horizontalLinePlayers;
    private int[][] verticalLinePlayers;

    private int[][] boxOwners;

    public Board(int size) {

        rows = size;
        cols = size;

        initializeBoard();
    }

    private void initializeBoard() {

        dots = new Dot[rows][cols];

        horizontalLines =
                new boolean[rows][cols - 1];

        verticalLines =
                new boolean[rows - 1][cols];

        horizontalLinePlayers =
                new int[rows][cols - 1];

        verticalLinePlayers =
                new int[rows - 1][cols];

        boxOwners =
                new int[rows - 1][cols - 1];

        for (int row = 0; row < rows; row++) {

            for (int col = 0; col < cols; col++) {

                dots[row][col] =
                        new Dot(row, col);
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Dot getDot(int row, int col) {

        if (row < 0 || row >= rows ||
                col < 0 || col >= cols) {

            return null;
        }

        return dots[row][col];
    }

    // =====================================
    // HORIZONTAL LINE
    // =====================================

    public boolean selectHorizontalLine(
            int row,
            int col) {

        if (row < 0 || row >= rows) {
            return false;
        }

        if (col < 0 || col >= cols - 1) {
            return false;
        }

        if (horizontalLines[row][col]) {
            return false;
        }

        horizontalLines[row][col] = true;

        return true;
    }

    public void setHorizontalLinePlayer(
            int row,
            int col,
            int player) {

        if (row >= 0 && row < rows &&
                col >= 0 && col < cols - 1) {

            horizontalLinePlayers[row][col] =
                    player;
        }
    }

    public int getHorizontalLinePlayer(
            int row,
            int col) {

        if (row < 0 || row >= rows ||
                col < 0 || col >= cols - 1) {

            return 0;
        }

        return horizontalLinePlayers[row][col];
    }

    // =====================================
    // VERTICAL LINE
    // =====================================

    public boolean selectVerticalLine(
            int row,
            int col) {

        if (row < 0 || row >= rows - 1) {
            return false;
        }

        if (col < 0 || col >= cols) {
            return false;
        }

        if (verticalLines[row][col]) {
            return false;
        }

        verticalLines[row][col] = true;

        return true;
    }

    public void setVerticalLinePlayer(
            int row,
            int col,
            int player) {

        if (row >= 0 && row < rows - 1 &&
                col >= 0 && col < cols) {

            verticalLinePlayers[row][col] =
                    player;
        }
    }

    public int getVerticalLinePlayer(
            int row,
            int col) {

        if (row < 0 || row >= rows - 1 ||
                col < 0 || col >= cols) {

            return 0;
        }

        return verticalLinePlayers[row][col];
    }

    // =====================================
    // CHECK SELECTED LINES
    // =====================================

    public boolean isHorizontalLineSelected(
            int row,
            int col) {

        if (row < 0 || row >= rows ||
                col < 0 || col >= cols - 1) {

            return false;
        }

        return horizontalLines[row][col];
    }

    public boolean isVerticalLineSelected(
            int row,
            int col) {

        if (row < 0 || row >= rows - 1 ||
                col < 0 || col >= cols) {

            return false;
        }

        return verticalLines[row][col];
    }

    // =====================================
    // BOX CHECK
    // =====================================

    public boolean isBoxCompleted(
            int row,
            int col) {

        if (row < 0 || row >= rows - 1 ||
                col < 0 || col >= cols - 1) {

            return false;
        }

        boolean top =
                horizontalLines[row][col];

        boolean bottom =
                horizontalLines[row + 1][col];

        boolean left =
                verticalLines[row][col];

        boolean right =
                verticalLines[row][col + 1];

        return top && bottom && left && right;
    }

    // =====================================
    // COUNT SELECTED SIDES OF BOX
    // =====================================

    public int countBoxSides(
            int row,
            int col) {

        if (row < 0 || row >= rows - 1 ||
                col < 0 || col >= cols - 1) {

            return -1;
        }

        int count = 0;

        if (horizontalLines[row][col]) {
            count++;
        }

        if (horizontalLines[row + 1][col]) {
            count++;
        }

        if (verticalLines[row][col]) {
            count++;
        }

        if (verticalLines[row][col + 1]) {
            count++;
        }

        return count;
    }

    // =====================================
    // ASSIGN BOX
    // =====================================

    public boolean assignBox(
            int row,
            int col,
            int player) {

        if (!isBoxCompleted(row, col)) {
            return false;
        }

        if (boxOwners[row][col] != 0) {
            return false;
        }

        if (player != 1 && player != 2) {
            return false;
        }

        boxOwners[row][col] = player;

        return true;
    }

    public int getBoxOwner(
            int row,
            int col) {

        if (row < 0 || row >= rows - 1 ||
                col < 0 || col >= cols - 1) {

            return 0;
        }

        return boxOwners[row][col];
    }

    // =====================================
    // CHECK WHETHER ANY MOVE IS AVAILABLE
    // =====================================

    public boolean hasAvailableMove() {

        for (int row = 0; row < rows; row++) {

            for (int col = 0;
                    col < cols - 1;
                    col++) {

                if (!horizontalLines[row][col]) {
                    return true;
                }
            }
        }

        for (int row = 0;
                row < rows - 1;
                row++) {

            for (int col = 0;
                    col < cols;
                    col++) {

                if (!verticalLines[row][col]) {
                    return true;
                }
            }
        }

        return false;
    }
}