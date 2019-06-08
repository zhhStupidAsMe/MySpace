package com.zhh.zbs.controllers;

import com.zhh.zbs.beans.Autowired;
import com.zhh.zbs.service.SalaryService;
import com.zhh.zbs.web.mvc.Controller;
import com.zhh.zbs.web.mvc.RequestMapping;
import com.zhh.zbs.web.mvc.RequestParam;

/**
 * @ClassName SalaryController
 * @Description TODO
 * @Author Zhang Haohan
 * @Date 2019/6/8 0008 14:51
 * @Version 1.0
 **/
@Controller
public class SalaryController {
    @Autowired
    private SalaryService salaryService;

    @RequestMapping("/getSalary.json")
    public Integer getSalary(@RequestParam("name") String name, @RequestParam("experience") String experience) {
        return salaryService.calSalary(Integer.parseInt(experience));
    }
}
