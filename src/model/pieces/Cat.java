package src.model.pieces;

/**
 * The Cat class represents a cat piece in the Jungle King game.
 */
public class Cat extends RegularPiece {
    /**
     * Constructor for the Cat class.
     *
     * @param isBlueTeam boolean indicating if the cat is on the blue team
     */
    public Cat(boolean isBlueTeam) {
        super("Cat", 1, isBlueTeam, isBlueTeam ? "/Assets/Pieces/blue_cat.png" : "/Assets/Pieces/red_cat.png");
    }
}
