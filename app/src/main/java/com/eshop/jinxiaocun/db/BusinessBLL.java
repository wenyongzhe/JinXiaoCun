package com.eshop.jinxiaocun.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.base.bean.BillType;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
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

    public  static  BusinessBLL getInstance(){
        if(businessBLL==null)
            businessBLL=new BusinessBLL();
        return  businessBLL;
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
    //盘点
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
    //根据条件获取盘点商品数据集合
    public List<PandianDetailBeanResult> getDBStocktakeGoodsDatas(String where) throws SQLException{

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
    //获取盘点商品数据  有进度条显示
    public void getDBStocktakeGoodsDatas(String where ,DbCallBack callBack) throws SQLException{
        Cursor cursor = null;
        try{
            String sql = "select * from " + Config.PANDIAN_DETAIL_GOODS;
            if (!where.equals(""))
                sql += " where " + where;

            cursor = Config.DBHelper.query(sql);
            int count = cursor.getCount();
            cursor.moveToFirst();
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
                        field.set(module, MyUtils.convertToInt(cursor.getString(columnIdx), 0));
                    else if (type == float.class)
                        field.set(module, MyUtils.convertToFloat(cursor.getString(columnIdx), 0f));
                }
                if (callBack != null) {
                    callBack.progressUpdate(i + 1, count, module);
                }
                cursor.moveToNext();
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cursor.close();
        }
    }
    public boolean insertPandianGoodsEntity(String sheetNo,PandianDetailBeanResult data) throws SQLException {
        try{
            ContentValues values = new ContentValues();
            values.put("sheet_no",sheetNo);
            Field[] flds = data.getClass().getDeclaredFields();//私有字段
            for (Field fld : flds) {
                if(fld.getName().equals("serialVersionUID"))
                    continue;
                if (fld.getName().equals("$change")) {
                    continue;
                }
                Object value = ReflactUtility.getInstance().getFldValue(data, fld.getName());
                if (value != null) {
                    if(value instanceof String){
                        values.put(fld.getName(),value.toString());
                    }else if(value instanceof Integer){
                        values.put(fld.getName(),MyUtils.convertToInt(value,0));
                    }else if(value instanceof Float){
                        values.put(fld.getName(),MyUtils.convertToFloat(value,0f));
                    }else if(value instanceof Double){
                        values.put(fld.getName(),MyUtils.convertToDouble(value,0d));
                    }
                }
            }
            Config.DBHelper.insert(Config.PANDIAN_DETAIL_GOODS,values);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean insertPandianGoodsEntitys(String sheetNo,List<PandianDetailBeanResult> listResult) throws SQLException {
        try{
            Config.DBHelper.beginTrans();
            for (PandianDetailBeanResult beanResult : listResult) {
                insertPandianGoodsEntity(sheetNo,beanResult);
            }
            Config.DBHelper.commitTrans();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    //添加或删除盘点商品时，修改盘点状态 has_stocktake 0未盘点 1已盘点
    public int updateStocktakeGoodsCheckQty(int check_qty ,String item_no,String sheet_no)throws SQLException {
        try{
            if(!tableIsExist(Config.PANDIAN_DETAIL_GOODS))return 0;
            String sql = "update "+Config.PANDIAN_DETAIL_GOODS+" set check_qty=? where item_no=? and sheet_no=?";
            Config.DBHelper.exeSql(sql, new String[]{check_qty+"",item_no,sheet_no});
            return 1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    //上传成功后修改盘点状态  status 0未上传 1已上传 / has_stocktake 0未盘点 1已盘点
    public void updateStocktakeGoodsStatus(String status,String has_stocktake,String item_no,String sheet_no)throws SQLException {
        if(!tableIsExist(Config.PANDIAN_DETAIL_GOODS))return ;
        String sql = "update "+Config.PANDIAN_DETAIL_GOODS+" set status=?,has_stocktake=? where item_no=? and sheet_no=?";
        Config.DBHelper.exeSql(sql, new String[]{status,has_stocktake,item_no,sheet_no});
    }
    //更改上传的商品盘点状态更改为已上传、已盘点
    public int updateStocktakeGoodsStatus(List<PandianDetailBeanResult> list,String sheet_no)throws SQLException{
        try{
            if(list==null || list.size()==0)return 0;
            Config.DBHelper.beginTrans();
            for (PandianDetailBeanResult info : list) {
                updateStocktakeGoodsStatus("1","1",info.getItem_no(),sheet_no);
            }
            Config.DBHelper.commitTrans();
            return 1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    //添加或删除盘点商品时，修改盘点状态和盘点数量 has_stocktake 0未盘点 1已盘点
    public int updateStocktakeGoodsStatusAndCheckQty(int check_qty ,String has_stocktake,String item_no,String sheet_no)throws SQLException {
        try{
            if(!tableIsExist(Config.PANDIAN_DETAIL_GOODS))return 0;
            String sql = "update "+Config.PANDIAN_DETAIL_GOODS+" set check_qty=?,has_stocktake=? where item_no=? and sheet_no=?";
            Config.DBHelper.exeSql(sql, new String[]{check_qty+"",has_stocktake,item_no,sheet_no});
            return 1;
        }catch (Exception e){
            e.printStackTrace();
        }
       return 0;
    }

    //根本商品号和盘点批号 删除对应的单品盘点记录
    public int deleteStocktakeGoods(String item_no,String sheet_no)throws SQLException {
        try{
            if(!tableIsExist(Config.PANDIAN_DETAIL_GOODS))return 0;
            String sql = "DELETE FROM "+Config.PANDIAN_DETAIL_GOODS+" where item_no=? and sheet_no=?";
            Config.DBHelper.exeSql(sql, new String[]{item_no,sheet_no});
            return 1;
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
    //根本盘点批号 删除对应的单品盘点记录
    public int deleteStocktakeGoods(String sheet_no)throws SQLException {
        try{
            if(!tableIsExist(Config.PANDIAN_DETAIL_GOODS))return 0;
            String sql = "DELETE FROM "+Config.PANDIAN_DETAIL_GOODS+" where sheet_no=?";
            Config.DBHelper.exeSql(sql, new String[]{sheet_no});
            return 1;
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    private void insertEntity(Class bean ,String tableName) throws SQLException{
        StringBuilder insertSqlBuilder = new StringBuilder();
        insertSqlBuilder.append("insert into ").append(tableName).append("(");
        StringBuilder valuesBuilder = new StringBuilder();

        List<Object> values=new ArrayList<>();
        Field[] flds = bean.getClass().getDeclaredFields();//私有字段
        int mark = 0;
        for (Field fld : flds) {
            if(fld.getName().equals("serialVersionUID"))
                continue;
            if (fld.getName().equals("$change")) {
                continue;
            }
            if (fld.getName().equals("hasYiJia")) {
                continue;
            }
            Object value = ReflactUtility.getInstance().getFldValue(bean, fld.getName());
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

        String[] strValues = new String[values.size()];
        for (int i = 0; i < values.size(); i++) {
            strValues[i] = values.get(i).toString();
        }
        String insertSql=insertSqlBuilder.toString()+") values ("+valuesBuilder.toString()+")";
        Config.DBHelper.exeSql(insertSql,strValues);
    }
    //取出类的字段
    private String queryFlds(Class moduleClass){
        Field[] flds = moduleClass.getDeclaredFields();//私有字段
        StringBuffer queryFlds = new StringBuffer();
        int mark = 0;
        for (Field fld  : flds) {
            if(fld.getName().equals("serialVersionUID"))
                continue;
            if(fld.getName().equals("$change"))
                continue;

            if (mark == 0) {
                mark++;
                queryFlds.append(fld.getName());
            } else {
                queryFlds.append(",");
                queryFlds.append(fld.getName());
            }
        }
        return queryFlds.toString();
    }
    //根据单据类型 获取商品信息集合
    public List<GetClassPluResult> getAllGoodsInfo(String orderType)throws SQLException{

        List<GetClassPluResult> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        sql.append("Select ");
        sql.append(queryFlds(GetClassPluResult.class));
        sql.append(" from ");
        sql.append(Config.GETCLASSPLURESULT);
        Cursor c = null;
        try {
            if(TextUtils.isEmpty(orderType)){
                c = Config.DBHelper.exeRawQuery(sql.toString());
            }else{
                sql.append(" where orderType=?");
                c = Config.DBHelper.exeRawQuery(sql.toString(),new String[]{orderType});
            }

            while (c.moveToNext()) {
                // 取出所有的列名
                Field[] arrField = GetClassPluResult.class.getDeclaredFields();
                GetClassPluResult module = GetClassPluResult.class.newInstance();
                // 遍历有列
                for (Field field : arrField) {
                    if (field.isSynthetic()) {
                        continue;
                    }
                    if (field.getName().equals("serialVersionUID")) {
                        continue;
                    }
                    if (field.getName().equals("hasYiJia")) {
                        continue;
                    }

                    String columnName = field.getName();
                    int columnIdx = c.getColumnIndex(columnName);
                    if (columnIdx == -1) {
                        continue;
                    }

                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    Class<?> type = field.getType();
                    if (type == String.class)
                        field.set(module, c.getString(columnIdx));
                    else if (type == int.class)
                        field.set(module, c.getInt(columnIdx));
                }
                list.add(module);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (c != null)
                c.close();
        }

        return list;
    }
    //保存商品信息
    public void insertGoodsInfo(List<GetClassPluResult> infos){
        if(!tableIsExist(Config.GETCLASSPLURESULT))return ;
        if(infos==null||infos.size()<1) return ;
        Config.DBHelper.beginTrans();
        for (GetClassPluResult item : infos) {
            insertEntity(item.getClass(),Config.GETCLASSPLURESULT);
        }
        Config.DBHelper.commitTrans();

    }
    //保存商品信息
    public void insertGoodsInfo(GetClassPluResult info){
        if(!tableIsExist(Config.GETCLASSPLURESULT))return ;
        insertEntity(info.getClass(),Config.GETCLASSPLURESULT);
    }
    //根本单据类型删除商品信息
    public void deleteGoodsInfoByOrderType(String orderType)throws SQLException {
        if(!tableIsExist(Config.GETCLASSPLURESULT))return ;
        String sql = "DELETE FROM "+Config.GETCLASSPLURESULT+" where orderType=? ";
        Config.DBHelper.exeSql(sql, new String[]{orderType});
    }
    //修改商品数量
    public void updateGoodsInfoSaleQnty(String orderType,String sale_qnty,String item_no)throws SQLException {
        if(!tableIsExist(Config.GETCLASSPLURESULT))return ;
        String sql = "update "+Config.GETCLASSPLURESULT+" set sale_qnty=? where orderType =? and item_no=?";
        Config.DBHelper.exeSql(sql, new String[]{sale_qnty, orderType,item_no});
    }
    //修改商品销售价格
    public void updateGoodsInfoSalePrice(String orderType,String sale_price,String item_no)throws SQLException {
        if(!tableIsExist(Config.GETCLASSPLURESULT))return ;
        String sql = "update "+Config.GETCLASSPLURESULT+" set sale_price=? where orderType =? and item_no=?";
        Config.DBHelper.exeSql(sql, new String[]{sale_price, orderType,item_no});
    }
    //修改商品批发价格
    public void updateGoodsInfoBasePrice(String orderType,String base_price,String item_no)throws SQLException {
        if(!tableIsExist(Config.GETCLASSPLURESULT))return ;
        String sql = "update "+Config.GETCLASSPLURESULT+" set base_price=? where orderType =? and item_no=?";
        Config.DBHelper.exeSql(sql, new String[]{base_price, orderType,item_no});
    }
    //修改商品VIP价格
    public void updateGoodsInfoVipPrice(String orderType,String vip_price,String item_no)throws SQLException {
        if(!tableIsExist(Config.GETCLASSPLURESULT))return ;
        String sql = "update "+Config.GETCLASSPLURESULT+" set vip_price=? where orderType =? and item_no=?";
        Config.DBHelper.exeSql(sql, new String[]{vip_price, orderType,item_no});
    }


    public interface DbCallBack{
        void progressUpdate(int progress ,int maxProgress,PandianDetailBeanResult module);
    }

}
