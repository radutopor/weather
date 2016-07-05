package radutopor.weather.ui.grid;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import radutopor.weather.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class GridActivityTest {
    @Rule
    public ActivityTestRule<GridActivity> activityRule = new ActivityTestRule<>(GridActivity.class);

    @Test
    public void shouldShowDetailedView() throws Exception {
        onView(withText("Tomorrow"))
                .perform(click());

        onView(allOf(withId(R.id.temperature_day), isDisplayed()))
                .check(matches(isDisplayed()));
        onView(withText("Tomorrow"))
                .check(matches(isDisplayed()));
    }
}
