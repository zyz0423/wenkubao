package com.wenkubao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DownloadController {

    @RequestMapping("/index")
    public String index(){
        return "main";
    }

}
