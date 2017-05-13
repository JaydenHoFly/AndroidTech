package com.jaydenho.androidtech.widget.recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.jaydenho.androidtech.BaseActivity;
import com.jaydenho.androidtech.R;

/**
 * Created by hedazhao on 2017/5/9.
 */

public class RecyclerViewActivity extends BaseActivity {

    private RecyclerView mRecyclerView = null;
    private CustomAdapter mCustomAdapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        CustomLayoutManager customLayoutManager = new CustomLayoutManager(this, 4);
        customLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                // 这里的返回值,表示下标为position的item 占据多少列，每一行的总列数由setSpanCount设置
                if(position > 15) {
                    return 4;
                } else {
                    return 2;
                }
            }
        });
        mRecyclerView.setLayoutManager(customLayoutManager);

        mCustomAdapter = new CustomAdapter(this);

        mRecyclerView.setAdapter(mCustomAdapter);
        mRecyclerView.addItemDecoration(new CustomDecoration());

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new CustomItemTouchHelperCallback(mCustomAdapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

    }
}
