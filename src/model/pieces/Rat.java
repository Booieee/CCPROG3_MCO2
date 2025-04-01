package src.model.pieces;

/**
 * The Rat class represents a rat piece in the Jungle King game.
 * It extends the Piece class and initializes the rat piece with its name, rank, team color, and image path.
 */
public class Rat extends Piece {
    /**
     * Constructor for the Rat class.
     *
     * @param isBlueTeam boolean indicating if the rat is on the blue team
     */
    public Rat(boolean isBlueTeam) {
        super("Rat", 1, isBlueTeam, isBlueTeam ? "/Assets/Pieces/blue_rat.png" : "/Assets/Pieces/red_rat.png");
    }
    
    /**
     * Determines if the rat can move to a specified tile.
     *
     * @param fromRow the row of the current position
     * @param fromCol the column of the current position
     * @param toRow   the row of the target position
     * @param toCol   the column of the target position
     * @param isLake  boolean indicating if the target position is a lake
     * @return true if the rat can move to the specified tile, false otherwise
     */
    @Override
    public boolean canMoveToTile(int fromRow, int fromCol, int toRow, int toCol, boolean isLake) {
        // Rats can move anywhere including lakes
        return Math.abs(fromRow - toRow) + Math.abs(fromCol - toCol) == 1;
    }
}
