package src.model;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import src.controller.GameController;
import src.model.pieces.Piece;
import src.view.GameView;

/**
 * GameModel class to manage the game state and pieces.
 * This class is responsible for keeping track of the game state, including whether the game has started,
 * whether it has ended, whose turn it is, and the pieces on the board.
 */
public class GameModel {
    public static boolean isGameStarted;
    public static boolean isGameEnded;
    public static boolean isBlueTeamTurn;
    
    public static Piece[][] pieces;
    public static Piece[][] originalPieces;

    public static final int ROWS = 7;
    public static final int COLS = 9;
    public static JButton[][] grid;
    public static ImageIcon landIcon;
    public static ImageIcon lakeIcon;
    public static ImageIcon trapIcon;
    public static ImageIcon denIcon;
    public static ImageIcon hiddenIcon;
    public static JLabel turnLabel;
    public static JButton restartBtn;
    public static GameView view;

    /**
     * Constructor to initialize the game model.
     * Sets the initial state of the game to not started and not ended.
     */
    public GameModel() {
        isGameStarted = false;
        isGameEnded = false;
        isBlueTeamTurn = true;
    }

    /**
     * Method to start the game.
     * Initializes the game state and sets the pieces on the board.
     * @param bluePiece The piece for the blue team.
     * @param greenPiece The piece for the green team.
     * @param blueTeamFirst Indicates if the blue team goes first.
     */
    public static void startGame(Piece bluePiece, Piece greenPiece, boolean blueTeamFirst) {
        isGameStarted = true;
        isBlueTeamTurn = blueTeamFirst;
    }

    /**
     * boolean method to check if the game has started.
     * @return true if the game has started, false otherwise.
     */
    public static boolean isGameStarted() {
        return isGameStarted;
    }

    /**
     * Method to check if the game has ended.
     * @return true if the game has ended, false otherwise.
     */
    public static boolean isGameEnded() {
        return isGameEnded;
    }

    /**
     * Method to set the game as ended.
     * @param ended Indicates if the game has ended.
     */
    public static void setGameEnded(boolean ended) {
        isGameEnded = ended;
    }

    /**
     * Method to check if it's the blue team's turn.
     * @return true if it's the blue team's turn, false otherwise.
     */
    public static boolean isBlueTeamTurn() {
        return isBlueTeamTurn;
    }

    /**
     * Method to set the turn to the other team.
     */
    public static void toggleTurn() {
        isBlueTeamTurn = !isBlueTeamTurn;
    }

    /**
     * Method to restart the game.
     * This method disposes of the current JFrame and creates a new instance of the game.
     * @param jFrame The current JFrame instance to be disposed of.
     */
    public static void Restart(JFrame jFrame) {
        SwingUtilities.invokeLater(() -> {
            jFrame.dispose(); 
            GameModel.isGameStarted = false;
            GameModel.isGameEnded = false;;
            
            GameController controller = new GameController();
            GameView view = new GameView(controller);
            controller.setGameView(view); 
            GameModel.view = view;
            view.setVisible(true);
        });
    }

    /**
     * Method to start the game.
     * This method initializes the game model and sets the game view.
     */
    public static void Start() {
        SwingUtilities.invokeLater(() -> {
            GameController controller = new GameController();
            view = new GameView(controller);
            controller.setGameView(view); 
            view.setVisible(true);
        });
    }
}
