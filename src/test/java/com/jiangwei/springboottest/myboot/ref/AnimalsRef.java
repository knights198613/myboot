package com.jiangwei.springboottest.myboot.ref;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author: weijiang
 * @date: 2022/2/28
 * @description:
 **/
public class AnimalsRef {

    public static void main(String[] args) {
        doTest();
    }

    private static void doTest() {
        String fullClassName = "com.jiangwei.springboottest.myboot.ref.Animals";
        try {
            Class<?> clazz = Class.forName(fullClassName);
            handleFields(clazz);
            handleConstructors(clazz);
            handleMethods(clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取字段信息
     * @param clazz
     */
    private static void handleFields(Class<?> clazz) {
        if(clazz != null) {
            Field[] fields = clazz.getFields();
            Field[] fields1 = clazz.getDeclaredFields();
            for(Field field : fields) {
                System.out.println("fields:"+ field);
            }

            for (Field field : fields1) {
                System.out.println("fields = " + field);
            }
        }
    }

    /**
     * 处理构造器信息
     * @param clazz
     */
    private static void handleConstructors(Class<?> clazz) {
        if(clazz != null) {
            Constructor<?>[] constructors = clazz.getConstructors();
            Constructor<?>[] constructors1 = clazz.getDeclaredConstructors();
            for (Constructor<?> cst : constructors) {
                System.out.println("constructor: " + cst);
            }

            for (Constructor<?> cst : constructors1) {
                System.out.println("constructor = " + cst);
            }
        }

    }


    public static void handleMethods(Class<?> clazz) {
        if(clazz != null) {
            Method[] methods = clazz.getMethods();
            Method[] methods1 = clazz.getDeclaredMethods();
            for(Method method : methods) {
                System.out.println("methods: " + method);
            }
            for(Method method : methods1) {
                System.out.println("methods = " + method);
            }
        }
    }



}
