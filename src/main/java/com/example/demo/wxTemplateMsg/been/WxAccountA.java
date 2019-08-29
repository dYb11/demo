package com.example.demo.wxTemplateMsg.been;


import jdk.nashorn.internal.objects.Global;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 获取微信公众号账号
 */


@Component
@Scope("singleton")
public class WxAccountA implements WxAccount {

    public String getAppids() {
        return "";
    }

    public String getSecret() {
        return "";
    }


    public String getDomain() {
        return "";
    }

    public String getMchID() {
        return "";
    }

    public String getKey() {
        return "";
    }



}
