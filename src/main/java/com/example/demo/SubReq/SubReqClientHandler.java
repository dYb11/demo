package com.example.demo.SubReq;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.logging.Logger;

public class SubReqClientHandler extends ChannelHandlerAdapter {

    private  static final Logger logger=Logger.getLogger(SubReqClientHandler.class.getName());

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        for(int i=0;i<10;i++){
            ctx.write(new YmOrderBean("cs","csyy",i));
        }

        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        YmOrderRespBean body = (YmOrderRespBean) msg;
        System.out.println("now is:"+body.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warning(cause.getMessage());
        ctx.close();
    }
}
