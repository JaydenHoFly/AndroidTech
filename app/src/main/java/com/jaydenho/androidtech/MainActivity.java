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
import com.jaydenho.androidtech.hotfix.HotFixActivity;
import com.jaydenho.androidtech.plugin.droidplugin.HookHelper;
import com.jaydenho.androidtech.plugin.droidplugin.PluginActivity;
import com.jaydenho.androidtech.test.TestAty;
import com.jaydenho.androidtech.widget.anim.AttrAty;
import com.jaydenho.androidtech.widget.view.viewpager.InfiniteAutoScrollViewPagerActivity;

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
        int HOT_FIX = 5;
        int PLUGIN = 6;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      /*  setTheme(R.style.AppTheme);*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initEvent();
        HookHelper.changeActivityInstrumentation(this);
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
        DashboardInfo hotFix = new DashboardInfo(DashboardIds.HOT_FIX, "HotFix");
        mDashboardInfos.add(hotFix);
        DashboardInfo plugin = new DashboardInfo(DashboardIds.PLUGIN, "Plugin");
        mDashboardInfos.add(plugin);

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
                        startActivity(new Intent(mContext, AttrAty.class));
                        break;
                    case DashboardIds.VIEW:
                        startActivity(new Intent(mContext, InfiniteAutoScrollViewPagerActivity.class));
                        break;
                    case DashboardIds.HOT_FIX:
                        startActivity(new Intent(mContext, HotFixActivity.class));
                        break;
                    case DashboardIds.PLUGIN:
                        Intent intent = new Intent("com.jaydenho.androidtech.plugin.droidplugin.PluginActivity");
                        /*intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        getApplicationContext().startActivity(intent);*/

                        startActivity(intent);
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        try {
            // 在这里进行Hook
            HookHelper.attachContext();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
