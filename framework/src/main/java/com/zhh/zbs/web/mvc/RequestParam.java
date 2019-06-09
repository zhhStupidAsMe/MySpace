package com.zhh.zbs.web.mvc;

import java.lang.annotation.*;

/**
 * @Author Zhang Haohan
 * @Description //TODO
 * @Date 14:29 2019/6/9 0009
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface RequestParam {
    String value();
}
