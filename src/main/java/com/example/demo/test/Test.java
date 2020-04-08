package com.example.demo.test;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Test implements Runnable{

    //private static ConcurrentHashMap<String, Integer> concurrentHashMap=new ConcurrentHashMap();
    private static HashMap<String, Integer> concurrentHashMap=new HashMap();
    // 在多线程间共享的对象上使用wait
    private static String[] shareObj = { "true" };

    public Test(String[] shareObj) {
        shareObj=shareObj;

    }

    public static void main(String[] args) {

        buffTest();


    }

    /**
     * 缓冲区测试
     */
    public static void buffTest(){
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
        int a=0;

        //代码块退出
        /*label1098:
        while (a<10){
            for (int i = 0; i <= 10; i++) {
                System.out.println("i=" + i);
                if (i == 5) {
                    break label1098;
                }
            }
            System.out.println("++++++++++++++++++++++");
            a++;
        }*/
    }

    /**
     * 线程测试
     */
    public void threadTest(){
        concurrentHashMap.put("a",0);
        for(int i=0;i<10000;i++){
            Test t=new Test(shareObj);
            new Thread(t,String.valueOf(i)).start();
        }

        try {

            Thread.sleep(4000);    //延时2秒
            System.out.println("结果："+concurrentHashMap.get("a"));
            System.out.println(Thread.activeCount());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        synchronized (shareObj) {
            if(Thread.currentThread().getName().equals("9999")){
                System.out.println("?线程名称："+Thread.currentThread().getName());

            }else {

                try {
                    System.out.println("等待线程名称："+Thread.currentThread().getName());
                    shareObj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        synchronized (shareObj) {
            shareObj.notifyAll();
        }
        up();
    }
    final ReadWriteLock lock = new ReentrantReadWriteLock();
    private synchronized void up(){
        lock.writeLock().lock();
        lock.readLock().lock();
        try{
            int b=concurrentHashMap.get("a");
            System.out.println("线程名称："+Thread.currentThread().getName()+":"+ b);
            if(!concurrentHashMap.replace("a",b,b+1)){
                System.out.println("插入失败");
                up();
            }
        }finally {
            lock.writeLock().unlock();
            lock.readLock().unlock();
        }

    }
}
