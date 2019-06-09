package com.zhh.zbs.starter;

import com.zhh.zbs.beans.BeanFactory;
import com.zhh.zbs.core.ClassScanner;
import com.zhh.zbs.web.handler.HandlerManager;
import com.zhh.zbs.web.server.TomcatServer;
import org.apache.catalina.LifecycleException;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName MiniApplication
 * @Description //TODO framework启动类
 * @Author Zhang Haohan
 * @Date 2019/6/8 0008 13:10
 * @Version 1.0
 **/
public class MiniApplication {
    /**
     * @return void
     * @Author Zhang Haohan
     * @Description //TODO framework启动方法
     * @Date 13:18 2019/6/9 0009
     * @Param [cls, args]
     **/
    public static void run(Class<?> cls, String[] args) {
        System.out.println("Hello Mini-spring!");
        TomcatServer tomcatServer = new TomcatServer(args);//创建服务器实例
        try {
            tomcatServer.startServer();//启动服务器
            List<Class<?>> classList = ClassScanner.scanClasses(cls.getPackage().getName());//获取包名，将包下的所有类扫描解析方法容器中
            classList.forEach(it -> System.out.println(it.getName()));//test
            BeanFactory.initBean(classList);//将容器中的类全都初始化为Bean
            HandlerManager.resolveMappingHandler(classList);//初始化MappingHandler
        } catch (LifecycleException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
