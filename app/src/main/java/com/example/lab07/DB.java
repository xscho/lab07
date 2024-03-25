package com.example.lab07;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {
    public DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE my_test (my_key TEXT PRIMARY KEY, my_value TEXT);";
        db.execSQL(sql);
    }

    public void do_insert(String key, String value)
    {
        String sql = "INSERT INTO my_test VALUES('" + key +"','" + value + "');";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    public String do_select(String key)
    {
        String sql = "SELECT my_value FROM my_test WHERE my_key = '" + key + "';";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.rawQuery(sql, null);

        if(cur.moveToFirst() == true)
            return cur.getString(0);

        return "(!) not found";
    }

    public void do_update(String key, String value)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("my_value", value);

        int affectedRows = db.update("my_test", values, "my_key=?", new String[]{key});

        if (affectedRows > 0) {
            Log.d("DB", "Record updated");
        } else {
            Log.d("DB", "Key not found");
        }
    }

    public boolean do_delete(String key) {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM my_test WHERE my_key=?", new String[]{key});

        if(cursor.moveToFirst()){
            db.execSQL("UPDATE my_test SET my_value=NULL WHERE my_key=?", new String[]{key});
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
