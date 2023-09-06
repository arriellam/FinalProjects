package org.cis1200.othello;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Collection;

public class Board extends JPanel {
    private final Othello othello; // model for the game
    private final JLabel status; // current status text
    private final JFrame frame;

    // Game constants
    public static final int BOARD_WIDTH = 720;
    public static final int BOARD_HEIGHT = 720;

    /**
     * Initializes the game board.
     */
    public Board(JLabel statusInit, JFrame frame) {
        // creates border around the court area
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus
        setFocusable(true);
        othello = new Othello(); // initializes model for the game
        status = statusInit; // initializes the status JLabel
        this.frame = frame;
        /*
         * Listens for mouseclicks. Updates the model, then updates the game
         * board based off of the updated model.
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!othello.gameOver()) {
                    Point p = e.getPoint();

                    // updates the model given the coordinates of the mouseclick
                    try {
                        othello.playTurn(p.y / 90, p.x / 90);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    repaint();
                    updateStatus();
                }
            }
        });
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset(boolean b) {
        othello.reset(b);
        updateStatus();
        repaint();

        requestFocusInWindow();
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        int w = othello.getWhiteDiscs();
        int b = othello.getBlackDiscs();

        if (othello.getCurrentPlayer()) {
            status.setText("Player 1's Turn    W Discs: " + w + "  B Discs: " + b);
        } else {
            status.setText("Player 2's Turn    W Discs: " + w + "  B Discs: " + b);
        }
        int winner = othello.checkWinner();
        if (winner == 1 || winner == 2 || winner == 3) {
            status.setText("GameOver   W Discs: " + w + "  B Discs: " + b);
        }

        if (winner == 1) {
            JOptionPane.showMessageDialog(frame, "Player 1 Wins!");
        } else if (winner == 2) {
            JOptionPane.showMessageDialog(frame, "Player 2 Wins!");
        } else if (winner == 3) {
            JOptionPane.showMessageDialog(frame, "It's a tie!");
        }
        if (!othello.gameOver()) {
            if (othello.getCurrentPlayer() && othello.getBlackPossibleMoves().size() == 0) {
                JOptionPane.showMessageDialog(frame, "Player 1 has no possible moves");
                othello.setCurrentPlayer(false);
                status.setText("Player 2's Turn    W Discs: " + w + "  B Discs: " + b);
                repaint();
            } else if (!othello.getCurrentPlayer() && othello.getWhitePossibleMoves().size() == 0) {
                JOptionPane.showMessageDialog(frame, "Player 2 has no possible moves");
                othello.setCurrentPlayer(true);
                status.setText("Player 1's Turn    W Discs: " + w + "  B Discs: " + b);
                repaint();
            }
        }
    }

    /**
     * Draws the game board.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color color = new Color(72, 93, 63);
        g.setColor(color);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        g.setColor(Color.BLACK);
        // Draws board grid
        for (int i = 0; i <= BOARD_HEIGHT; i += BOARD_HEIGHT / 8) {
            g.drawLine(i, 0, i, BOARD_HEIGHT);
        }
        for (int i = 0; i < BOARD_WIDTH; i += BOARD_WIDTH / 8) {
            g.drawLine(0, i, BOARD_WIDTH, i);
        }

        Disc[][] discs = othello.getBoard();
        for (int r = 0; r < discs.length; r++) {
            for (int c = 0; c < discs.length; c++) {
                if (discs[r][c] == Othello.BLACK_DISC) {
                    g.setColor(Color.black);
                    g.fillOval(
                            c * (BOARD_WIDTH / 8), r * (BOARD_HEIGHT / 8),
                            BOARD_WIDTH / 8, BOARD_HEIGHT / 8
                    );
                } else if (discs[r][c] == Othello.WHITE_DISC) {
                    g.setColor(Color.white);
                    g.fillOval(
                            c * (BOARD_WIDTH / 8), r * (BOARD_HEIGHT / 8),
                            BOARD_WIDTH / 8, BOARD_HEIGHT / 8
                    );
                }
            }
        }
        if (othello.getCurrentPlayer()) {
            Collection<Position> pos = othello.getBlackPossibleMoves();
            for (Position p : pos) {
                g.setColor(Color.black);
                g.drawOval(
                        p.getY() * (BOARD_WIDTH / 8), p.getX() * (BOARD_HEIGHT / 8),
                        BOARD_WIDTH / 8, BOARD_HEIGHT / 8
                );
            }
        } else {
            Collection<Position> pos = othello.getWhitePossibleMoves();
            for (Position p : pos) {
                g.setColor(Color.white);
                g.drawOval(
                        p.getY() * (BOARD_WIDTH / 8), p.getX() * (BOARD_HEIGHT / 8),
                        BOARD_WIDTH / 8, BOARD_HEIGHT / 8
                );
            }
        }
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }

    public void saveGame() {
        try {
            othello.saveGameState();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean gameOver() {
        return othello.gameOver();
    }
}
