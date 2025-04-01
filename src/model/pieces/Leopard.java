package src.model.pieces;

/**
 * The Leopard class represents a leopard piece in the Jungle King game.
 * It extends the RegularPiece class and initializes the leopard piece with its name, rank, team color, and image path.
 */
public class Leopard extends RegularPiece {
    /**
     * Constructor for the Leopard class.
     *
     * @param isBlueTeam boolean indicating if the leopard is on the blue team
     */
    public Leopard(boolean isBlueTeam) {
        super("Leopard", 4, isBlueTeam, isBlueTeam ? "/Assets/Pieces/blue_leopard.png" : "/Assets/Pieces/red_leopard.png");
    }
}
