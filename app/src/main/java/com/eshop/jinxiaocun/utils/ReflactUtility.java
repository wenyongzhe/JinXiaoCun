package com.eshop.jinxiaocun.utils;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/2/22.
 */

public class ReflactUtility {

    private  static ReflactUtility reflactUtility;

    public static ReflactUtility getInstance()
    {
        return reflactUtility==null?new ReflactUtility():reflactUtility;
    }

    /**
     * 是否有某个字段
     * @param object
     * @param fldName
     * @return
     */
    public  boolean hasField(Object object, String fldName)
    {
        boolean has=false;
        Field fld=null;
        try {
            try {
                fld = object.getClass().getDeclaredField(fldName);
            } catch (NoSuchFieldException e) {
                fld = object.getClass().getField(fldName);
            }
            if (fld != null) {
                has=true;
            }
        }
        catch (Exception ex)
        {
        }
        return  has;
    }

    public <T> T getFldValue(Object object, String fldName)
    {
        if(object==null)
            return  null;
        T result=null;
        Field fld=null;
        try {
            try {
                fld = object.getClass().getDeclaredField(fldName);
            } catch (NoSuchFieldException e) {
                fld = object.getClass().getField(fldName);
            }
            if (fld != null) {
                fld.setAccessible(true);
                result = (T) fld.get(object);
            }
        }
        catch (Exception ex)
        {
        }
        return result;
    }

    public void setFldValue(Object object, String fldName, Object value)
    {
        Field fld=null;
        try {
            try {
                fld = object.getClass().getDeclaredField(fldName);
            } catch (NoSuchFieldException e) {
                fld = object.getClass().getField(fldName);
            }
            if (fld != null) {
                fld.setAccessible(true);
                fld.set(object,value);
            }
        }
        catch (Exception ex)
        {
            int ii =0;
        }
    }
}
