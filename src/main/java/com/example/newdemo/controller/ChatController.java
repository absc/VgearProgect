package com.example.newdemo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ChatController {


    @ResponseBody
    @RequestMapping("/templates")
    public String hello() {
        return "Hello World";
    }


//    @RequestMapping("/chats")
//    public String chat(ModelMap map) {
//        map.addAttribute("ids", "http://blog.didispace.com");
//        return "chats";
//    }
}
