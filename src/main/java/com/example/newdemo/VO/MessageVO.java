package com.example.newdemo.VO;

import lombok.Data;

/**
 * Created by Ldlood on 2017/8/20.
 */

@Data
public class MessageVO {


    private Integer userNum;


    /**
     * 1连接
     * 2退出
     * 3普通消息
     * 4消费消息
     * 5霸屏
     */
    private Integer type;


    private String message;

    private String headpic;

    private String nickname;

    private String time;


    private String money;


    private String imageOne;

    private String  imageTwo;


}