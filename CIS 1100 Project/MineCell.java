/* Name: Arriella Mafuta
 * PennKey: arriella
 * Execution: no main function, hence no execution
 * Description: This class is a subtype of the Cell interface
 */
public class MineCell implements Cell {
    private double xPos;
    private double yPos;
    private boolean hasMine = true;
    private double mineRadius = 0.34;
    private boolean isRevealed;

    // Constructor
    public MineCell(double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.hasMine = true;
        isRevealed = false;
    }

    /* Inputs: No inputs
     * Outputs: No outputs
     * Desciption: draw an unrevealed cell
    */
    @Override
    //colour
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
        PennDraw.filledSquare(xPos, yPos, WIDTH);
        PennDraw.setPenColor();
        PennDraw.square(xPos, yPos, WIDTH);

        PennDraw.filledCircle(xPos, yPos, mineRadius);
        isRevealed = true;
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
     * Outputs: an integer value
     * Desciption: getter function that has a dummy return of -1
    */
    @Override
    public int getNumMines() {
        return -1;
    }
    
    /* Inputs: No inputs
     * Outputs: boolean value
     * Desciption: getter function that returns the instance isRevealed
    */
    @Override
    public boolean isRevealed() {
        return isRevealed;
    }

    /* Inputs: and integer
     * Outputs: No outputs
     * Desciption: setter function with a dummy return value
    */
    @Override 
    public void setNumMines(int a) {
        return;
    }
}