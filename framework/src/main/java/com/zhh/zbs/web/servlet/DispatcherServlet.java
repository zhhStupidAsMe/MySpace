package com.zhh.zbs.web.servlet;

import com.zhh.zbs.web.handler.HandlerManager;
import com.zhh.zbs.web.handler.MappingHandler;

import javax.servlet.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @ClassName TestServlet
 * @Description TODO 中央控制器
 * @Author Zhang Haohan
 * @Date 2019/6/8 0008 13:38
 * @Version 1.0
 **/
public class DispatcherServlet implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    /**
     * @return void
     * @Author Zhang Haohan
     * @Description //TODO 处理客户端请求
     * @Date 12:26 2019/6/9 0009
     * @Param [request, response]
     **/
    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        for (MappingHandler mappingHandler : HandlerManager.mappingHandlerList) {
            try {
                if (mappingHandler.handle(request, response)) {
                    return;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
