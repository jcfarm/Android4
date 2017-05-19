package com.bignerdranch.android.internettest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;

public class MainActivity extends AppCompatActivity {
    private Button btn1;
    private Button btn2;
    //private String urlAddress="http://cloud.bmob.cn/0906a62b462a3082/";
    //private String method="getMemberBySex";


    private Http http;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1=(Button)findViewById(R.id.btn1);
        btn1.setOnClickListener(new ButtonClickListener());
        btn2=(Button)findViewById(R.id.btn2);
        btn2.setOnClickListener(new ButtonClickListener());
        http = new Http();
    }
    class ButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            switch (v.getId()){
                case R.id.btn1:
                    http.doGet("json");
                    break;
                case R.id.btn2:
                    http.doPost("json");
                    break;
                default:
                    break;
            }
        }
    }




}
