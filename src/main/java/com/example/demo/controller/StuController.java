package com.example.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.example.demo.dao.service.StuService;
import com.example.demo.pojo.Stu;
import com.example.util.ThreadLocalUtil;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class StuController {

    @Autowired
    StuService stuService;

    @GetMapping("/list")
    public IPage<Stu> l(@ModelAttribute Stu stu){
        LambdaQueryChainWrapper<Stu> lambdaQuery = stuService.lambdaQuery();
        if (!StringUtils.isEmpty(stu.getName())){
            lambdaQuery = lambdaQuery.eq(Stu::getName, stu.getName());
        }

        IPage<Stu> page = new Page<>(1,10);
        page= lambdaQuery.page(page);
        page.setRecords(page.getRecords());
        return page;
    }

    @GetMapping("/get")
    public String f1(){
        return "HELLO ACTIONS";
    }

    @GetMapping("/get1")
    public String f2(){
        return "HELLO ACTIONS";
    }

    @GetMapping("/get2")
    public String f3(){
        return "HELLO ACTIONS";
    }

    @GetMapping(value = "/update")
    public void update(){
    }

    @GetMapping(value = "/login")
    public void login(@RequestParam Long userId){
        ThreadLocalUtil.setUserId(userId);
    }

    @GetMapping(value = "/getUserId")
    public void getUserId(){
        System.err.println("im com  ming");
        System.err.println(ThreadLocalUtil.userThreadLocal.get());
    }

}
