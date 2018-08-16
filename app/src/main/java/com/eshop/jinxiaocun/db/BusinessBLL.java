package com.eshop.jinxiaocun.db;

import android.database.Cursor;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.base.bean.BillType;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.utils.ReflactUtility;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 业务操作类
 */

public class BusinessBLL {
    private static  BusinessBLL businessBLL;

    public  static  BusinessBLL getInstance()
    {
        if(businessBLL==null)
            businessBLL=new BusinessBLL();
        return  businessBLL;
    }

    public boolean isHaveEntity(String where, BaseBean entity) {
        String sql = "select * from " + entity.getTableName();
        if (!where.equals(""))
            sql += " where " + where;
        Cursor cursor = Config.DBHelper.query(sql);
        if (cursor.moveToFirst()) {
            return true;
        }
        else
            return false;
    }

    public <T extends BaseBean> void deleteEntity(String where, Class<T> moduleClass) throws IllegalAccessException, InstantiationException {
        String sql = "delete from " + moduleClass.newInstance().getTableName();
        if (!where.equals(""))
            sql += " where " + where;
        Config.DBHelper.execSQL(sql);
    }

    public String getBillNo(BillType e_billType)
    {
        return e_billType.name() + MyUtils.getCurrentTime();
    }

    //直接修改数量
    public boolean saveDetailQty(BaseBean entity){

        Object valueBillNo = ReflactUtility.getInstance().getFldValue(entity, "BillNo");
        Object valueSku = ReflactUtility.getInstance().getFldValue(entity, "Sku");
        Object valueQty = ReflactUtility.getInstance().getFldValue(entity, "Qty");
        String where = "BillNo='" + valueBillNo.toString() + "' and Sku='"+ valueSku +"' ";

        String sql = "update " + entity.getTableName() + " set Qty="+ valueQty +" where " + where;
        Config.DBHelper.execSQL(sql);

        return true;
    }

    //直接修改数量 差异数（库存盘点）
    public boolean saveDetailQtyDiff(BaseBean entity){

        Object valueBillNo = ReflactUtility.getInstance().getFldValue(entity, "BillNo");
        Object valueSku = ReflactUtility.getInstance().getFldValue(entity, "Sku");
        Object valueQty = ReflactUtility.getInstance().getFldValue(entity, "Qty");
        Object valueDiff = ReflactUtility.getInstance().getFldValue(entity, "Diff");
        String where = "BillNo='" + valueBillNo.toString() + "' and Sku='"+ valueSku +"' ";

        String sql = "update " + entity.getTableName() + " set Qty="+ valueQty + ", Diff="+ valueDiff +" where " + where;
        Config.DBHelper.execSQL(sql);

        return true;
    }

    //直接修改数量
    public boolean saveMainQty(BaseBean entity, int allQty){

        Object valueBillNo = ReflactUtility.getInstance().getFldValue(entity, "BillNo");
        String where = "BillNo='" + valueBillNo.toString() + "'";

        String sql = "update " + entity.getTableName() + " set AllQty="+ allQty +" where " + where;
        Config.DBHelper.execSQL(sql);

        return true;
    }

    public boolean deleteMainEntity(BaseBean entity) {
        Object value = ReflactUtility.getInstance().getFldValue(entity, "BillNo");

        String deleteSql = "delete from " + entity.getTableName() + " where BillNo='" + value.toString() + "'";
        Config.DBHelper.execSQL(deleteSql);
        return true;
    }

    public <T extends BaseBean> boolean deleteEntity(Class<T> mainClass, Class<T> detailClass, String billNo) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //删除主表
        String tableName = mainClass.getConstructor().newInstance().getTableName();
        String pdaFldName = "BillNo";

        String sql = "delete from " + tableName;
        if (!billNo.equals(""))
            sql += " where " + pdaFldName + "='" + billNo + "'";
        Config.DBHelper.execSQL(sql);

        //删除从表数据
        tableName = detailClass.getConstructor().newInstance().getTableName();
        sql = "delete from " + tableName;
        if (!billNo.equals(""))
            sql += " where " + pdaFldName + "='" + billNo + "'";
        Config.DBHelper.execSQL(sql);

