package dot.and.boxes;

public class Player {

    private int playerNumber;

    private String name;

    private String color;

    private int score;


    public Player(
            int playerNumber,
            String name,
            String color) {

        this.playerNumber =
                playerNumber;

        this.name =
                name;

        this.color =
                color;

        this.score = 0;
    }


    public int getPlayerNumber() {

        return playerNumber;
    }


    public String getName() {

        return name;
    }


    public String getColor() {

        return color;
    }


    public int getScore() {

        return score;
    }


    public void addScore() {

        score++;
    }
}

