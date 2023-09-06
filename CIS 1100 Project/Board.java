/* Name: Arriella Mafuta
 * PennKey: arriella
 * Execution: no main function, hence no execution
 * Description: This class contains the different function that the board can 
                exhibit.
 */

public class Board {
    // instance variables
    private Cell[][] board;
    private boolean endGame;
   
    // Constructor
    public Board(Cell[][] board) {
        this.board = board;
        endGame = false;
    }

    /* Inputs: No inputs 
     * Outputs: void output
     * Desciption: Redraws the board making sure to keep a revealed cell revealed,
                     
    */ 
    public void draw() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].isRevealed()) {
                    board[i][j].reveal();
                }
                else {
                    board[i][j].draw();
                }
            
                if (board[i][j].isRevealed() && board[i][j].getNumMines() == 0) {
                    revealCells(board, i, j);
                } 
            }
        }
        
        // draw the reset button
        PennDraw.setFontPlain();
        PennDraw.setPenColor(128, 60, 98);
        PennDraw.filledSquare(0.5, 9.25, 0.25);
        PennDraw.text(0.75, 9.75, "New Game");
    }

    /* Inputs: No inputs
     * Outputs: void output
     * Desciption: inserts number cells in all positions of the 2D array
    */
    public void insertCells() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new NumberCell(0.5 + j, 8.5 - i);
            }
        }
    }

    /* Inputs: two integers representing the array position of the first click
     * Outputs: void output
     * Desciption: Inserts 10 mines in random position of the 2D array except the 
                    first click position
    */
    public void insertMines(int a, int b) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].hasMine()) {
                    return;
                } 
            }
        }

        for (int i = 0; i < 10; i++) {
            int x = (int) (Math.random() * 9);
            int y = (int) (Math.random() * 9);
        
            if (board[x][y].hasMine() || board[a][b].equals(board[x][y])) {
                i--;
            }
            else {
                board[x][y] = new MineCell(0.5 + y, 8.5 - x);
            }
        }
    }

    /* Inputs: No inputs 
     * Outputs: No outputs
     * Desciption: Sets the numMines instance variable to the output of countMines
    */
    public void insertNumbers() {
        // combine these for loops
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getNumMines() > 0) {
                   return; 
                } 
                if (!board[i][j].hasMine()) {
                    int number = countMines(board, i, j);
                    board[i][j].setNumMines(number);
                }
            }
        }
    }

    /* Inputs: A 2D array of type Cell, and two integers 
     * Outputs: an integer
     * Desciption: counts how many surrounding cells have mines and returns that int
    */
    public int countMines(Cell[][] arr, int i, int j) {
        int counter = 0;

        for (int a = i - 1; a < i + 2 && a < arr.length; a++) {
            for (int b = j - 1; b < j + 2 && b < arr[i].length; b++) {
                if (a >= 0 && b >= 0) {
                    if (arr[a][b].hasMine()) {
                        counter++;
                    }
                }
            }
        } 
        return counter;
    }
    
    /* Inputs: No inputs 
     * Outputs: a boolean value
     * Desciption: getter function that returns the instance variable endGame
    */
    public boolean getEndGame() {
        return endGame;
    }

    /* Inputs: a boolean value
     * Outputs: void output
     * Desciption: setter function that assigns a value to the instance endGame
    */
    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }

    /* Inputs: 2D array of type cell, and two integers
     * Outputs: void output
     * Desciption: reveals surrounding cells of cells with numMines as 0
    */
    public void revealCells(Cell[][] arr, int i, int j) {
        arr[i][j].reveal();
        for (int a = i - 1; a < i + 2 && a < arr.length; a++) {
            for (int b = j - 1; b < j + 2 && b < arr[i].length; b++) {
                if (a >= 0 && b >= 0) {
                    arr[a][b].reveal();
                }
            }
        }
    }

    /* Inputs: object of type Board, Cell 2D array, two integers
     * Outputs: void
     * Desciption: updates the game based on what type of cell the player clicks
    */
    public void update(Board game, Cell[][] cells, int xPos, int yPos) {
        if (xPos == 0 && yPos == 9) {
            cells = new Cell[9][9];
            game = new Board(cells);
            game.insertCells();
            
            game.endGame = false;
            PennDraw.clear();
        }
        
        if (xPos < 9 && yPos < 9) {
            int arrRow = (int) (8.5 - yPos);
            int arrCol = (int) xPos;
            
            game.insertMines(arrRow, arrCol);
            game.insertNumbers();

            if (cells[arrRow][arrCol].hasMine()) {
                game.playerLoses(); 
            }
            else if (cells[arrRow][arrCol].getNumMines() == 0) {
                game.revealCells(cells, arrRow, arrCol);
            }
            else {
                cells[arrRow][arrCol].reveal();
            }
        }
    }

    /* Inputs: no inputs 
     * Outputs: void output
     * Desciption: ends the game if a player by revealing all mine cells
    */
    public void playerLoses() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].hasMine()) {
                    board[i][j].reveal();
                } 
            }
        }
        PennDraw.text(4, 9.5, "You Lose :("); 
        endGame = true;       
    }

    /* Inputs: No inputs 
     * Outputs: void output
     * Desciption: checks if all numberCells have been revealed, if so, end the game
    */
    public void playerWins() {
        int counter = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (!(board[i][j].hasMine())) {
                    if (board[i][j].isRevealed()) {
                        counter++;
                    }
                } 
            }
        }
        if (counter == 71) {
            PennDraw.setPenColor();
            PennDraw.text(4, 9.5, "You Cleared the Mines"); 
            endGame = true;
        }
        else {
            return;
        } 
    }
}