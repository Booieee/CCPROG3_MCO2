package src.model.pieces;

import javax.swing.ImageIcon;

/**
 * Abstract class representing a game piece.
 * This class serves as a base for all specific piece types in the game.
 */
public abstract class Piece {
    protected String name;
    protected int rank;
    protected boolean isBlueTeam;
    protected ImageIcon icon;
    protected boolean isInTrap = false;
    
    /**
     * Constructor to initialize a piece with its name, rank, team color, and image path.
     * @param name The name of the piece.
     * @param rank The rank of the piece.
     * @param isBlueTeam Indicates if the piece belongs to the blue team.
     * @param imagePath The path to the image representing the piece.
     */
    public Piece(String name, int rank, boolean isBlueTeam, String imagePath) {
        this.name = name;
        this.rank = rank;
        this.isBlueTeam = isBlueTeam;
        this.icon = new ImageIcon(imagePath);
        java.net.URL imgURL = getClass().getResource(imagePath);
        if (imgURL != null) {
            this.icon = new ImageIcon(imgURL);
        } else {
            System.err.println("Resource not found: " + imagePath);
            this.icon = new ImageIcon(); // Provide a fallback or handle the error
        }
    }
    
    /**
     * Method to check if the piece can capture another piece.
     * The rules for capturing depend on the specific type of piece.
     * @param other The other piece to check against.
     * @return true if this piece can capture the other piece, false otherwise.
     */
    public boolean canCapture(Piece other) {
        if (other == null) return false;
        
        if (this instanceof Rat && other instanceof Elephant) return true;
        
        if (other.isInTrap) {
            return true;
        }
        return this.rank >= other.rank;
    }
    
    /**
     * Abstract method to check if the piece can move to a specific tile.
     * The rules for movement depend on the specific type of piece.
     * @param fromRow The row of the current position.
     * @param fromCol The column of the current position.
     * @param toRow The row of the target position.
     * @param toCol The column of the target position.
     * @param isLake Indicates if the target tile is a lake.
     * @return true if the piece can move to the target tile, false otherwise.
     */
    public abstract boolean canMoveToTile(int fromRow, int fromCol, int toRow, int toCol, boolean isLake);

    /**
     * Method to set the piece as being in a trap.
     * @param inTrap Indicates if the piece is in a trap.
     */
    public void setInTrap(boolean inTrap) {
        this.isInTrap = inTrap;
    }
    
    /**
     * Method to get name of the piece.
     * @return The name of the piece.
     */
    public String getName() { return name; }

    /**
     * Method to get the rank of the piece.
     * @return The rank of the piece.
     */
    public int getRank() { return rank; }

    /**
     * Method to check if the piece belongs to the blue team.
     * @return true if the piece is on the blue team, false otherwise.
     */
    public boolean isBlueTeam() { return isBlueTeam; }

    /**
     * Method to get the icon representing the piece.
     * @return The ImageIcon of the piece.
     */
    public ImageIcon getIcon() { return icon; }
}
