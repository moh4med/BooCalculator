package com.example.mohamed.boocalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements numbersFragment.numberListener {
    private static String TAG=MainActivity.class.getSimpleName();
    TextView outputTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        outputTv=(TextView)findViewById(R.id.outputtv);
        Log.e(TAG,"ONCREATE");
    }

    @Override
    public void OnClick(String s) {
        outputTv.append(s);
    }
    public void opClick(View v) {
        outputTv.append(((Button)v).getText());
        return;
    }
}
