package com.example.demo.test;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Test implements Runnable{

    private static ConcurrentHashMap<String, Integer> concurrentHashMap=new ConcurrentHashMap();
    //private static HashMap<String, Integer> concurrentHashMap=new HashMap();

    public static void main(String[] args) {

        /*concurrentHashMap.put("a",0);
        for(int i=0;i<10000;i++){
            Test t=new Test();
            new Thread(t,String.valueOf(i)).start();
        }*/

        ByteBuffer byteBuffer=ByteBuffer.allocate(88);
        String val="醴龠";
        byteBuffer.put(val.getBytes());
        System.out.println(byteBuffer.remaining());

        byteBuffer.flip();
        byteBuffer.mark();
        byte[] varr=new byte[byteBuffer.remaining()];
        System.out.println(byteBuffer.remaining());
        byteBuffer.get(varr);
        String dv=new String(varr);

        System.out.println(dv);
        byteBuffer.reset();
        varr=new byte[byteBuffer.remaining()];
        System.out.println(byteBuffer.remaining());
        byteBuffer.get(varr);
        dv=new String(varr);

        System.out.println(dv);

    }
    // 在多线程间共享的对象上使用wait
    private String[] shareObj = { "true" };
    @Override
    public void run() {

        synchronized (shareObj) {
            if(Thread.currentThread().getName().equals("9999")){
                System.out.println("?线程名称："+Thread.currentThread().getName());
                shareObj.notify();
            }else {

                try {
                    System.out.println("线程名称："+Thread.currentThread().getName());
                    shareObj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


        up();
        System.out.println(concurrentHashMap.get("a"));
    }

    private void up(){
        int b=concurrentHashMap.get("a");
        System.out.println("线程名称："+Thread.currentThread().getName()+":"+ b);
        if(!concurrentHashMap.replace("a",b,b+1)){
            up();
        }
    }
}
