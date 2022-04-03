package com.example.mysavings;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final int DATABASE_VERSION = 9;

    private static final String TABLE_NAME = "savings";
    private static final String COL1 = "currency";
    private static final String COL2 = "value";
    private static final String COL3 = "eurratio";
    private static final String COL4 = "usdratio";
    private static final String COL5 = "gbpratio";

    private static final String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL1 + " TEXT NOT NULL UNIQUE, " + COL2 + " NUMERIC NOT NULL, " + COL3 + " NUMERIC NOT NULL, " + COL4 + " NUMERIC NOT NULL, " + COL5 + " NUMERIC NOT NULL)";
    private static final String dropTables = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DatabaseHelper(Context context){
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1){
        db.execSQL(dropTables);
        onCreate(db);
    }

    public void initiateData(String currency, Double value, Double eurR, Double usdR, Double gbpR){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, currency);
        contentValues.put(COL2, value);
        contentValues.put(COL3, eurR);
        contentValues.put(COL4, usdR);
        contentValues.put(COL5, gbpR);

        db.insert(TABLE_NAME, null, contentValues);

    }

    public Cursor allData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from savings", null);
        return cursor;
    }

    public void updateData(String currency, String value, boolean add){
        Double newTotal = 0.0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select value from savings where currency = ?", new String[]{currency});
        while(cursor.moveToNext()){
            if(add){
                newTotal = Double.parseDouble(cursor.getString(0)) + Double.parseDouble(value);
            } else {
                newTotal = Double.parseDouble(cursor.getString(0)) - Double.parseDouble(value);
            }
        }

        if(newTotal < 0) {
            newTotal = 0.0;
        }

        ContentValues cv = new ContentValues();
        cv.put(COL2, newTotal);

        db.update(TABLE_NAME, cv, "currency = ?", new String[]{currency});
    }

    public Double getExchangeRatio(String currency1, String currency2){
        SQLiteDatabase db = this.getWritableDatabase();
        Double ratio = 0.0;
        Cursor cursor = db.rawQuery("select * from savings where currency = ?", new String[]{currency1});
        if(currency2.equals("EUR")){
            while(cursor.moveToNext()){
                ratio = Double.parseDouble(cursor.getString(2));
            }
        } else if(currency2.equals("USD")) {
            while(cursor.moveToNext()){
                ratio = Double.parseDouble(cursor.getString(3));
            }
        } else if(currency2.equals("GBP")) {
            while(cursor.moveToNext()){
                ratio = Double.parseDouble(cursor.getString(4));
            }
        }
        return ratio;
    }

    public boolean convertSavings(String cr1, Double value1, String cr2, Double value2) {
        SQLiteDatabase db = this.getWritableDatabase();

        Double newValue1 = 0.0;
        Cursor cursor1 = db.rawQuery("select value from savings where currency = ?", new String[]{cr1});

        Double newValue2 = 0.0;
        Cursor cursor2 = db.rawQuery("select value from savings where currency = ?", new String[]{cr2});

        while(cursor1.moveToNext()){
            newValue1 = Double.parseDouble(cursor1.getString(0)) - value1;
        }

        if(newValue1 >= 0) {
            ContentValues cv = new ContentValues();
            cv.put(COL2, newValue1);

            db.update(TABLE_NAME, cv, "currency = ?", new String[]{cr1});

            while(cursor2.moveToNext()){
                newValue2 = Double.parseDouble(cursor2.getString(0)) + value2;
            }
            ContentValues cv2 = new ContentValues();
            cv2.put(COL2, newValue2);

            db.update(TABLE_NAME, cv2, "currency = ?", new String[]{cr2});
            return true;
        } else {
            return false;
        }
    }
}
