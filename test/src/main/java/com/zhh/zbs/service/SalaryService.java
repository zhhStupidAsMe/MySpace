package com.zhh.zbs.service;

import com.zhh.zbs.beans.Bean;

/**
 * @ClassName SalaryService
 * @Description TODO
 * @Author Zhang Haohan
 * @Date 2019/6/8 0008 18:49
 * @Version 1.0
 **/
@Bean
public class SalaryService {
    public Integer calSalary(Integer experience) {
        return experience * 5000;
    }
}
