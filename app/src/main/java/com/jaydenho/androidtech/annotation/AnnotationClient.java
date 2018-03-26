package com.jaydenho.androidtech.annotation;

/**
 * Created by hedazhao on 2018/3/26.
 */

public class AnnotationClient {
    public static void main(String[] args) {
        AnnotationInfo info = new AnnotationInfo();
        String createTableSql = new DBInterpreter().createTableSql(info.getClass());
        System.out.println("createTableSql: " + createTableSql);
    }
}
