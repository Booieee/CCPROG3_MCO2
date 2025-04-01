package src.model.pieces;

/**
 * The Dog class represents a dog piece in the Jungle King game.
 * It extends the RegularPiece class and initializes the dog piece with its name, rank, team color, and image path.
 */
public class Dog extends RegularPiece {
    /**
     * Constructor for the Dog class.
     *
     * @param isBlueTeam boolean indicating if the dog is on the blue team
     */
    public Dog(boolean isBlueTeam) {
        super("Dog", 2, isBlueTeam, isBlueTeam ? "/Assets/Pieces/blue_dog.png" : "/Assets/Pieces/red_dog.png");
    }
}
