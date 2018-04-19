package com.example.newdemo.controller;


import com.example.newdemo.Service.WxpayService;
import com.example.newdemo.util.MD5Util;
import com.example.newdemo.util.WXMyConfigUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/wxpay")
public class PayController {


    private WxpayService wxPayService;

    @Autowired
    public PayController(WxpayService wxPayService){
        this.wxPayService = wxPayService;
    }

    @RequestMapping(value = "/pay", method = {RequestMethod.GET, RequestMethod.POST})
    public String orderPay(/*@RequestParam(required = true,value = "user_id")String user_id,
                           @RequestParam(required = true,value = "coupon_id")String coupon_id,
            @RequestParam(required = true,value = "out_trade_no")String out_trade_no,
                         @RequestParam(required = true,value = "total_fee")String total_fee,*/
            HttpServletRequest req, HttpServletResponse response) throws Exception {
        System.err.println("进入微信支付申请");
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");//可以方便地修改日期格式
        String hehe = dateFormat.format(now);

        String out_trade_no=hehe+"wxpay";  //777777 需要前端给的参数
        String total_fee="1";              //7777777  微信支付钱的单位为分
        String user_id="1";               //77777
        String coupon_id="7";               //777777

        String attach=user_id+","+coupon_id;
        WXMyConfigUtil config = new WXMyConfigUtil();
//        String spbill_create_ip = GetIPAddrUtil.getIpAddr(req);
        String spbill_create_ip="10.4.21.78";
        System.err.println(spbill_create_ip);

        Map<String,String> result = wxPayService.dounifiedOrder(attach,out_trade_no,total_fee,spbill_create_ip,1);
        String nonce_str = (String)result.get("nonce_str");
        String prepay_id = (String)result.get("prepay_id");
        Long time =System.currentTimeMillis()/1000;
        String timestamp=time.toString();

        //签名生成算法
        MD5Util md5Util = new MD5Util();
        Map<String,String> map = new HashMap<>();
        map.put("appid",config.getAppID());
        map.put("partnerid",config.getMchID());
        map.put("package","Sign=WXPay");
         map.put("noncestr",nonce_str);
        map.put("timestamp",timestamp);
        map.put("prepayid",prepay_id);
        String sign = md5Util.getSign(map);

        String resultString="{\"appid\":\""+config.getAppID()+"\",\"partnerid\":\""+config.getMchID()+"\",\"package\":\"Sign=WXPay\"," +
                "\"noncestr\":\""+nonce_str+"\",\"timestamp\":"+timestamp+"," +
                "\"prepayid\":\""+prepay_id+"\",\"sign\":\""+sign+"\"}";
        System.err.println(resultString);

        return resultString;    //给前端app返回此字符串，再调用前端的微信sdk引起微信支付

    }


    /**
     * 订单支付异步通知
     */
    @ApiOperation(value = "手机订单支付完成后回调")
    @RequestMapping(value = "/notify",method = {RequestMethod.GET, RequestMethod.POST})
    public String WXPayBack(HttpServletRequest request,HttpServletResponse response){
        String resXml="";
        System.err.println("进入异步通知");
        try{
            //
            InputStream is = request.getInputStream();
            //将InputStream转换成String
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            resXml=sb.toString();
            System.err.println(resXml);
            String result = wxPayService.payBack(resXml);
//            return "<xml><return_code><![CDATA[SUCCESS]]></return_code> <return_msg><![CDATA[OK]]></return_msg></xml>";
            return result;
        }catch (Exception e){
            System.err.println("手机支付回调通知失败:"+e.getMessage());
//            log.error("手机支付回调通知失败",e);
            String result = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            return result;
        }
    }

}
