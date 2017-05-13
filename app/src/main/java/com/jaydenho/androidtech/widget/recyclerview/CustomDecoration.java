package com.jaydenho.androidtech.widget.recyclerview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.jaydenho.androidtech.AndroidApplicationLike;
import com.jaydenho.androidtech.R;

/**
 * Created by hedazhao on 2017/5/9.
 */

public class CustomDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = "RV.CustomDecoration";
    private Paint mPaint = null;

    public CustomDecoration() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(AndroidApplicationLike.getApplicationInstance().getResources().getColor(R.color.colorAccent));
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        //onDraw方法先于drawChildren
        Log.d(TAG, "onDraw");
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
            int top = child.getBottom() - lp.bottomMargin;
            c.drawRect(left, top, right, top + 20, mPaint);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        Log.d(TAG, "onDrawOver");
        //onDrawOver在drawChildren之后
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        Log.d(TAG, "getItemOffsets");
        //outRect.set()方法的四个参数分别代表每个item四周往外扩展的区域，用于draw分割线，如果四个值都设置x，x>0，那么每个item的四周都会往外扩展x个pixel.
        outRect.set(0, 0, 0, 20);
    }
}
