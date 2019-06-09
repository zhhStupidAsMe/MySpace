package com.zhh.zbs.web.handler;

import com.zhh.zbs.beans.BeanFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ClassName MappingHandler
 * @Description TODO MappingHandler实例
 * @Author Zhang Haohan
 * @Date 2019/6/8 0008 15:58
 * @Version 1.0
 **/
public class MappingHandler {
    private String uri;
    private Method method;
    private Class<?> controller;
    private String[] args;

    public MappingHandler(String uri, Method method, Class<?> controller, String[] args) {
        this.uri = uri;
        this.method = method;
        this.controller = controller;
        this.args = args;
    }

    /**
     * @return boolean
     * @Author Zhang Haohan
     * @Description //TODO 判断是否能处理请求
     * @Date 13:41 2019/6/9 0009
     * @Param [request, res]
     **/
    public boolean handle(ServletRequest request, ServletResponse res) throws IllegalAccessException, InstantiationException, InvocationTargetException, IOException {
        String requestUri = ((HttpServletRequest) request).getRequestURI();
        if (!uri.equals(requestUri)) {//判断请求是否相等
            return false;
        }
        //获取请求参数
        Object[] parameters = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            parameters[i] = request.getParameter(args[i]);
        }
        Object ctl = BeanFactory.getBean(controller);//创建Bean
        Object response = method.invoke(ctl, parameters);//执行方法获取结果
//        System.out.println(response.toString());
        res.getWriter().println(response.toString());//将结果输出到页面
        return true;
    }
}
