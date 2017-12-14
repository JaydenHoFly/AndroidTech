package com.jaydenho.androidtech.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jaydenho.androidtech.BR;
import com.jaydenho.androidtech.R;

import java.util.List;

/**
 * Created by hedazhao on 2017/12/14.
 */

public class DataBindingListAdapter extends RecyclerView.Adapter<DataBindingListAdapter.BindingViewHolder> {

    private List<String> mData = null;

    public void setData(List<String> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding vb = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_test_list_view, parent, false);
        return new BindingViewHolder(vb);
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        holder.getViewDataBinding().setVariable(BR.viewModel, mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class BindingViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding mViewDataBinding = null;

        public BindingViewHolder(ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
        }

        public ViewDataBinding getViewDataBinding() {
            return mViewDataBinding;
        }
    }
}
