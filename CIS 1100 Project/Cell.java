/* Name: Arriella Mafuta
 * PennKey: arriella
 * Execution: no main function, hence no execution
 * Description: Is an interface Cell that has two subtypes NumberCell and MineCell.
 */
public interface Cell {
    public static final double WIDTH = 0.5; 
    public void draw();
    public void reveal();
    public boolean hasMine();
    public int getNumMines();
    public boolean isRevealed();
    public void setNumMines(int a);
}