package com.kavy.simplesyncadapter;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    ListView lvResults;
    String answer;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvResults = (ListView) findViewById(R.id.lv_results);
    }

    public void onClickAddItem(View view) {

        MyPresenter.fetchDataFromWebServer(this);

    }




    public void onClickReadDb(View view) {

        ArrayList<String> arrayListDays = MyPresenter.loadDataFromDB(this);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayListDays);
        lvResults.setAdapter(arrayAdapter);
    }
}
