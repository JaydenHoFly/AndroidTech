package com.jaydenho.androidtech.test;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jaydenho.androidtech.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hedazhao on 2016/10/24.
 */
public class TestListViewAty extends Activity {

    private static final String TAG = TestListViewAty.class.getSimpleName();
    private ListView mLV = null;
    private List<String> mData = null;
    private MyAdapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
        setContentView(R.layout.aty_test_list_view);
        mLV = (ListView) findViewById(R.id.lv_test);
        mData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mData.add("item: " + i);
        }
        mAdapter = new MyAdapter();
        mLV.setAdapter(mAdapter);
        mLV.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.d(TAG, "onScrollStateChanged scrollState: " + scrollState);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.d(TAG,"onScroll firstVisibleItem: " + firstVisibleItem + " visibleItemCount: " + visibleItemCount);
            }
        });

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        overridePendingTransition(R.anim.right_in_anim, R.anim.left_out_anim);
    }

    @Override
    public void finish() {
        setResult(1000);
        super.finish();
        overridePendingTransition(R.anim.left_in_anim, R.anim.right_out_anim);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.d(TAG,"getView position: " + position);
            if (convertView == null) {
                convertView = new MyItem(TestListViewAty.this);
            }
            ((MyItem) convertView).bindView(position);
            ((MyItem) convertView).update(mData.get(position));
            return convertView;
        }
    }

    class MyItem extends FrameLayout implements ItemInOutHelper.IInOutItem {

        private TextView mTV = null;
        private int mPosition;

        public MyItem(Context context) {
            super(context);
            init(context);
        }

        private void init(Context context) {
            View rootView = LayoutInflater.from(context).inflate(R.layout.list_item_test_list_view, this, true);
            mTV = (TextView) rootView.findViewById(R.id.tv_title);
        }

        public void bindView(int position) {
            mPosition = position;
        }

        @Override
        public int getPosition() {
            return mPosition;
        }

        public void update(String title) {
            mTV.setText(title);
        }

        @Override
        public void in() {
            Log.d(TAG, "in");
        }

        @Override
        public void out() {

        }
    }
}
