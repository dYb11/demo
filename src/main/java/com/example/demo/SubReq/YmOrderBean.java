package com.example.demo.SubReq;

import java.io.Serializable;
import java.math.BigDecimal;

public class YmOrderBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String product;//产品名称
    private String hospitalName;//医院名称
    private int num;//手术时间



    public String getProduct() {
        return product;
    }

    public YmOrderBean(String product, String hospitalName, int num) {
        this.product = product;
        this.hospitalName = hospitalName;
        this.num = num;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "YmOrderBean{" +
                "product='" + product + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                ", num='" + num + '\'' +
                '}';
    }
}
