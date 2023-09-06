package org.cis1200.othello;

import javax.swing.*;
import java.awt.*;

public class RunOthello implements Runnable {
    @Override
    public void run() {
        final JFrame frame = new JFrame("Othello");
        frame.setLocation(100, 100);

        final JPanel status_panel = new JPanel();
        final JLabel status = new JLabel("Player 1's Turn    W Discs: 2   B Discs: 2");
        // status.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        status_panel.add(status);

        frame.setResizable(false);
        frame.setBounds(0, 0, 720, 720);
        final Board board = new Board(status, frame);
        frame.add(board, BorderLayout.CENTER);

        final JPanel control_panel = new JPanel();
        // reset button
        final JButton reset = new JButton("New Game");
        reset.addActionListener(e -> board.reset(false));
        control_panel.add(reset);

        // Save game button
        final JButton saved = new JButton(("Save Game"));
        saved.addActionListener(e -> {
            if (board.gameOver()) {
                JOptionPane.showMessageDialog(frame, "Cannot save finished game");
            } else {
                board.saveGame();
            }
        });
        control_panel.add(saved);

        // Instructions Button
        final JButton instruct = new JButton(("How to Play"));
        instruct.addActionListener(
                e -> JOptionPane.showMessageDialog(
                        frame,
                        "Welcome to Othello!\n" +
                                "Each player gets assigned a color and" +
                                "receives some discs.\n" +
                                "Black moves first and places a disc to outflank" +
                                "an opponent's disc.\n" + "To outflank is to surround" +
                                " your opponent's discs with two of your own discs.\n" +
                                "This turns the opponent's surrounded discs to your" +
                                " color\n" + "This can be done horizontally, " +
                                "diagonally, or vertically.\n" + "The player with the" +
                                " most discs of their color on\n" +
                                "the board at endgame wins.",
                        "Instructions", JOptionPane.INFORMATION_MESSAGE
                )
        );
        control_panel.add(instruct);

        // saved game button
        final JButton getSaved = new JButton("Saved Game");
        getSaved.addActionListener(e -> board.reset(true));
        control_panel.add(getSaved);

        final JLabel space = new JLabel(" ");
        final JPanel top_panel = new JPanel();
        top_panel.add(control_panel);
        top_panel.add(space);
        top_panel.add(status_panel);
        frame.add(top_panel, BorderLayout.NORTH);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        String[] option = { "Yes", "No" };
        int select = JOptionPane.showOptionDialog(
                frame, "Do you want the Instructions?",
                "Welcome to Othello", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, option, option[0]
        );

        if (select == 0) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Welcome to Othello!\n" +
                            "Each player gets assigned a color and" +
                            "receives some discs.\n" +
                            "Black moves first and places a disc to outflank" +
                            "an opponent's disc.\n" + "To outflank is to surround" +
                            " your opponent's discs with two of your own discs.\n" +
                            "This turns the opponent's surrounded discs to your" +
                            " color\n" + "This can be done horizontally, " +
                            "diagonally, or vertically.\n" + "The player with the" +
                            " most discs of their color on\n" +
                            "the board at endgame wins.",
                    "Instructions", JOptionPane.INFORMATION_MESSAGE
            );
        }

        String[] options = { "Saved Game", "New Game" };
        int selection = JOptionPane.showOptionDialog(
                frame, "Choose Game State",
                "GameState", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]
        );

        if (selection == 0) {
            board.reset(true);
        } else if (selection == 1) {
            board.reset(false);
        }

    }

}
