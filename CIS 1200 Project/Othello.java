package org.cis1200.othello;

import java.awt.*;
import java.io.*;
import java.util.*;

public class Othello {

    private Disc[][] board;
    private boolean player1;
    public static final Disc BLACK_DISC = new Disc(Color.BLACK);
    public static final Disc WHITE_DISC = new Disc(Color.WHITE);
    private int blackDiscs;
    private int whiteDiscs;
    private Collection<Position> whitePossibleMoves;
    private Collection<Position> blackPossibleMoves;

    /**
     * Constructor sets up game state.
     */
    public Othello() {
        reset(false);
    }

    public void reset(boolean saved) {
        if (!saved) {
            board = new Disc[8][8];
            player1 = true;

            board[3][3] = WHITE_DISC;
            board[3][4] = BLACK_DISC;
            board[4][3] = BLACK_DISC;
            board[4][4] = WHITE_DISC;
            blackDiscs = 2;
            whiteDiscs = 2;
        } else {
            try {
                setUpBoard();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        updatePM();
    }

    public void saveGameState() throws IOException {
        FileOutputStream fin1 = new FileOutputStream("files/gamestate.txt");
        OutputStream fin = new BufferedOutputStream(fin1);

        if (player1) {
            fin.write(4);
        } else {
            fin.write(5);
        }

        for (Disc[] discs : board) {
            for (int j = 0; j < board.length; j++) {
                if (discs[j] == null) {
                    fin.write(0);
                } else if (discs[j] == BLACK_DISC) {
                    fin.write(1);
                } else {
                    fin.write(2);
                }
            }
        }
        fin.close();
    }

    public void setUpBoard() throws IOException {
        FileInputStream fin1 = new FileInputStream("files/gamestate.txt");
        InputStream fin = new BufferedInputStream(fin1);

        board = new Disc[8][8];
        blackDiscs = 0;
        whiteDiscs = 0;
        int c = fin.read();
        if (c == -1) {
            fin.close();
            throw new IOException("File ended early");
        } else if (c == 4) {
            player1 = true;
        } else if (c == 5) {
            player1 = false;
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                int ch = fin.read();
                if (ch == -1) {
                    fin.close();
                    throw new IOException("File ended early");
                } else if (ch == 1) {
                    board[i][j] = BLACK_DISC;
                    blackDiscs++;
                } else if (ch == 2) {
                    board[i][j] = WHITE_DISC;
                    whiteDiscs++;
                }
            }
        }
    }

    /**
     * this function is for testing purposes
     */
    public void setBoard(int[][] arr) {
        blackDiscs = 0;
        whiteDiscs = 0;
        board = new Disc[8][8];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (arr[i][j] == 1) {
                    board[i][j] = BLACK_DISC;
                    blackDiscs++;
                } else if (arr[i][j] == 2) {
                    board[i][j] = WHITE_DISC;
                    whiteDiscs++;
                }
            }
        }
        updatePM();
    }

    /**
     * playTurn allows players to play a turn. Returns true if the move is
     * successful and false if a player tries to play in a location that is
     * taken or after the game has ended. If the turn is successful and the game
     * has not ended, the player is changed. If the turn is unsuccessful or the
     * game has ended, the player is not changed.
     *
     * @param c column to play in
     * @param r row to play in
     */
    public void playTurn(int r, int c) throws IOException {
        if (!validMove(r, c) || gameOver()) {
            return;
        }
        Disc disc;
        if (player1) {
            board[r][c] = BLACK_DISC;
            blackDiscs++;
            disc = BLACK_DISC;
        } else {
            board[r][c] = WHITE_DISC;
            whiteDiscs++;
            disc = WHITE_DISC;
        }

        flipAll(r, c, disc, 0, -1);
        flipAll(r, c, disc, 0, 1);
        flipAll(r, c, disc, 1, 0);
        flipAll(r, c, disc, -1, 0);
        flipAll(r, c, disc, 1, 1);
        flipAll(r, c, disc, 1, -1);
        flipAll(r, c, disc, -1, 1);
        flipAll(r, c, disc, -1, -1);

        if (checkWinner() == 0) {
            player1 = !player1;
        }
        updatePM();
    }

    private void flipAll(int r, int c, Disc disc, int rDir, int cDir) {
        int currR = r + rDir;
        int currC = c + cDir;
        Collection<Position> poss = new LinkedList<>();
        Disc otherDisc = BLACK_DISC;
        if (disc == BLACK_DISC) {
            otherDisc = WHITE_DISC;
        }
        if (currR < 0 || currC < 0 || currR >= 8 || currC >= 8) {
            return;
        }

        if ((board[currR][currC] == null)) {
            return;
        }

        Disc currDisc = board[currR][currC];
        while (currDisc == otherDisc) {
            poss.add(new Position(currR, currC));
            currR += rDir;
            currC += cDir;

            if (currR < 0 || currC < 0 || currR >= 8 || currC >= 8) {
                return;
            }
            currDisc = board[currR][currC];
            if (currDisc == null) {
                return;
            } else if (currDisc == disc) {
                for (Position p : poss) {
                    flip(p.getX(), p.getY());
                }
            }
        }
        // update num discs
        if (disc == BLACK_DISC) {
            blackDiscs += poss.size();
            whiteDiscs -= poss.size();
        } else {
            whiteDiscs += poss.size();
            blackDiscs -= poss.size();
        }
    }

    private void flip(int r, int c) {
        Disc d = board[r][c];
        if (d == BLACK_DISC) {
            board[r][c] = WHITE_DISC;
        } else {
            board[r][c] = BLACK_DISC;
        }
    }

    /**
     * This function checks whether the clicked position is valid
     * The input is the array position
     *
     * @return whether the location is a valid move.
     */
    private boolean validMove(int r, int c) {
        // Position p = new Position(r, c);
        if (player1) {
            return blackPossibleMoves.contains(new Position(r, c));
        } else {
            return whitePossibleMoves.contains(new Position(r, c));
        }
    }

    private void updatePM() {
        whitePossibleMoves = new TreeSet<>();
        blackPossibleMoves = new TreeSet<>();
        Disc d;

        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board.length; c++) {
                if (board[r][c] != null) {
                    if (board[r][c] == BLACK_DISC) {
                        d = WHITE_DISC;
                    } else {
                        d = BLACK_DISC;
                    }
                } else {
                    d = null;
                }

                check(r, c, d, 0, -1);
                check(r, c, d, 0, 1);
                check(r, c, d, 1, 0);
                check(r, c, d, -1, 0);
                check(r, c, d, 1, 1);
                check(r, c, d, 1, -1);
                check(r, c, d, -1, 1);
                check(r, c, d, -1, -1);
            }
        }
    }

    private void check(int r, int c, Disc d, int rDir, int cDir) {
        if (d == null) {
            return;
        }
        int currR = r + rDir;
        int currC = c + cDir;
        if (currR < 0 || currC < 0 || currR >= 8 || currC >= 8) {
            return;
        }

        Disc currD = board[currR][currC];
        if ((currD == null)) {
            return;
        }

        while (currD == d) {
            currR += rDir;
            currC += cDir;

            if (currR < 0 || currC < 0 || currR >= 8 || currC >= 8) {
                return;
            }
            currD = board[currR][currC];
            if (currD == null) {
                if (d == BLACK_DISC) {
                    whitePossibleMoves.add(new Position(currR, currC));
                    return;
                } else {
                    blackPossibleMoves.add(new Position(currR, currC));
                    return;
                }
            }
        }

    }

    /**
     * checkWinner checks whether the game has reached a win condition.
     * A player wins if the at the end of the game, that player has more of
     * their discs on the board that the opponent.
     *
     * @return 0 if nobody has won yet, 1 if player 1 has won, and 2 if player 2
     *         has won, 3 if the game hits stalemate
     */
    public int checkWinner() {
        if (gameOver()) {
            if (blackDiscs > whiteDiscs) {
                return 1;
            } else if (blackDiscs < whiteDiscs) {
                return 2;
            } else {
                return 3;
            }
        }
        return 0;
    }

    /**
     * Update GameOver checks if possible moves can still be made.
     * returns false if the board is filled, or if there are no more possible moves
     * to be made.
     *
     * @return whether the game is over.
     */
    public boolean gameOver() {
        return whitePossibleMoves.size() == 0 && blackPossibleMoves.size() == 0;
    }

    /**
     * getCurrentPlayer is a getter for the player
     * whose turn it is in the game.
     *
     * @return true if it's Player 1's turn,
     *         false if it's Player 2's turn.
     */
    public boolean getCurrentPlayer() {
        return player1;
    }

    public Disc[][] getBoard() {
        return Arrays.copyOf(board, board.length);
    }

    public int getBlackDiscs() {
        return blackDiscs;
    }

    public int getWhiteDiscs() {
        return whiteDiscs;
    }

    public Collection<Position> getBlackPossibleMoves() {
        return new LinkedList<>(blackPossibleMoves);
    }

    public Collection<Position> getWhitePossibleMoves() {
        return new LinkedList<>(whitePossibleMoves);
    }

    public void setCurrentPlayer(boolean b) {
        player1 = b;
    }
}
