
package bamcorp.gameoflifebybam.ui;


import android.content.Context;
import android.widget.LinearLayout;
import bamcorp.gameoflifebybam.data.DataState;
import bamcorp.gameoflifebybam.input.Input;


/**
 * Maintains individual cells in a single grid view
 *
 * @author Moeed
 *
 */
public class Grid
{
    Cell[][] cells;
    LinearLayout gridLayout;

    public LinearLayout getGridLayout()
    {
        return gridLayout;
    }

    public void setGridLayout(LinearLayout gridLayout)
    {
        this.gridLayout = gridLayout;
    }

    public void setItemState(int x, int y, DataState state) throws ArrayIndexOutOfBoundsException
    {
        cells[x][y].setState(state);
    }

    public DataState getItemState(int x, int y) throws ArrayIndexOutOfBoundsException
    {
        return cells[x][y].getState();
    }

    public interface GridIterator
    {
        public void onIterate(int x, int y, Grid grid, int counter);
    }

    public int getWidth()
    {
        return cells.length;
    }

    public int getHeight()
    {
        return cells[0].length;
    }

    public int getSize()
    {
        return getWidth() * getHeight();
    }

    /**
     * Construct a Grid of the given width, height and assign Input listener to each individual cell. Individual cells
     * are automatically laid out in a grid view.
     *
     * @param context
     *        Application context to create View
     * @param width
     *        width in units
     * @param height
     *        height in units
     * @param input
     *        GameInput object
     */
    public Grid(final Context context, int width, int height, final Input input)
    {
        this.cells = new Cell[height][width];
        this.gridLayout = new LinearLayout(context);
        this.gridLayout.setOrientation(LinearLayout.VERTICAL);

        iterateGrid(new GridIterator() {

            LinearLayout horizontalLayout;
            int lastY = -1;

            @Override
            public void onIterate(int x, int y, Grid grid, int counter)
            {
                grid.cells[x][y] = new Cell(context, x, y, counter);
                grid.cells[x][y].setInput(input);

                if (lastY != y)
                {
                    lastY = y;
                    horizontalLayout = new LinearLayout(context);
                    horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
                    gridLayout.addView(horizontalLayout);
                }

                horizontalLayout.addView(grid.cells[x][y].getView());
            }
        });

    }

    /**
     * Iterates over entire view grid one-by-one and performs iterator.onIterate() on each step
     *
     * @param iterator
     *        the iterator object that specifies iterator.onIterate() behavior
     */
    public void iterateGrid(GridIterator iterator)
    {
        int counter = 0;
        int width = getWidth();
        int height = getHeight();

        for (int y = 0; y < width; y++)
        {
            for (int x = 0; x < height; x++)
            {
                iterator.onIterate(x, y, this, ++counter);
            }
        }
    }

}
