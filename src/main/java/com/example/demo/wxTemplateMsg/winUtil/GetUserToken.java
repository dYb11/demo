package com.example.demo.wxTemplateMsg.winUtil;

import com.thinkgem.jeesite.modules.dh.weixin.been.WxAccount;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取用户的access_token与js的不同
 * 用来获取用户信息
 */
@Component
public class GetUserToken {


    public String getToken(String code,WxAccount wxAccount){
        Map<String,String> data = new HashMap<>();

        data.put("appid", wxAccount.getAppids());
        data.put("secret",wxAccount.getSecret());
        data.put("code",code);
        data.put("grant_type","authorization_code");

        String resultStr= HttpClientUtil.post("https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code", data);
        return resultStr;
    }

    /**
     * 获取用户信息
     * @param openId
     * @param userAccessToken
     * @return
     */
    public static String getUserData(String openId,String userAccessToken){
        String res=null;
        String url="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        url=url.replace("ACCESS_TOKEN",userAccessToken).replace("OPENID",openId);
        try {
            res=new String(HttpClientUtil.get(url).getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return res;
    }
}
