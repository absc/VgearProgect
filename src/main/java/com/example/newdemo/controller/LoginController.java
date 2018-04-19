package com.example.newdemo.controller;

import com.alibaba.fastjson.JSON;
import com.example.newdemo.bean.WxToken;
import com.example.newdemo.dao.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.IOException;

@EnableTransactionManagement  // 需要事务的时候加上
@Controller
@RequestMapping(value="/login")
public class LoginController {

    @Autowired
    private VgearUserMapper vgearUserMapper;

    @Autowired
    private WeixinMapper weixinMapper;

    @Autowired
    private OkHttpClient httpClient;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);


    @Transactional
    @RequestMapping(value="/getcode")
    public String oindex(ModelMap map,@RequestParam(name = "code", required = false) String code,
                          @RequestParam(name = "state", required = false) String state) {
//         url中的code可通过@PathVariable绑定到函数的参数中
        Weixin weixin =weixinMapper.findUserByToken(1);
        String url ="https://api.weixin.qq.com/sns/oauth2/access_token?appid="+weixin.getAppid()+"&secret="+weixin.getAppsecret()+"&code="+code+"&grant_type=authorization_code";
        WxToken token = JSON.parseObject(getJson(url),WxToken.class);
        VgearUser user = vgearUserMapper.findUserByToken(token.getOpenid());
        if (user==null){
            VgearUser user1;
            WxUser wxUser=null;
            String userget ="https://api.weixin.qq.com/sns/userinfo?access_token="+token.getAccess_token()+"&openid="+token.getOpenid()+"&lang=zh_CN";
            user1 = JSON.parseObject(getJson(userget),VgearUser.class);
            if (user1.getWxopenid()==null){
                String refresh = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid="+weixin.getAppid()+"&grant_type=refresh_token&refresh_token="+token.getRefresh_token();
                token = JSON.parseObject(getJson(refresh),WxToken.class);
                userget ="https://api.weixin.qq.com/sns/userinfo?access_token="+token.getAccess_token()+"&openid="+token.getOpenid()+"&lang=zh_CN";
                wxUser = JSON.parseObject(getJson(userget), WxUser.class);
            }
            if (wxUser.getOpenid()==null){
                return "重新登录";
            }
            vgearUserMapper.insert(wxUser.getOpenid(),wxUser.getNickname(),wxUser.getSex(),wxUser.getProvince()
                    ,wxUser.getCity(),wxUser.getCountry(),wxUser.getHeadimgurl(),0);
            user = vgearUserMapper.findUserByToken(token.getOpenid());
        }
        map.addAttribute("ids", user.getId());
        map.addAttribute("headurl", user.getWxheadimgurl());
        map.addAttribute("nickname", user.getWxnickname());
        return "oindex";
    }

    private String getJson(String  url){
        String  json="";
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = httpClient.newCall(request).execute();
            json = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

}
