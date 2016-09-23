package com.jaydenho.androidtech.databinding;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jaydenho.androidtech.R;

/**
 * Created by hedazhao on 2016/8/4.
 */
public class CustomView extends TextView {
    private static final String TAG = "CustomView";

    private String mText = null;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomView, defStyleAttr, 0);
        int count = array.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.CustomView_jtext:
                    mText = array.getString(attr);
                    break;
            }
        }
    }

    /**
     * dataBinding会根据attribute的命名自动寻找setter,如果命名和setter方法匹配不上,可以通过在@BindMethods指定对应的方法,但是该方法必须是自定义View这个类中的,而且需要通过
     *         mBinding = DataBindingUtil.setContentView(this, R.layout.aty_data_binding, new CustomSetterComponent());等方法将Setter通过Component设置到layout文件中
     * @param text
     */
    public void setJtext(String text){
        Log.d(TAG,"setJText: " + text);
        setText(text);
    }

}
