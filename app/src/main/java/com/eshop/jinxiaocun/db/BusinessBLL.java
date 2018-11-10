package com.eshop.jinxiaocun.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.base.bean.BillType;
import com.eshop.jinxiaocun.piandian.bean.PandianDetailBeanResult;
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

    /**
     * 判断表是否存在数据库中
     * @param tableName 表名
     * @return true表示存在 否则不存在
     */
    private boolean tableIsExist(String tableName){
        boolean result = false;
        if(tableName == null){
            return false;
        }
        Cursor cursor = null;
        try {
            String sql = "select count(*) as c from Sqlite_master  where type ='table' and name ='"+tableName.trim()+"' ";
            cursor = Config.DBHelper.getReadableDatabase().rawQuery(sql, null);
            if(cursor.moveToNext()){
                int count = cursor.getInt(0);
                if(count>0){
                    result = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public boolean isHavePandianGoodsEntity(String where) {

        if(!tableIsExist(Config.PANDIAN_DETAIL_GOODS))return false;

        String sql = "select * from " + Config.PANDIAN_DETAIL_GOODS;
        if (!where.equals(""))
            sql += " where " + where;
        Cursor cursor = Config.DBHelper.query(sql);
        if (cursor.moveToFirst()) {
            return true;
        }
        else
            return false;
    }

    public List<PandianDetailBeanResult> getDBPandianGoodsDatas(String where ,DbCallBack callBack) throws Exception{

        Field[] flds = PandianDetailBeanResult.class.getDeclaredFields();//私有字段
        String queryFlds = "";
        int mark = 0;
        for (Field fld  : flds) {
            if (fld.isSynthetic()) {
                continue;
            }
            if (fld.getName().equals("serialVersionUID")) {
                continue;
            }
            if (fld.getName().equals("$change")) {
                continue;
            }
            if (mark == 0) {
                mark++;
                queryFlds += fld.getName();
            } else {
                queryFlds += "," + fld.getName();
            }
        }
        String sql = "select " + queryFlds + " from " + Config.PANDIAN_DETAIL_GOODS;
        if (!where.equals(""))
            sql += " where " + where;

        SQLiteDatabase db =Config.DBHelper.getReadableDatabase();
        db.beginTransaction();
        Cursor cursor = db.rawQuery(sql, null);
        List<PandianDetailBeanResult> moduleList = new ArrayList<>();
        int count = cursor.getCount();
        cursor.moveToFirst();
        try{

            // 取出所有的列名
            Field[] arrField = PandianDetailBeanResult.class.getDeclaredFields();
            // 遍历游标
            for (int i = 0; i < count; i++) {
                // 转化为moduleClass类的一个实例
                PandianDetailBeanResult module = new PandianDetailBeanResult();
                // 遍历有列
                for (Field field : arrField) {
                    if (field.isSynthetic()) {
                        continue;
                    }
                    if (field.getName().equals("serialVersionUID")) {
                        continue;
                    }
                    if (field.getName().equals("$change")) {
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
                        field.set(module, MyUtils.convertToInt(cursor.getString(columnIdx),0));
                    else if (type == float.class)
                        field.set(module, MyUtils.convertToFloat(cursor.getString(columnIdx),0f));
                }
                moduleList.add(module);
                cursor.moveToNext();
                if(callBack!=null){
                    callBack.progressUpdate(i+1,count,module);
                }
            }

            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cursor.close();
            db.endTransaction();
        }

        return moduleList;
    }

    public boolean insertPandianGoodsEntity(String sheetNo,List<PandianDetailBeanResult> listResult) throws Exception {

        SQLiteDatabase db =Config.DBHelper.getWritableDatabase();
        try{

            db.beginTransaction();

            for (PandianDetailBeanResult beanResult : listResult) {
                StringBuilder insertSqlBuilder = new StringBuilder();
                insertSqlBuilder.append("insert into ").append(Config.PANDIAN_DETAIL_GOODS).append("(");
                StringBuilder valuesBuilder = new StringBuilder();

                List<Object> values=new ArrayList<>();
                Field[] flds = beanResult.getClass().getDeclaredFields();//私有字段

                values.add(sheetNo);
                insertSqlBuilder.append("sheet_no");//添加真实字段
                valuesBuilder.append("?");

                for (Field fld : flds) {
                    if(fld.getName().equals("serialVersionUID"))
                        continue;
                    if (fld.getName().equals("$change")) {
                        continue;
                    }
                    Object value = ReflactUtility.getInstance().getFldValue(beanResult, fld.getName());
                    if (value != null) {
                        values.add(value);
                        insertSqlBuilder.append(",").append(fld.getName());//添加真实字段
                        valuesBuilder.append(",?");
                    }
                }
                String insertSql=insertSqlBuilder.toString()+") values ("+valuesBuilder.toString()+")";
                db.execSQL(insertSql,values.toArray());
            }

            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            db.endTransaction();
        }

        return true;
    }

    public interface DbCallBack{
        void progressUpdate(int progress ,int maxProgress,PandianDetailBeanResult module);
    }

}
