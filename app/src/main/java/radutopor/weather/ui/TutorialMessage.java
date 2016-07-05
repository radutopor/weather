package radutopor.weather.ui;

import android.content.Context;
import android.widget.Toast;

import radutopor.weather.R;

public enum TutorialMessage {
    SWIPE_LEFT(R.string.swipe_left),
    TAP(R.string.tap),
    SWIPE_DOWN(R.string.swipe_down);

    private static final String SHARED_PREFS = "shared_prefs";

    public int messageResId;

    TutorialMessage(int messageResId) {
        this.messageResId = messageResId;
    }

    public void show(Context context) {
        if (!context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE).getBoolean(toString(), false)) {
            Toast.makeText(context, messageResId, Toast.LENGTH_LONG).show();
            context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE).edit().putBoolean(toString(), true).commit();
        }
    }
}
