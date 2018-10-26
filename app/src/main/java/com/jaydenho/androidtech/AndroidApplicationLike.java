package com.jaydenho.androidtech;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;

import com.jaydenho.androidtech.util.CrashHandler;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by hedazhao on 2016/8/22.
 */
@DefaultLifeCycle(
        application = ".SampleApplication",                       //application类名
        flags = ShareConstants.TINKER_ENABLE_ALL,                 //tinkerFlags
        loaderClass = "com.tencent.tinker.loader.TinkerLoader",   //loaderClassName, 我们这里使用默认即可!
        loadVerifyFlag = false)                                   //tinkerLoadVerifyFlag
public class AndroidApplicationLike extends DefaultApplicationLike {

    private static Application sApplicationInstance = null;

    public AndroidApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
        sApplicationInstance = application;
    }

    @Override
    public void onCreate() {
        StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(tp);
        super.onCreate();
        initImageLoader(getApplicationInstance());
        initTinker();
        sApplicationInstance.registerActivityLifecycleCallbacks(AppStatusChgObserver.getInstance().getActivityLifecycleCallbacks());
//        CrashHandler.getInstance().init(getApplication());
    }

    public static Application getApplicationInstance() {
        return sApplicationInstance;
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);

    }

    public void initTinker() {
        TinkerInstaller.install(this);
        Tinker.with(getApplication());
    }


    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 3)
                .denyCacheImageMultipleSizesInMemory()  //设置不将大图片数据缓存到memory cache中，默认是将图片大数据放到memory cache
                .discCacheFileNameGenerator(new Md5FileNameGenerator()) //设置磁盘缓存文件命名为根据图片原始名称按照0~9 和 a~z的内容填充的md5转化
                .tasksProcessingOrder(QueueProcessingType.LIFO) //设置图片缓存处理为后进新出 (栈)
                // .writeDebugLogs() // Remove for release app 打印log 需要在release中将此功能disable掉
                //.memoryCacheSizeOneOfpoints(8)
                //.memoryCacheSize(int)// 这个不能随便设置，这个不是使用内存的几分之一为缓存 例如 8 --> 1/8  这个而是直接的内存数，如果填了8则仅仅用8个byge来做缓存了
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }
}
