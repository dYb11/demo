package com.example.demo.wxTemplateMsg;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

/**
 * 搜索class中对应该字符串的值/获取class对象指定字段值
 */
@Component
public class TemplateUtil {



    /**
     * 获取class属性，值
     * @param clz
     * @param data
     * @param name  been属性名，例：ShOrders.id
     * @return
     * @throws IllegalAccessException
     */
    public String getClsData(Object data, String name) throws IllegalAccessException {
        return replaceData(data.getClass(),data,"${"+name+"}");
    }

    /**
     * 替换模板中参数
     * @param clz   javabeen 的class对象
     * @param data		该对象的实例
     * @param tempate	模板
     * @return
     * @throws IllegalAccessException
     */
    public String replaceTempateData(Object data, String tempate) throws IllegalAccessException {
        return replaceData(data.getClass(),data,tempate);
    }

    private String replaceData(Class clz, Object data, String tempate) throws IllegalAccessException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String copyTempate = tempate;
        if(tempate.contains(getClsName(clz))){
            copyTempate = copyTempate.replace(getClsName(clz) + ".", "");
        }
        Class clzSuperclass = clz.getSuperclass();
        if(clzSuperclass!=null){
            String tempateRes=replaceData(clzSuperclass,data,copyTempate);
            if(!copyTempate.equals(tempateRes)){
                tempate=tempateRes;
                copyTempate=tempateRes;
            }
        }

        for(Field field:clz.getDeclaredFields()){
            if(field!=null&&!field.equals("null")){
                field.setAccessible(true);
                Object da;
                try{
                    da=field.get(data);
                }catch (NullPointerException e){
                    continue;
                }

                if(da!=null&&!da.equals("null")){

                    if("java.util.Date".equals(field.getType().getName())){
                        Date date=(Date) da;
                        da = simpleDateFormat.format(date.getTime());
                    }
                    if(field.getType().getName().startsWith("com")){
                        String tempateRes=replaceData(field.getType(),da,copyTempate);
                        if(!copyTempate.equals(tempateRes)){
                            tempate=tempateRes;
                            copyTempate=tempateRes;
                        }
                    }

                    try {
                        String tempateRes=copyTempate.replace("${"+field.getName()+"}", String.valueOf(da));
                        if(!copyTempate.equals(tempateRes)){
                            tempate=tempateRes;
                            copyTempate=tempateRes;
                        }
                    }catch (NullPointerException e){
                        System.out.println(111);
                    }
                }
            }
        }
        return tempate;

    }


    /**
     * 获取消息模板框架

     * @return
     * @throws IllegalAccessException
     */
    public JSONObject getTempateFrame() throws IllegalAccessException {
        String data="{\n" +
                "           \"touser\":\"${openId}\",\n" +
                "           \"template_id\":\"${wxTemplateId}\",\n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\"恭喜你购买成功！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\"欢迎再次购买！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";
        return JSONObject.fromObject(data);
    }

    /**
     *
     * @param tempateData  基本格式  {"keyword1","{\n" +
    "                       \"value\":\"巧克力\",\n" +
    "                       \"color\":\"#173177\"\n" +
    "                   }"}
     * @return
     * @throws IllegalAccessException
     */
    public JSONObject packageTempate(String tempateData) throws IllegalAccessException {
        JSONObject jsonObject= getTempateFrame();
        JSONObject jsonObject1= JSONObject.fromObject(tempateData);

        JSONObject data = (JSONObject) jsonObject.get("data");
        Iterator a = jsonObject1.keys();
        while (a.hasNext()){
            Object b = a.next();
            data.put(b,jsonObject1.get(b));
        }

        jsonObject.put("data",data);


        return jsonObject;
    }

    public static String getClsName(Class cls){
        String clsName = cls.getName();
        return clsName.substring(clsName.lastIndexOf(".")+1,clsName.length());
    }

    /* public static void main(String[] args) throws IllegalAccessException {

         ShOrders shOrders=new ShOrders();
         shOrders.setStatus("1");
         FqMember fqMember=new FqMember();
         fqMember.setMobile("15681");

         String a="{\"touser\":\"${openId}\",\"template_id\":\"${wxTemplateId}\"," +
                 "\"data\":{\"first\":{\"value\":\"${FqMember.name}\",\"color\":\"\"}," +
                 "\"remark\":{\"value\":\"${ShOrders.amount}\",\"color\":\"\"}," +
                 "\"keyword1\":{\"value\":\"${ShOrders.firstAmount}\",\"color\":\"\"}," +
                 "\"keyword2\":{\"value\":\"${ShOrders.productAmount}\",\"color\":\"\"}," +
                 "\"keyword3\":{\"value\":\"${ShOrders.periodsAmount}\",\"color\":\"\"}," +
                 "\"keyword4\":{\"value\":\"${FqMember.mobile}\",\"color\":\"\"}," +
                 "\"keyword5\":{\"value\":\"${ShOrders.sn}\",\"color\":\"\"}}}";
         shOrders.setAmount(new BigDecimal("10000"));
         shOrders.setFirstAmount(new BigDecimal("100"));
         shOrders.setProductAmount(new BigDecimal("1000000"));
         shOrders.setPeriodsAmount(new BigDecimal("999999"));
         shOrders.setSn("23232323223233232");
         fqMember.setMobile("15681008705");
         fqMember.setName("啊啊啊");
         shOrders.setBorrower(fqMember);
         //String a="${ShOrders.id}";
         //System.out.println(replaceData(shOrders.getClass(),srders,a));

     }
     /*

         shOrders.setBorrower(fqMember);
     //System.out.println(getClsName(shOrders.getClass()));
     //System.out.println(replaceTempateData(shOrders.getClass(),shOrders,"${FqMember.id}"));
     Class<?> cls = fqMember.getClass().getSuperclass();
     Type b = cls.getGenericSuperclass();
     Type c = cls.getGenericSuperclass();
         System.out.println(shOrders.getEntityClass());
         System.out.println(getActualTypeArgument(cls));*/
    /*
     * 获取泛型类Class对象，不是泛型类则返回null
     */
    public static Class<?> getActualTypeArgument(Class<?> clazz) {
        Class<?> entitiClass = null;
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass)
                    .getActualTypeArguments();
            if (actualTypeArguments != null && actualTypeArguments.length > 0) {
                entitiClass = (Class<?>) actualTypeArguments[0];
            }
        }

        return entitiClass;
    }



    public void a(Object a){
        try {
            System.out.println(getClsData(a,"idNo"));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    public static void b(Class clz,Object b){
        System.out.println(clz);
    }

    public static void main(String[] args) {
        ShOrders shOrders=new ShOrders();
        TemplateUtil templateUtil=new TemplateUtil();
        shOrders.setBranchName("adada");
        FqMember fqMember=new FqMember();
        fqMember.setIdNo("1312414");
        shOrders.setFqMember(fqMember);
        templateUtil.a(shOrders);
        b(shOrders.getClass(),shOrders);
    }
}
