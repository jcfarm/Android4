package com.example.zhangnan.myfarm;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by zhangnan on 17/6/16.
 */

public class FragmentActivity extends AppCompatActivity {

    private static final String TAG = "FragmentActivity";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        getWindow().setStatusBarColor(getResources().getColor(R.color.app_green));

        switch (MyFarmActivity.selectItem){
            case 0:replaceFragment(new FieldsFragment(),"FieldsFragment");break;
            case 1:replaceFragment(new ControlFragment(),"ControlFragment");break;
        }

    }

    public static Intent newIntent(Context packageContext){
        Intent i = new Intent(packageContext,FragmentActivity.class);
        return i;
    }

    public void replaceFragment(Fragment fragment, String tag) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, tag);
        transaction.commit();
    }
}
