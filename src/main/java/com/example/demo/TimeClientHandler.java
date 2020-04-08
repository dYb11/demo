package com.example.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.logging.Logger;

public class TimeClientHandler extends ChannelHandlerAdapter {

    private  static final Logger logger=Logger.getLogger(TimeClientHandler.class.getName());

    //private ;

    private byte[] req;

    public  TimeClientHandler(){
        req=("qq"+System.getProperty("line.separator")).getBytes();

    }

    //@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf firstmessage=null;
        for(int i=0;i<100;i++){
            //System.out.println(i);
            firstmessage= Unpooled.buffer(req.length);
            firstmessage.writeBytes(req);
            ctx.writeAndFlush(firstmessage);
        }

    }

    //@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /*ByteBuf buf= (ByteBuf) msg;

        byte[] req=new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body=new String(req,"UTF-8");*/
        String body= (String) msg;
        System.out.println("now is:"+body);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warning(cause.getMessage());
        ctx.close();
    }
}
