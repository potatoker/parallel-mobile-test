package com.ray.offloading1.IPContacts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Ray on 2015/4/12.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;

    private static final String DATABASE_NAME="sProvider";

    public static final String TABLE_PROVIDER="provider";

    public static final String KEY_ID="_id";

    public static final String KEY_NAME="name";

    public static final String KEY_IP="IP";

    public static final String KEY_LEVEL="level";


    private static String DATABASE_CREATE="CREATE TABLE "+TABLE_PROVIDER+"("+KEY_ID+" INTEGER PRIMARY KEY  AUTOINCREMENT,"+
            KEY_NAME+" TEXT,"+KEY_IP+" TEXT,"+KEY_LEVEL+" INTEGER"+")";

    public MySQLiteHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(DATABASE_CREATE);
    }

    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PROVIDER);
        onCreate(db);
    }

}
