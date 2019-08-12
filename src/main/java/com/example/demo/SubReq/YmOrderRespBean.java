package com.example.demo.SubReq;

import java.io.Serializable;
import java.math.BigDecimal;

public class YmOrderRespBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int num;//产品名称
    private String res;//结果

    public YmOrderRespBean(int num, String res) {
        this.num = num;
        this.res = res;
    }

    public YmOrderRespBean(){

    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    @Override
    public String toString() {
        return "YmOrderRespBean{" +
                "product='" + num + '\'' +
                ", res='" + res + '\'' +
                '}';
    }
}
