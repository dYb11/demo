package com.example.demo.nettyProprietaryProtocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class Test {
    public static void main(String[] args) {
        ByteBuf sendBuf = Unpooled.buffer();
        sendBuf.writeInt(1);
        sendBuf.writeInt(2);
        sendBuf.writeLong(3);

        System.out.println(sendBuf.readInt());
        System.out.println(sendBuf.readInt());
        System.out.println(sendBuf.readInt());
        System.out.println(sendBuf.readInt());
    }
}
