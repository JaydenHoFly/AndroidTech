package com.jaydenho.androidtech.annotation;

/**
 * Created by hedazhao on 2018/3/26.
 */

@Sql.DBTable(name = "annotation")
public class AnnotationInfo {
    @Sql.Id()
    @Sql.Property(nameInDb = "id", constraint = @Sql.Constraint(notNull = true))
    private int _id;

    @Sql.Property(nameInDb = "key_name")
    private String name;
}
