package com.jaydenho.androidtech;

import android.os.Parcel;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import com.jaydenho.androidtech.ut.LogHistory;


import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by hedazhao on 2016/10/14.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class LogHistoryAndroidUnitTest {

    private LogHistory mLogHistory;
    private static final String LOG_STRING = "log_string";
    private static final long LOG_LONG = 131212L;

    @Before
    public void createLogHistory() {
        mLogHistory = new LogHistory();
    }

    @Test
    public void testLogHistory() {
        mLogHistory.addEntry(LOG_STRING, LOG_LONG);
        Parcel p = Parcel.obtain();
        mLogHistory.writeToParcel(p, mLogHistory.describeContents());

        p.setDataPosition(0);

        LogHistory logHistory = LogHistory.CREATOR.createFromParcel(p);

        assertThat(logHistory.getmLong(), IsEqual.equalTo(LOG_LONG));
        assertThat(logHistory.getmString(), IsEqual.equalTo(LOG_STRING));
        p.recycle();
    }
}
