package com.zhh.zbs.web.mvc;

import java.lang.annotation.*;

/**
 * @Author Zhang Haohan
 * @Description //TODO 代表这个类是Controller，并会被初始化为Bean
 * @Date 14:26 2019/6/9 0009
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Controller {
}
