package com.example.newdemo.Service;


import java.util.Map;

public interface WxPayServiceIntefer {
    Map<String, String> dounifiedOrder(String attach, String out_trade_no, String total_fee, String spbill_create_ip, int type) throws Exception;

    String payBack(String notifyData);
}
