package com.kavy.simplesyncadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

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
                tvResults.setText(response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.i(Utilities.TAG, "error: " + errorResponse.toString() );
                answer = null;
            }
        });

        return answer;
    }
}
