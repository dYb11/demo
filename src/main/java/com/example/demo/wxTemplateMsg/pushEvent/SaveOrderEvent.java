package com.example.demo.wxTemplateMsg.pushEvent;


import com.example.demo.wxTemplateMsg.ShOrders;
import com.example.demo.wxTemplateMsg.been.WxTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


/**
 * 订单提交事件
 */
@Component
public class SaveOrderEvent extends WxEventMsg {



    private static final String eventName="订单提交事件";

    @Override
    public void afterPropertiesSet() {
        registerEvent(eventName);
    }

    @Async
    public void start(String sn) throws IllegalAccessException {
        System.out.println("开始通知");
        WxTemplate wxTemplate=new WxTemplate();
        wxTemplate.setEventName(eventName);

        ShOrders shOrders=new ShOrders();

        send(ShOrders.class,shOrders,eventName);
    }

}
