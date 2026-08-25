package dot.and.boxes;

public class Board {

    private final int rows = 4;
    private final int cols = 4;

    private Dot[][] dots;

    // Horizontal lines
    private boolean[][] horizontalLines;

    // Vertical lines
    private boolean[][] verticalLines;

    // Horizontal line owners
    // 0 = None
    // 1 = Player 1
    // 2 = Player 2
    private int[][] horizontalLineOwners;

    // Vertical line owners
    // 0 = None
    // 1 = Player 1
    // 2 = Player 2
    private int[][] verticalLineOwners;

    // Box owners
    // 0 = None
    // 1 = Player 1
    // 2 = Player 2
    private int[][] boxOwners;

    // Last selected line
    // 0 = None
    // 1 = Horizontal
    // 2 = Vertical
    private int lastLineType = 0;

    private int lastLineRow = -1;
    private int lastLineCol = -1;

    public Board() {

        initializeBoard();
    }

    // Initialize board
    private void initializeBoard() {

        dots = new Dot[rows][cols];

        horizontalLines =
                new boolean[rows][cols - 1];

        verticalLines =
                new boolean[rows - 1][cols];

        horizontalLineOwners =
                new int[rows][cols - 1];

        verticalLineOwners =
                new int[rows - 1][cols];

        boxOwners =
                new int[rows - 1][cols - 1];

        // Create dots
        for (int row = 0; row < rows; row++) {

            for (int col = 0; col < cols; col++) {

                dots[row][col] =
                        new Dot(0, 0);
            }
        }
    }

    // Get rows
    public int getRows() {

        return rows;
    }

    // Get columns
    public int getCols() {

        return cols;
    }

    // Get dot
    public Dot getDot(int row, int col) {

        return dots[row][col];
    }

    // Select horizontal line
    public boolean selectHorizontalLine(
            int row,
            int col,
            int player) {

        if (row < 0 || row >= rows) {

            return false;
        }

        if (col < 0 || col >= cols - 1) {

            return false;
        }

        // Prevent duplicate line
        if (horizontalLines[row][col]) {

            return false;
        }

        if (player != 1 && player != 2) {

            return false;
        }

        horizontalLines[row][col] = true;

        horizontalLineOwners[row][col] =
                player;

        // Save last move
        lastLineType = 1;

        lastLineRow = row;

        lastLineCol = col;

        return true;
    }

    // Select vertical line
    public boolean selectVerticalLine(
            int row,
            int col,
            int player) {

        if (row < 0 || row >= rows - 1) {

            return false;
        }

        if (col < 0 || col >= cols) {

            return false;
        }

        // Prevent duplicate line
        if (verticalLines[row][col]) {

            return false;
        }

        if (player != 1 && player != 2) {

            return false;
        }

        verticalLines[row][col] = true;

        verticalLineOwners[row][col] =
                player;

        // Save last move
        lastLineType = 2;

        lastLineRow = row;

        lastLineCol = col;

        return true;
    }

    // Check horizontal line
    public boolean isHorizontalLineSelected(
            int row,
            int col) {

        if (row < 0 || row >= rows) {

            return false;
        }

        if (col < 0 || col >= cols - 1) {

            return false;
        }

        return horizontalLines[row][col];
    }

    // Check vertical line
    public boolean isVerticalLineSelected(
            int row,
            int col) {

        if (row < 0 || row >= rows - 1) {

            return false;
        }

        if (col < 0 || col >= cols) {

            return false;
        }

        return verticalLines[row][col];
    }

    // Get horizontal line owner
    public int getHorizontalLineOwner(
            int row,
            int col) {

        if (row < 0 || row >= rows) {

            return 0;
        }

        if (col < 0 || col >= cols - 1) {

            return 0;
        }

        return horizontalLineOwners[row][col];
    }

    // Get vertical line owner
    public int getVerticalLineOwner(
            int row,
            int col) {

        if (row < 0 || row >= rows - 1) {

            return 0;
        }

        if (col < 0 || col >= cols) {

            return 0;
        }

        return verticalLineOwners[row][col];
    }

    // Check whether a box is completed
    public boolean isBoxCompleted(
            int row,
            int col) {

        if (row < 0 || row >= rows - 1) {

            return false;
        }

        if (col < 0 || col >= cols - 1) {

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

    // Assign box to player
    public boolean assignBox(
            int row,
            int col,
            int player) {

        if (!isBoxCompleted(row, col)) {

            return false;
        }

        // Already assigned
        if (boxOwners[row][col] != 0) {

            return false;
        }

        if (player != 1 && player != 2) {

            return false;
        }

        boxOwners[row][col] = player;

        return true;
    }

    // Get box owner
    public int getBoxOwner(
            int row,
            int col) {

        if (row < 0 || row >= rows - 1) {

            return 0;
        }

        if (col < 0 || col >= cols - 1) {

            return 0;
        }

        return boxOwners[row][col];
    }

    // Check last horizontal line
    public boolean isLastHorizontalLine(
            int row,
            int col) {

        return lastLineType == 1
                && lastLineRow == row
                && lastLineCol == col;
    }

    // Check last vertical line
    public boolean isLastVerticalLine(
            int row,
            int col) {

        return lastLineType == 2
                && lastLineRow == row
                && lastLineCol == col;
    }
}