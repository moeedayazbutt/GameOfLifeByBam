
package bamcorp.gameoflifebybam.logic;


import bamcorp.gameoflifebybam.data.Data;
import bamcorp.gameoflifebybam.data.DataState;


/**
 * Logic interface to be implemented on a cell-by-cell basis
 *
 * @author Moeed
 *
 */
public interface Logic
{
    /**
     *
     * Provides next state of a data element by executing Game of Life logic on an x,y data grid element. Current
     * implementation (http://www.bitstorm.org/gameoflife) is in GameLogic.java:
     *
     * <p>
     *
     * For a space that is 'populated':
     *
     * Each cell with one or no neighbors dies, as if by loneliness.
     *
     * Each cell with four or more neighbors dies, as if by overpopulation.
     *
     * Each cell with two or three neighbors survives.
     *
     * <p>
     * For a space that is 'empty' or 'unpopulated' Each cell with three neighbors becomes populated.
     *
     * <p>
     *
     *
     * @param x
     *        the x offset of the data element to execute logic upon
     * @param y
     *        the y offset of the data element to execute logic upon
     * @param data
     *        the data object to perform logic upon
     * @return next state of the element
     */
    public DataState getNextState(int x, int y, Data data);
}
