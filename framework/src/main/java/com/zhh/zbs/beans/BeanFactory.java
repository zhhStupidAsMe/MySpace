package com.zhh.zbs.beans;

import com.zhh.zbs.web.mvc.Controller;

import javax.print.attribute.standard.Finishings;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName BeanFactory
 * @Description 负责Bean的初始化和存储
 * @Author Zhang Haohan
 * @Date 2019/6/8 0008 18:33
 * @Version 1.0
 **/
public class BeanFactory {
    private static Map<Class<?>, Object> classToBean = new ConcurrentHashMap<>();

    /**
     * @return java.lang.Object
     * @Author Zhang Haohan
     * @Description //TODO 获取Bean
     * @Date 14:13 2019/6/9 0009
     * @Param [cls]
     **/
    public static Object getBean(Class<?> cls) {
        return classToBean.get(cls);
    }

    /**
     * @return void
     * @Author Zhang Haohan
     * @Description //TODO 初始化Bean
     * @Date 14:14 2019/6/9 0009
     * @Param [classList]
     **/
    public static void initBean(List<Class<?>> classList) throws Exception {
        List<Class<?>> toCreate = new ArrayList<>(classList);
        while (toCreate.size() != 0) {//循环遍历
            int remainSize = toCreate.size();//记录size
            for (int i = 0; i < toCreate.size(); i++) {//循环初始化
                if (finishCreate(toCreate.get(i))) {//初始化
                    toCreate.remove(i);//完成创建就从容器中移除
                }
            }
            if (toCreate.size() == remainSize) {//如果size没变则说明陷入了死循环，抛出异常停止
                throw new Exception("cycle dependency!");
            }
        }
    }

    /**
     * @return boolean
     * @Author Zhang Haohan
     * @Description //TODO 初始化Bean
     * @Date 14:17 2019/6/9 0009
     * @Param [cls]
     **/
    public static boolean finishCreate(Class<?> cls) throws IllegalAccessException, InstantiationException {
        if (!cls.isAnnotationPresent(Bean.class) && !cls.isAnnotationPresent(Controller.class)) {//判断是否需要初始化
            return true;
        }
        Object bean = cls.newInstance();//创建实例
        for (Field field : cls.getDeclaredFields()) {//循环属性
            if (field.isAnnotationPresent(Autowired.class)) {//判断是否需要依赖
                Class<?> fieldType = field.getType();//获取属性类型
                Object reliantBean = BeanFactory.getBean(fieldType);//通过类型获取被依赖的Bean
                if (reliantBean == null) {//如果没获取到返回false
                    return false;
                }
                field.setAccessible(true);//设置可见性
                field.set(bean, reliantBean);//注入到Bean中
            }
        }
        classToBean.put(cls, bean);//放到Bean容器中
        return true;
    }
}
