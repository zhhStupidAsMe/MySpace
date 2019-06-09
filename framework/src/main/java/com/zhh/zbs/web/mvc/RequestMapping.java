package com.zhh.zbs.web.mvc;

import java.lang.annotation.*;
/**
 * @Author Zhang Haohan
 * @Description //TODO 代表这个方法是MappingHandler
 * @Date 14:28 2019/6/9 0009
 * @Param 
 * @return 
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequestMapping {
    String value();
}
