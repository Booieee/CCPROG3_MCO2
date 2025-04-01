package src.model.pieces;

/**
 * The Tiger class represents a tiger piece in the Jungle King game.
 * It extends the LionTiger class and initializes the tiger piece with its name, rank, team color, and image path.
 */
public class Tiger extends LionTiger {
    /**
     * Constructor for the Tiger class.
     *
     * @param isBlueTeam boolean indicating if the tiger is on the blue team
     */
    public Tiger(boolean isBlueTeam) {
        super("Tiger", 5, isBlueTeam, isBlueTeam ? "/Assets/Pieces/blue_tiger.png" : "/Assets/Pieces/red_tiger.png");
    }
}
