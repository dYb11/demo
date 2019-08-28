package com.example.demo.SubReq;

import com.example.demo.TimeServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.MultithreadEventExecutorGroup;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.chmv8.ConcurrentHashMapV8;
import sun.misc.Unsafe;
import sun.nio.ch.SelectorImpl;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class SubReqServer {

    protected void bind(int port) throws Exception {
        EventLoopGroup bossGroup=new NioEventLoopGroup();//接收客户端连接
        EventLoopGroup workerGroup=new NioEventLoopGroup();//socket读写
        try{
            ServerBootstrap b=new ServerBootstrap();

            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,100)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ObjectDecoder(1024*1024, ClassResolvers
                                    .weakCachingConcurrentResolver(this.getClass().getClassLoader())//序列化解码
                            ));
                            socketChannel.pipeline().addLast(new ObjectEncoder());
                            socketChannel.pipeline().addLast(new SubReqServerHandler());
                        }
                    });

            ChannelFuture f=b.bind(port).sync();
            f.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    /*public static void main(String[] args) throws Exception {
        int port=8080;

        if(args!=null && args.length>0){
            try{
                port=Integer.valueOf(args[0]);
            }catch (NumberFormatException e){

            }
        }
        new SubReqServer().bind(port);
    }*/


    /*public static void main(String[] args) {
        *//*List list=new ArrayList();
        List list2=new ArrayList();
        list2.add("a");
        list.add("a");
        list.add("b");
        list.add("c");
        list2.add("c");
        list.add("d");
        list2.add("g");
        list2.add("h");*//*

        List<YmOrderRespBean> list=new ArrayList<>();
        for(int i=0;i<5;i++){
            YmOrderRespBean ymOrderRespBean=new YmOrderRespBean();
            ymOrderRespBean.setNum((int) (Math.random()*100));
            list.add(ymOrderRespBean);
        }
        list.sort(Comparator .comparingInt(YmOrderRespBean::getNum));
        //list.sort( Comparable.comparingInt(YmOrderRespBean::getNum));
        System.out.println(Math.random()*100);


        new BigDecimal("1");

        Map map=new HashMap();
        System.out.println(map.get("1"));

        LinkedHashMap linkedHashMap=new LinkedHashMap();
        linkedHashMap.put("a","a");
        linkedHashMap.put("b","b");
        linkedHashMap.put("c","c");

        ConcurrentHashMap concurrentHashMap=new ConcurrentHashMap();
        concurrentHashMap.put("a","a");
        concurrentHashMap.put("b","b");
        concurrentHashMap.put("c","c");


        Field theUnsafe = null;
        try {
            theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        theUnsafe.setAccessible(true);
        try {
            Unsafe U = (Unsafe) theUnsafe.get(null);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }*/

    // Unsafe mechanics
    /*private static final sun.misc.Unsafe U;

    static {
        try {
            U = sun.misc.Unsafe.getUnsafe();
        } catch (Exception e) {
            throw new Error(e);
        }
    }*/

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {

        System.setSecurityManager(new SecurityManager());
        SecurityManager q = System.getSecurityManager();


        Class h=Class.forName("com.example.demo.TimeServer");
        Object a = h.newInstance();
        System.out.println(1);

    }

    SubReqServer(){
        String pk = getClass().getName().substring(getClass().getPackage().getName().length() + 1);
        System.out.println(Character.toLowerCase(pk.charAt(0)) + pk.substring(1));
        System.out.println(getClass().getName());
    }

}
