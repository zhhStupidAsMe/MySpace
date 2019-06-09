package com.zhh.zbs.core;

import sun.net.www.protocol.jar.JarURLConnection;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @ClassName ClassScanner
 * @Description //TODO 实现类扫描
 * @Author Zhang Haohan
 * @Date 2019/6/8 0008 15:15
 * @Version 1.0
 **/
public class ClassScanner {
    /**
     * @return java.util.List<java.lang.Class < ?>>
     * @Author Zhang Haohan
     * @Description //TODO 类扫描方法
     * @Date 13:06 2019/6/9 0009
     * @Param [packageName]
     **/
    public static List<Class<?>> scanClasses(String packageName) throws IOException, ClassNotFoundException {
        List<Class<?>> classList = new ArrayList<Class<?>>();
        String path = packageName.replace(".", "/");//包名转换为路径
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();//获取应用类加载器
        Enumeration<URL> resources = classLoader.getResources(path);//通过路径获取类资源
        while (resources.hasMoreElements()) {//遍历
            URL resource = resources.nextElement();
            if (resource.getProtocol().contains("jar")) {//判断资源类型，处理jar类型
                JarURLConnection jarURLConnection = (JarURLConnection) resource.openConnection();//获取资源链接
                String jarFilePath = jarURLConnection.getJarFile().getName();//获取jar包绝对路径
                classList.addAll(getClassesFromJar(jarFilePath, path));//获取jar包下所有类并添加到容器
            } else {
                //TODO
            }
        }
        return classList;
    }

    /**
     * @return java.util.List<java.lang.Class < ?>>
     * @Author Zhang Haohan
     * @Description //TODO 通过jar包路径获取jar包下所有的类
     * @Date 13:10 2019/6/9 0009
     * @Param [jarFilePath, path]
     **/
    private static List<Class<?>> getClassesFromJar(String jarFilePath, String path) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        JarFile jarFile = new JarFile(jarFilePath);//类似File
        Enumeration<JarEntry> jarEntries = jarFile.entries();
        while (jarEntries.hasMoreElements()) {//遍历
            JarEntry jarEntry = jarEntries.nextElement();
            String entryName = jarEntry.getName();//com/zhh/zbs/test/Test.class
            if (entryName.startsWith(path) && entryName.endsWith(".class")) {
                String classFullName = entryName.replace("/", ".")
                        .substring(0, entryName.length() - 6);//获取类的全限定名
                classes.add(Class.forName(classFullName));//加载到jvm中

            }
        }
        return classes;
    }
}
