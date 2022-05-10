package com.example.dbloc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBCountries
{
    private static final String DATABASE_NAME="countries.db";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_NAME="Countries";

    private static final String COLUMN_ID="id";
    private static final String COLUMN_NAME="name";
    private static final String COLUMN_CAPITAL="capital";

    private static final int NUM_COLUMN_ID=0;
    private static final int NUM_COLUMN_NAME=1;
    private static final int NUM_COLUMN_CAPITAL=2;

    private SQLiteDatabase db;

    public DBCountries(Context context) {
        OpenHelper openHelper=new OpenHelper(context);
        db=openHelper.getWritableDatabase();
    }

    public long Insert(String name,String capital)
    {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_CAPITAL,capital);
        return db.insert(TABLE_NAME,null,cv);
    }
    public int Update(Country country)
    {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_NAME,country.getName());
        cv.put(COLUMN_CAPITAL,country.getCapital());
        return db.update(TABLE_NAME,cv,COLUMN_ID+"=?",
                new String[]{String.valueOf(country.getID())});
    }
    public void deleteAll()
    {
        db.delete(TABLE_NAME,null,null);
    }
    public void Delete(long id)
    {
        db.delete(TABLE_NAME,COLUMN_ID+"=?",
                new String[]{String.valueOf(id)});
    }
    public ArrayList<Country> selectAll()
    {
        Cursor cursor=db.query(TABLE_NAME,null,null,
                null,null,null,null);
        ArrayList<Country> list=new ArrayList<>();
        cursor.moveToFirst();
        if(!cursor.isAfterLast()) {
           do {
                long id = cursor.getLong(NUM_COLUMN_ID);
                String name = cursor.getString(NUM_COLUMN_NAME);
                String capital = cursor.getString(NUM_COLUMN_CAPITAL);
                list.add(new Country(id, name, capital));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public Country select(int id)
    {
        Cursor cursor=db.query(TABLE_NAME,null,COLUMN_ID+"=?",
                new String[]{String.valueOf(id)},null,null,null);
        cursor.moveToFirst();
        String name=cursor.getString(NUM_COLUMN_NAME);
        String capital=cursor.getString(NUM_COLUMN_CAPITAL);
        return new Country(id,name,capital);
    }
    private class OpenHelper extends SQLiteOpenHelper
    {
        public OpenHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String query="CREATE TABLE "+TABLE_NAME+ " (" +
                    COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME+" TEXT UNIQUE NOT NULL," +
                    COLUMN_CAPITAL+" TEXT NOT NULL);";
            sqLiteDatabase.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
            onCreate(sqLiteDatabase);
        }
    }
}
