package com.jayden.learnproguard3;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 下载中心广告单例生成器。
 * 创建和key相对应的单例，目前构造方法中只能有一个String类型的参数。
 * Created by hedazhao on 2018/12/18.
 */
public class DownloadCenterAdInstanceProviders {
    private static final String TAG = "DownloadCenterAdInstanceProviders";
    private static Map<String, IDownloadCenterAdInstance> sInstanceStore = new HashMap<>();

    @MainThread
    public static <T extends IDownloadCenterAdInstance> T getInstance( String origin, @NonNull Class<T> clazz) {
        String key = buildUniqueStoreKey(origin, clazz);
        IDownloadCenterAdInstance instance = sInstanceStore.get(key);
        if (clazz.isInstance(instance)) {
            //noinspection unchecked
            return (T) instance;
        }
        instance = create(origin, clazz);
        sInstanceStore.put(key, instance);
        //noinspection unchecked
        return (T) instance;
    }

    @MainThread
    public static <T extends IDownloadCenterAdInstance> void destroyInstance( String origin, @NonNull Class<T> clazz) {
        sInstanceStore.remove(buildUniqueStoreKey(origin, clazz));
    }

    @NonNull
    private static <T extends IDownloadCenterAdInstance> T create( String origin, @NonNull Class<T> clazz) {
        try {
            Constructor constructor = clazz.getDeclaredConstructor(String.class);
            constructor.setAccessible(true);
            //noinspection unchecked
            return (T) constructor.newInstance(origin);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException | SecurityException e) {
            throw new RuntimeException("Cannot create an instance of " + clazz, e);
        }
    }

    @NonNull
    private static <T extends IDownloadCenterAdInstance> String buildUniqueStoreKey(String origin, @NonNull Class<T> clazz) {
        return origin + "_" + clazz.getCanonicalName();
    }
}
