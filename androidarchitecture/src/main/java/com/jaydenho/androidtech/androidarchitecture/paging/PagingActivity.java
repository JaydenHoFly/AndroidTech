package com.jaydenho.androidtech.androidarchitecture.paging;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jaydenho.androidtech.androidarchitecture.R;

/**
 * Created by hedazhao on 2018/8/1.
 */
public class PagingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_paging);
        PagingAdapter pagingAdapter = new PagingAdapter();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(pagingAdapter);
        pagingAdapter.submitList(new PagingRepository().create());
    }
}
