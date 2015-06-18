package bamcorp.gameoflifebybam;

import android.test.ActivityUnitTestCase;

import bamcorp.gameoflifebybam.data.Data;
import bamcorp.gameoflifebybam.data.DataState;
import bamcorp.gameoflifebybam.logic.GameLogic;
import bamcorp.gameoflifebybam.ui.MainActivity;

/**
 * Created by Moeed on 018, 18, Jun, 2015.
 */
public class GameLogicTest extends ActivityUnitTestCase<MainActivity>
{
    public GameLogicTest()
    {
        super(MainActivity.class);
    }

    public void testAliveNeighborCount()
    {
        int width = 3;
        int height = 3;

        int x = 1;
        int y = 1;

        Data data = new Data(width, height);
        GameLogic logic = new GameLogic();

        data.fillData(DataState.ALIVE);
        assertEquals(logic.getAliveNeighborsCount(x, y, data), 8);

        data.fillData(DataState.DEAD);
        assertEquals(logic.getAliveNeighborsCount(x, y, data), 0);
    }

    public void testDataWidthHeight()
    {
        int width = 3;
        int height = 3;

        Data data = new Data(width, height);
        assertEquals(width, data.getWidth());
        assertEquals(height, data.getHeight());
    }

    public void testFillData()
    {
        int width = 3;
        int height = 3;
        DataState state = DataState.DEAD;

        Data data = new Data(width, height);
        data.fillData(state);

        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                assertEquals(data.getItemState(x, y), state);
            }
        }
    }
}
