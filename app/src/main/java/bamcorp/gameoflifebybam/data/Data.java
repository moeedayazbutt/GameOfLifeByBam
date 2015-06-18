
package bamcorp.gameoflifebybam.data;


import java.util.Arrays;


/**
 * Basic data class representing a Game of Life state
 *
 * @author Moeed
 *
 */

public class Data
{

    public interface DataIterator
    {
        /**
         * Provides custom action behavior on the data grid's elements one-by-one
         *
         * @param x
         *        the element's x offset
         * @param y
         *        the element's y offset
         * @param data
         *        self data object
         */
        public void onIterate(int x, int y, Data data);
    }

    public interface DataUpdateListener
    {
        /**
         * An update listener that fires onUpdate() whenever a data element's state gets changed
         *
         * @param x
         *        the element's x offset
         * @param y
         *        the element's y offset
         * @param state
         *        the element's updated state
         */

        public void onUpdate(int x, int y, DataState state);
    }

    int[][] dataGrid;
    DataUpdateListener dataUpdateListener;

    public DataUpdateListener getDataUpdateListener()
    {
        return dataUpdateListener;
    }

    public void setDataUpdateListener(DataUpdateListener dataUpdateListener)
    {
        this.dataUpdateListener = dataUpdateListener;
    }

    /**
     * Uses Arrays.deepEquals() method to check if data grids are same or not
     *
     * @param otherData
     *        the data grid object to compare with
     * @return true if data grids are equal, false otherwise
     */
    public boolean isEqual(Data otherData)
    {
        return Arrays.deepEquals(dataGrid, otherData.dataGrid);
    }

    /**
     * Creates a deep copy of the data grid (used to duplicate data for logic processing while looping)
     * http://stackoverflow.com/a/1564856/1931531
     *
     * @param original
     *        data grid 2-D array to be copied
     * @return deep copy of original 2-D array
     */
    public static int[][] deepCopy(int[][] original)
    {
        if (original == null)
        {
            return null;
        }

        final int[][] result = new int[original.length][];
        for (int i = 0; i < original.length; i++)
        {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }

    /**
     * Clones a Data object (used to duplicate data for logic processing while looping)
     *
     * @return clone of the current data object
     */
    public Data getClone()
    {
        Data dataClone = new Data(getWidth(), getHeight());
        dataClone.dataGrid = deepCopy(dataGrid);

        return dataClone;
    }

    public Data(int width, int height)
    {
        dataGrid = new int[height][width];
    }

    public void setItemState(int x, int y, DataState state) throws ArrayIndexOutOfBoundsException
    {
        dataGrid[x][y] = state.getValue();

        if (this.dataUpdateListener != null)
            this.dataUpdateListener.onUpdate(x, y, state);
    }

    public DataState getItemState(int x, int y) throws ArrayIndexOutOfBoundsException
    {
        return DataState.getEnum(dataGrid[x][y]);
    }

    public int getWidth()
    {
        return dataGrid.length;
    }

    public int getHeight()
    {
        return dataGrid[0].length;
    }

    public int getSize()
    {
        return getWidth() * getHeight();
    }

    /**
     * Iterates over entire data grid one-by-one and performs onIterate() on each step
     *
     * @param iterator
     *        the iterator object that specifies onIterate() behavior
     */
    public void iterateData(DataIterator iterator)
    {
        int width = getWidth();
        int height = getHeight();

        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                iterator.onIterate(x, y, this);
            }
        }
    }

    /**
     * Fills the specified data state across the entire grid
     *
     * @param state
     *        the state to fill across the entire grid
     */
    public void fillData(final DataState state)
    {
        iterateData(new DataIterator() {

            @Override
            public void onIterate(int x, int y, Data data)
            {
                dataGrid[x][y] = state.getValue();

                if (dataUpdateListener != null)
                    dataUpdateListener.onUpdate(x, y, state);
            }

        });
    }

    /**
     * String printable representation of data grid
     *
     *
     */
    public String toString()
    {
        return Arrays.deepToString(dataGrid).replaceAll("\\[", "\n\\[");
    }
}
