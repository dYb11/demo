package com.example.demo.nettyProprietaryProtocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.util.Recycler;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import sun.misc.Unsafe;
import sun.misc.VM;
import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Stack;

public class Test<T> {
    private static Unsafe UNSAFE = null;

    /*private static final sun.misc.Unsafe U = sun.misc.Unsafe.getUnsafe();*/

    static {
        Field unsafeField = null;//获取unsafe方法用于直接操作内存
        try {
            unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        unsafeField.setAccessible(true);
        try {
            UNSAFE = (Unsafe) unsafeField.get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws Exception {
        test1();
    }

    //java nio buff test(DirectByteBuffer在putint过程中对int的操作
    private static void test1() throws Exception {
        ByteBuffer sendBuf1 = ByteBuffer.allocateDirect(10);//获取直接内存buff：使用unsafe方法直接操作内存
        sendBuf1.putInt(1);

        Class<? extends ByteBuffer> cls = sendBuf1.getClass();
        Method a=cls.getMethod("address");
        a.setAccessible(true);
        long ret = (long) a.invoke(sendBuf1);//通过反射调用 address 方法获取该buffer的首地址

        byte q = UNSAFE.getByte(ret);
        byte w = UNSAFE.getByte(ret+1);
        byte e = UNSAFE.getByte(ret+2);
        byte r = UNSAFE.getByte(ret+3);
        System.out.println(11);

        long b = UNSAFE.allocateMemory(4);

        UNSAFE.putInt(b,Integer.reverseBytes(9));
        System.out.println(Integer.reverseBytes(UNSAFE.getInt(b)));

    }

    //检查机器使用的是大端储存，还是小端储存
   /* 在计算机系统中，规定：每个地址单元都会对应一个字节（8个bit），但是，在c语言中，
    除了有一个字节（8个bit）的char，也有两个字节（16个bit）的short，
    也有四个字节（32个bit）的long（在不同的编译器下可能不同）。对于16位或者32位的处理器，
    即就是大于8位的处理器，由于寄存器的宽度大于一个字节，
    那么就存在如何将一个多字节的变量的数据如何存放的问题——所以，就有了大小端之分。

    大端模式：是指数据的高字节保存在内存的低地址中，而数据的低字节保存在内存的高地址端。

    小端模式，是指数据的高字节保存在内存的高地址中，低位字节保存在在内存的低地址端。

    小端模式 ：强制转换数据不需要调整字节内容，1、2、4字节的存储方式一样。
    大端模式 ：符号位的判定固定为第一个字节，容易判断正负。
    */
    private static void test9() throws Exception{

        long a = UNSAFE.allocateMemory(8);
        try {
            UNSAFE.putLong(a, 0x0102030405060708L);
            //存放此long类型数据，实际存放占8个字节，01,02,03，04,05,06,07,08
            byte b = UNSAFE.getByte(a);
            //通过getByte方法获取刚才存放的long，取第一个字节
            //如果是大端，long类型顺序存放—》01,02,03,04,05,06,07,08  ，取第一位便是0x01
            //如果是小端，long类型顺序存放—》08,07,06,05,04,03,02,01  ，取第一位便是0x08
            switch (b) {
                case 0x01:
                    ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
                    break;
                case 0x08:
                    byteOrder = ByteOrder.LITTLE_ENDIAN;
                    break;
                default:
                    assert false;
                    byteOrder = null;
            }
        }catch (Exception e){

        }

    }

    //netty buffer pool test
    private static void test8(){
        PooledByteBufAllocator pooledByteBufAllocator=new PooledByteBufAllocator();
        ByteBuf buf=pooledByteBufAllocator.buffer(16);
        ByteBuf buf2=pooledByteBufAllocator.directBuffer(16);
        buf.writeInt(1);
        buf.writeInt(2);
        buf.writeInt(3);
        System.out.println(11);


    }

    //java nio buff test
    private static void test7(){
        ByteBuffer sendBuf = ByteBuffer.allocate(10);//获取堆内存buff：使用byte数组保存数据
        ByteBuffer sendBuf1 = ByteBuffer.allocateDirect(10);//获取直接内存buff：使用unsafe方法直接操作内存
        sendBuf1.putInt(1);
        sendBuf1.putInt(2);
        //sendBuf.putInt(3);
        sendBuf1.mark();
        sendBuf1.flip();//读取之前需要翻转buffer调整pos，lim,cap
        System.out.println(sendBuf1.getInt());
        sendBuf1.position(8);
        sendBuf1.limit(10);
        sendBuf1.flip();

        System.out.println(sendBuf1.getInt());
    }
    private static void test6(){
        ByteBuf sendBuf = Unpooled.buffer();//获取堆内存buff,不使用Buffer
        sendBuf.writeInt(1);
        sendBuf.writeInt(2);
        sendBuf.writeLong(3);
    }
    private static void test5(){
        boolean noUnsafe = SystemPropertyUtil.getBoolean("io.netty.noUnsafe", false);
        System.out.println(noUnsafe);
    }
    private static void test4(){
        System.out.println(System.getenv("LOCALAPPDATA"));
    }
    private static void test3() throws ClassNotFoundException {
        Class.forName("io.netty.buffer.ByteBuf", false, ClassLoader.getSystemClassLoader());
    }


    private static void test2() throws NoSuchFieldException, IllegalAccessException {
        ByteBuf sendBuf = Unpooled.directBuffer(10);//直接使用java Buffer的内存地址保存数据，
        sendBuf.writeInt(1);
        sendBuf.writeInt(2);
        sendBuf.writeLong(3);

        Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");//获取unsafe方法用于直接操作内存
        unsafeField.setAccessible(true);
        UNSAFE = (Unsafe) unsafeField.get(null);
        System.out.println(UNSAFE);
        Field addressField = Buffer.class.getDeclaredField("address");
        //ByteBuffer b=ByteBuffer.allocateDirect(255);
        ByteBuffer b=sendBuf.nioBuffer();
        //System.out.println(b.getInt());

        long aLong = UNSAFE.getLong(b, UNSAFE.objectFieldOffset(addressField));
        long aLong2 = PlatformDependent.directBufferAddress(b);

        System.out.println("----------"+aLong);
        System.out.println("----------"+aLong2);

        b.putInt(12132);

         UNSAFE.putInt(aLong+1, 12333);
        //UNSAFE.putInt(123,aLong+1, 123);
        System.out.println("----------");
        //System.out.println(sendBuf.readInt());
        //System.out.println(sendBuf.readInt());
        //sendBuf.discardReadBytes();
        System.out.println(UNSAFE.getInt(aLong+1));
    }

}
