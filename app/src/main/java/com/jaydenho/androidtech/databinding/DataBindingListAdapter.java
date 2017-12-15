package com.jaydenho.androidtech.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jaydenho.androidtech.BR;
import com.jaydenho.androidtech.R;

import java.util.List;

import com.jaydenho.androidtech.databinding.ListItemDataBindingBinding;//不能删除

/**
 * Created by hedazhao on 2017/12/14.
 */

public class DataBindingListAdapter extends RecyclerView.Adapter<DataBindingListAdapter.BindingViewHolder> {

    private List<DataBindingInfo> mData = null;

    public void setData(List<DataBindingInfo> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            ListItemDataBindingBinding vb = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_data_binding, parent, false);
            return new DataBinding1ViewHolder(vb);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        //BR:DataBinding在xml文件的<data>标签中定义的属性
        //不一定所有情况都能拿到具体的ViewDataBinding子类对象，向RecyclerView#onBindViewHolder就只能拿到通用的ViewDataBinding对象，所以就要通过setVariable的方式为<data>标签中的属性设置值。
        holder.getViewDataBinding().setVariable(BR.viewModel, mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getViewType();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public abstract class BindingViewHolder<B extends ViewDataBinding> extends RecyclerView.ViewHolder {

        private B mViewDataBinding = null;

        public BindingViewHolder(B viewDataBinding) {
            super(viewDataBinding.getRoot());
            mViewDataBinding = viewDataBinding;
        }

        public B getViewDataBinding() {
            return mViewDataBinding;
        }
    }

    public class DataBinding1ViewHolder extends BindingViewHolder<ListItemDataBindingBinding> {

        public DataBinding1ViewHolder(ListItemDataBindingBinding viewDataBinding) {
            super(viewDataBinding);
        }


    }
}
