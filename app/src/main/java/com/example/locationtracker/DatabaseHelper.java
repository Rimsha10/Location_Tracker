package com.example.locationtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper  extends SQLiteOpenHelper {
    private static final String DB_NAME ="User_Record";
    private static final String TABLE_NAME="User";
    private  static final  String COL_1="ID";
    private static final String COL_2="USERNAME";
    private static final String COL_3="EMAIL";
    private static final String COL_4="PASSWORD";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME,null,5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, EMAIL VARCHAR, PASSWORD VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public boolean registerUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,username);
        contentValues.put(COL_3,email);
        contentValues.put(COL_4,password);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result <0) {
            return false;
        }else{
            return  true;
        }
    }
    public Cursor checkUser(String username,String password){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from User where USERNAME=?  AND PASSWORD=?" ,new String[] { username,password});
        return cursor;
    }
    public  boolean forgotPass(String username, String password ){
        SQLiteDatabase db=this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
             contentValues.put(COL_4,password);
        int result = db.update(TABLE_NAME, contentValues, "USERNAME = ?",new String[] { username });
        if (result ==-1) {
            return false;
        }else{
            return  true;
        }
        }
    public boolean checkUsername(String username){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from User where USERNAME=?" ,new String[] { username });
        if(cursor.getCount()==-1){
            return false;
        }else{
            return true;}
        }
    }


