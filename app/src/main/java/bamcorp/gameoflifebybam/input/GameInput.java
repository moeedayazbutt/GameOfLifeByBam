
package bamcorp.gameoflifebybam.input;


import bamcorp.gameoflifebybam.data.Data;
import bamcorp.gameoflifebybam.data.DataState;


/**
 *
 * Game of Life input implementation
 *
 * @author Moeed
 *
 */
public class GameInput implements Input
{
    Data data;
    int x;
    int y;
    int index;

    public GameInput(Data data)
    {
        this.data = data;
    }

    @Override
    public void performInput(int x, int y, int index)
    {
        if (data != null)
        {
            DataState state = data.getItemState(x, y);

            if (state == DataState.ALIVE)
            {
                data.setItemState(x, y, DataState.DEAD);
            }
            else
            {
                data.setItemState(x, y, DataState.ALIVE);
            }
        }
    }

}
