package com.jaydenho.androidtech.androidarchitecture.paging;

import android.arch.paging.ContiguousPagedList;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * Created by hedazhao on 2018/10/9.
 */
public class PagingRepository {
    public PagingRepository() {


    }

    public PagedList<Concert> create() {
        PageKeyedDataSource<String, Concert> pageKeyedDataSource = new PageKeyedDataSource<String, Concert>() {
            @Override
            public void loadInitial(@NonNull final LoadInitialParams<String> params, @NonNull final LoadInitialCallback<String, Concert> callback) {
                callback.onResult(PagingNetwork.request(params.requestedLoadSize), null, "2");
            }

            /**
             * 加载上一页的数据。
             * 当列表加载的最大index小于{@link PagedList.Config#prefetchDistance}时调用。
             * @see ContiguousPagedList#schedulePrepend()
             * @param params
             * @param callback
             */
            @Override
            public void loadBefore(@NonNull LoadParams params, @NonNull LoadCallback callback) {

            }

            /**
             * 加载下一页的数据。
             * 当列表加载的最大index加上{@link PagedList.Config#prefetchDistance}大于列表size时调用。
             * @param params
             * @param callback
             */
            @Override
            public void loadAfter(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Concert> callback) {
                callback.onResult(PagingNetwork.request(params.requestedLoadSize), "3");
            }
        };
        final PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(30)
                .setPageSize(30)
                .setPrefetchDistance(10)// 用户滑到列表的倒数第N个时，开始加载下一页的数据。
                .build();
        PagedList<Concert> pagedList = new PagedList.Builder<>(pageKeyedDataSource, config)
                .setBoundaryCallback(new PagedList.BoundaryCallback() {
                    @Override
                    public void onZeroItemsLoaded() {
                        super.onZeroItemsLoaded();
                    }

                    @Override
                    public void onItemAtFrontLoaded(@NonNull Object itemAtFront) {
                        super.onItemAtFrontLoaded(itemAtFront);
                    }

                    @Override
                    public void onItemAtEndLoaded(@NonNull Object itemAtEnd) {
                        super.onItemAtEndLoaded(itemAtEnd);
                    }
                })
                .setFetchExecutor(new Executor() {
                    @Override
                    public void execute(@NonNull Runnable command) {
                        new Thread(command).start();
                    }
                })
                .setInitialKey("1")
                .setNotifyExecutor(new Executor() {
                    @Override
                    public void execute(@NonNull Runnable command) {
                        new Handler(Looper.getMainLooper()).post(command);
                    }
                })
                .build();
        return pagedList;
    }
}
