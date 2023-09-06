package org.cis1200.othello;

import org.junit.jupiter.api.*;

import java.awt.*;
import java.io.IOException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

/**
 * You can use this file (and others) to test your
 * implementation.
 */

public class GameTest {
    /**
     * Tests that the board size is correct, it is player 1's turn,
     * and that the number of possible moves is correct.
     */
    @Test
    public void testGameSetUp() {
        Othello game = new Othello();
        assertEquals(8, game.getBoard().length);
        assertEquals(2, game.getBlackDiscs());
        assertEquals(2, game.getWhiteDiscs());
        assertTrue(game.getCurrentPlayer());
        assertEquals(4, game.getBlackPossibleMoves().size());
        assertEquals(4, game.getWhitePossibleMoves().size());
    }

    @Test
    public void testFirstTurn() throws IOException {
        Othello game = new Othello();
        game.playTurn(2, 3);
        assertFalse(game.getCurrentPlayer());
        assertEquals(3, game.getWhitePossibleMoves().size());
        assertEquals(1, game.getWhiteDiscs());
        assertEquals(4, game.getBlackDiscs());

    }

    @Test
    public void testInvalidTurn() throws IOException {
        Othello game = new Othello();
        game.playTurn(0, 0);
        assertEquals(2, game.getBlackDiscs());
        assertEquals(2, game.getWhiteDiscs());
        assertTrue(game.getCurrentPlayer());
        assertEquals(4, game.getBlackPossibleMoves().size());
        assertEquals(4, game.getWhitePossibleMoves().size());
    }

    @Test
    public void testEndGameNotFullBoard() {
        Othello game = new Othello();
        int[][] arr = {
                {1, 1, 1, 1, 1, 0, 0, 0},
                {1, 1, 1, 1, 1, 0, 0, 0},
                {1, 1, 1, 1, 1, 0, 2, 0},
                {1, 1, 1, 1, 1, 0, 0, 0},
                {1, 1, 1, 2, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 0, 0, 0},
                {1, 1, 1, 1, 1, 0, 0, 0},
                {1, 1, 1, 1, 1, 0, 0, 0}
        };
        game.setBoard(arr);
        assertEquals(2, game.getWhiteDiscs());
        assertEquals(38, game.getBlackDiscs());
        assertEquals(2, game.getWhitePossibleMoves().size());
        assertEquals(1, game.getBlackPossibleMoves().size());
        try {
            game.playTurn(4, 4);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(1, game.getWhiteDiscs());
        assertEquals(40, game.getBlackDiscs());
        assertEquals(0, game.getWhitePossibleMoves().size());
        assertEquals(0, game.getBlackPossibleMoves().size());
        assertTrue(game.gameOver());
        assertEquals(1, game.checkWinner());

    }

    @Test
    public void testEndGameFullBoard() {
        Othello game = new Othello();
        int[][] arr = {
                {1, 1, 1, 1, 1, 2, 2, 2},
                {1, 1, 1, 1, 1, 2, 2, 2},
                {1, 1, 1, 1, 1, 2, 2, 2},
                {1, 1, 1, 1, 1, 2, 2, 2},
                {1, 1, 1, 2, 2, 2, 2, 2},
                {1, 1, 1, 1, 1, 2, 2, 2},
                {1, 1, 1, 1, 1, 2, 2, 2},
                {1, 1, 1, 1, 1, 1, 2, 0}
        };
        game.setBoard(arr);
        try {
            game.playTurn(7, 7);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertTrue(game.gameOver());
        assertEquals(1, game.checkWinner());
    }

    @Test
    public void testTie() {
        Othello game = new Othello();
        int[][] arr = {
                {2, 1, 1, 1, 2, 2, 2, 2},
                {1, 2, 1, 1, 1, 1, 2, 2},
                {1, 1, 2, 1, 2, 2, 2, 2},
                {1, 1, 1, 2, 2, 2, 2, 2},
                {1, 1, 1, 2, 2, 2, 2, 2},
                {1, 1, 1, 1, 2, 2, 2, 2},
                {1, 1, 1, 1, 2, 2, 2, 2},
                {1, 1, 1, 1, 2, 1, 2, 0}
        };
        game.setBoard(arr);
        try {
            game.playTurn(7, 7);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertTrue(game.gameOver());
        assertEquals(3, game.checkWinner());
    }

    @Test
    public void testWinner() {
        Othello game = new Othello();
        int[][] arr = {
                {2, 1, 1, 1, 2, 2, 2, 2},
                {1, 2, 1, 1, 1, 1, 1, 2},
                {1, 1, 2, 1, 2, 2, 2, 2},
                {1, 1, 1, 2, 2, 2, 2, 2},
                {1, 1, 1, 2, 2, 2, 2, 2},
                {1, 1, 1, 1, 2, 2, 2, 2},
                {1, 1, 1, 1, 2, 2, 2, 2},
                {1, 1, 1, 1, 2, 1, 2, 0}
        };
        game.setBoard(arr);
        try {
            game.playTurn(7, 7);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertTrue(game.gameOver());
        assertEquals(1, game.checkWinner());
    }

    @Test
    public void testEncapsulationBoard() {
        Othello game = new Othello();
        Disc[][] arr = game.getBoard();
        arr[0][0] = new Disc(Color.BLACK);

        assertNotSame(arr, game.getBoard());
    }

    @Test
    public void testEncapsulationPossibleMoves() {
        Othello game = new Othello();
        Collection<Position> arr = game.getBlackPossibleMoves();
        Collection<Position> arr1 = game.getWhitePossibleMoves();
        arr.add(new Position(0, 0));
        arr1.add(new Position(0, 0));

        assertNotSame(arr, game.getBlackPossibleMoves());
        assertNotSame(arr1, game.getWhitePossibleMoves());
    }
}
