package com.zhh.zbs.web.server;

import com.zhh.zbs.web.servlet.DispatcherServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

/**
 * @ClassName TomcatServer
 * @Description //TODO 创建Tomcat服务器
 * @Author Zhang Haohan
 * @Date 2019/6/8 0008 13:26
 * @Version 1.0
 **/
public class TomcatServer {
    private Tomcat tomcat;
    private String[] args;//留作扩展

    public TomcatServer(String[] args) {
        this.args = args;
    }

    /**
     * @return void
     * @Author Zhang Haohan
     * @Description //TODO 启动Tomcat
     * @Date 12:20 2019/6/9 0009
     * @Param []
     **/
    public void startServer() throws LifecycleException {
        tomcat = new Tomcat();
        tomcat.setPort(6699);//设置监听端口
        tomcat.start();
        Context context = new StandardContext();//创建Context容器
        context.setPath("");//设置路径
        context.addLifecycleListener(new Tomcat.FixContextListener());//设置生命周期监听器
        DispatcherServlet servlet = new DispatcherServlet();//实例化
        Tomcat.addServlet(context, "dispatcherServlet", servlet).setAsyncSupported(true);//将servlet添加到context
        context.addServletMappingDecoded("/", "dispatcherServlet");//指定访问路径
        tomcat.getHost().addChild(context);//context依附于host
        /*
         * @Author Zhang Haohan
         * @Description //TODO 防止Tomcat中途停止，一直等待
         * @Date 12:21 2019/6/9 0009
         **/
        Thread awaitThread = new Thread("tomcat_await_thread") {
            @Override
            public void run() {
                TomcatServer.this.tomcat.getServer().await();
            }
        };
        awaitThread.setDaemon(false);//设置线程是否守护
        awaitThread.start();
    }

    /**
     * @return void
     * @Author Zhang Haohan
     * @Description //TODO 关闭Tomcat
     * @Date 12:35 2019/6/9 0009
     * @Param []
     **/
    public void stopServer() throws LifecycleException {
        tomcat.stop();
    }
}
