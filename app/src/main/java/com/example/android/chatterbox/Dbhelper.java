package com.example.android.chatterbox;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Kunal on 10-Nov-17.
 */

public class Dbhelper extends SQLiteOpenHelper {
    Context c;

    private static final String DatabaseName = "ChatRoom.db";
    private static final String TableName = "USERDATA";
    private static final String Column1name = "EMAIL";
    private static final String Column2name = "PASSWORD";

    private static final String CreateTable = "CREATE TABLE IF NOT EXISTS "+TableName+" ( "+Column1name+" TEXT, "+Column2name+" TEXT)";

    public Dbhelper(Context context) {
        super(context,DatabaseName,null, 1);
        c=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TableName);
    }
    public void enteruserinfo(String email, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Column1name, email);
        cv.put(Column2name, pass);

        long result = db.insert(TableName, null, cv);
        if (result == -1) {
            Toast.makeText(c,"Some Error Occurred!!", Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(c, "Successfully inserted", Toast.LENGTH_SHORT).show();
        }
    }
    public Cursor getalldata() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TableName,null);
        return res;
    }

    public void deleteall() {
        SQLiteDatabase db =this.getWritableDatabase();
        Toast.makeText(c,"Successfully Logged out :)", Toast.LENGTH_SHORT).show();
        db.delete(TableName,null,null);
    }
}
