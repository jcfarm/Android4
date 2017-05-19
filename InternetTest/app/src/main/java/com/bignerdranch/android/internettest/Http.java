package com.bignerdranch.android.internettest;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Far-away on 17/5/4.
 */

public class Http {
    private String urlAddress="http://10.0.2.2:8080/";
    //doGet
    public void doGet(String s) {
        final String getUrl=urlAddress+s;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url=new URL(getUrl);
                    try {
                        HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                        httpURLConnection.connect();
                        if(httpURLConnection.getResponseCode()==httpURLConnection.HTTP_OK){
                            InputStream inputStream=httpURLConnection.getInputStream();
                            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                            StringBuffer stringBuffer=new StringBuffer();
                            String readLine="";
                            while ((readLine=bufferedReader.readLine())!=null){
                                stringBuffer.append(readLine);
                            }
                            inputStream.close();
                            bufferedReader.close();
                            httpURLConnection.disconnect();
                            Log.d("TAG", stringBuffer.toString());
                        }else{
                            Log.d("TAG", "fail");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }).start();



    }

    //doPost
    public void doPost(final String s) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url=new URL(urlAddress+s);
                    HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                    //打开输入输出流
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);
                    //请求方法
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setUseCaches(false);
                    httpURLConnection.connect();
                    DataOutputStream outputStream=new DataOutputStream(httpURLConnection.getOutputStream());
                    String content="sex="+s;
                    outputStream.writeBytes(content);
                    outputStream.flush();
                    outputStream.close();
                    if(httpURLConnection.getResponseCode()==httpURLConnection.HTTP_OK){
                        InputStream inputStream=httpURLConnection.getInputStream();
                        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                        StringBuffer stringBuffer=new StringBuffer();
                        String readLine="";
                        while ((readLine=bufferedReader.readLine())!=null){
                            stringBuffer.append(readLine);
                        }
                        inputStream.close();
                        bufferedReader.close();
                        httpURLConnection.disconnect();
                        Log.d("TAG", stringBuffer.toString());
                    }else{
                        Log.d("TAG", "fail");
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
}
