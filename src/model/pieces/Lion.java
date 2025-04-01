package src.model.pieces;

/**
 * The Lion class represents a lion piece in the Jungle King game.
 * It extends the LionTiger class and initializes the lion piece with its name, rank, team color, and image path.
 */
public class Lion extends LionTiger {
    /**
     * Constructor for the Lion class.
     *
     * @param isBlueTeam boolean indicating if the lion is on the blue team
     */
    public Lion(boolean isBlueTeam) {
        super("Lion", 6, isBlueTeam, isBlueTeam ? "/Assets/Pieces/blue_lion.png" : "/Assets/Pieces/red_lion.png");
    }
}
