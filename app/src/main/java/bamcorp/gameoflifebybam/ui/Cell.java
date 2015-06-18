
package bamcorp.gameoflifebybam.ui;


import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TableRow;
import android.widget.TextView;
import bamcorp.gameoflifebybam.data.DataState;
import bamcorp.gameoflifebybam.input.Input;


/**
 * Grid element holder. Holds element's view and input method. View can be changed dynamically by calling setView()
 *
 * @author Moeed
 *
 */
public class Cell
{
    private final static int COLOR_ALIVE = 0xFFF26649;
    private final static int COLOR_DEAD = Color.WHITE;
    private final static int COLOR_TEXT = Color.TRANSPARENT;
    private final static int TEXT_SIZE = 10;
    private final static int VIEW_DIMENS = 40;

    int x, y;
    int index;
    View view;
    DataState state;
    Input input;

    public interface CellUpdateListener
    {
        /**
         * An update listener that fires onUpdate() whenever a cell's state gets changed
         *
         * @param x
         *        the cell's x offset
         * @param y
         *        the cell's y offset
         * @param state
         *        the cell's updated state
         */
        public void onUpdate(int index, String text);
    }

    public Cell(Context context, int x, int y, int index)
    {
        this.index = index;
        this.x = x;
        this.y = y;
        initTextView(context);
    }

    public void setInput(Input input)
    {
        this.input = input;
    }

    /**
     * Create a TextView cell and set Input performInput() on onTouch()
     *
     * @param x
     *        the cell's x offset
     * @param y
     *        the cell's y offset
     * @param state
     *        the cell's updated state
     */
    void initTextView(Context context)
    {
        TextView textView = new TextView(context);

        textView.setTextSize(TEXT_SIZE);
        textView.setTextColor(COLOR_TEXT);
        textView.setGravity(Gravity.CENTER);

        textView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    if (input != null)
                        input.performInput(x, y, index);
                }
                return true;
            }
        });

        textView.setLayoutParams(new TableRow.LayoutParams(VIEW_DIMENS, VIEW_DIMENS));

        view = textView;
    }

    public View getView()
    {
        return view;
    }

    public void setState(DataState dataState)
    {
        state = dataState;

        TextView textView = (TextView) view;
        textView.setText(state.getValue() + "");

        if (state == DataState.ALIVE)
        {

            textView.setBackgroundColor(COLOR_ALIVE);
        }
        else
        {
            textView.setBackgroundColor(COLOR_DEAD);
        }
    }

    public DataState getState()
    {
        return state;
    }

}
