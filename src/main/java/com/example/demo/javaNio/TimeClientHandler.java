package com.example.demo.javaNio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 原生nio
 */
public class TimeClientHandler implements Runnable{

    private Selector selector;

    private SocketChannel socketChannel;

    private volatile boolean stop;

    private String host;

    private int port;


    public TimeClientHandler(String host,int port) {

        this.host=host==null?"127.0.0.1":host;
        this.port=port;
        try{
            selector=Selector.open();
            socketChannel=SocketChannel.open();
            socketChannel.configureBlocking(false);

        }catch (IOException e){

            e.printStackTrace();
            System.exit(1);
        }
    }

    public void stop(){
        this.stop=true;
    }

    @Override
    public void run() {

        try{
            doConnect();
        }catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }

        while(!stop){
            try{
                selector.select(1000);
                Set<SelectionKey> selectionKeys=selector.selectedKeys();
                Iterator<SelectionKey> it=selectionKeys.iterator();
                SelectionKey key=null;
                while (it.hasNext()){
                    key=it.next();
                    it.remove();
                    try{
                        handleInput(key);
                    }catch (Exception e){
                        if(key != null){
                            key.cancel();
                            if(key.channel()!=null){
                                key.channel().close();
                            }
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if(selector!=null)
            try{
            selector.close();
            }catch (IOException e){
            e.printStackTrace();
            }

    }


    private void handleInput(SelectionKey key) throws IOException{
        if(key.isValid()){
                SocketChannel sc=(SocketChannel)key.channel();
            if(key.isConnectable()){
                if(sc.finishConnect()){
                    sc.register(selector,SelectionKey.OP_READ);
                    doWrite(sc);
                }

                ByteBuffer readBuffer= ByteBuffer.allocate(1024);
                int readBytes=sc.read(readBuffer);
                if(readBytes>0){
                    readBuffer.flip();
                    byte[] bytes=new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body=new String(bytes,"UTF-8");
                    System.out.println("the time server receive order:"+body);
                    String currentTime="Query Time order".equalsIgnoreCase(body) ? new java.util.Date(
                            System.currentTimeMillis()
                    ).toString():"bad order";

                    doWrite(sc,currentTime);
                }else if(readBytes<0){
                    key.cancel();
                    sc.close();
                }
            }else
                ;
        }

    }

    private void doConnect() throws IOException{
        if(socketChannel.connect(new InetSocketAddress(host,port))){
            socketChannel.register(selector,SelectionKey.OP_READ);
            doWrite(socketChannel);
        }else
            socketChannel.register(selector,SelectionKey.OP_CONNECT);
    }

    private void doWrite(SocketChannel channel) throws IOException{
        byte[] req="Query Time order".getBytes();

        ByteBuffer writeBuffer=ByteBuffer.allocate(req.length);
        writeBuffer.put(req);
        writeBuffer.flip();
        channel.write(writeBuffer);
        if(!writeBuffer.hasRemaining())
            System.out.println("Send order 2 server succeed");

    }
}
