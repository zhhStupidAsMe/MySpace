package com.zhh.zbs.beans;

import java.lang.annotation.*;

/**
 * @Author Zhang Haohan
 * @Description //TODO 被Bean标注的类会被创建为Bean
 * @Date 14:23 2019/6/9 0009
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Bean {

}
