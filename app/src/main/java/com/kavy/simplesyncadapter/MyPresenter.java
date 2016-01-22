package com.kavy.simplesyncadapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * This class is an attempt to implement MVP architecture. All methods handling data operations will
 * come here, while Main Activity will handle only UI staff
 */
public class MyPresenter {

    public static void addDayToDatabase(Context context, String day, String hour) {
        MyDbHelper myDbHelper = new MyDbHelper(
                context, Contract.DB_DAYS_NAME, null, Contract.DB_VERSION);
        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        if (db != null) {
            ContentValues newDayValues = new ContentValues();
            newDayValues.put(Contract.COL_DAY, day);
            newDayValues.put(Contract.COL_HOUR, hour);
            Long i = db.insert(Contract.TABLE_DAYS_NAME, null, newDayValues);
            if (i > 0) {
                Toast.makeText(context, "D: " + day +
                        " H: " + hour +
                        " .Day stored in db", Toast.LENGTH_SHORT).show();
            }
        }
    }



    public static ArrayList<String> loadDataFromDB(Context context) {
        MyDbHelper myDbHelper = new MyDbHelper(context, Contract.DB_DAYS_NAME, null, Contract.DB_VERSION);
        SQLiteDatabase db = myDbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + Contract.TABLE_DAYS_NAME;
        ArrayList<String> daysList = new ArrayList<>();
        if (db != null) {
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            do {
                String row = "Day: " + cursor.getString(cursor.getColumnIndex(Contract.COL_DAY)) + " "
                        + "Time: " + cursor.getString(cursor.getColumnIndex(Contract.COL_HOUR));
                daysList.add(row);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return daysList;
    }



}
