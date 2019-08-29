package com.example.demo.wxTemplateMsg;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;

import javax.naming.Context;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ScanAllAnnoUtil {

    /**
     * spring扫描项目所有注解
     * @throws IOException
     */
    public static Set<BeanDefinition> springGetAnn() throws IOException {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();

        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        //这里特别注意一下类路径必须这样写
        //获取指定包下的所有类D:\work\XFenqi\src\main\webapp\WEB-INF\classes\com\thinkgem\jeesite\common\annotation
        Resource[] resources = resourcePatternResolver.getResources("classpath*:com\\thinkgem\\jeesite\\*");
        getResources(resources,candidates);

        return candidates;
        /*for(BeanDefinition beanDefinition : candidates) {
            try{
                String classname=beanDefinition.getBeanClassName();
                //扫描controller注解
                Controller c=Class.forName(classname).getAnnotation(Controller.class);
                //扫描Service注解
                Service s=Class.forName(classname).getAnnotation(Service.class);
                //扫描Component注解
                Component component=Class.forName(classname).getAnnotation(Component.class);
                if(c!=null ||s!=null ||component!=null){
                    System.out.println(classname);
                }
                Class<?> e = Class.forName(classname);
                for(Field field:e.getDeclaredFields()){
                    WxEventMsgPram c = field.getAnnotation(WxEventMsgPram.class);
                    if(c!=null){
                        System.out.println(c.value());
                        System.out.println(field.getName());
                    }
                }

            }catch (Error err){

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }*/
    }
    public static void getResources(Resource[] resources, Set<BeanDefinition> candidates) throws IOException {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        for(Resource resource:resources) {
            File a = resource.getFile();
            FileSystemResource b = (FileSystemResource) resource;
            String c = b.getPath();
            String d = c.substring(c.indexOf("com/"));
            if(a.isDirectory()){
                Resource[] resources1=resourcePatternResolver.getResources("classpath*:"+d+"\\*");
                getResources(resources1,candidates);
            }else {
                MetadataReaderFactory metadata=new SimpleMetadataReaderFactory();
                MetadataReader metadataReader=metadata.getMetadataReader(resource);
                ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader);
                sbd.setResource(resource);
                sbd.setSource(resource);
                candidates.add(sbd);
            }
        }
    }

    /**
     * 我的扫描项目所有注解
     * @throws IOException
     */
    public static List<String> myGetAnn() {
        List<String> a = getClassesList(getClassPath());
        return a;
        /*Map map =new HashMap();
        for(String url:a){
            try {
                Class clz=Class.forName(url);
                for(Field field:clz.getDeclaredFields()){
                    WxEventMsgPram c = field.getAnnotation(WxEventMsgPram.class);
                    try{
                        System.out.println(c.value());
                        System.out.println(field.getName());
                        map.put(c.value(),field.getName());
                    }catch (NullPointerException e){
                        continue;
                    }

                }

            }catch (Error e){

            }catch (Exception e) {

            }
        }*/

    }
    private static String getClassPath() {
        String url = null;
        try {
            url = URLDecoder.decode(Context.class.getResource("/").getPath(), String.valueOf(Charset.defaultCharset()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (url.startsWith("/")) {
            url = url.replaceFirst("/", "");
        }
        url = url.replaceAll("/", "\\\\");
        return url;
    }
    private static List<String> getClassesList(String url) {
        File file = new File(url);
        List<String> classes = getAllClass(file);
        for (int i = 0; i < classes.size(); i++) {
            classes.set(i, classes.get(i).replace(url, "").replace(".class", "").replace("\\", "."));
        }
        return classes;
    }


    private static List<String> getAllClass(File file) {
        List<String> ret = new ArrayList<>();
        if (file.isDirectory()) {
            File[] list = file.listFiles();
            for (File i : list) {
                List<String> j = getAllClass(i);
                ret.addAll(j);
            }
        } else {

            ret.add(file.getAbsolutePath());
        }
        return ret;
    }

    public static void main(String[] args) throws IOException {
        springGetAnn();
    }
}