        return true;
    }

    public <T extends BaseBean> List<T> getEntityList(String where, Class<T> moduleClass) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        T tMain = moduleClass.getConstructor().newInstance();
        String tableName = tMain.getTableName();
        if (tableName.equals(""))
            return null;

        Field[] flds = moduleClass.getDeclaredFields();//私有字段
        String queryFlds = "";
        int mark = 0;
        for (Field fld  : flds) {
            if (mark == 0) {
                mark++;
                queryFlds += fld.getName();
            } else {
                queryFlds += "," + fld.getName();
            }
        }
        String sql = "select " + queryFlds + " from " + tableName;
        if (!where.equals(""))
            sql += " where " + where;

        Cursor cursor = Config.DBHelper.getReadableDatabase().rawQuery(sql, null);

        return convertToList(cursor, moduleClass);
    }

    public boolean saveEntity(BaseBean entity)
    {
        StringBuilder insertSqlBuilder = new StringBuilder();
        insertSqlBuilder.append("insert into ").append(entity.getTableName()).append("(");
        StringBuilder valuesBuilder = new StringBuilder();

        List<Object> values=new ArrayList<>();
        Field[] flds = entity.getClass().getDeclaredFields();//私有字段
        int mark = 0;
        for (Field fld : flds) {
            if(fld.getName().equals("serialVersionUID"))
                continue;
            Object value = ReflactUtility.getInstance().getFldValue(entity, fld.getName());
            if (value != null) {
                values.add(value);
                if (mark == 0) {
                    insertSqlBuilder.append(fld.getName());//添加真实字段
                    valuesBuilder.append("?");
                    mark++;
                } else {
                    insertSqlBuilder.append(",").append(fld.getName());
                    valuesBuilder.append(",?");
                }
            }
        }
        String insertSql=insertSqlBuilder.toString()+") values ("+valuesBuilder.toString()+")";
        Config.DBHelper.getWritableDatabase().execSQL(insertSql,values.toArray());
        return true;
    }


    public boolean updateEntity(BaseBean entity, String where)
    {
        StringBuilder updateSqlBuilder = new StringBuilder();
        updateSqlBuilder.append("update ").append(entity.getTableName()).append(" set ");
        StringBuilder valuesBuilder = new StringBuilder();

        List<Object> values=new ArrayList<>();
        Field[] flds = entity.getClass().getDeclaredFields();//私有字段
        int mark = 0;
        for (Field fld : flds) {
            if(fld.getName().equals("serialVersionUID"))
                continue;
            Object value = ReflactUtility.getInstance().getFldValue(entity, fld.getName());
            if (value != null) {
                values.add(value);
                if (mark == 0) {
                    updateSqlBuilder.append(fld.getName()).append("=?");//添加真实字段
                    mark++;
                } else {
                    updateSqlBuilder.append(",").append(fld.getName()).append("=?");
                }
            }
        }
        String updateSql;
        if (where.equals(""))
            updateSql=updateSqlBuilder.toString()+valuesBuilder.toString();
        else
            updateSql=updateSqlBuilder.toString()+valuesBuilder.toString()+ " where " + where;

        Config.DBHelper.getWritableDatabase().execSQL(updateSql,values.toArray());
        return true;
    }

    public <T extends BaseBean> T convertToModule(Cursor cursor, Class<T> moduleClass) throws IllegalAccessException, InstantiationException {
        // 取出所有的列名
        Field[] arrField = moduleClass.getDeclaredFields();
        T module = moduleClass.newInstance();
        // 遍历有列
        for (Field field : arrField) {
            if (field.isSynthetic()) {
                continue;
            }
            if (field.getName().equals("serialVersionUID")) {
                continue;
            }

            String columnName = field.getName();
            int columnIdx = cursor.getColumnIndex(columnName);
            if (columnIdx == -1) {
                continue;
            }

            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            Class<?> type = field.getType();
            if (type == String.class)
                field.set(module, cursor.getString(columnIdx));
            else if (type == int.class)
                field.set(module, cursor.getInt(columnIdx));
        }
        return module;
    }

    public <T extends BaseBean> List<T> convertToList(Cursor cursor, Class<T> clazz) throws InstantiationException, IllegalAccessException {

        List<T> moduleList = new ArrayList<>();

        int count = cursor.getCount();
        T module;
        cursor.moveToFirst();

        // 遍历游标
        for (int i = 0; i < count; i++) {
            // 转化为moduleClass类的一个实例
            module = convertToModule(cursor, clazz);
            moduleList.add(module);
            cursor.moveToNext();
        }
        cursor.close();

        return moduleList;
    }

    public boolean deleteDetailEntity(BaseBean entity) {
        Object billNovalue = ReflactUtility.getInstance().getFldValue(entity, "BillNo");
        Object skuValue = ReflactUtility.getInstance().getFldValue(entity, "Sku");
        String where =" where BillNo='" + billNovalue.toString() + "' and Sku='" + skuValue + "'";

        String deleteSql = "delete from " + entity.getTableName() + where;
        Config.DBHelper.execSQL(deleteSql);
        return true;
    }
}