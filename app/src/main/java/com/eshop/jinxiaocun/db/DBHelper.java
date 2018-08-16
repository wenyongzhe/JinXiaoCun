package com.eshop.jinxiaocun.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.eshop.jinxiaocun.utils.Config;

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
        String sql = "create table "+ Config.UP_MAIN_DANJU+"("+
                "id"+" integer primary key autoincrement,"+
                "ss"+" varchar(20)," +
                "ddff"+" integer)";
        sqLiteDatabase.execSQL(sql);//执行sql语句
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

    public void execSQL(String sql)
    {
        db = getWritableDatabase();
        db.execSQL(sql);
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
