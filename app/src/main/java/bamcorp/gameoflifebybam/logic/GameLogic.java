
package bamcorp.gameoflifebybam.logic;


import bamcorp.gameoflifebybam.data.Data;
import bamcorp.gameoflifebybam.data.DataState;


/**
 * Implementation of http://www.bitstorm.org/gameoflife
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
 * @author Moeed
 *
 */
public class GameLogic implements Logic
{
    @Override
    public DataState getNextState(int x, int y, Data data)
    {
        DataState returnState = DataState.DEAD;
        DataState currentState = data.getItemState(x, y);
        int aliveNeighborsCount = getAliveNeighborsCount(x, y, data);

        if (currentState == DataState.ALIVE)
        {
            if (aliveNeighborsCount <= 1 || aliveNeighborsCount >= 4)
            {
                returnState = DataState.DEAD;
            }
            else
            {
                returnState = DataState.ALIVE;
            }
        }
        else if (currentState == DataState.DEAD)
        {
            if (aliveNeighborsCount == 3)
            {
                returnState = DataState.ALIVE;
            }
        }

        return returnState;
    }

    /**
     * @param x
     *        the x coordinate of current element
     * @param y
     *        the y coordinate of current element
     * @param data
     *        the data object
     * @return number of elements that surround current element and have state ALIVE. Max = 8, min = 0.
     *
     */
    public int getAliveNeighborsCount(int x, int y, Data data)
    {
        int count = 0;

        for (int a = x - 1; a <= x + 1; a++)
        {
            for (int b = y - 1; b <= y + 1; b++)
            {
                int neighborX = a;//(((k % data.getHeight()) + data.getHeight()) % data.getHeight());
                int neighborY = b;//(((t % data.getWidth()) + data.getWidth()) % data.getWidth());

                try
                {
                    if ((data.getItemState(neighborX, neighborY) == DataState.ALIVE) && !(neighborX == x && neighborY == y))
                    {
                        count++;
                    }
                }
                catch (ArrayIndexOutOfBoundsException e)
                {
                    //it's a non-existent (treat as dead) cell so don't increment count
                    //e.printStackTrace();
                }
            }
        }

        return count;

    }

}
