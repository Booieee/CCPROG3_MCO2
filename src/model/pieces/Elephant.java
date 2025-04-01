package src.model.pieces;

/**
 * The Elephant class represents an elephant piece in the Jungle King game.
 * It extends the RegularPiece class and initializes the elephant piece with its name, rank, team color, and image path.
 */
public class Elephant extends RegularPiece {
    /**
     * Constructor for the Elephant class.
     *
     * @param isBlueTeam boolean indicating if the elephant is on the blue team
     */
    public Elephant(boolean isBlueTeam) {
        super("Elephant", 7, isBlueTeam, isBlueTeam ? "/Assets/Pieces/blue_elephant.png" : "/Assets/Pieces/red_elephant.png");
    }
}
