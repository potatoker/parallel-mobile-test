package com.ray.offloading1.IPContacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ray on 2015/4/8.
 */
public class DatabaseHandler {

    private SQLiteDatabase db;
    private MySQLiteHelper dbHelper;

    private String[] allColumns={MySQLiteHelper.KEY_ID,MySQLiteHelper.KEY_NAME,MySQLiteHelper.KEY_IP,MySQLiteHelper.KEY_LEVEL};

    public  DatabaseHandler(Context context){
        dbHelper=new MySQLiteHelper(context);
    }

    public void open()throws SQLException{
        db=dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }





    public void addHelper(serverHelper sh){

        ContentValues values=new ContentValues();
        values.put(MySQLiteHelper.KEY_NAME,sh.getName());
        values.put(MySQLiteHelper.KEY_IP,sh.getIP());
        values.put(MySQLiteHelper.KEY_LEVEL,sh.getLevel());
        db.insert(MySQLiteHelper.TABLE_PROVIDER,null,values);
        db.close();
    }


    public serverHelper getHelper(int id){

        Cursor cursor=db.query(MySQLiteHelper.TABLE_PROVIDER,allColumns,MySQLiteHelper.KEY_ID+"=?",
                new String[]{String.valueOf(id)},null,null,null,null

        );

        if(cursor!=null)
            cursor.moveToFirst();

        serverHelper sh=new serverHelper(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),Integer.parseInt(cursor.getString(3)));
        return  sh;

    }


    public ArrayList<serverHelper> getAllHelpers(){
        ArrayList<serverHelper> helperList=new ArrayList<serverHelper>();

        String selectQuery="SELECT * FROM "+MySQLiteHelper.TABLE_PROVIDER;

        Cursor cursor=db.rawQuery(selectQuery,null);


        if(cursor.moveToFirst()){
            do{
                serverHelper sh=new serverHelper();
                sh.setId(Integer.parseInt(cursor.getString(0)));
                sh.setName(cursor.getString(1));
                sh.setIP(cursor.getString(2));
                sh.setLevel(Integer.parseInt(cursor.getString(3)));
                helperList.add(sh);
            }while(cursor.moveToNext());
        }
         return helperList;
    }

    public ArrayList<String> getAllIps(){
        ArrayList<String> IpArray=new ArrayList<String>();
        String selectQuery="SELECT "+MySQLiteHelper.KEY_IP+" FROM "+MySQLiteHelper.TABLE_PROVIDER;
        Cursor c=db.rawQuery(selectQuery,null);
        if(c.moveToFirst()){
            do{
                String IP=c.getString(0);
                IpArray.add(IP);
            }while(c.moveToNext());
        }

        return IpArray;
    }


    public int getHelperCount(){
        String countQuery="SELECT * FROM "+MySQLiteHelper.TABLE_PROVIDER;

        Cursor cursor=db.rawQuery(countQuery,null);
        cursor.close();
        return cursor.getCount();
    }


    public  int updateHelper(serverHelper sh){


        ContentValues values=new ContentValues();
        values.put(MySQLiteHelper.KEY_NAME,sh.getName());
        values.put(MySQLiteHelper.KEY_IP,sh.getIP());
        values.put(MySQLiteHelper.KEY_LEVEL,sh.getLevel());
        return db.update(MySQLiteHelper.TABLE_PROVIDER,values,MySQLiteHelper.KEY_ID+"=?",new String[]{String.valueOf(sh.getId())});
    }

  //  new String[]{MySQLiteHelper.KEY_NAME,MySQLiteHelper.KEY_IP,MySQLiteHelper.KEY_LEVEL
    public Cursor readData(){
        Cursor c=db.query(MySQLiteHelper.TABLE_PROVIDER,allColumns,null,null,null,null,null);
        if(c!=null){
            c.moveToFirst();
        }
        return  c;
    }

    public void deleteHelper(serverHelper sh){

        db.delete(MySQLiteHelper.TABLE_PROVIDER,MySQLiteHelper.KEY_ID+" =?",new String[]{String.valueOf(sh.getId())});
        db.close();
    }



}
