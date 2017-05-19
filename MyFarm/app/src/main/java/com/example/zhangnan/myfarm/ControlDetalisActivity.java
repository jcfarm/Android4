package com.example.zhangnan.myfarm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by zhangnan on 17/5/3.
 */

public class ControlDetalisActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_details);
        getWindow().setStatusBarColor(getResources().getColor(R.color.app_green));

        recyclerView = (RecyclerView) findViewById(R.id.control_details_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.setAdapter(new SoundAdapter());
    }

    private class SoundHodler extends RecyclerView.ViewHolder{

        public SoundHodler(LayoutInflater inflater, ViewGroup container) {
            super(inflater.inflate(R.layout.control_details_list_item,container,false));

        }

    }


    private class SoundAdapter extends RecyclerView.Adapter<SoundHodler>{


        @Override
        public SoundHodler onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater= LayoutInflater.from(ControlDetalisActivity.this);
            return new SoundHodler(inflater,parent);
        }

        @Override
        public void onBindViewHolder(SoundHodler soundHodler, int position) {
        }

        @Override
        public int getItemCount() {
            return 10;
        }

    }

    public static Intent newIntent(Context packageContext){
        Intent i = new Intent(packageContext,ControlDetalisActivity.class);
        return i;
    }

}
