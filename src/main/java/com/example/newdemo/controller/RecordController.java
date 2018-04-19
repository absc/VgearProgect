package com.example.newdemo.controller;

import com.alibaba.fastjson.JSON;
import com.example.newdemo.bean.ReciveMessage;
import com.example.newdemo.dao.ReciveMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;


@EnableTransactionManagement  // 需要事务的时候加上
@RestController
public class RecordController {

    @Autowired
    private ReciveMessageMapper reciveMessageMapper;



    //huo
    @Transactional
    @RequestMapping(value="/getrecord")
    public String getRecording (){
        List<ReciveMessage> reciveMessages;
        reciveMessages = reciveMessageMapper.seletAll();
        return JSON.toJSONString(reciveMessages);
    }

}
