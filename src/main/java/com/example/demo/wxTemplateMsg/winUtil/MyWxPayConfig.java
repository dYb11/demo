package com.example.demo.wxTemplateMsg.winUtil;


import com.example.demo.wxTemplateMsg.been.WxAccountA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 微信支付参数初始化
 */
@Component
public class MyWxPayConfig extends WXPayConfig {


    @Autowired
    WxAccountA wxAccount;

    private byte[] certData;

/*    public MyWxPayConfig() throws Exception {

        String certPath = GlobalVariable.credential;//从微信商户平台下载的安全证书存放的目录

        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }*/

    @Override
    String getAppID() {
        return wxAccount.getAppids();
    }

    @Override
    public String getMchID() {
        return wxAccount.getMchID();
    }

    @Override
    public String getKey() {
        return wxAccount.getKey();
    }

    @Override
    InputStream getCertStream() {
        ByteArrayInputStream certBis;
        certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    @Override
    IWXPayDomain getWXPayDomain() {
        return new MyIWXPayDomain();
    }
}
