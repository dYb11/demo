package com.example.demo.nettyProprietaryProtocol;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 登录握手》客户端
 */
public class LoginAuthReqHandler extends ChannelHandlerAdapter {

    //@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        ctx.writeAndFlush(buildLoginReq());
    }

    //@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message= (NettyMessage) msg;

        //如果是握手应答消息，需要判断是否认证成功
        if(message.getHeader()!=null && message.getHeader().getType() == MessageType.LOGIN_RESP.value()){
            byte loginResult= (byte) message.getBody();
            if(loginResult!=0){
                ctx.close();//握手失败
            }else {
                System.out.println("Login is ok:"+message);
                ctx.fireChannelRead(msg);
            }
        }else
            ctx.fireChannelRead(msg);//认证成功调用下一个读取
    }

    private NettyMessage buildLoginReq(){
        NettyMessage message=new NettyMessage();
        Header header=new Header();
        header.setType(MessageType.LOGIN_REQ.value());
        message.setHeader(header);
        return message;
    }

    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) throws Exception{
        ctx.fireExceptionCaught(cause);
    }


}
