
package bamcorp.gameoflifebybam.data;


/**
 * DataState enums DEAD(0) ALIVE(1)
 *
 * @author Moeed
 *
 */
public enum DataState
{
    DEAD(0),
    ALIVE(1);

    private int value;

    DataState(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

    public static DataState getEnum(int value)
    {
        DataState[] states = DataState.values();
        for (DataState state : states)
        {
            if (state.getValue() == value)
                return state;
        }

        return null;
    }
}
