package com.eshop.jinxiaocun.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.eshop.jinxiaocun.base.bean.BillGlideNo;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.bean.UpDetailBean;
import com.eshop.jinxiaocun.base.bean.UpMainBean;
import com.eshop.jinxiaocun.piandian.bean.PandianDetailBeanResult;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBeanResultItem;
import com.eshop.jinxiaocun.utils.Config;

import java.lang.reflect.Field;

/**
 * SQLITE数据库操作
 */

public class DBHelper extends SQLiteOpenHelper {

    private final static int VERSION = 1;
    private final static String DATABASE_NAME = "Business.db";
    private SQLiteDatabase db;
    protected static DBHelper mInstance = null;

    public DBHelper(Context context) {
        super(new DatabaseContext(context), DATABASE_NAME, null, VERSION);
    }

    public synchronized static DBHelper getInstance(
            Context context) {
        if (mInstance == null) {
            mInstance = new DBHelper(context);

        }
        return mInstance;
    };

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        System.out.println("upgrade a database1");
        //创建表结构
        String sql = "create table "+ Config.UP_MAIN_DANJU+"("+ "id integer primary key autoincrement,"
                + cloumFile(UpMainBean.class) +")";
        sqLiteDatabase.execSQL(sql);//执行sql语句

        //创建表结构
        sql = "create table "+ Config.UP_DETAIL_DANJU+"("+ "id integer primary key autoincrement,"
                + cloumFile(UpDetailBean.class) +")";
        sqLiteDatabase.execSQL(sql);//执行sql语句

        //创建表结构
        sql = "create table "+ Config.PANDIAN_DETAIL_GOODS+"("+ "id integer primary key autoincrement,"
                +"sheet_no varchar(50),"
                + cloumFile(PandianDetailBeanResult.class) +")";
        sqLiteDatabase.execSQL(sql);//执行sql语句

        //创建表结构  商品信息
        sql = "create table "+ Config.GETCLASSPLURESULT+"("+ "id integer primary key autoincrement,"
                + cloumFile(GetClassPluResult.class) +")";
        sqLiteDatabase.execSQL(sql);//执行sql语句

        //创建表结构  单据主体信息
        sql = "create table "+ Config.DANJUMAINBEANRESULTITEM+"("+ "id integer primary key autoincrement,"
                + cloumFile(DanJuMainBeanResultItem.class) +")";
        sqLiteDatabase.execSQL(sql);//执行sql语句

        //创建表结构  保存生成的临时编号
        sql = "create table "+ Config.BILLGLIDENO+"("+ "idno integer primary key autoincrement,"
                + cloumFile(BillGlideNo.class) +")";
        sqLiteDatabase.execSQL(sql);//执行sql语句

    }

    private String cloumFile(Class bean){
        Field[] arrField = bean.getDeclaredFields();
        StringBuffer arrayTable =new StringBuffer();
        for (int i=0; i<arrField.length; i++) {
            Field field = arrField[i];
            if (field.getName().equals("$change")) {
                continue;
            }
            if(field.getName().equals("serialVersionUID"))
                continue;

            if(i != (arrField.length-1)){
                arrayTable.append(field.getName()+" varchar(20),");
            }else {
                arrayTable.append(field.getName()+" varchar(20)");
            }
        }
        String strArrayTable;
        if(arrayTable.toString().endsWith(",")){
            strArrayTable = arrayTable.toString().substring(0,(arrayTable.toString().length()-1));
        }else{
            strArrayTable = arrayTable.toString();
        }
        return strArrayTable;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        System.out.println("upgrade a database");
    }

    public long insert(String table, ContentValues values){
        //获取SQLiteDatabase实例
        db = getWritableDatabase();
        //插入数据库中
        return db.insert(table, null, values);
    }
    public void beginTrans(){
        db.beginTransaction();
    }

    public void commitTrans(){
        db.setTransactionSuccessful();
        db.endTransaction();
    }
    /**执行Sql语句
     * @strSql
     * @values
     * @withTrans
     */
    public void exeSql(String strSql, String[] values) {
        exeSql(strSql, values, false);
    }

    /**执行Sql语句
     * @strSql
     * @values
     * @withTrans
     */
    public void exeSql(String strSql, String[] values, boolean withTrans) {
        //获取SQLiteDatabase实例
        db = getWritableDatabase();
        if (withTrans){
            db.beginTransaction();
            try {
                db.execSQL(strSql, values);
                db.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            } finally {
                db.endTransaction();
            }
        }
        else {
            try {
                db.execSQL(strSql, values);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            } finally {
            }
        }
    }


    public Cursor exeRawQuery(String strSql) {
        Cursor cursor = null;
        try {
            db = getReadableDatabase();
            String []selectionArgs = new String[0];
            cursor = db.rawQuery(strSql, selectionArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return cursor;
    }

    public Cursor exeRawQuery(String strSql, String[] selectionArgs) {
        Cursor cursor = null;
        try {
            db = getReadableDatabase();
            cursor = db.rawQuery(strSql, selectionArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return cursor;
    }

    //查询方法
    public Cursor query(String sql){
        db = getReadableDatabase();
        //获取Cursor
        return db.rawQuery(sql, null);
    }

    //根据唯一标识_id  来删除数据
    public  int delete(String table, String whereClause,
                       String[] whereArgs){
        db = getReadableDatabase();
        return db.delete(table, whereClause, whereArgs);
    }


    //更新数据库的内容
    public int update(String table, ContentValues values,
                      String whereClause, String[] whereArgs){
        db = getWritableDatabase();
        return db.update(table, values, whereClause, whereArgs);
    }

    //关闭数据库
    public  void close(){
        if(db != null){
            db.close();
        }
    }
}
