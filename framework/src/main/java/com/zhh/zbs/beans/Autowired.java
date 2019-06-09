package com.zhh.zbs.beans;

import java.lang.annotation.*;

/**
 * @Author Zhang Haohan
 * @Description //TODO 被AutoWired标注的类属性代表需要依赖注入
 * @Date 14:23 2019/6/9 0009
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Autowired {
}
