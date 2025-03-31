package src.model.pieces;

/**
 * RegularPiece class representing a standard game piece.
 * This class extends the Piece class and implements the specific movement rules for regular pieces.
 */
public class RegularPiece extends Piece {
    /**
     * Constructor to initialize a regular piece with its name, rank, team color, and image path.
     * @param name The name of the piece.
     * @param rank The rank of the piece.
     * @param isBlueTeam Indicates if the piece belongs to the blue team.
     * @param imagePath The path to the image representing the piece.
     */
    public RegularPiece(String name, int rank, boolean isBlueTeam, String imagePath) {
        super(name, rank, isBlueTeam, imagePath);
    }
    
    /** 
     * Method to check if the piece can move to a specific tile.
     * @param fromRow The row of the current position.
     * @param fromCol The column of the current position.
     * @param toRow The row of the target position.
     * @param toCol The column of the target position.
     * @param isLake Indicates if the target position is a lake.
     * @return true if the piece can move to the target position, false otherwise.
     */
    @Override
    public boolean canMoveToTile(int fromRow, int fromCol, int toRow, int toCol, boolean isLake) {
        // Regular pieces can only move one square orthogonally and can't enter lakes
        return !isLake && Math.abs(fromRow - toRow) + Math.abs(fromCol - toCol) == 1;
    }
}
