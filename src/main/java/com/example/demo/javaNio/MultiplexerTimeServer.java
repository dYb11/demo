package com.example.demo.javaNio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class MultiplexerTimeServer implements Runnable{

    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    private volatile boolean stop;


    public MultiplexerTimeServer(int port) {
        try{
            selector=Selector.open();
            serverSocketChannel=ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port),1024);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("the timeServer is start in port:"+port);

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





                    }
                }
            }catch (IOException e){

            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException{

    }
}
