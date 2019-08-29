package com.example.demo.wxTemplateMsg;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注释到been 属性上面
 * Created by dyb on 2019/4/10.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WxEventMsgPram {
    String value() default "";
}
