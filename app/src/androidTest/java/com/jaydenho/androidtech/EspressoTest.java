package com.jaydenho.androidtech;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.jaydenho.androidtech.widget.anim.ValueAnimatorAty;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created by hedazhao on 2016/10/14.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class EspressoTest {

    @Rule
    public ActivityTestRule<ValueAnimatorAty> mValueAnimatorRule = new ActivityTestRule<>(ValueAnimatorAty.class);
    @Rule
    public ActivityTestRule<MainActivity> mMainActivityRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void testDropBallBtn() {
        onView(withId(R.id.btn_drop_ball))
                .perform(click());

        onView(withId(R.id.tv_anim))
                .check(matches(withText("A")));
    }

    @Test
    public void testDashboard(){
        DashboardInfo info = new DashboardInfo(MainActivity.DashboardIds.DATA_BINDING,"");
        onData(allOf(is(instanceOf(List.class)),hasItem(equalTo(info))));
    }

    @Test
    public void testDashboardIntent(){

    }
}
