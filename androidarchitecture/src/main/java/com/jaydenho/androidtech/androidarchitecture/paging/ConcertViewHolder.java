package com.jaydenho.androidtech.androidarchitecture.paging;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jaydenho.androidtech.androidarchitecture.R;

/**
 * Created by hedazhao on 2018/10/8.
 */
public class ConcertViewHolder extends RecyclerView.ViewHolder {
    private TextView mTv;

    public ConcertViewHolder(View itemView) {
        super(itemView);
        mTv = itemView.findViewById(R.id.tv);
    }

    public void bindTo(Concert concert) {
        mTv.setText(concert.getText());
    }
}
