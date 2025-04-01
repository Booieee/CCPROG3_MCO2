import javax.swing.*;
import src.model.GameModel;

/**
 * The Main class serves as the entry point for the Jungle King Game.
 * It initializes and starts the game by invoking the GameModel's Start method.
 */
public class Main extends JFrame {

    /**
     * The main method starts the game by calling GameModel.Start().
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        GameModel.Start();
    }
}