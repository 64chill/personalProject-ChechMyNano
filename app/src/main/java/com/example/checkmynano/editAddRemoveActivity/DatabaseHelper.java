package com.example.checkmynano.editAddRemoveActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/***
 * public class DatabaseHelper extends SQLiteOpenHelper
 *
 *  public          DatabaseHelper  (Context context)
 *  public void     onCreate        (SQLiteDatabase db)                  - @Override
 *  public void     onUpgrade       (SQLiteDatabase db, int i, int i1)  - @Override
 *  public boolean  addData         (String item)
 *  public Cursor   getData         ()
 *  public void     deleteData      (String recordName)
 *  public boolean  checkIfExist    (String recordName)
 *
 *
 *
 *
 *
 *
 ***/
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "people_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "name";
    private static final String createTable = "CREATE TABLE " + TABLE_NAME +
            " ( "+COL1+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL2 +" TEXT)";

////////////////////////////////////////////////////////////////////////////////////////////////////
    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean addData(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL2, item);
        long result = db.insert(TABLE_NAME, null, values);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
    public Cursor getData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "+COL2+" FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    public void deleteData(String recordName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                                      + COL2 + " = '" + recordName + "'";
        db.execSQL(query);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean checkIfExist(String recordName){
        SQLiteDatabase  db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+COL2+" =  '" + recordName + "'", null);
        boolean exist = (cur.getCount() > 0);
        cur.close();
        db.close();
        return exist;
    }

}

