package com.eshop.jinxiaocun.utils;

/**
 * @Title: ReflectionUtils.java
 * @Description: 反射获取对象属性信息</br>
 */

import com.eshop.jinxiaocun.base.IJsonFormat;
import com.eshop.jinxiaocun.base.JsonFormatImp;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ReflectionUtils {

    public static Map<String, String> obj2Map(Object obj) {

        IJsonFormat mJsonFormatImp = new JsonFormatImp();
        Map<String, String> map = new HashMap<String, String>();
        // System.out.println(obj.getClass());
        // 获取f对象对应类中的所有属性域
        Field[] fields = obj.getClass().getDeclaredFields();
        Field[] supperFields = obj.getClass().getSuperclass().getDeclaredFields();
        for (int i = 0, len = fields.length; i < len; i++) {
            String varName = fields[i].getName();
//            varName = varName.toLowerCase();//将key置为小写，默认为对象的属性
            try {
                // 获取原来的访问控制权限
                boolean accessFlag = fields[i].isAccessible();
                // 修改访问控制权限
                fields[i].setAccessible(true);
                // 获取在对象f中属性fields[i]对应的对象中的变量
                Object o = fields[i].get(obj);
                if (o != null)
                    if(varName.equalsIgnoreCase("jsonData")){
                        map.put(varName, mJsonFormatImp.ObjetToString(o));
                    }else
                        map.put(varName, o.toString());
                // System.out.println("传入的对象中包含一个如下的变量：" + varName + " = " + o);
                // 恢复访问控制权限
                fields[i].setAccessible(accessFlag);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
        for (int i = 0, len = supperFields.length; i < len; i++) {
            String varName = supperFields[i].getName();
            varName = varName.toLowerCase();//将key置为小写，默认为对象的属性
            try {
                // 获取原来的访问控制权限
                boolean accessFlag = supperFields[i].isAccessible();
                // 修改访问控制权限
                supperFields[i].setAccessible(true);
                // 获取在对象f中属性supperFields[i]对应的对象中的变量
                Object o = supperFields[i].get(obj);
                if (o != null)
                    map.put(varName, o.toString());
                else
                    map.put(varName, "");
                // System.out.println("传入的对象中包含一个如下的变量：" + varName + " = " + o);
                // 恢复访问控制权限
                supperFields[i].setAccessible(accessFlag);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }

        return map;
    }
}

