
package dot.and.boxes;

public class Board {

    // Number of rows
    int rows = 4;

    // Number of columns
    int cols = 4;

    // Distance between two dots
    int spacing = 80;

    // 2D array to store all dots
    Dot[][] dots;

    // Constructor
    public Board() {

        dots = new Dot[rows][cols];

        initializeBoard();

    }

    // Initialize all dot positions
    public void initializeBoard() {

        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < cols; j++) {

                dots[i][j] = new Dot(

                        100 + (j * spacing),

                        100 + (i * spacing)

                );

            }

        }

    }

}