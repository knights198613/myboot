package com.jiangwei.springboottest.myboot.assist;

import org.apache.ibatis.javassist.ClassPool;
import org.apache.ibatis.javassist.CtClass;
import org.apache.ibatis.javassist.CtMethod;
import org.apache.ibatis.javassist.CtNewMethod;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author: weijiang
 * @date: 2021/9/10
 * @description:
 **/
public class MyAssistTest {


    @Test
    public void createClassFile() throws Exception {
        ClassPool cp = ClassPool.getDefault();

        CtClass ctClass = cp.makeClass("com.jiangwei.springboottest.assist.Programmer");

        CtMethod ctMethod = CtNewMethod.make("public void work(){}", ctClass);

        ctMethod.insertBefore("System.out.println(\"I am doing work!\");");

        ctClass.addMethod(ctMethod);

        ctClass.writeFile();

    }


    class MyClassLoad extends ClassLoader {

        public Class<?> loadClass(String name, FileInputStream fileInputStream) throws Exception {
            byte[] result = new byte[1024];
            int count = fileInputStream.read(result);
            if(count > 0) {
                return this.defineClass(name, result, 0, count);
            }
            return null;
        }
    }

    @Test
    public void loadClass2Instance() {
        MyClassLoad myClassLoad = new MyClassLoad();

        File file = new File("/Users/didi/IdeaProjects/myboot/com/jiangwei/springboottest/assist/"+"Programmer.class");
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            Class clazz = myClassLoad.loadClass(null, fileInputStream);
            if(clazz != null) {
                Object obj = clazz.newInstance();
                clazz.getMethod("work", null).invoke(obj, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
