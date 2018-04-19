package com.example.newdemo.util;

import com.github.wxpay.sdk.WXPayConfig;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

public class WXMyConfigUtil implements WXPayConfig {
    private byte[] certData;

    public WXMyConfigUtil() throws Exception{
        String certPath = "apiclient_cert.p12";//从微信商户平台下载的安全证书存放的目录
        File file = new File(certPath);
        InputStream certStream = org.springframework.util.ClassUtils.class.getClassLoader().getResourceAsStream("sensi_words.txt");
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }

    //填写你的appid
    @Override
    public String getAppID() {
        return "wx697a72ded07963a4";
    }

    //填写商户id
    @Override
    public String getMchID() {
        return "1501878081";
    }

    //填写api密钥
    @Override
    public String getKey() {
        return "vgearlive1111vgearlive1111vgearlive1";
    }

    @Override
    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }//implements WXPayConfig{

}
