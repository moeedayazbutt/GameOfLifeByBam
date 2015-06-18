package bamcorp.gameoflifebybam;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.TextView;

import bamcorp.gameoflifebybam.controller.ControllerState;
import bamcorp.gameoflifebybam.ui.MainActivity;

/**
 * @author Moeed
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity>
{
    public MainActivityTest()
    {
        super(MainActivity.class);
    }

    public void testActivityExists()
    {
        MainActivity activity = getActivity();
        assertNotNull(activity);
    }

    public void testStatusTextViewOnReset()
    {
        final MainActivity activity = getActivity();

        Button buttonReset = (Button) activity.findViewById(R.id.button_reset);
        TouchUtils.clickView(this, buttonReset);
        TextView textViewStatus = (TextView) activity.findViewById(R.id.textview_status);
        assertEquals(textViewStatus.getText(), activity.getResources().getString(R.string.status_game_new));
    }

    public void testLoopPausedOnPauseButtonPress()
    {
        final MainActivity activity = getActivity();

        Button buttonPlay = (Button) activity.findViewById(R.id.button_play);

        //initial state should be pause
        assertEquals(activity.getController().getControllerState(), ControllerState.PAUSE);

        //play
        TouchUtils.clickView(this, buttonPlay);
        //pause
        TouchUtils.clickView(this, buttonPlay);

        assertEquals(activity.getController().getControllerState(), ControllerState.PAUSE);
    }
}
