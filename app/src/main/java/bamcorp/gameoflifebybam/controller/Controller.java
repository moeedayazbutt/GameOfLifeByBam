
package bamcorp.gameoflifebybam.controller;


import android.os.AsyncTask;
import bamcorp.gameoflifebybam.data.Data;
import bamcorp.gameoflifebybam.data.DataState;
import bamcorp.gameoflifebybam.logic.Logic;
import bamcorp.gameoflifebybam.ui.Grid;


/**
 * Game controller class. Controls game looping, pause, reset, speed, logic and state. Game speed, logic and state can
 * also be changed externally/dynamically while a game is in loop (by using setFps(), setLogic() and setData())
 *
 *
 * @author Moeed
 *
 */
public class Controller
{
    long millis;
    ControllerState state;
    Data data;
    Logic logic;
    GameLoop loop;
    OnLoopListener onLoopListener;

    /**
     * Listener interface to listen to looping events executed by the controller
     *
     * @author Moeed
     *
     */
    public interface OnLoopListener
    {
        /**
         * Activated on each loop iteration
         *
         * @param generationCount
         *        the loop's generation number
         */
        public void onLoop(long generationCount);

        /**
         * Activated on loop end/game finish
         *
         * @param generationCount
         *        the loop's generation number
         * @param data
         *        final data object
         */
        public void onLoopFinished(long generationCount, Data data);
    }

    public void setOnLoopListener(OnLoopListener onLoopListener)
    {
        this.onLoopListener = onLoopListener;
    }

    /**
     * Implements a single background task that executes GameLogic over the entire data set until game is over. Also
     * maintains a generation count for the game.
     *
     * @author Moeed
     *
     */
    class GameLoop extends AsyncTask<Void, Integer, Void>
    {
        private Data copyData;
        private long generationCount = 0;
        private boolean isLooping = false;

        public GameLoop()
        {
            isLooping = false;
        }

        public boolean isLooping()
        {
            return isLooping;
        }

        @Override
        protected void onPreExecute()
        {
            isLooping = true;
            generationCount = 0;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            isLooping = false;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            while (true)
            {
                if (state == ControllerState.CANCEL)
                {
                    return null;
                }
                else if (state == ControllerState.PLAY)
                {

                    generationCount++;
                    onLoopListener.onLoop(generationCount);

                    //get a clone of the existing game state to perform logic upon
                    copyData = data.getClone();

                    copyData.iterateData(new Data.DataIterator() {

                        //for each logic executed on existing game data state, perform an update on the next game data state only if the states are different
                        @Override
                        public void onIterate(int y, int x, Data otherData)
                        {
                            DataState nextState = logic.getNextState(x, y, copyData);

                            //only update a data item state if there's a change in the state to save unnecessary UI updates
                            if (data.getItemState(x, y) != nextState)
                                data.setItemState(x, y, nextState);
                        }

                    });

                    //finish the game if the last state is equal to the next state
                    if (data.isEqual(copyData))
                    {
                        if (onLoopListener != null)
                            onLoopListener.onLoopFinished(generationCount, data);

                        reset();

                        break;
                    }
                }

                try
                {
                    Thread.sleep(millis);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

            return null;
        }
    }

    public Controller(Data data, Grid grid, Logic logic, long fps)
    {
        this.data = data;
        this.logic = logic;
        this.millis = 1000 / fps;
        this.loop = new GameLoop();
        this.state = ControllerState.PAUSE;
    }

    public long getFps()
    {
        return this.millis * 1000;
    }

    public void setFps(long fps)
    {
        this.millis = 1000 / fps;
    }

    public Data getData()
    {
        return data;
    }

    public void setData(Data data)
    {
        this.data = data;
    }

    public Logic getLogic()
    {
        return logic;
    }

    public void setLogic(Logic logic)
    {
        this.logic = logic;
    }

    public void setState(ControllerState state)
    {
        this.state = state;
    }

    public void reset()
    {
        pause();
        loop = new GameLoop();
    }

    public void cancel()
    {
        this.state = ControllerState.CANCEL;
    }

    public void pause()
    {
        this.state = ControllerState.PAUSE;
    }

    public void play()
    {
        this.state = ControllerState.PLAY;

        if (loop != null)
        {
            if (!loop.isLooping())
            {
                loop.execute();
            }
        }
    }
}
