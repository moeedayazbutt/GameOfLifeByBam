
package bamcorp.gameoflifebybam.input;


/**
 * Input interface to be implemented on a cell-by-cell basis
 *
 * @author Moeed
 *
 */
public interface Input
{
    /**
     * Performs changes to data object according to input captured on x,y gridview cell
     *
     * @param x
     *        the x offset of the grid cell
     * @param y
     *        the y offset of the grid cell
     * @param index
     *        the index of the grid cell
     */
    public void performInput(int x, int y, int index);
}
