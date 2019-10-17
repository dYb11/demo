package com.example.demo.nettyProprietaryProtocol;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import sun.nio.ch.Net;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 登录握手>服务端
 */
public class LoginAuthRespHandler extends ChannelHandlerAdapter {
    private String[] whiteList={"127.0.0.1","192.168.1.104"};
    private Map<String,Boolean> nodeCheck=new ConcurrentHashMap<>();


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message= (NettyMessage) msg;

        //如果是握手应答消息，需要判断是否认证成功
        if(message.getHeader()!=null && message.getHeader().getType() == MessageType.LOGIN_REQ.value()){
            String nodeIndex=ctx.channel().remoteAddress().toString();
            NettyMessage loginResp=null;

            if(nodeCheck.containsKey(nodeIndex)){
                loginResp=buildResponse((byte) -1);
            }else {
                InetSocketAddress address= (InetSocketAddress) ctx.channel();
                String ip=address.getAddress().getHostAddress();
                boolean isOk=false;
                for(String WIP:whiteList){
                    if(WIP.equals(ip)){
                        isOk=true;
                        break;
                    }
                }
                loginResp=isOk?buildResponse((byte)0):buildResponse((byte) 0);
                if(isOk)
                    nodeCheck.put(nodeIndex,true);
            }

            System.out.println("the login response is :"+loginResp + "body ["+loginResp.getBody()+"]");
            ctx.writeAndFlush(loginResp);
        }else
            ctx.fireChannelRead(msg);//认证成功调用下一个读取
    }

    private NettyMessage buildResponse(byte result){
        NettyMessage message=new NettyMessage();
        Header header=new Header();
        header.setType(MessageType.LOGIN_RESP.value());
        message.setHeader(header);
        return message;
    }

    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) throws Exception{
        nodeCheck.remove(ctx.channel().remoteAddress().toString());
        ctx.close();
        ctx.fireExceptionCaught(cause);
    }


}
