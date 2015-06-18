package bamcorp.gameoflifebybam.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import bamcorp.gameoflifebybam.R;
import bamcorp.gameoflifebybam.controller.Controller;
import bamcorp.gameoflifebybam.controller.Controller.OnLoopListener;
import bamcorp.gameoflifebybam.data.Data;
import bamcorp.gameoflifebybam.data.Data.DataUpdateListener;
import bamcorp.gameoflifebybam.data.DataState;
import bamcorp.gameoflifebybam.input.GameInput;
import bamcorp.gameoflifebybam.logic.GameLogic;

/**
 * @author Moeed
 */
public class MainActivity extends ActionBarActivity
{
    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;
    public static final long MAX_FPS = 10;
    public static final long DEFAULT_FPS = 1;

    Button buttonPlay;
    Button buttonReset;

    SeekBar seekBar;

    TextView textViewStatus;

    Grid grid;
    Data data;
    Controller controller;

    //cancel the controller AsyncTask if activity is closed
    //otherwise the AsyncTask keeps on running in the background on activity re-open
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        controller.cancel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonPlay = (Button) findViewById(R.id.button_play);
        buttonReset = (Button) findViewById(R.id.button_reset);
        textViewStatus = (TextView) findViewById(R.id.textview_status);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        data = new Data(WIDTH, HEIGHT);
        grid = new Grid(getApplicationContext(), WIDTH, HEIGHT, new GameInput(data));

        //get the grid layout to draw on screen
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.layout_main);
        mainLayout.addView(grid.getGridLayout());

        //set data update listener: each change in data state is reflected on the view grid immediately
        data.setDataUpdateListener(new DataUpdateListener()
        {

            @Override
            public void onUpdate(final int x, final int y, final DataState state)
            {
                runOnUiThread(new Runnable()
                {

                    @Override
                    public void run()
                    {
                        grid.setItemState(x, y, state);
                    }
                });
            }
        });

        //pre-fill the data grid with dead population
        data.fillData(DataState.DEAD);

        controller = new Controller(data, grid, new GameLogic(), DEFAULT_FPS);
        controller.setOnLoopListener(new OnLoopListener()
        {

            @Override
            public void onLoop(final long generationCount)
            {
                runOnUiThread(new Runnable()
                {

                    @Override
                    public void run()
                    {
                        textViewStatus.setText(getResources().getString(R.string.status_game_playing) + " [" + generationCount + "]");

                    }
                });

            }

            @Override
            public void onLoopFinished(final long generationCount, final Data data)
            {
                runOnUiThread(new Runnable()
                {

                    @Override
                    public void run()
                    {
                        buttonPlay.setText(getResources().getString(R.string.button_play_text));
                        textViewStatus.setText(getResources().getString(R.string.status_game_finished) + " [" + generationCount + "]");
                    }
                });

            }
        });

        buttonPlay.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if (buttonPlay.getText().toString().equals(getResources().getString(R.string.button_play_text)))
                {
                    controller.play();
                    buttonPlay.setText(getResources().getString(R.string.button_pause_text));
                    textViewStatus.setText(getResources().getString(R.string.status_game_playing));
                } else
                {
                    controller.pause();
                    buttonPlay.setText(getResources().getString(R.string.button_play_text));
                    textViewStatus.setText(getResources().getString(R.string.status_game_paused));
                }

            }
        });

        buttonReset.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                controller.reset();
                data.fillData(DataState.DEAD);
                buttonPlay.setText(getResources().getString(R.string.button_play_text));
                textViewStatus.setText(getResources().getString(R.string.status_game_new));
            }
        });

        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
        {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                long fps = progress * MAX_FPS / 100;

                if (fps == 0)
                    fps = 1;

                controller.setFps(fps);
            }
        });
    }
}
