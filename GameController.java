package src.controller;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javafx.scene.input.KeyEvent;
import java.awt.Color;
import src.model.GameModel;
import src.model.pieces.Lion;
import src.model.pieces.Piece;
import src.model.pieces.Rat;
import src.model.pieces.Tiger;
import src.view.GameView;


/**
 * GameController.java
 * This class handles the game logic and user interactions for the Jungle Game.
 * It manages the movement of pieces, validates moves, and updates the game state.
 * It also handles the initial piece selection and determines the first turn.
 */
public class GameController extends JFrame{
    
    public Piece selectedPiece;
    public int selectedRow = -1;
    public int selectedCol = -1;
    public Piece bluePieceSelected;
    public Piece redPieceSelected;
    
    private GameView gameView;
    
    /**
     * Sets the GameView instance for this controller.
     * This method is called to initialize the view component of the game.
     * @param view The GameView instance to be set.
     */
    public void setGameView(GameView view) {
        this.gameView = view;
        
        bluePieceSelected = null;
        redPieceSelected = null;
        selectedPiece = null;
        selectedRow = -1;
        selectedCol = -1;
    }
    
    /**
     * Constructor for the GameController class.
     * Initializes the focus traversal keys and sets up the game controller.
     */
    public GameController() {
        setFocusTraversalKeysEnabled(false);
    }
    
    /**
     * Moves a piece from one tile to another.
     * This method updates the game model and the view to reflect the move.
     * It also checks if the move ends the game by entering the opponent's den.
     * @param fromRow The row of the piece being moved.
     * @param fromCol The column of the piece being moved.
     * @param toRow The row of the destination tile.
     * @param toCol The column of the destination tile.
     */
    public void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        Piece piece = GameModel.pieces[fromRow][fromCol];
    
        // Update the isInTrap flag for the piece based on the destination tile
        if (isTrap(toRow, toCol) && !isOwnTrap(toRow, toCol, piece.isBlueTeam())) {
            piece.setInTrap(true);
        } else {
            piece.setInTrap(false);
        }
    
        // Move the piece
        GameModel.pieces[toRow][toCol] = piece;
        GameModel.pieces[fromRow][fromCol] = null;
    
        // Update the board display
        gameView.updateBoardDisplay();
    
        // Update the turn label
        String currentTurn = GameModel.isBlueTeamTurn ? "Green" : "Blue";
        GameModel.turnLabel.setText(currentTurn + "'s Turn");
    
