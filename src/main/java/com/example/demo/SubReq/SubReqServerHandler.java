package com.example.demo.SubReq;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandlerInvoker;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.logging.Logger;

public class SubReqServerHandler extends ChannelHandlerAdapter {
    private  static final Logger logger=Logger.getLogger(SubReqServerHandler.class.getName());

    private int count;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


        YmOrderBean body= (YmOrderBean) msg;
        System.out.println("1111:"+body+ ++count);

        ctx.writeAndFlush(new YmOrderRespBean(body.getNum(),"保存成功"));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warning(cause.toString());
        ctx.close();
    }

}
