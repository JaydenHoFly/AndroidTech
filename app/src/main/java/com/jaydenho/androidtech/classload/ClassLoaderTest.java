package com.jaydenho.androidtech.classload;

import com.jaydenho.androidtech.test.Son;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hedazhao on 2017/3/11.
 */

public class ClassLoaderTest {
    public static void main(String[] args) {
        final ClassLoader loader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                System.out.println("name: " + name);
                String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                System.out.println("fileName: " + fileName);
                InputStream is = getClass().getResourceAsStream(fileName);
                if (is == null) {
                    return super.loadClass(name);
                }
                try {
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return super.loadClass(name);
            }
        };

        try {
            Object o = loader.loadClass("com.jaydenho.androidtech.classload.ClassLoaderTest").newInstance();
            System.out.println("obj.getClass: " + o.getClass());
            System.out.println(o instanceof com.jaydenho.androidtech.classload.ClassLoaderTest);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
