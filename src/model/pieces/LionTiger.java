package src.model.pieces;

/**
 * The LionTiger class represents a lion or tiger piece in the Jungle King game.
 * It extends the Piece class and initializes the lion or tiger piece with its name, rank, team color, and image path.
 */
public abstract class LionTiger extends Piece {
    /**
     * Constructor for the LionTiger class.
     *
     * @param name      The name of the piece (Lion or Tiger).
     * @param rank      The rank of the piece.
     * @param isBlueTeam Indicates if the piece belongs to the blue team.
     * @param imagePath The path to the image representing the piece.
     */
    public LionTiger(String name, int rank, boolean isBlueTeam, String imagePath) {
        super(name, rank, isBlueTeam, imagePath);
    }
    
    /**
     * Checks if the piece can move to a specified tile.
     *
     * @param fromRow  The row of the current position.
     * @param fromCol  The column of the current position.
     * @param toRow    The row of the target position.
     * @param toCol    The column of the target position.
     * @param isLake   Indicates if the move is over a lake.
     * @return true if the piece can move to the specified tile, false otherwise.
     */
    @Override
    public boolean canMoveToTile(int fromRow, int fromCol, int toRow, int toCol, boolean isLake) {
        // Regular move
        if (Math.abs(fromRow - toRow) + Math.abs(fromCol - toCol) == 1) return true;
        
        // Lake jump move (implement lake jump logic in game controller)
        if (isLake) {
            return (fromRow == toRow && Math.abs(fromCol - toCol) == 3) || 
                   (fromCol == toCol && Math.abs(fromRow - toRow) == 2);
        }
        return false;
    }
}
