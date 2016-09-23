package com.jaydenho.androidtech.databinding;


/**
 * Created by hedazhao on 2016/8/4.
 */
public class  CustomSetterComponent implements android.databinding.DataBindingComponent {
    @Override
    public CustomSetter getCustomSetter() {
        return new CustomSetter();
    }
}
