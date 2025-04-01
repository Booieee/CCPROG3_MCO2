package src.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;

import java.awt.image.BufferedImage;

import src.controller.GameController;
import src.model.GameModel;
import src.model.pieces.*;


/**
 * The GameView class represents the graphical user interface of the Jungle King Game.
 * It initializes the game board, handles UI interactions, and manages visual updates.
 */
public class GameView extends JFrame {
    private final GameController gameController;

    /**
     * Constructs the GameView and initializes all GUI components.
     *
     * @param controller The game controller responsible for handling game logic.
     */
    public GameView(GameController controller) {
        this.gameController = controller;
        setTitle("Jungle King Game Board");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

         // Initialize top panel with labels and buttons
        JPanel topPanel = new JPanel();
        GameModel.turnLabel = new JLabel("Waiting for initial piece selection...");
        GameModel.turnLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(GameModel.turnLabel);

        
        GameModel.restartBtn = new JButton("Restart");
        GameModel.restartBtn.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(GameModel.restartBtn);

        GameModel.restartBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameModel.Restart(GameView.this);
            }
        });

        add(topPanel, BorderLayout.NORTH);
       
        // Initialize board panel
        JPanel boardPanel = new JPanel(new GridLayout(GameModel.ROWS, GameModel.COLS));
        add(boardPanel, BorderLayout.CENTER);

        // Load board images
        String basePath = "/Assets/Board/";
        System.out.println("Base path: " + basePath); // Debugging output
        
        GameModel.landIcon = scaleImage(basePath + "land.png", 100, 100);
        GameModel.lakeIcon = scaleImage(basePath + "lake.png", 100, 100);
        GameModel.trapIcon = scaleImage(basePath + "trap.png", 100, 100);
        GameModel.denIcon = scaleImage(basePath + "den.png", 100, 100);
        GameModel.hiddenIcon = scaleImage(basePath + "hidden.png", 100, 100);

        
        // Initialize game grid and pieces
        GameModel.grid = new JButton[GameModel.ROWS][GameModel.COLS];
        GameModel.pieces = new Piece[GameModel.ROWS][GameModel.COLS];
        initializeBoard(boardPanel);
        initializePieces();
        gameController.addPieceSelectionListeners();

         // Keyboard event handling
        setFocusTraversalKeysEnabled(false);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                gameController.handleKeyPress(e);
            }
        });
        setFocusable(true);
        requestFocus();
    }

    /**
     * Initializes the game pieces and assigns them to the board.
     * Pieces are shuffled before being placed.
     */
    private void initializePieces() {
        
        GameModel.pieces = new Piece[GameModel.ROWS][GameModel.COLS];
        GameModel.originalPieces = new Piece[GameModel.ROWS][GameModel.COLS];

        // Initializing predefined pieces

        GameModel.originalPieces[0][0] = new Lion(true);
        GameModel.originalPieces[0][2] = new Elephant(true);
        GameModel.originalPieces[1][1] = new Cat(true);
        GameModel.originalPieces[2][2] = new Wolf(true);
        GameModel.originalPieces[4][2] = new Leopard(true);
        GameModel.originalPieces[5][1] = new Dog(true);
        GameModel.originalPieces[6][0] = new Tiger(true);
        GameModel.originalPieces[6][2] = new Rat(true);
        
        
        GameModel.originalPieces[0][8] = new Tiger(false);
        GameModel.originalPieces[1][7] = new Dog(false);
        GameModel.originalPieces[2][6] = new Leopard(false);
        GameModel.originalPieces[0][6] = new Rat(false);
        GameModel.originalPieces[6][6] = new Elephant(false);
        GameModel.originalPieces[6][8] = new Lion(false);
        GameModel.originalPieces[5][7] = new Cat(false);
        GameModel.originalPieces[4][6] = new Wolf(false);

        // Shuffle and assign pieces randomly
        java.util.List<Piece> allPieces = new java.util.ArrayList<>();
        for (int row = 0; row < GameModel.ROWS; row++) {
            for (int col = 0; col < GameModel.COLS; col++) {
                if (GameModel.originalPieces[row][col] != null) {
                    allPieces.add(GameModel.originalPieces[row][col]);
                }
            }
        }

        
        java.util.Collections.shuffle(allPieces);
        int pieceIndex = 0;
        for (int row = 0; row < GameModel.ROWS; row++) {
            for (int col = 0; col < GameModel.COLS; col++) {
                if (!isLake(row, col) && !isDen(row, col) && pieceIndex < allPieces.size()) {
                    GameModel.pieces[row][col] = allPieces.get(pieceIndex++);
                }
            }
        }

        updateBoardDisplayWithHiddenPieces();
    }

     /**
     * Initializes the game board with buttons representing each tile.
     *
     * @param boardPanel The panel where the board is displayed.
     */
    private void initializeBoard(JPanel boardPanel) {
        for (int row = 0; row < GameModel.ROWS; row++) {
            for (int col = 0; col < GameModel.COLS; col++) {
                GameModel.grid[row][col] = new JButton();
                GameModel.grid[row][col].setIcon(getTileIcon(row, col));
                GameModel.grid[row][col].setBorderPainted(true); 
                GameModel.grid[row][col].setFocusPainted(false);
                GameModel.grid[row][col].setContentAreaFilled(false);
                boardPanel.add(GameModel.grid[row][col]);
            }
        }
    }

    /**
     * Updates the board display, initially hiding all pieces.
     */
    private void updateBoardDisplayWithHiddenPieces() {
        for (int row = 0; row < GameModel.ROWS; row++) {
            for (int col = 0; col < GameModel.COLS; col++) {
                ImageIcon baseIcon = getTileIcon(row, col);
                if (GameModel.pieces[row][col] != null) {
                    GameModel.grid[row][col].setIcon(overlayIcons(baseIcon, GameModel.hiddenIcon));
                } else {
                    GameModel.grid[row][col].setIcon(baseIcon);
                }
            }
        }
    }

    /**
     * Overlays two icons to create a combined image.
     *
     * @param backgroundIcon The background icon.
     * @param foregroundIcon The foreground icon to overlay.
     * @return A new ImageIcon that combines the two images.
     */
    public ImageIcon overlayIcons(ImageIcon backgroundIcon, ImageIcon foregroundIcon) {
        Image background = backgroundIcon.getImage();
        Image foreground = foregroundIcon.getImage();
        
        BufferedImage combined = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = combined.createGraphics();
        
        g.drawImage(background, 0, 0, 100, 100, null);
        g.drawImage(foreground, 0, 0, 100, 100, null);
        g.dispose();
        
        return new ImageIcon(combined);
    }

    /**
     * Returns the icon for a specific tile based on its type.
     *
     * @param row The row of the tile.
     * @param col The column of the tile.
     * @return The ImageIcon representing the tile type.
     */
    public ImageIcon getTileIcon(int row, int col) {
        if (isLake(row, col)) return GameModel.lakeIcon;
        if (isTrap(row, col)) return GameModel.trapIcon;
        if (isDen(row, col)) return GameModel.denIcon;
        return GameModel.landIcon;
    }

    /**
     * Checks if the tile at the specified coordinates is a lake.
     * @param row
     * @param col
     * @return true if the tile is a lake, false otherwise.
     */
    private boolean isLake(int row, int col) {
        return gameController.isLake(row, col);
    }

    /**
     * Checks if the tile at the specified coordinates is a trap.
     * @param row
     * @param col
     * @return true if the tile is a trap, false otherwise.
     */
    private boolean isTrap(int row, int col) {
        return gameController.isTrap(row, col);
    }

    /**
     * Checks if the tile at the specified coordinates is a den.
     * @param row
     * @param col
     * @return true if the tile is a den, false otherwise.
     */
    private boolean isDen(int row, int col) {
        return gameController.isDen(row, col);
    }

    /**
     * Scales an image to the specified width and height.
     *
     * @param resourcePath The path to the image resource.
     * @param width        The desired width of the scaled image.
     * @param height       The desired height of the scaled image.
     * @return A scaled ImageIcon, or a fallback icon if loading fails.
     */
    private ImageIcon scaleImage(String resourcePath, int width, int height) {
            
        try {
            System.out.println("Attempting to load resource: " + resourcePath); // Debugging output
            java.net.URL resourceUrl = getClass().getResource(resourcePath);
    
            if (resourceUrl == null) {
                System.err.println("Resource not found: " + resourcePath);
                return createFallbackIcon(width, height);
            }
    
            ImageIcon icon = new ImageIcon(resourceUrl);
    
            if (icon.getImageLoadStatus() != MediaTracker.COMPLETE) {
                System.err.println("Failed to load resource: " + resourcePath);
                return createFallbackIcon(width, height);
            }
    
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImg);
        } catch (Exception e) {
            System.err.println("Error loading resource " + resourcePath + ": " + e.getMessage());
            return createFallbackIcon(width, height);
        }
        
    }

    /**
     * Creates a fallback icon in case the image loading fails.
     *
     * @param width  The width of the fallback icon.
     * @param height The height of the fallback icon.
     * @return A fallback ImageIcon.
     */
    private ImageIcon createFallbackIcon(int width, int height) {
        BufferedImage fallback = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = fallback.createGraphics();
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, width-1, height-1);
        g.dispose();
        return new ImageIcon(fallback);
    }

    /**
     * Clears the current selection of a piece.
     */
    public void clearSelection() {
        gameController.selectedPiece = null;
        gameController.selectedRow = -1;
        gameController.selectedCol = -1;
        
        
        for (int r = 0; r < GameModel.ROWS; r++) {
            for (int c = 0; c < GameModel.COLS; c++) {
                GameModel.grid[r][c].setBorder(null);
            }
        }
        requestFocus(); 
    }

    /**
     * Updates the display of the game board with the current pieces.
     */
    public void updateBoardDisplay() {
        for (int row = 0; row < GameModel.ROWS; row++) {
            for (int col = 0; col < GameModel.COLS; col++) {
                ImageIcon baseIcon = getTileIcon(row, col);
                if (GameModel.pieces[row][col] != null) {
                    GameModel.grid[row][col].setIcon(overlayIcons(baseIcon, GameModel.pieces[row][col].getIcon()));
                } else {
                    GameModel.grid[row][col].setIcon(baseIcon);
                }
            }
        }
    }
/**
     * Highlights the selected piece.
     *
     * @param row The row of the selected piece.
     * @param col The column of the selected piece.
     */
    public void highlightSelectedPiece(int row, int col) {
        
        GameModel.grid[row][col].setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 1),
            BorderFactory.createLineBorder(Color.GREEN, 3)
        ));
    }

}
