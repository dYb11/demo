package com.example.demo.nettyProprietaryProtocol;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ScheduledFuture;

/**
 * 心跳检测》客户端
 */
public class HeartBeatReqHandler extends ChannelHandlerAdapter {

    private volatile ScheduledFuture<?> heartBeat;

    //@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message= (NettyMessage) msg;

        //如果是握手成功，主动发送心跳消息
        if(message.getHeader()!=null && message.getHeader().getType() == MessageType.LOGIN_RESP.value()){
            /*heartBeat=ctx.executor().scheduleAtFixedRate(
                    new HeartBeatReqHandler().Hea
            )*/
        }else
            ctx.fireChannelRead(msg);//认证成功调用下一个读取
    }



    private NettyMessage buildHeatBeat(){
        NettyMessage message=new NettyMessage();
        Header header=new Header();
        header.setType(MessageType.HEARTBEAT_REQ.value());
        message.setHeader(header);
        return message;
    }


    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) throws Exception{
        ctx.fireExceptionCaught(cause);
    }


}
