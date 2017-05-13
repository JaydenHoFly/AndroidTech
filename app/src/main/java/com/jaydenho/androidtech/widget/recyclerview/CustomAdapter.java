package com.jaydenho.androidtech.widget.recyclerview;

import android.content.Context;
import android.view.ViewGroup;

import com.jaydenho.androidtech.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hedazhao on 2017/5/9.
 */

public class CustomAdapter extends CustomItemTouchHelperCallback.ItemTouchAdapter<BaseViewHolder> {

    private static final int TYPE_TEXT = 1;
    private static final int TYPE_IMAGE = 2;

    private Context mContext = null;
    private List<String> mData = null;

    public CustomAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
        for (int i = 97; i < 200; i++) {
            mData.add(Character.toString((char) i));
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_IMAGE:
                viewHolder = new ImageViewHolder(mContext, R.layout.item_recyclerview_text, parent);
                break;
            case TYPE_TEXT:
                viewHolder = new TextViewHolder(mContext, R.layout.item_recyclerview_text, parent);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(mData.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        if(position > 15) {
            return TYPE_IMAGE;
        } else {
            return TYPE_TEXT;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mData, fromPosition, toPosition);
    }

    @Override
    public void onItemRemove(int position) {
        mData.remove(position);
    }

    @Override
    protected boolean isSwipeSwitchOn() {
        return false;
    }
}
