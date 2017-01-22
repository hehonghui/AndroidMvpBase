package com.simple.mvp;

import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.simple.mvpbase.demo.MainActivity;
import com.simple.mvpbase.demo.R;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static java.lang.Thread.sleep;

/**
 * Created by mrsimple on 22/1/17.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void testClick() throws Exception {
        Matcher toolbarMatcher = withId(R.id.jump_button);
        onView(toolbarMatcher).check(matches(isDisplayed()));

        sleep(3 * 1000);
        // 下载按钮
        onView(withId(R.id.jump_button)).perform(ViewActions.click());
        sleep(1 * 1000);
    }
}
