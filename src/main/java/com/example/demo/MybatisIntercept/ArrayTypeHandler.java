package com.example.demo.MybatisIntercept;


import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;

import java.sql.*;

// 继承自BaseTypeHandler<Object[]> 使用时传入的参数一定要是Object[]，例如 int[]是 Object, 不是Object[]，所以传入int[] 会报错的
/**
 * mysql一个字段，保存数组类型，处理
 *
 * <result column="list_pic_template_id"  property="listPicTemplateId" typeHandler="com.qrcode.wx.model.ArrayTypeHandler"/>
 * */
public class ArrayTypeHandler extends BaseTypeHandler<Object[]> {

    private static final String TYPE_NAME_VARCHAR = "varchar";
    private static final String TYPE_NAME_INTEGER = "integer";
    private static final String TYPE_NAME_BOOLEAN = "boolean";
    private static final String TYPE_NAME_NUMERIC = "numeric";
    //private static final String TYPE_NAME_LONGVARCHAR = "LONGVARCHAR";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object[] parameter,
                                    JdbcType jdbcType) throws SQLException {

        /* 这是ibatis时的做法
        StringBuilder arrayString = new StringBuilder("{");

        for (int j = 0, l = parameter.length; j < l; j++) {
            arrayString.append(parameter[j]);
            if (j < l - 1) {
                arrayString.append(",");
            }
        }

        arrayString.append("}");

        ps.setString(i, arrayString.toString());
        */

        String typeName = null;
        if (parameter instanceof Integer[]) {
            typeName = TYPE_NAME_INTEGER;
        } else if (parameter instanceof String[]) {
            typeName = TYPE_NAME_VARCHAR;
        } else if (parameter instanceof Boolean[]) {
            typeName = TYPE_NAME_BOOLEAN;
        } else if (parameter instanceof Double[]) {
            typeName = TYPE_NAME_NUMERIC;
        }

        if (typeName == null) {
            throw new TypeException("ArrayTypeHandler parameter typeName error, your type is " + parameter.getClass().getName());
        }

        // 这3行是关键的代码，创建Array，然后ps.setArray(i, array)就可以了
        Connection conn = ps.getConnection();
        StringBuilder arrayString = new StringBuilder("{");

        for (int j = 0, l = parameter.length; j < l; j++) {
            arrayString.append(parameter[j]);
            if (j < l - 1) {
                arrayString.append("(())");
            }
        }

        arrayString.append("}");

        ps.setString(i, arrayString.toString());
        //Array array = conn.createArrayOf(typeName, parameter);
        //ps.setArray(i, array);
    }

    @Override
    public String[] getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        String[] c = new String[0];
        if(rs.getString(columnName)==null)
            return c;
        StringBuilder arrayString = new StringBuilder(rs.getString(columnName));

        try{
            String b = arrayString.substring(arrayString.indexOf("{")+1,arrayString.lastIndexOf("}"));


            c = b.split("\\(\\(\\)\\)");

        }catch (Exception e){

        }

       /* for(int i=0;i<c.length;i++){
            try{
                c[i]=c[i].substring(c[i].indexOf("[")+2,c[i].lastIndexOf("]")-1);
            }catch (Exception e){
                continue;
            }

        }*/
       // arrayString.substring(arrayString.indexOf("\"data\":"),arrayString.length()-2);
        return c;
    }

    @Override
    public Object[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {

        return getArray(rs.getArray(columnIndex));
    }

    @Override
    public Object[] getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {

        return getArray(cs.getArray(columnIndex));
    }

    private Object[] getArray(Array array) {

        if (array == null) {
            return null;
        }

        try {
            return (Object[]) array.getArray();
        } catch (Exception e) {
        }

        return null;
    }
}