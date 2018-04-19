package com.example.newdemo.controller;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class TextController {


    @PostMapping(value = "/pay")
    public String creat(@RequestParam("uid")String  uid){
        return "";
    }
}
