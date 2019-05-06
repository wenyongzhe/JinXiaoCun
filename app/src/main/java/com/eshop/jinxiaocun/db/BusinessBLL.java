package com.eshop.jinxiaocun.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.base.bean.BillType;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.lingshou.bean.GetBillMain;
import com.eshop.jinxiaocun.piandian.bean.PandianDetailBeanResult;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBeanResultItem;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.utils.ReflactUtility;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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
    //根据单据号 获取商品信息集合
    public List<GetClassPluResult> getAllGoodsInfo(String sheet_No)throws SQLException{
        List<GetClassPluResult> list = new ArrayList<>();
        Cursor cursor = null;
        try{
            StringBuffer sbf = new StringBuffer();
            sbf.append("select * from ");
            sbf.append(Config.GETCLASSPLURESULT);
            sbf.append(" where Sheet_No='"+sheet_No+"'");

            cursor = Config.DBHelper.query(sbf.toString());
            int count = cursor.getCount();
            cursor.moveToFirst();
            // 取出所有的列名
            Field[] arrField = GetClassPluResult.class.getDeclaredFields();
            // 遍历游标
            for (int i = 0; i < count; i++) {
                // 转化为moduleClass类的一个实例
                GetClassPluResult module = new GetClassPluResult();
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
                list.add(module);
                cursor.moveToNext();
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cursor.close();
        }
        return list;
    }
    //保存商品信息
    public void insertGoodsInfo(List<GetClassPluResult> infos){
        if(!tableIsExist(Config.GETCLASSPLURESULT))return ;
        if(infos==null||infos.size()<1) return ;
        Config.DBHelper.beginTrans();
        for (GetClassPluResult item : infos) {
            insertGoodsInfo(item);
        }
        Config.DBHelper.commitTrans();

    }

    //保存商品信息
    public boolean insertGuaDanMain(GetBillMain data)throws SQLException{
        try{
            Field[] flds = data.getClass().getDeclaredFields();//私有字段
            ContentValues values = new ContentValues();
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
            Config.DBHelper.insert(Config.LINSHOU_MAIN,values);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //保存商品信息
    public boolean insertGuaDanGoodsInfo(String time,List<GetClassPluResult> data)throws SQLException{
        try{
            for(int i=0; i<data.size(); i++){
                Field[] flds = data.get(i).getClass().getDeclaredFields();//私有字段
                ContentValues values = new ContentValues();
                for (Field fld : flds) {
                    if(fld.getName().equals("serialVersionUID"))
                        continue;
                    if (fld.getName().equals("$change")) {
                        continue;
                    }
                    Object value = ReflactUtility.getInstance().getFldValue(data.get(i), fld.getName());
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
                values.put("timeNo",time);
                Config.DBHelper.insert(Config.LINSHOU,values);
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //保存商品信息
    public boolean insertGoodsInfo(GetClassPluResult data)throws SQLException{
        try{
            ContentValues values = new ContentValues();
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
            Config.DBHelper.insert(Config.GETCLASSPLURESULT,values);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    //根本单据号,商品编码删除商品信息
    public int deleteGoodsInfo(String sheet_No,String item_no)throws SQLException {
        try{
            if(!tableIsExist(Config.GETCLASSPLURESULT))return 0;
            String sql = "DELETE FROM "+Config.GETCLASSPLURESULT+" where Sheet_No=? and item_no=?";
            Config.DBHelper.exeSql(sql, new String[]{sheet_No,item_no});
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    //修改商品数量 订单数量
    public int updateGoodsInfoSaleQnty(String sheet_No,String sale_qnty,String order_qnty,String item_no)throws SQLException {
        try{
            if(!tableIsExist(Config.GETCLASSPLURESULT))return 0;
            String sql = "update "+Config.GETCLASSPLURESULT+" set sale_qnty=?,order_qnty=? where Sheet_No=? and item_no=?";
            Config.DBHelper.exeSql(sql, new String[]{sale_qnty,order_qnty,sheet_No,item_no});
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    //修改商品销售价格
    public int updateGoodsInfoSalePrice(String sheet_No,String sale_price,String item_no)throws SQLException {
        try{
            if(!tableIsExist(Config.GETCLASSPLURESULT))return 0;
            String sql = "update "+Config.GETCLASSPLURESULT+" set sale_price=?,hasModifyPrice=1 where Sheet_No=? and item_no=?";
            Config.DBHelper.exeSql(sql, new String[]{sale_price, sheet_No,item_no});
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    //修改商品批发价格
    public int updateGoodsInfoBasePrice(String sheet_No,String base_price,String item_no)throws SQLException {
        try{
            if(!tableIsExist(Config.GETCLASSPLURESULT))return 0;
            String sql = "update "+Config.GETCLASSPLURESULT+" set base_price=?,hasModifyPrice=1 where Sheet_No=? and item_no=?";
            Config.DBHelper.exeSql(sql, new String[]{base_price, sheet_No,item_no});
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    //取到单据主表的数量
    public List<DanJuMainBeanResultItem> getOrderMainInfos(String sheetType) throws SQLException{
        List<DanJuMainBeanResultItem> listDatas=new ArrayList<>();
        Cursor cursor = null;
        try{
            String sql = "select * from " + Config.DANJUMAINBEANRESULTITEM;
            if (!sheetType.equals(""))
                sql += " where SheetType='" + sheetType+"'";

            cursor = Config.DBHelper.query(sql);
            int count = cursor.getCount();
            cursor.moveToFirst();
            // 取出所有的列名
            Field[] arrField = DanJuMainBeanResultItem.class.getDeclaredFields();
            // 遍历游标
            for (int i = 0; i < count; i++) {
                // 转化为moduleClass类的一个实例
                DanJuMainBeanResultItem module = new DanJuMainBeanResultItem();
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
                listDatas.add(module);
                cursor.moveToNext();
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cursor.close();
        }
        return listDatas;
    }
    //根据单据号,是否保存过单据主表信息
    public boolean isSaveOrderMainInfo(String sheet_No){
        String sql = "Select Sheet_No from "+Config.DANJUMAINBEANRESULTITEM+" where Sheet_No=? limit 1";
        Cursor c = null;
        try {
            c = Config.DBHelper.exeRawQuery(sql, new String[]{sheet_No});
            if (c.moveToFirst() && c.getCount() > 0) {
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (c != null)
                c.close();
        }
        return false;
    }
    //保存单据主表信息
    public boolean insertOrderMianInfo(DanJuMainBeanResultItem data) throws SQLException {
        try{
            ContentValues values = new ContentValues();
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
            Config.DBHelper.insert(Config.DANJUMAINBEANRESULTITEM,values);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    //添加商品时  更新商品数量和主表总数量
    public int upDateGoodsQtyAndOrderQty(String sheet_no,String item_no,String goodsQty,String orderQty,String orderAllQty){
        try{
            Config.DBHelper.beginTrans();
            updateGoodsInfoSaleQnty(sheet_no,goodsQty,orderQty,item_no);
            String sql = "update "+Config.DANJUMAINBEANRESULTITEM+" set GoodsNum=? where Sheet_No=?";
            Config.DBHelper.exeSql(sql, new String[]{orderAllQty, sheet_no});
            Config.DBHelper.commitTrans();
            return 1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    //添加一个商品，并更新主表总数量
    public int insertGoodsInfoAndUpdateOrderQty(GetClassPluResult goodsInfo,String sheet_no,String orderAllQty){
        try{
            Config.DBHelper.beginTrans();
            insertGoodsInfo(goodsInfo);
            String sql = "update "+Config.DANJUMAINBEANRESULTITEM+" set GoodsNum=? where Sheet_No=?";
            Config.DBHelper.exeSql(sql, new String[]{orderAllQty, sheet_no});
            Config.DBHelper.commitTrans();
            return 1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    //删除一个商品，并更新主表总数量
    public int deleteGoodsInfoAndUpdateOrderQty(String sheet_no,String item_no ,String orderAllQty){
        try{
            Config.DBHelper.beginTrans();
            deleteGoodsInfo(sheet_no,item_no);
            String sql = "update "+Config.DANJUMAINBEANRESULTITEM+" set GoodsNum=? where Sheet_No=?";
            Config.DBHelper.exeSql(sql, new String[]{orderAllQty, sheet_no});
            Config.DBHelper.commitTrans();
            return 1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    //删除单据下的商品和主表
    public int deleteMainInfoAndGoodsInfo(String sheet_no){
        try{
            Config.DBHelper.beginTrans();
            String sql_goods = "DELETE FROM "+Config.GETCLASSPLURESULT+" where Sheet_No=?";
            Config.DBHelper.exeSql(sql_goods, new String[]{sheet_no});
            String sql_mainInfo = "DELETE FROM "+Config.DANJUMAINBEANRESULTITEM+" where Sheet_No=?";
            Config.DBHelper.exeSql(sql_mainInfo, new String[]{sheet_no});
            Config.DBHelper.commitTrans();
            return 1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    //生成一个唯一的编号
    public String getNewBillNO(String tableName, int glideLength, boolean withIncre){
        java.util.Date now_date = new java.util.Date();
        String curDate = new java.text.SimpleDateFormat("yyMMdd").format(now_date);  //.SSS

        String sql = "select glide from billGlideNO where tableName=? and glideDate=?";
        Cursor cursor = null;
        try {
            cursor = Config.DBHelper.exeRawQuery(sql, new String[]{ tableName,curDate });
            if (cursor.moveToFirst()) {
                int iGlide = cursor.getInt(0);
                iGlide = iGlide + 1;
                if (withIncre) {
                    sql = "update billGlideNO set glide=? where tableName=? and glideDate=?";
                    Config.DBHelper.exeSql(sql, new String[]{String.valueOf(iGlide), tableName, curDate}, false);
                }
                String format = String.format("%%s%%0%dd", glideLength);
                return String.format(format, curDate, iGlide);
            }else{
                int iGlide = 1;
                if (withIncre) {
                    sql = "insert into billGlideNO(tableName, glideDate, glide) values(?,?,?)";
                    Config.DBHelper.exeSql(sql, new String[]{tableName, curDate, String.valueOf(iGlide)}, false);
                }
                String format = String.format("%%s%%0%dd", glideLength);
                return String.format(format, curDate, iGlide);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return "";
    }
    public interface DbCallBack<T>{
        void progressUpdate(int progress ,int maxProgress,T module);
    }

    //挂单主表
    public List<GetBillMain> getGuaDanMain()throws SQLException{
        List<GetBillMain> list = new ArrayList<>();
        Cursor cursor = null;
        try{
            StringBuffer sbf = new StringBuffer();
            sbf.append("select * from ");
            sbf.append(Config.LINSHOU_MAIN);

            cursor = Config.DBHelper.query(sbf.toString());
            int count = cursor.getCount();
            cursor.moveToFirst();
            // 取出所有的列名
            Field[] arrField = GetBillMain.class.getDeclaredFields();
            // 遍历游标
            for (int i = 0; i < count; i++) {
                // 转化为moduleClass类的一个实例
                GetBillMain module = new GetBillMain();
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
                list.add(module);
                cursor.moveToNext();
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cursor.close();
        }
        return list;
    }

    //挂单明细
    public List<GetClassPluResult> getGuaDanDetail(String time)throws SQLException{
        List<GetClassPluResult> list = new ArrayList<>();
        Cursor cursor = null;
        try{
            StringBuffer sbf = new StringBuffer();
            sbf.append("select * from ");
            sbf.append(Config.LINSHOU);
            sbf.append(" where timeNo="+time);

            cursor = Config.DBHelper.query(sbf.toString());
            int count = cursor.getCount();
            cursor.moveToFirst();
            // 取出所有的列名
            Field[] arrField = GetClassPluResult.class.getDeclaredFields();
            // 遍历游标
            for (int i = 0; i < count; i++) {
                // 转化为moduleClass类的一个实例
                GetClassPluResult module = new GetClassPluResult();
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
                list.add(module);
                cursor.moveToNext();
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cursor.close();
        }
        return list;
    }

    //根据时间删除挂单
    public int deleGuaDanMainDetail(String time)throws SQLException {
        try{
            if(!tableIsExist(Config.PANDIAN_DETAIL_GOODS))return 0;
            String sql = "DELETE FROM "+Config.LINSHOU+" where timeNo=?";
            Config.DBHelper.exeSql(sql, new String[]{time});

            sql = "DELETE FROM "+Config.LINSHOU_MAIN+" where timeNo=?";
            Config.DBHelper.exeSql(sql, new String[]{time});

            return 1;
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

}
