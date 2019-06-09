package com.zhh.zbs.web.server;

import com.zhh.zbs.web.servlet.DispatcherServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

/**
 * @ClassName TomcatServer
 * @Description //TODO ����Tomcat������
 * @Author Zhang Haohan
 * @Date 2019/6/8 0008 13:26
 * @Version 1.0
 **/
public class TomcatServer {
    private Tomcat tomcat;
    private String[] args;//������չ

    public TomcatServer(String[] args) {
        this.args = args;
    }

    /**
     * @return void
     * @Author Zhang Haohan
     * @Description //TODO ����Tomcat
     * @Date 12:20 2019/6/9 0009
     * @Param []
     **/
    public void startServer() throws LifecycleException {
        tomcat = new Tomcat();
        tomcat.setPort(6699);//���ü����˿�
        tomcat.start();
        Context context = new StandardContext();//����Context����
        context.setPath("");//����·��
        context.addLifecycleListener(new Tomcat.FixContextListener());//�����������ڼ�����
        DispatcherServlet servlet = new DispatcherServlet();//ʵ����
        Tomcat.addServlet(context, "dispatcherServlet", servlet).setAsyncSupported(true);//��servlet��ӵ�context
        context.addServletMappingDecoded("/", "dispatcherServlet");//ָ������·��
        tomcat.getHost().addChild(context);//context������host
        /*
         * @Author Zhang Haohan
         * @Description //TODO ��ֹTomcat��;ֹͣ��һֱ�ȴ�
         * @Date 12:21 2019/6/9 0009
         **/
        Thread awaitThread = new Thread("tomcat_await_thread") {
            @Override
            public void run() {
                TomcatServer.this.tomcat.getServer().await();
            }
        };
        awaitThread.setDaemon(false);//�����߳��Ƿ��ػ�
        awaitThread.start();
    }

    /**
     * @return void
     * @Author Zhang Haohan
     * @Description //TODO �ر�Tomcat
     * @Date 12:35 2019/6/9 0009
     * @Param []
     **/
    public void stopServer() throws LifecycleException {
        tomcat.stop();
    }
}
