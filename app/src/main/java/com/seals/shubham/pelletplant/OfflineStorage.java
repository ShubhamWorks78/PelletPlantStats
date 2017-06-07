package com.seals.shubham.pelletplant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

/**
 * Created by shubham on 6/8/2017.
 */

public class OfflineStorage extends SQLiteOpenHelper{

    public OfflineStorage(Context context){
        super(context,"Data.db",null,2);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE PelletPlant"+"(Day varchar(2),Month varchar(2),Year varchar(2),Coal varchar(15),Limestone varchar(15),Haematite varchar(15),Manganese varchar(15))";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "Drop Table if exists PelletPlant";
        db.execSQL(query);
    }

    public void insertData(String day,String month,String year,String coal,String lime,String haem,String mang){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Day",day);
        cv.put("Month",month);
        cv.put("Year",year);
        cv.put("Coal",coal);
        cv.put("Limestone",lime);
        cv.put("Haematite",haem);
        cv.put("Manganese",mang);

        database.insert("PelletPlant",null,cv);
    }
    public boolean chck(String day,String month,String year){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM PelletPlant Where Day ='"+day+"' AND Month = '"+month+"' AND Year = '"+year+"'";
        Cursor c = database.rawQuery(query,null);
        if(c.getCount()==0){
            return false;
        }
        return true;
    }
    public HashMap<String,String> getData(String day,String month,String year){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM PelletPlant Where Day ='"+day+"' AND Month = '"+month+"' AND Year = '"+year+"'";
        Cursor c = database.rawQuery(query,null);
        HashMap<String,String> param = new HashMap<String,String>();
        if(c.moveToNext()){
            param.put("Coal",c.getString(3));
            param.put("Limestone",c.getString(4));
            param.put("Haematite",c.getString(5));
            param.put("Manganese",c.getString(6));
        }
        return param;
    }
}
