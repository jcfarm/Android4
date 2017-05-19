package com.example.zhangnan.myfarm;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;
import com.example.zhangnan.myfarm.swipecardrecyclerview.ItemRemovedListener;
import com.example.zhangnan.myfarm.swipecardrecyclerview.SwipeCardAdapter;
import com.example.zhangnan.myfarm.swipecardrecyclerview.SwipeCardLayoutManager;
import com.example.zhangnan.myfarm.swipecardrecyclerview.SwipeCardRecyclerView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangnan on 17/5/11.
 */

public class MonitorActivity extends AppCompatActivity {

    private SwipeCardRecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private int nowIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        getWindow().setStatusBarColor(getResources().getColor(R.color.app_green));

        mRecyclerView = (SwipeCardRecyclerView) findViewById(R.id.swipe_card_recycler_view);
        mRecyclerView.setLayoutManager(new SwipeCardLayoutManager());
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(String.valueOf("http://video.jiecao.fm/11/23/xin/%E5%81%87%E4%BA%BA.mp4"));
        }
        mAdapter = new MyAdapter(this, list);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setRemovedListener(new ItemRemovedListener() {
            @Override
            public void onRightRemoved() {
                //Toast.makeText(MonitorActivity.this, nowIndex + " was right removed", Toast.LENGTH_SHORT).show();
                nowIndex--;
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftRemoved() {
                //Toast.makeText(MonitorActivity.this, nowIndex + " was left removed", Toast.LENGTH_SHORT).show();
                nowIndex--;
                mAdapter.notifyDataSetChanged();
            }
        });
    }


    public class MyAdapter extends SwipeCardAdapter<MyAdapter.MyHolder> {
        private Context mContext;

        public MyAdapter(Context context, List<String> list) {
            super(list);
            mContext = context;
            nowIndex=mList.size()-1;
        }


        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.monitor_list_item, parent, false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
                if (position == nowIndex){
                    holder.setData(Uri.parse((String)mList.get(mList.size()-1)));
                    holder.mVideoView.start();
                }
        }

        public class MyHolder extends RecyclerView.ViewHolder {
            public VideoView mVideoView;
            public MyHolder(View itemView) {
                super(itemView);
                mVideoView = (VideoView)itemView.findViewById(R.id.monitor_video);
            }

            public void setData(Uri path) {
                mVideoView.setVideoURI(path);
            }
        }
    }

    public static Intent newIntent(Context packageContext){
        Intent i = new Intent(packageContext,MonitorActivity.class);
        return i;
    }

}
