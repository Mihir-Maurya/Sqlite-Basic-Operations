package com.example.sqliteintro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    //database name
    private static final String dbName = "signup.db";

    public Database(@Nullable Context context) {
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //we write sql query to create table
        String query = "create table users( id integer primary key autoincrement,name text,username text,password text)";
       //this funtion will execute sqlQuery
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
       sqLiteDatabase.execSQL("drop table if exists users");
       onCreate(sqLiteDatabase);
    }
    //method to insert data in database
    public boolean insertData(String name,String username,String password){
        SQLiteDatabase database = this.getWritableDatabase();
        //whenever we want to insert data or update data we will make an object of ContentValues
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("username",username);
        contentValues.put("password",password);
        //now we will insert values in database and the method is going to return either 0 and-1
        //we insert method in database which is inbuilt
        long r=database.insert("users",null,contentValues);
        if(r==-1)
            return false;
        else
            return true;


    }
    //update data
    public boolean updateData(String name,String username,String password){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("password",password);
        Cursor cursor = database.rawQuery("select * from users where username=?",new String[]{username});
        if(cursor.getCount()>0){
            long r = database.update("users",values,"username=?",new String[]{username});
            if(r==-1){
                return false;
            }
            return true;
        }
        else return false;
    }
    //delete data
    public boolean deleteData(String username) {
        SQLiteDatabase database = this.getWritableDatabase();
      
        //cursor return data row wise and read data column wise
        Cursor cursor = database.rawQuery("select * from users where username=?", new String[]{username});
        if (cursor.getCount() > 0) {
            long r = database.delete("users", "username=?", new String[]{username});
            if (r == -1) {
                return false;
            }
            return true;
        } else return false;
    }
    //cursor is a class
    public Cursor getInfo(){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor=database.rawQuery("select * from users",null);
        return cursor; //cursor reads data row wise
    }
}
