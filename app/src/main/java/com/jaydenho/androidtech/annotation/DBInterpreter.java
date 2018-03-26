package com.jaydenho.androidtech.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hedazhao on 2018/3/26.
 */

public class DBInterpreter {
    public String createTableSql(Class<?> clazz) {
        Sql.DBTable dbTable = clazz.getAnnotation(Sql.DBTable.class);
        if (dbTable == null) {
            return null;
        }
        String table_name = dbTable.name();
        List<DBField> dbFields = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Sql.Property.class)) {
                DBField dbField = new DBField();
                if (field.isAnnotationPresent(Sql.Id.class)) {
                    dbField.setId(true);
                }
                if (field.getGenericType() instanceof Class) {
                    String fieldTypeName = ((Class) field.getGenericType()).getName();
                    if (fieldTypeName.equals("java.lang.String"))
                        dbField.setType("TEXT");
                    else
                        dbField.setType("INT");
                }
                dbField.setName(getPropertyNameInDb(field));
                dbFields.add(dbField);
            }
        }
        StringBuilder fieldBuilder = new StringBuilder("CREATE TABLE " + table_name + " (");
        for (DBField dbField : dbFields) {
            fieldBuilder.append(dbField.getName()).append(" ").append(dbField.getType()).append(dbField.isId ? " PRIMARY KEY, " : ", ");
        }
        fieldBuilder.append(" )");
        return fieldBuilder.toString();
    }

    // 获取List中泛型的实际类型
    private static Type getActualType(Type genericType) {
        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            Type actualType = parameterizedType.getActualTypeArguments()[0];
            if (actualType instanceof TypeVariable) {// 泛型类型，比如T
                TypeVariable typeVariable = (TypeVariable) actualType;
                System.out.println("TypeVariable类型: " + typeVariable);

            } else if (actualType instanceof WildcardType) {// 含通配符? 类型
                WildcardType wildcardType = (WildcardType) actualType;
                System.out.println("WildcardType类型: " + wildcardType);

            } else if (actualType instanceof Class) { // 普通类对象
                Class cls = (Class) actualType;
                System.out.println("Class类型: " + actualType); // class
                // java.lang.String
            }
            return actualType;
        }
        return null;
    }

    private String getPropertyNameInDb(Field field) {
        if (field.isAnnotationPresent(Sql.Property.class)) {
            return field.getAnnotation(Sql.Property.class).nameInDb();
        }
        return null;
    }

    static class DBField {
        private boolean isId;
        private String name;
        private boolean isNotNull;
        private String type;

        public boolean isId() {
            return isId;
        }

        public void setId(boolean id) {
            isId = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isNotNull() {
            return isNotNull;
        }

        public void setNotNull(boolean notNull) {
            isNotNull = notNull;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
