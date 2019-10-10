package com.example.demo.nettyProprietaryProtocol;

import java.util.HashMap;
import java.util.Map;

public class Header {
    private int crcCode =0xabef0101;
    /*      1	Netty消息的校验码，它由三部分组成:
            2	1) 0xABEF:固定值，表明该消息是Netty协议消息，2个字节:
            3	2)主版本号: 1~255， 1 个字节:
            4	3)次版本号: 1~255， 1 个字节。
            5	creCode = 0xABEF+主版本号+次版本号*/
    private int length;//消息长度
    private long sessionId;//回话ID
    private byte type;//消息类型
    /*      	0:业务请求消息:
            	1:业务响应消息:
            	2:业务ONE WAY消息(既是请求又是响应消息);
            	3:握手请求消息:
            	4:握手应答消息:
            	5:心跳请求消息:
            	6:心跳应答消息。*/
    private byte priority;//消息优先级

    private Map<String,Object> attachment=new HashMap<>();

    public int getCrcCode() {
        return crcCode;
    }

    public void setCrcCode(int crcCode) {
        this.crcCode = crcCode;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getPriority() {
        return priority;
    }

    public void setPriority(byte priority) {
        this.priority = priority;
    }

    public Map<String, Object> getAttachment() {
        return attachment;
    }

    public void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }
}