        // Check if the move ends the game by entering the opponent's den
        if (isDen(toRow, toCol)) {
            GameModel.setGameEnded(true);
            GameModel.turnLabel.setText((GameModel.isBlueTeamTurn() ? "Blue" : "Green") + " team wins!");
            JOptionPane.showMessageDialog(this, 
                (GameModel.isBlueTeamTurn() ? "Blue" : "Green") + " team wins!");
        }
    }

    /**
     * Highlights valid moves for the selected piece.
     * This method updates the borders of the tiles to indicate valid moves.
     * @param row The row of the selected piece.
     * @param col The column of the selected piece.
     */
    public void highlightValidMoves(int row, int col) {
        for (int r = 0; r < GameModel.ROWS; r++) {
            for (int c = 0; c < GameModel.COLS; c++) {
                if (isValidMove(row, col, r, c)) {
                    
                    GameModel.grid[r][c].setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.WHITE, 1),
                        BorderFactory.createLineBorder(Color.YELLOW, 2)
                    ));
                }
            }
        }
    }

    /**
     * Determines the first turn based on the selected pieces.
     * This method compares the ranks of the selected pieces and displays a message indicating which team goes first.
     */
    private void determineFirstTurn() {
        
        String blueAnimal = bluePieceSelected.getClass().getSimpleName();
        String redAnimal = redPieceSelected.getClass().getSimpleName();
        
        boolean blueGoesFirst = bluePieceSelected.getRank() > redPieceSelected.getRank();
        
        JOptionPane.showMessageDialog(this, 
            "Blue selected: " + blueAnimal + "\n" +
            "Green selected: " + redAnimal + "\n\n" +
            (blueGoesFirst ? "Blue" : "Green") + " team goes first!");
        
        GameModel.startGame(bluePieceSelected, redPieceSelected, blueGoesFirst);
        
        
        GameModel.pieces = new Piece[GameModel.ROWS][GameModel.COLS];
        for (int row = 0; row < GameModel.ROWS; row++) {
            for (int col = 0; col < GameModel.COLS; col++) {
                GameModel.pieces[row][col] = GameModel.originalPieces[row][col];
            }
        }
        
        GameModel.turnLabel.setText((GameModel.isBlueTeamTurn() ? "Blue" : "Green") + "'s Turn");
        gameView.updateBoardDisplay();
    }

    /**
     * Handles the initial piece selection for the game.
     * This method allows players to select their pieces before the game starts.
     * @param row The row of the selected piece.
     * @param col The column of the selected piece.
     */
    private void handleInitialPieceSelection(int row, int col) {
        if (GameModel.pieces[row][col] == null) return;
        
        if (!GameModel.isGameStarted()) {
            if (GameModel.pieces[row][col].isBlueTeam() && bluePieceSelected == null) {
                bluePieceSelected = GameModel.pieces[row][col];
                GameModel.grid[row][col].setIcon(gameView.overlayIcons(gameView.getTileIcon(row, col), GameModel.hiddenIcon));
            } else if (!GameModel.pieces[row][col].isBlueTeam() && redPieceSelected == null) {
                redPieceSelected = GameModel.pieces[row][col];
                GameModel.grid[row][col].setIcon(gameView.overlayIcons(gameView.getTileIcon(row, col), GameModel.hiddenIcon));
            }
            
            if (bluePieceSelected != null && redPieceSelected != null) {
                determineFirstTurn();
            }
        }
    }

    /**
     * Checks if the move is a valid lake jump move.
     * This method verifies if the piece can jump over the lake based on its type and position.
     * @param fromRow The row of the piece being moved.
     * @param fromCol The column of the piece being moved.
     * @param toRow The row of the destination tile.
     * @param toCol The column of the destination tile.
     * @return true if the move is a valid lake jump move, false otherwise.
     */
    private boolean isLakeJumpMove(int fromRow, int fromCol, int toRow, int toCol) {
        
        if (fromRow != toRow && fromCol != toCol) return false;
        
        
        if (GameModel.pieces[toRow][toCol] != null) {
            if (GameModel.pieces[toRow][toCol].isBlueTeam() == GameModel.pieces[fromRow][fromCol].isBlueTeam()) {
                return false; 
            }
            
            if (!GameModel.pieces[fromRow][fromCol].canCapture(GameModel.pieces[toRow][toCol])) {
                return false;
            }
        }
        
        
        if (fromRow == toRow) { 
            
            if ((fromCol == 2 && toCol == 6) || (fromCol == 6 && toCol == 2)) {
                
                for (int col = 3; col <= 5; col++) {
                    if (GameModel.pieces[fromRow][col] instanceof Rat) return false;
                }
                return isLake(fromRow, 4); 
            }
        } else { 
            
            if (((fromRow == 0 && toRow == 3) || (fromRow == 3 && toRow == 0) ||
                 (fromRow == 3 && toRow == 6) || (fromRow == 6 && toRow == 3)) &&
                (fromCol >= 3 && fromCol <= 5)) { 
                
                int minRow = Math.min(fromRow, toRow);
                int maxRow = Math.max(fromRow, toRow);
                for (int row = minRow + 1; row < maxRow; row++) {
                    if (GameModel.pieces[row][fromCol] instanceof Rat) return false;
                }
                return isLake((fromRow + toRow)/2, fromCol); 
            }
        }
        return false;
    }

    /**
     * Checks if the move is valid based on the piece type and destination tile.
     * This method verifies if the move is legal according to the game rules.
     * @param fromRow The row of the piece being moved.
     * @param fromCol The column of the piece being moved.
     * @param toRow The row of the destination tile.
     * @param toCol The column of the destination tile.
     * @return true if the move is valid, false otherwise.
     */
    private boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
        Piece piece = GameModel.pieces[fromRow][fromCol];
        
        
        if (isOwnDen(toRow, toCol, piece.isBlueTeam())) {
            return false;
        }

        if (piece instanceof Lion || piece instanceof Tiger) {
            if (isLakeJumpMove(fromRow, fromCol, toRow, toCol)) {
                return true;
            }
            if (isLake(toRow, toCol)) {
                return false; 
            }
        }

        if (!isAdjacent(fromRow, fromCol, toRow, toCol)) {
            return false; 
        }

        if (GameModel.pieces[toRow][toCol] != null) {
            Piece targetPiece = GameModel.pieces[toRow][toCol];

            if (targetPiece.isBlueTeam() == piece.isBlueTeam()) {
                return false;
            }
            
            if (isTrap(toRow, toCol) && !isOwnTrap(toRow, toCol, piece.isBlueTeam())) {
                return true;
            }

            if (!piece.canCapture(targetPiece)) {
                return false;
            }

        }
        
        boolean isLakeMove = isLake(toRow, toCol);
        return piece.canMoveToTile(fromRow, fromCol, toRow, toCol, isLakeMove);
    }

    /**
     * Handles the tile click event.
     * This method determines the action to take based on the clicked tile and the game state.
     * @param row The row of the clicked tile.
     * @param col The column of the clicked tile.
     */
    private void handleTileClick(int row, int col) {
        if (!GameModel.isGameStarted()) {
            handleInitialPieceSelection(row, col);
            return;
        }

        
        if (GameModel.pieces[row][col] != null && 
            GameModel.pieces[row][col].isBlueTeam() == GameModel.isBlueTeamTurn()) {
            gameView.clearSelection();
            selectedPiece = GameModel.pieces[row][col];
            selectedRow = row;
            selectedCol = col;
            gameView.highlightSelectedPiece(row, col);
            highlightValidMoves(row, col);
            requestFocus(); 
            return;
        }

        
        if (selectedPiece != null && isValidMove(selectedRow, selectedCol, row, col)) {
            movePiece(selectedRow, selectedCol, row, col);
            GameModel.toggleTurn();
            gameView.clearSelection();
        }
    }

    /**
     * Checks if the tile is a lake.
     * @param row The row of the tile.
     * @param col The column of the tile.
     * @return true if the tile is a lake, false otherwise.
     */
    public boolean isLake(int row, int col) {
        return ((row >= 1 && row <= 2 || row >= 4 && row <= 5) && 
                (col >= 3 && col <= 5));
    }

    /**
     * Checks if the tile is a trap.
     * @param row The row of the tile.
     * @param col The column of the tile.
     * @return true if the tile is a trap, false otherwise.
     */
    public boolean isTrap(int row, int col) {
        
        if (row == 2 && (col == 0 || col == 8)) return true;
        if (row == 3 && (col == 1 || col == 7)) return true;
        if (row == 4 && (col == 0 || col == 8)) return true;
        return false;
    }

    /**
     * Checks if the tile is an opponent's trap.
     * @param row The row of the tile.
     * @param col The column of the tile.
     * @param isBlueTeam Indicates if the current team is blue.
     * @return true if the tile is an opponent's trap, false otherwise.
     */
    private boolean isOwnTrap(int row, int col, boolean isBlueTeam) {
        // Blue team's traps are on the left side
        if (isBlueTeam) {
            return (row == 2 && col == 0) || (row == 3 && col == 1) || (row == 4 && col == 0);
        }
        // Green team's traps are on the right side
        return (row == 2 && col == 8) || (row == 3 && col == 7) || (row == 4 && col == 8);
    }

    /**
     * Checks if two tiles are adjacent.
     * @param fromRow The row of the first tile.
     * @param fromCol The column of the first tile.
     * @param toRow The row of the second tile.
     * @param toCol The column of the second tile.
     * @return true if the tiles are adjacent, false otherwise.
     */
    private boolean isAdjacent(int fromRow, int fromCol, int toRow, int toCol) {
        int rowDiff = Math.abs(fromRow - toRow);
        int colDiff = Math.abs(fromCol - toCol);
        return (rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1);
    }

    /**
     * Checks if the tile is a den.
     * @param row The row of the tile.
     * @param col The column of the tile.
     * @return true if the tile is a den, false otherwise.
     */
    public boolean isDen(int row, int col) {
        return (row == 3 && (col == 0 || col == 8)); 
    }

    /**
     * Checks if the tile is the player's own den.
     * @param row The row of the tile.
     * @param col The column of the tile.
     * @param isBlueTeam Indicates if the current team is blue.
     * @return true if the tile is the player's own den, false otherwise.
     */
    public boolean isOwnDen(int row, int col, boolean isBlueTeam) {
        
        return isDen(row, col) && (isBlueTeam ? (col == 0) : (col == 8));
    }

    /**
     * Adds action listeners to the game pieces for selection.
     * This method allows players to select their pieces by clicking on them.
     */
    public void addPieceSelectionListeners() {
        for (int row = 0; row < GameModel.ROWS; row++) {
            for (int col = 0; col < GameModel.COLS; col++) {
                final int r = row;
                final int c = col;
                GameModel.grid[row][col].addActionListener(e -> handleTileClick(r, c));
            }
        }
    }

    /**
     * Handles key press events for piece movement.
     * This method allows players to move their selected piece using keyboard keys (WASD).
     * @param e The key event triggered by the user.
     */
    public void handleKeyPress(java.awt.event.KeyEvent e) {
        if (selectedPiece == null || !GameModel.isGameStarted()) return;

        int newRow = selectedRow;
        int newCol = selectedCol;

        switch (e.getKeyChar()) {
            case 'w': case 'W': newRow--; break;
            case 's': case 'S': newRow++; break;
            case 'a': case 'A': newCol--; break;
            case 'd': case 'D': newCol++; break;
            default: return;
        }

        if (newRow >= 0 && newRow < GameModel.ROWS && newCol >= 0 && newCol < GameModel.COLS) {
            if (isValidMove(selectedRow, selectedCol, newRow, newCol)) {
                movePiece(selectedRow, selectedCol, newRow, newCol);
                GameModel.toggleTurn();
                gameView.clearSelection();
            }
        }
    }

    
}
