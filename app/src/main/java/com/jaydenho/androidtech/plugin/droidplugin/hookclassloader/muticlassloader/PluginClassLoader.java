package com.jaydenho.androidtech.plugin.droidplugin.hookclassloader.muticlassloader;

import android.util.Log;

import dalvik.system.DexClassLoader;

/**
 * Created by hedazhao on 2017/4/30.
 */
public class PluginClassLoader extends DexClassLoader {
    private static final String TAG = "PluginClassLoader";

    public PluginClassLoader(String dexPath, String optimizedDirectory, String libraryPath, ClassLoader parent) {
        super(dexPath, optimizedDirectory, libraryPath, parent);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Log.d(TAG, "findClass name: " + name);
        Class<?> result = super.findClass(name);
        Log.d(TAG, "findClass result: " + (result == null ? "null" : result.getName()));
        return result;
    }
}
