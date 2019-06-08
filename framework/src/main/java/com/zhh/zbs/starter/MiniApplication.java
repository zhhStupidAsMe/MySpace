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
 * @Description TODO
 * @Author Zhang Haohan
 * @Date 2019/6/8 0008 13:10
 * @Version 1.0
 **/
public class MiniApplication {
    public static void run(Class<?> cls, String[] args) {
        System.out.println("Hello Mini-spring!");
        TomcatServer tomcatServer = new TomcatServer(args);
        try {
            tomcatServer.startServer();
//            tomcatServer.stopServer();
            List<Class<?>> classList = ClassScanner.scanClasses(cls.getPackage().getName());
            BeanFactory.initBean(classList);
            classList.forEach(it -> System.out.println(it.getName()));
            HandlerManager.resolveMappingHandler(classList);
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
