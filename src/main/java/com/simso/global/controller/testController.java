package com.simso.global.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RefreshScope
public class testController {


    private String identity;

    @GetMapping("/test")
    public String test(){
        return identity;
    }


}
