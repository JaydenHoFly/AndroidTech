package com.jaydenho.androidtech;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jaydenho.androidtech.databinding.DataBindingAty;
import com.jaydenho.androidtech.test.TestAty;
import com.jaydenho.androidtech.widget.anim.ShootIconAty;
import com.jaydenho.androidtech.widget.view.BasicAttrsAty;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Context mContext = null;
    private ListView mDashboardsLv = null;
    private DashboardAdapter mDashboardAdapter = null;
    private LayoutAnimationController mListViewAnimationController = null;
    private List<DashboardInfo> mDashboardInfos = null;

    public interface DashboardIds {
        int DATA_BINDING = 1;
        int TEST = 2;
        int ANIM = 3;
        int VIEW = 4;
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
        initListViewAnimation();
        mDashboardsLv.setLayoutAnimation(mListViewAnimationController);
    }

    private void initListViewAnimation() {
        Animation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -0.5f, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0);
        Animation alphaAnimation = new AlphaAnimation(0, 1);
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(translateAnimation);
        set.addAnimation(alphaAnimation);
        set.setDuration(800);
        set.setInterpolator(new LinearInterpolator());
        mListViewAnimationController = new LayoutAnimationController(set);
        mListViewAnimationController.setOrder(LayoutAnimationController.ORDER_NORMAL);
    }

    private void initData() {
        mContext = this;
        mDashboardInfos = new ArrayList<>();
        DashboardInfo dataBinding = new DashboardInfo(DashboardIds.DATA_BINDING, "DataBinding");
        mDashboardInfos.add(dataBinding);
        DashboardInfo test = new DashboardInfo(DashboardIds.TEST, "Test");
        mDashboardInfos.add(test);
        DashboardInfo anim = new DashboardInfo(DashboardIds.ANIM, "Anim");
        mDashboardInfos.add(anim);
        DashboardInfo view = new DashboardInfo(DashboardIds.VIEW, "View");
        mDashboardInfos.add(view);
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
                    case DashboardIds.TEST:
                        startActivity(new Intent(mContext, TestAty.class));
                        break;
                    case DashboardIds.ANIM:
                        startActivity(new Intent(mContext, ShootIconAty.class));
                        break;
                    case DashboardIds.VIEW:
                        startActivity(new Intent(mContext, BasicAttrsAty.class));
                        break;
                }
            }
        });
        mDashboardsLv.startLayoutAnimation();
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
