package com.jaydenho.androidtech.hotfix;

import android.content.Context;

import com.morgoo.droidplugin.PluginHelper;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

public class MyTinkerApplication extends TinkerApplication {
    public MyTinkerApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.jaydenho.androidtech.AndroidApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //这里必须在super.onCreate方法之后，顺序不能变
        PluginHelper.getInstance().applicationOnCreate(getBaseContext());
    }

    @Override
    protected void attachBaseContext(Context base) {
        PluginHelper.getInstance().applicationAttachBaseContext(base);
        super.attachBaseContext(base);
    }
}
