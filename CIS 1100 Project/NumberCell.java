
/* Name: Arriella Mafuta
 * PennKey: arriella
 * Execution: no main function, hence no execution
 * Description: This class is a subtype of the Cell interface.
 */

public class NumberCell implements Cell {
    private double xPos;
    private double yPos;
    private int numMines;
    private boolean hasMine = false;
    private boolean isRevealed;
    
    // constructor
    public NumberCell(double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.hasMine = false;
        this.numMines = 0;
        this.isRevealed = false;
    }

    /* Inputs: No inputs
     * Outputs: No outputs
     * Desciption: draw an unrevealed cell
    */
    @Override
    public void draw() {
        PennDraw.setPenRadius(0.001);
        PennDraw.setPenColor(128, 60, 98);
        PennDraw.filledSquare(xPos, yPos, WIDTH);
        PennDraw.setPenColor();
        PennDraw.square(xPos, yPos, WIDTH);
    }

    /* Inputs: No inputs
     * Outputs: No outputs
     * Desciption: draws a revealed cell
    */ 
    @Override  
    public void reveal() {
        PennDraw.setPenRadius(0.001);
        PennDraw.setPenColor(233, 219, 232);
        PennDraw.filledSquare(xPos, yPos, 0.5);
        PennDraw.setPenColor();
        PennDraw.square(xPos, yPos, WIDTH);

        PennDraw.setPenColor(); 
        int number = numMines;

        if (number > 0) {
            String numberToText = number + "";
            PennDraw.text(xPos, yPos, numberToText);
        }
        isRevealed = true;
    }

    /* Inputs: No inputs
     * Outputs: an integer value
     * Desciption: getter function that returns the instance numMines
    */
    @Override 
    public int getNumMines() {
        return numMines;
    }

    /* Inputs: and integer
     * Outputs: No outputs
     * Desciption: setter function that assigns a value to the instance numMines
    */ 
    @Override
    public void setNumMines(int a) {
        numMines = a;
    }
    
    /* Inputs: No inputs
     * Outputs: boolean value
     * Desciption: getter function that returns the instance hasMine
    */ 
    @Override     
    public boolean hasMine() {
        return hasMine;
    }

    /* Inputs: No inputs
     * Outputs: boolean value
     * Desciption: getter function that returns the instance isRevealed
    */
    @Override 
    public boolean isRevealed() {
        return isRevealed;
    }  
}