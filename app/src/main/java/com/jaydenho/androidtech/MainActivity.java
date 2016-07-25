package com.jaydenho.androidtech;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaydenho.androidtech.databinding.DataBindingAty;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Context mContext = null;
    private ListView mDashboardsLv = null;
    private List<DashboardInfo> mDashboardInfos = null;
    private DashboardAdapter mDashboardAdapter = null;

    public interface DashboardIds {
        int DATA_BINDING = 1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        mDashboardsLv = (ListView) findViewById(R.id.lv_dashboards);
    }

    private void initData() {
        mContext = this;
        mDashboardInfos = new ArrayList<>();
        DashboardInfo dataBinding = new DashboardInfo(DashboardIds.DATA_BINDING, "DataBinding");
        mDashboardInfos.add(dataBinding);
        mDashboardAdapter = new DashboardAdapter();
    }

    private void initEvent() {
        mDashboardsLv.setAdapter(mDashboardAdapter);
        mDashboardsLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (mDashboardInfos.get(i).getId()) {
                    case DashboardIds.DATA_BINDING:
                        startActivity(new Intent(mContext, DataBindingAty.class));
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    class DashboardAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mDashboardInfos.size();
        }

        @Override
        public Object getItem(int i) {
            return mDashboardInfos.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final ViewHolder vh;
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.list_item_dashboard, null, false);
                vh = new ViewHolder();
                vh.title = (TextView) view.findViewById(R.id.tv_title);
                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }
            DashboardInfo info = mDashboardInfos.get(i);
            vh.title.setText(info.getTitle());
            return view;
        }

        class ViewHolder {
            TextView title;
        }
    }

}
