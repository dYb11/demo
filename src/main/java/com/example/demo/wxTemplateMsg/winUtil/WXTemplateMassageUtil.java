package com.example.demo.wxTemplateMsg.winUtil;


import com.example.demo.wxTemplateMsg.been.WxAccountA;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

/**
 * 模板消息工具类
 */
@Component
public class WXTemplateMassageUtil {
    private static Logger logger = LoggerFactory.getLogger(WXTemplateMassageUtil.class);
    @Autowired
    JsapiTicket jsapiTicket;

    @Autowired
    WxAccountA wxAccountKk;

    public  boolean sendMassage(String Template){

        String accessToken = jsapiTicket.GetAccessToken(wxAccountKk);
        //HttpClientUtil.get("https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token="+accessToken);//获取行业

        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date=format.format(System.currentTimeMillis());
        Template=Template.replace("date",date);

        String result = HttpClientUtil.postJson("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken, Template);
        JSONObject json= JSONObject.fromObject(result);
        if("ok".equals(json.getString("errmsg"))){
            return true;
        }else {
            return false;
        }
    }

}
