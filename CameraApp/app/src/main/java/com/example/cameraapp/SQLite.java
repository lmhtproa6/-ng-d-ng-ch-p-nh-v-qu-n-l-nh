package com.example.cameraapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import java.sql.Blob;

public class SQLite extends SQLiteOpenHelper {


    public SQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void QuerryData (String sql){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);

    }

    public Cursor GetData (String sql){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql,null);
    }

    public void INSERT_Hinh (byte[] hinh, String album){
        SQLiteDatabase db = getWritableDatabase();
        String sql="INSERT INTO Hinh VALUES (null, ?, ?)";

        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindBlob(1,hinh);
        statement.bindString(2,album);


        statement.executeInsert();

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
