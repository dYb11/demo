package com.example.demo.wxTemplateMsg.winUtil;

public class MyIWXPayDomain implements IWXPayDomain {
    @Override
    public void report(String domain, long elapsedTimeMillis, Exception ex) {

    }

    @Override
    public DomainInfo getDomain(WXPayConfig config) {
        return new DomainInfo(WXPayConstants.DOMAIN_API,true);
    }
}
