package com.jaydenho.androidtech;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import com.jaydenho.androidtech.androidarchitecture.lifecycle.LearnLifeCycleActivity2;
import com.jaydenho.androidtech.androidarchitecture.paging.PagingActivity;
import com.jaydenho.androidtech.databinding.DataBindingAty;
import com.jaydenho.androidtech.hack.vpn.SgToolVpnClient;
import com.jaydenho.androidtech.hotfix.HotFixActivity;
import com.jaydenho.androidtech.ipc.binder.AIDLActivity;
import com.jaydenho.androidtech.oom.OOMActivity;
import com.jaydenho.androidtech.plugin.droidplugin.Utils;
import com.jaydenho.androidtech.process.ProcessActivity;
import com.jaydenho.androidtech.profile.ProfileAty;
import com.jaydenho.androidtech.test.TestAty;
import com.jaydenho.androidtech.test.TestAty2;
import com.jaydenho.androidtech.widget.anim.AttrAty;
import com.jaydenho.androidtech.widget.anim.transitions.TransitionsOutAty;
import com.jaydenho.androidtech.widget.recyclerview.RecyclerViewActivity;
import com.jaydenho.androidtech.widget.view.LearnTouchAty;
import com.morgoo.droidplugin.pm.PluginManager;
import com.morgoo.helper.compat.PackageManagerCompat;

import java.io.File;
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
        int BINDER = 7;
        int START_PLUGIN_1 = 8;
        int ANDROID_ARCHITECTURE = 9;
        int PROFILE = 10;
        int VPN = 11;
        int PROCESS = 12;
        int OOM = 13;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*  setTheme(R.style.AppTheme);*/
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
        DashboardInfo hotFix = new DashboardInfo(DashboardIds.HOT_FIX, "HotFix");
        mDashboardInfos.add(hotFix);
        DashboardInfo plugin = new DashboardInfo(DashboardIds.PLUGIN, "Plugin");
        mDashboardInfos.add(plugin);
        DashboardInfo binder = new DashboardInfo(DashboardIds.BINDER, "Binder");
        mDashboardInfos.add(binder);
        DashboardInfo profile = new DashboardInfo(DashboardIds.PROFILE, "Profile");
        mDashboardInfos.add(profile);
        DashboardInfo vpn = new DashboardInfo(DashboardIds.VPN, "Vpn");
        mDashboardInfos.add(vpn);

        DashboardInfo process = new DashboardInfo(DashboardIds.PROCESS, "Process");
        mDashboardInfos.add(process);

        DashboardInfo startPlugin1 = new DashboardInfo(DashboardIds.START_PLUGIN_1, "StartPlugin1");
        mDashboardInfos.add(startPlugin1);

        DashboardInfo androidArchitecture = new DashboardInfo(DashboardIds.ANDROID_ARCHITECTURE, "Android Architecture");
        mDashboardInfos.add(androidArchitecture);

        DashboardInfo oom = new DashboardInfo(DashboardIds.OOM, "OOM");
        mDashboardInfos.add(oom);

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
                        Intent testIntent = new Intent(mContext, TestAty.class);
                        testIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(testIntent);
                        break;
                    case DashboardIds.ANIM:
//                        startActivityForResult(new Intent(mContext, AttrAty.class), 100);
                        Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, view.findViewById(R.id.iv_avatar), "detail:avatar").toBundle();
                        startActivity(new Intent(mContext, TransitionsOutAty.class), bundle);
                        break;
                    case DashboardIds.VIEW:
                        startActivity(new Intent(mContext, LearnTouchAty.class));
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
                    case DashboardIds.BINDER:
                        startActivity(new Intent(mContext, AIDLActivity.class));
                        break;
                    case DashboardIds.START_PLUGIN_1:
                        Utils.extractAssets(mContext, "plugin1-debug.apk");
//                        String pluginPath = CommonUtil.BASE_PATH + File.separator + "plugin" + File.separator + "plugin1" + File.separator + "plugin1.apk";
                        File pluginFile = mContext.getFileStreamPath("plugin1-debug.apk");
                        try {
                            int result = PluginManager.getInstance().installPackage(pluginFile.getAbsolutePath(), PackageManagerCompat.INSTALL_REPLACE_EXISTING);
                            Log.d(TAG, "DashboardIds.START_PLUGIN_1 result: " + result);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        startActivity(new Intent("com.jayden.plugin1.launchactivity"));
                        break;
                    case DashboardIds.ANDROID_ARCHITECTURE:
                        startActivity(new Intent(mContext, PagingActivity.class));
                        break;
                    case DashboardIds.PROFILE:
                        startActivity(new Intent(mContext, ProfileAty.class));
                        break;
                    case DashboardIds.VPN:
                        startActivity(new Intent(mContext, SgToolVpnClient.class));
                        break;
                    case DashboardIds.PROCESS:
                        startActivity(new Intent(mContext, ProcessActivity.class));
                        break;
                    case DashboardIds.OOM:
                        startActivity(new Intent(mContext, OOMActivity.class));
                        break;
                }
            }
        });
//        mDashboardsLv.startLayoutAnimation();
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
