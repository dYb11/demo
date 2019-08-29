package com.example.demo.wxTemplateMsg.winUtil;

import com.example.demo.wxTemplateMsg.been.WxAccount;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


/**
 *JsapiTicket
 *微信js sdk使用的签名类
 */
@Component
public class JsapiTicket {
    private static Logger logger = LoggerFactory.getLogger(JsapiTicket.class);


    ConcurrentHashMap CacheUtils=new ConcurrentHashMap();

    /**
     * 获取获取ticket
     *
     * @return
     */
    public Map getData(WxAccount wxAccount) {

        String access_token = GetAccessToken(wxAccount);
        String xml = HttpClientUtil.get("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi");
        JSONObject jsonMap = JSONObject.fromObject(xml);
        logger.debug("请求ticket："+jsonMap);
        return jsonMap;
    }

    /**
     * 获取js使用的access_token
     *
     * @return
     */
    public String GetAccessToken(WxAccount wxAccount) {
        int Date = (int) System.currentTimeMillis();
        String access_token=null;
        if (CacheUtils.get("accessToken") == null || Integer.parseInt((String)((Map)CacheUtils.get("accessToken")).get("gainTime")) + 7100 < Date){
            String xml = HttpClientUtil.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + wxAccount.getAppids() + "&secret=" + wxAccount.getSecret());

            JSONObject jsonMap = JSONObject.fromObject(xml);
            Map<String, String> map = new HashMap<String, String>();
            Iterator<String> it = jsonMap.keys();
            while (it.hasNext()) {
                String key = (String) it.next();
                String u = jsonMap.get(key).toString();
                map.put(key, u);
            }
             access_token= map.get("access_token");
            map.put("gainTime", String.valueOf(Date));

            logger.debug("请求AccessToken："+access_token+"获得时间："+Date);
            CacheUtils.put("accessToken",map);

        }else {
            Map map=(Map) CacheUtils.get("accessToken");
            access_token= (String) map.get("access_token");
        }
        return access_token;
    }

    /**
     * 检查ticket是否有效，默认有效时间为7200
     * 并生成签名
     */
    public void examineTicket(HttpServletRequest request,WxAccount wxAccount) {
        int Date = (int) System.currentTimeMillis();
        if (CacheUtils.get("jsapiTicket") == null || Integer.parseInt((String) ((Map)CacheUtils.get("jsapiTicket")).get("gainTime")) + 7100*1000 < Date) {
            Map jsonMap = getData(wxAccount);
            String jsapi_ticket = (String) jsonMap.get("ticket");

            Map setData = new HashMap();
            setData.put("jsapi_ticket", jsapi_ticket);
            setData.put("gainTime", Date);

            CacheUtils.put("jsapiTicket",setData);
            setSignature(request,jsapi_ticket);
        }
    }

    /**
     * 检查ticket是否有效，默认有效时间为7200
     * 不生成签名
     */
    public Map examineTicket(WxAccount wxAccount) {
        Map setData = new HashMap();
        int Date = (int) System.currentTimeMillis();
        if (CacheUtils.get("jsapiTicket") == null || (int)((Map)CacheUtils.get("jsapiTicket")).get("gainTime") + 7100*1000 < Date) {
            Map jsonMap = getData(wxAccount);
            String jsapi_ticket = (String) jsonMap.get("ticket");

            setData.put("jsapi_ticket", jsapi_ticket);
            setData.put("gainTime", Date);
            CacheUtils.put("jsapiTicket",setData);
            return setData;
        }else {
            return (Map)CacheUtils.get("jsapiTicket");
        }
    }

    /**
     * 生成签名，需要时间戳
     * @param request
     * @param jsapi_ticket
     * @param timestamp
     * @return
     */
    public static Map setSignature(HttpServletRequest request, String jsapi_ticket, String timestamp) {
        //获取签名signature
        String noncestr = UUID.randomUUID().toString();
        String url = String.valueOf(request.getRequestURL());
        if (request.getQueryString() != null) {
            url = url + "?" + String.valueOf(request.getQueryString());
        }
        String str = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + noncestr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        //sha1加密
        String signature = SHA1.SetSHA1(str);
        Map map=new HashMap();
        map.put("signature", signature);//签名
        map.put("timestamp", timestamp);//时间戳
        map.put("noncestr", noncestr);//签名中使用的随机数
        return map;
    }

    /**
     * 生成签名，不需要时间戳
     * @param request
     * @param jsapi_ticket
     * @return
     */
    public static Map setSignature(HttpServletRequest request, String jsapi_ticket) {
        //获取签名signature
        String noncestr = UUID.randomUUID().toString();
        String timestamp = Long.toString(System.currentTimeMillis());
        String url = String.valueOf(request.getRequestURL());
        if (request.getQueryString() != null) {
            url = url + "?" + String.valueOf(request.getQueryString());
        }
        String str = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + noncestr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        //sha1加密
        String signature = SHA1.SetSHA1(str);
        Map map=new HashMap();
        map.put("signature", signature);
        map.put("timestamp", timestamp);
        map.put("noncestr", noncestr);
        return map;
    }

    /**
     * 生成签名，不需要时间戳
     * @param request
     * @param url   当前路径，需要所有的参数
     * @param jsapi_ticket
     * @return
     */
    public static Map setSignature(String url, HttpServletRequest request, String jsapi_ticket) {
        //获取签名signature
        String noncestr = UUID.randomUUID().toString();
        String timestamp = Long.toString(System.currentTimeMillis());
        String str = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + noncestr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        //sha1加密
        String signature = SHA1.SetSHA1(str);
        Map map=new HashMap();
        map.put("signature", signature);
        map.put("timestamp", timestamp);
        map.put("noncestr", noncestr);
        return map;
    }
}
