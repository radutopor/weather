package radutopor.weather.ui.paginated;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import radutopor.weather.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class PaginatedActivityTest {
    @Rule
    public ActivityTestRule<PaginatedActivity> activityRule = new ActivityTestRule<>(PaginatedActivity.class);

    @Test
    public void dataIsOrdered() throws Exception {
        onView(withText("Today"))
                .check(matches(isDisplayed()));

        onView(withId(R.id.view_pager))
                .perform(swipeLeft());

        onView(withText("Tomorrow"))
                .check(matches(isDisplayed()));
    }
}
