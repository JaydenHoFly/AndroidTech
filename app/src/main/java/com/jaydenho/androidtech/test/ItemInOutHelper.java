package com.jaydenho.androidtech.test;

import android.widget.AbsListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hedazhao on 2016/10/24.
 */
public class ItemInOutHelper {

    private ListView mListView;
    private List<IInOutItem> mPreItems;
    private List<IInOutItem> mAfterItems;

    public ItemInOutHelper() {
        mPreItems = new ArrayList<>();
        mAfterItems = new ArrayList<>();
    }

    public void bindListView(ListView listView) {
        mListView = listView;
    }

    public void onScroll(int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    /**
     * 顺序遍历
     *
     * @param listView
     */
    private void traverseInOrder(AbsListView listView) {
        int childCount = listView.getChildCount();
        for (int i = 0; i < childCount; i++) {
//            if (checkItemCanAutoPlay(listView, i)) break;
        }
    }

    public interface IInOutItem {
        void in();

        void out();

        int getPosition();
    }
}
