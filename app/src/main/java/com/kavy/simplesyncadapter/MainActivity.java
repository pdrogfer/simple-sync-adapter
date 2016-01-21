package com.kavy.simplesyncadapter;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    String currentTime;
    TextView tvResults;
    String answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResults = (TextView) findViewById(R.id.tv_results);
    }

    public void onClickAddItem(View view) {

        fetchDataFromWebServer();

    }

    private String fetchDataFromWebServer() {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://192.168.1.4/SimpleSyncAdapter/get_time_json.php", new JsonHttpResponseHandler() {
            // client.get("http://kavy.servehttp.com/SimpleSyncAdapter/get_time_json.php", new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // tvResults.setText(response.toString());
                try {
                    String tempDay = response.getString(Contract.COL_DAY);
                    String tempHour = response.getString(Contract.COL_HOUR);
                    addDayToDatabase(tempDay, tempHour);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.i(Utilities.TAG, "error: " + errorResponse.toString());
                answer = null;
            }
        });

        return answer;
    }

    private void addDayToDatabase(String day, String hour) {
        MyDbHelper myDbHelper = new MyDbHelper(this, Contract.DB_DAYS_NAME, null, 1);
        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        if (db != null) {
            ContentValues newDayValues = new ContentValues();
            newDayValues.put(Contract.COL_DAY, day);
            newDayValues.put(Contract.COL_HOUR, hour);
            Long i = db.insert(Contract.TABLE_DAYS_NAME, null, newDayValues);
            if (i > 0) {
                Toast.makeText(this, "D: " + day +
                        " H: " + hour +
                        " .Day stored in db", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
