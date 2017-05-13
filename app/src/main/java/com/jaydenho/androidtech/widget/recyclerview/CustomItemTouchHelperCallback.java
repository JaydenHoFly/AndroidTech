package com.jaydenho.androidtech.widget.recyclerview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.jaydenho.androidtech.R;

/**
 * Created by hedazhao on 2017/5/10.
 */

public class CustomItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private static final String TAG = "RV.ItemTouchHelper";

    private ItemTouchAdapter mItemTouchAdapter = null;

    public CustomItemTouchHelperCallback(@NonNull ItemTouchAdapter itemTouchAdapter) {
        this.mItemTouchAdapter = itemTouchAdapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlag = ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT | ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlag = ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlag, swipeFlag);
    }

    /**
     * 当{@code viewHolder} 在 {@code target}的上方时，回调该方法，让开发者改变Adapter中的数据源，达到刷新item的目的
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        Log.d(TAG, "onMove");
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        int from = viewHolder.getAdapterPosition();
        int to = target.getAdapterPosition();
        if (from < to) {
            for (int i = from; i < to; i++) {
                mItemTouchAdapter.onItemMove(i, i + 1);//从起始位置起，依次交互相邻的数据，类似冒泡排序操作
            }
        } else {
            for (int i = from; i > to; i--) {
                mItemTouchAdapter.onItemMove(i, i - 1);
            }
        }
        mItemTouchAdapter.notifyItemMoved(from, to);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Log.d(TAG, "onSwiped");
        mItemTouchAdapter.onItemRemove(viewHolder.getAdapterPosition());
        mItemTouchAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
    }

    /**
     * item被选中(长按)
     * 这里改变了 item的背景色, 也可以通过接口暴露, 让adapter去处理逻辑
     */
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        Log.d(TAG, "onChildDraw viewHolder.p: " + viewHolder.getAdapterPosition() + " actionState: " + actionState);
        if (isCurrentlyActive) {
            if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                viewHolder.itemView.setBackgroundColor(Color.BLUE);
            } else if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                // 滑动删除状态
                viewHolder.itemView.setBackgroundColor(Color.RED);
            }
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    /**
     * item取消选中(取消长按)
     * 这里改变了 item的背景色, 也可以通过接口暴露, 让adapter去处理逻辑
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        Log.d(TAG, "clearView viewHolder.p: " + viewHolder.getAdapterPosition());
        viewHolder.itemView.setBackgroundColor(Color.RED);
        super.clearView(recyclerView, viewHolder);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return mItemTouchAdapter.isDragSwitchOn();
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return mItemTouchAdapter.isSwipeSwitchOn();
    }

    public static abstract class ItemTouchAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

        public abstract void onItemMove(int fromPosition, int toPosition);

        public abstract void onItemRemove(int position);

        protected boolean isDragSwitchOn() {
            return true;
        }

        protected boolean isSwipeSwitchOn() {
            return true;
        }
    }
}
