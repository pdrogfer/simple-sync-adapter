package com.kavy.simplesyncadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String currentTime;
    TextView tvResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResults = (TextView) findViewById(R.id.tv_results);
    }

    public void onClickAddItem(View view) {

        currentTime = String.valueOf(System.currentTimeMillis());

        tvResults.setText(currentTime);
    }
}
