package src.model.pieces;

/**
 * The Wolf class represents a wolf piece in the Jungle King game.
 * It extends the RegularPiece class and initializes the wolf piece with its name, rank, team color, and image path.
 */
public class Wolf extends RegularPiece {
    /**
     * Constructor for the Wolf class.
     *
     * @param isBlueTeam boolean indicating if the wolf is on the blue team
     */
    public Wolf(boolean isBlueTeam) {
        super("Wolf", 3, isBlueTeam, isBlueTeam ? "/Assets/Pieces/blue_wolf.png" : "/Assets/Pieces/red_wolf.png");
    }
}
