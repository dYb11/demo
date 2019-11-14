package com.example.demo.test;

import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ViewResolver;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class Test2{

    private static ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap();
    //private static HashMap<String, Integer> concurrentHashMap=new HashMap();

    @RequestMapping(value = "tt",method = RequestMethod.GET)
    @ResponseBody
    public void tt(String a,String b, HttpServletRequest request){
        request.setAttribute("wewe","121232");
        System.out.println(a);

    }
    @RequestMapping(value = "ff",method = RequestMethod.GET)
    @ResponseBody
    public void ff(String a, HttpServletRequest request){
        request.setAttribute("ffff","121232");
        System.out.println(a);

    }

 //   public static void main(String[] args) {
        //DispatcherServlet
//        HandlerAdapter
//        HandlerMapping
//        ViewResolver
  //  }

/*    public static void main(String[] args) {
        int num = 6;//局部变量
        Sum sum = value -> {
//            num = 8; 这里会编译出错
            return num + value;
        };
        sum.add(8);
    }

    *//**
     * 函数式接口
     *//*
    @FunctionalInterface
    interface Sum{
        int add(int value);
    }*/

    public static final ReflectionUtils.MethodFilter INIT_BINDER_METHODS = method ->
                    AnnotatedElementUtils.hasAnnotation(method, InitBinder.class);

   /* public static void main(String[] args) {
        Set<Method> a = MethodIntrospector.selectMethods(Test2.class, INIT_BINDER_METHODS);
        System.out.println(11);
    }
*/

   public static void a(String a){
       System.out.println(a);

   }

    public static void main(String[] args) {
        Class<Test2> a = Test2.class;
        Method[] b = a.getMethods();
        for(Method c:b){
            System.out.println(((AnnotatedElement)c).isAnnotationPresent(ResponseBody.class));//判断该方法上是否有此注解
        }

        Runnable runnable=()->{
                Test2.a("232");
        };

        runnable.run();
    }
}
