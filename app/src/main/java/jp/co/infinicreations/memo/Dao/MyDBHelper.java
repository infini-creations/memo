package jp.co.infinicreations.memo.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;

public class MyDBHelper extends SQLiteOpenHelper {

    private static final String CREATE_TABLE_SQL = "" +
            "create table memo (" +
            "id integer primary key autoincrement, " +
            "memo text not null, " +
            "datetime text not null, " +
            "alarm_datetime text null" +
            ")";

    private static final String DROP_TABLE_SQL = "drop table if exists memo";
    private static final String DB_NAME = "memo.db";

    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, 2);
    }

    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_SQL);
        db.execSQL(CREATE_TABLE_SQL);
    }
}
