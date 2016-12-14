package com.jaydenho.androidtech;

import android.content.Context;

import com.jaydenho.androidtech.ut.JUnitTestUtil;

import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {

    @Mock
    Context mContext;

    @Mock
    List mData = mock(List.class);

    @Captor
    private ArgumentCaptor<JUnitTestUtil.ICallback> mCallbackCaptor;

    @Mock
    private JUnitTestUtil.ICallback mCallback;

    JUnitTestUtil util = new JUnitTestUtil();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void isValidEmail_CorrectEmail_ReturnsTrue() {
        assertThat(JUnitTestUtil.isValidEmail("7752@qq.com"), IsEqual.equalTo(true));
    }

    @Test
    public void testAndroidString() {
        when(mContext.getString(R.string.app_name)).thenReturn("hello");
        assertThat(util.getString(mContext), IsEqual.equalTo("hello"));
    }

    @Test
    public void testCalcList() {
        util.calcList(mData);
        verify(mData).add("one");
    }

    @Test
    public void testCaptor() {
        util.makeRequest(mCallback);
        List<String> data = new ArrayList<>();
        data.add("john");
        data.add("jayden");
//        verify(util).makeRequest(mCallbackCaptor.capture());
//        mCallbackCaptor.getValue().onFail();
        verify(mCallback).onFail();
//        verify(mCallback).onSuccess(data);
    }
}