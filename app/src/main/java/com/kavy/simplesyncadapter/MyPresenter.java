package com.kavy.simplesyncadapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * This class is an attempt to implement MVP architecture. All methods handling data operations will
 * come here, while Main Activity will handle only UI staff
 */
public class MyPresenter {

    public static void fetchDataFromWebServer(final Context context) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://192.168.1.4/SimpleSyncAdapter/get_time_json.php", new JsonHttpResponseHandler() {
        // client.get("http://kavy.servehttp.com/SimpleSyncAdapter/get_time_json.php", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String tempDay = response.getString(Contract.COL_DAY);
                    String tempHour = response.getString(Contract.COL_HOUR);
                    //addDayToDatabase(tempDay, tempHour);
                    MyDbHelper myDbHelper = new MyDbHelper(
                            context, Contract.DB_DAYS_NAME, null, Contract.DB_VERSION);
                    SQLiteDatabase db = myDbHelper.getWritableDatabase();
                    if (db != null) {
                        ContentValues newDayValues = new ContentValues();
                        newDayValues.put(Contract.COL_DAY, tempDay);
                        newDayValues.put(Contract.COL_HOUR, tempHour);
                        Long i = db.insert(Contract.TABLE_DAYS_NAME, null, newDayValues);
                        if (i > 0) {
                            Toast.makeText(context, "D: " + tempDay +
                                    " H: " + tempHour +
                                    " .Day stored in db", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.i(Utilities.TAG, "error: " + errorResponse.toString());
            }
        });
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
