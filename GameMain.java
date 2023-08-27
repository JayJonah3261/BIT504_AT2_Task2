import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameMain<statusBar> extends JPanel implements MouseListener {
    private static final long serialVersionUID = 1L;
	// Constants for game
    // number of ROWS by COLS cell constants
    public static final int ROWS = 3;
    public static final int COLS = 3;
    public static final String TITLE = "Tic Tac Toe";

    // Constants for dimensions used for drawing
    // cell width and height
    public static final int CELL_SIZE = 100;
    // drawing canvas
    public static final int CANVAS_WIDTH = CELL_SIZE * COLS;
    public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
    // Noughts and Crosses are displayed inside a cell, with padding from border
    public static final int CELL_PADDING = CELL_SIZE / 6;
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
    public static final int SYMBOL_STROKE_WIDTH = 8;

    /* declare game object variables */
    // the game board
    private Board board;

    // Enumeration for GameState
    private enum GameState {
        Playing, Draw, Cross_won, Nought_won
    }

    private GameState currentState;
    private Player currentPlayer;
    private JLabel statusBar;

    /** Constructor to setup the UI and game components on the panel */
    public GameMain() {
        addMouseListener(this);

        // Setup the status bar (JLabel) to display status message
        statusBar = new JLabel();
        statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14));
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
        statusBar.setOpaque(true);
        statusBar.setBackground(Color.LIGHT_GRAY);

        setLayout(new BorderLayout());
        add(statusBar, BorderLayout.SOUTH);
        setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT + 30));

        board = new Board();
        initGame();
    }

    private void add(JLabel statusBar2, String south) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(TITLE);
            @SuppressWarnings("rawtypes")
			GameMain panel = new GameMain();
            frame.add(panel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);
        board.paint(g);

        if (currentState == GameState.Playing) {
            statusBar.setBackground(Color.BLACK);
            if (currentPlayer == Player.Cross) {
                statusBar.setText("X's Turn");
            } else {
                statusBar.setText("O's Turn");
            }
        } else if (currentState == GameState.Draw) {
            statusBar.setBackground(Color.RED);
            statusBar.setText("It's a Draw! Click to play again.");
        } else if (currentState == GameState.Cross_won) {
            statusBar.setBackground(Color.RED);
            statusBar.setText("'X' Won! Click to play again.");
        } else if (currentState == GameState.Nought_won) {
            statusBar.setBackground(Color.RED);
            statusBar.setText("'O' Won! Click to play again.");
        }
    }

    public void initGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                board.cells[row][col].content = Player.Empty;
            }
        }
        currentState = GameState.Playing;
        currentPlayer = Player.Cross;
    }

    public void updateGame(Player thePlayer, int row, int col) {
        if (board.hasWon(thePlayer, row, col)) {
            if (thePlayer == Player.Cross) {
                currentState = GameState.Cross_won;
            } else {
                currentState = GameState.Nought_won;
            }
        } else if (board.isDraw()) {
            currentState = GameState.Draw;
        }
    }

    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        int rowSelected = mouseY / CELL_SIZE;
        int colSelected = mouseX / CELL_SIZE;

        if (currentState == GameState.Playing) {
            if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0 && colSelected < COLS &&
                    board.cells[rowSelected][colSelected].content == Player.Empty) {

                board.cells[rowSelected][colSelected].content = currentPlayer;
                updateGame(currentPlayer, rowSelected, colSelected);

                if (currentPlayer == Player.Cross) {
                    currentPlayer = Player.Noughts;
                } else {
                    currentPlayer = Player.Cross;
                }
            }
        } else {
            initGame();
        }

        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}

