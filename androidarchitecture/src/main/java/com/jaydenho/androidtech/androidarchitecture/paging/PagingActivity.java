package com.jaydenho.androidtech.androidarchitecture.paging;


import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jaydenho.androidtech.androidarchitecture.R;

import java.util.List;

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
        final PagingRepository pagingRepository = new PagingRepository();
        final PagedList<Concert> pagedList = pagingRepository.create();
        pagingAdapter.submitList(pagedList);
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 开始刷新，让老的dataSource失效，使其不再能加载新数据。
                pagingRepository.invalid();
                List<Concert> concerts = pagedList.snapshot();
            }
        });
    }
}
