/* Name: Arriella Mafuta
 * PennKey: arriella
 * Execution: java Minesweeper
 * Description: This class combines the functions of the other classes to make the
                game.
 */

public class Minesweeper {
    public static void main(String[] args) {
        // set X scale to be from 0 to 9 and Y scale to be from 0 to 10.
        PennDraw.setXscale(0, 9);
        PennDraw.setYscale(0, 10);

        Cell[][] cells = new Cell[9][9]; 
        Board game = new Board(cells);
        game.insertCells();
        
        PennDraw.enableAnimation(30);

        while (true) {
            if (PennDraw.mousePressed() && game.getEndGame()) {
                int xPos = (int) PennDraw.mouseX();
                int yPos = (int) PennDraw.mouseY();

                if (xPos == 0 && yPos == 9) {
                    cells = new Cell[9][9];
                    game = new Board(cells);
                    game.insertCells();
                    
                    game.setEndGame(false);
                    PennDraw.clear();
                }
            }

            game.draw();
            game.playerWins();
            if (PennDraw.mousePressed() && !game.getEndGame()) {
                int xPos = (int) PennDraw.mouseX();
                int yPos = (int) PennDraw.mouseY();
                
                if (xPos == 0 && yPos == 9) {
                    cells = new Cell[9][9];
                    game = new Board(cells);
                    game.insertCells();
                    game.setEndGame(false);
                    PennDraw.clear();
                }

                else {
                    game.update(game, cells, xPos, yPos);
                }
            }
            PennDraw.advance();
        }
    }
}