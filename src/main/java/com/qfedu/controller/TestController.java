package com.qfedu.controller;/*
 *@ClassName:TestController
 *@Author:lg
 *@Description:
 *@Date:2020/6/721:00
 */

import com.qfedu.service.IClickService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private IClickService clickService;

    @RequestMapping("/clickNumber")
    public String click() {

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                clickService.clickNumber();
            }).start();
        }

        return "OK";
    }

}
