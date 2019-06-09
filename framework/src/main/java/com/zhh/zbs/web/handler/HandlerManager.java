package com.zhh.zbs.web.handler;

import com.zhh.zbs.web.mvc.Controller;
import com.zhh.zbs.web.mvc.RequestMapping;
import com.zhh.zbs.web.mvc.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName HandlerManager
 * @Description //TODO Handler管理器
 * @Author Zhang Haohan
 * @Date 2019/6/8 0008 16:01
 * @Version 1.0
 **/
public class HandlerManager {
    public static List<MappingHandler> mappingHandlerList = new ArrayList<>();

    /**
     * @return void
     * @Author Zhang Haohan
     * @Description //TODO 找出Controller类，并把Controller中的MappingHandler方法初始化为Handler
     * @Date 13:26 2019/6/9 0009
     * @Param [classList]
     **/
    public static void resolveMappingHandler(List<Class<?>> classList) {
        for (Class<?> cls : classList) {//遍历
            if (cls.isAnnotationPresent(Controller.class)) {//判断是否带有Controller注解
                parseHandlerFromController(cls);
            }
        }
    }

    /**
     * @return void
     * @Author Zhang Haohan
     * @Description //TODO 解析Controller类
     * @Date 13:34 2019/6/9 0009
     * @Param [cls]
     **/
    private static void parseHandlerFromController(Class<?> cls) {
        Method[] methods = cls.getDeclaredMethods();//获取类的所有方法
        for (Method method : methods) {//遍历方法
            if (!method.isAnnotationPresent(RequestMapping.class)) {//判断是否带有RequestMapping注解
                continue;//如果没有直接返回
            }
            String uri = method.getDeclaredAnnotation(RequestMapping.class).value();//获取URI
            List<String> paramNameList = new ArrayList<>();//存放参数
            for (Parameter parameter : method.getParameters()) {//遍历参数
                if (parameter.isAnnotationPresent(RequestParam.class)) {//判断是否带有RequestParam注解
                    paramNameList.add(parameter.getDeclaredAnnotation(RequestParam.class).value());//添加到容器中
                }
            }
            String[] params = paramNameList.toArray(new String[paramNameList.size()]);//转换成数组
            MappingHandler mappingHandler = new MappingHandler(uri, method, cls, params);//实例化MappingHandler
            HandlerManager.mappingHandlerList.add(mappingHandler);//添加到管理容器中
        }
    }
}
