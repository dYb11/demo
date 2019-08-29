package com.example.demo.wxTemplateMsg.been;

/**
 * 获取微信公众号账号
 */


public interface WxAccount {


    public String getAppids();

    public String getSecret() ;

    public String getDomain();

    public String getMchID() ;
    public String getKey();

    
}
