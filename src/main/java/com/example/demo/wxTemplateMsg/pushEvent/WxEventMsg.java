package com.example.demo.wxTemplateMsg.pushEvent;

import com.example.demo.wxTemplateMsg.FqMember;
import com.example.demo.wxTemplateMsg.ScanAllAnnoUtil;
import com.example.demo.wxTemplateMsg.TemplateUtil;
import com.example.demo.wxTemplateMsg.WxEventMsgPram;
import com.example.demo.wxTemplateMsg.been.WxTemplate;
import com.example.demo.wxTemplateMsg.been.WxUserEvent;
import com.example.demo.wxTemplateMsg.winUtil.WXTemplateMassageUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存事件
 */
@Component
public class WxEventMsg implements
        InitializingBean {


    private static ConcurrentHashMap CacheUtils=new ConcurrentHashMap();
    @Autowired
    TemplateUtil templateUtil;

    @Autowired
    WXTemplateMassageUtil wxTemplateMassageUtil;

    /**
     * 注册事件
     * @param event 事件名称
     */
    public synchronized static void registerEvent (String event){
        if(CacheUtils.get("event")==null){
            List list=new ArrayList();
            list.add("模板列表");
            try {
                scanWxEventMsgPramSpring();
            } catch (IOException e) {
                e.printStackTrace();
            }
            CacheUtils.put("event",list);
        }

        List list= (List) CacheUtils.get("event");
        list.add(event);
        CacheUtils.put("event",list);
        //注册事件到缓存

    }

    /**
     * 该been加载完成后执行
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception{}

    /**
     * 事件触发后调用
     */
    @Async
    public void start(){}

    /**
     * 发送
     * @param clz   javabeen 的class对象
     * @param data		该对象的实例
     * @param eventName	  事件名称
     * @return
     * @throws IllegalAccessException
     */
    public void send(Class clz,Object data,String eventName) throws IllegalAccessException {

        //触发事件时事件子类调用发送

        WxTemplate wxTemplate=new WxTemplate();
        wxTemplate.setEventName(eventName);
        List<WxTemplate> wxTemplates=null;//获取数据库中与该事件绑定的模板
        WxUserEvent wxUserEvent=new WxUserEvent();
        for(WxTemplate wxTemplate1:wxTemplates){
            WxUserEvent wxUserEvent2=null;
            switch (wxTemplate1.getAimUser()){
                case "2"://用户
                    String userId = templateUtil.getClsData(clz, data, "FqMember.id");
                    if(!userId.contains("FqMember.id")){
                        wxUserEvent2=new WxUserEvent();

                    }
            }

            wxUserEvent.setWxTemplateId(wxTemplate1.getId());
            List<WxUserEvent> wxUserEvents=null;//获取数据库中与该模板绑定的用户
            wxUserEvents.add(wxUserEvent2);
            for(WxUserEvent wxUserEvent1:wxUserEvents){
                String template = templateUtil.replaceTempateData(clz, data, wxTemplate1.getTemplate());
                template = templateUtil.replaceTempateData(WxUserEvent.class,wxUserEvent1,template);

                wxTemplateMassageUtil.sendMassage(template);//发送推送
            }
        }
    }

    /**
     * 测试发送
     * @param clz   javabeen 的class对象
     * @param data		该对象的实例
     * @param wxUserEventId	  事件名称

     * @return
     * @throws IllegalAccessException
     */
    public void sendTest(Class clz,Object data,String wxUserEventId) throws IllegalAccessException {


    }

    public static void scanWxEventMsgPramSpring() throws IOException {

        Map map =new HashMap();
        Set<BeanDefinition> candidates = ScanAllAnnoUtil.springGetAnn();
        for(BeanDefinition beanDefinition : candidates) {
            try{
                String classname=beanDefinition.getBeanClassName();
                Class<?> e = Class.forName(classname);
                for(Field field:e.getDeclaredFields()){
                    WxEventMsgPram c = field.getAnnotation(WxEventMsgPram.class);
                    if(c!=null){
                        map.put(TemplateUtil.getClsName(e)+"."+field.getName(),c.value());

                    }
                }

            }catch (Error err){

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        //CacheUtils.put("wxEventMsgPram", JSONArray.toJSON(map));//缓存项目中有该注解的字段
    }

    @Deprecated
    public static void scanWxEventMsgPram(){
        List list=new ArrayList();
        List<String> a = ScanAllAnnoUtil.myGetAnn();

        for(String url:a){
            try {
                Class clz=Class.forName(url);
                for(Field field:clz.getDeclaredFields()){
                    Map map =new HashMap();
                    WxEventMsgPram c = field.getAnnotation(WxEventMsgPram.class);
                    try{
                        map.put(field.getName(),c.value());
                        list.add(map);
                    }catch (NullPointerException e){
                        continue;
                    }
                }
            }catch (Error e){
            }catch (Exception e) {
            }
        }

        //CacheUtils.put("wxEventMsgPram",list);
    }





}
