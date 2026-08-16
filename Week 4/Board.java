package dot.and.boxes;

public class Board {

    private final int rows = 4;
    private final int cols = 4;

    private Dot[][] dots;

    // Horizontal lines
    // 4 rows × 3 lines
    private boolean[][] horizontalLines;

    // Vertical lines
    // 3 rows × 4 lines
    private boolean[][] verticalLines;

    public Board() {

        initializeBoard();
    }

    private void initializeBoard() {

        dots = new Dot[rows][cols];

        horizontalLines = new boolean[rows][cols - 1];

        verticalLines = new boolean[rows - 1][cols];

        // Create dots
        for (int row = 0; row < rows; row++) {

            for (int col = 0; col < cols; col++) {

                dots[row][col] = new Dot(0, 0);
            }
        }
    }

    // Get number of rows
    public int getRows() {
        return rows;
    }

    // Get number of columns
    public int getCols() {
        return cols;
    }

    // Get dot
    public Dot getDot(int row, int col) {

        return dots[row][col];
    }

    // Select horizontal line
    public boolean selectHorizontalLine(int row, int col) {

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

        horizontalLines[row][col] = true;

        return true;
    }

    // Select vertical line
    public boolean selectVerticalLine(int row, int col) {

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

        verticalLines[row][col] = true;

        return true;
    }

    // Check horizontal line
    public boolean isHorizontalLineSelected(int row, int col) {

        if (row < 0 || row >= rows) {
            return false;
        }

        if (col < 0 || col >= cols - 1) {
            return false;
        }

        return horizontalLines[row][col];
    }

    // Check vertical line
    public boolean isVerticalLineSelected(int row, int col) {

        if (row < 0 || row >= rows - 1) {
            return false;
        }

        if (col < 0 || col >= cols) {
            return false;
        }

        return verticalLines[row][col];
    }
}