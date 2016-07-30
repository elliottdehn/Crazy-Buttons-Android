package android.apps.elliott.thebuttons;

import com.google.gson.annotations.SerializedName;

/**
 * Created by elliottdehn on 7/29/2016.
 */
public class ButtonResponse {
    @SerializedName("currentCount")
    private int count;
    @SerializedName("actionPerformed")
    private String action;
    @SerializedName("success")
    private boolean success;

    private static transient final String ACTION_UP = "plus";
    private static transient final String ACTION_NEUTRAL = "neutral";
    private static transient final String ACTION_DOWN = "minus";

    public enum Action {
        PLUS, MINUS, NEUTRAL
    }

    public int count() {
        return count;
    }

    public boolean success() {
        return success;
    }

    public Action action() {
        if (action != null) {
            if (action.equals(ACTION_UP)) {
                return Action.PLUS;
            } else if (action.equals(ACTION_DOWN)) {
                return Action.MINUS;
            } else if (action.equals(ACTION_NEUTRAL)) {
                return Action.NEUTRAL;
            }
        }
        return null;
    }
}
