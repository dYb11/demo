package com.example.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.logging.Logger;

public class TimeServerHandler extends ChannelHandlerAdapter{
    private  static final Logger logger=Logger.getLogger(TimeServerHandler.class.getName());

    private int count;

    //@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //ByteBuf buf= (ByteBuf) msg;
        //byte[] req = new byte[buf.readableBytes()];

        //buf.readBytes(req);

        //String body=new String (req,"UTF-8");
        String body= (String) msg;
        System.out.println("1111:"+body+ ++count);

        String curentTime="qq".equalsIgnoreCase(body)?new java.util.Date(System.currentTimeMillis()).toString()+"+count:"+count+"":"bad_qq";
        curentTime=curentTime+System.getProperty("line.separator");
        ByteBuf resp= Unpooled.copiedBuffer(curentTime.getBytes());
        ctx.write(resp);


    }

    //@Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warning(cause.toString());
        ctx.close();
    }
}
