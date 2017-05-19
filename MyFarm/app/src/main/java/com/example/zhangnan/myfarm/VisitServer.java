package com.example.zhangnan.myfarm;

import android.util.Log;

import com.example.zhangnan.myfarm.activity_information.FieldsInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Far-away on 17/5/19.
 */

public class VisitServer {
    private String fieldsUrl = "http://10.0.2.2:8080/MyFarm/fields";
    private OkHttpClient okHttpClient;
    private static final String TAG="VisitServer";

    public Request getRequest(String address){
        Request request=new Request.Builder().url(address).build();
        return request;
    }


    public String getJsonString(){
        String jsonString=null;
        Request request=getRequest(fieldsUrl);
        okHttpClient=new OkHttpClient();
        try {
            Response response=okHttpClient.newCall(request).execute();
            if (response.isSuccessful()){
                jsonString=response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
       // Log.d(TAG, "getJsonString: "+jsonString);
        return jsonString;
    }

    public void parseFields(List<FieldsInfo> fields, JSONObject jsonBody){
        try {
            JSONArray fieldsArray=jsonBody.getJSONArray("fields");
            for (int i=0;i<fieldsArray.length();i++){
                JSONObject filedObject=fieldsArray.getJSONObject(i);
                FieldsInfo filedsInfo=new FieldsInfo(filedObject.getString("id"),filedObject.getString("temp"),filedObject.getString("humidity"),
                filedObject.getString("ph"),filedObject.getString("name"));
                fields.add(filedsInfo);
                Log.d(TAG, "parseFields: "+filedObject.getString("id"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<FieldsInfo> getFields(){
        List<FieldsInfo> fields=new ArrayList<>();
        String string=getJsonString();
        try {
            JSONObject object=new JSONObject(string);
            parseFields(fields,object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "getFields: "+fields.size());
        return fields;
    }

}
