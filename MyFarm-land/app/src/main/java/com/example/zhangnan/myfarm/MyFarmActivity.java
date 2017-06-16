package com.example.zhangnan.myfarm;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.*;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhangnan.myfarm.ChartUtils.ChartUtils;
import com.example.zhangnan.myfarm.Utils.DensityUtils;
import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyFarmActivity extends AppCompatActivity {

    private String[] name={"My Farm","远程控制","机器人控制","报警信息","历史图表","历史记录","设置","关于我们"};
    private  String[] values = new String[]{ "操作记录", "日志", "检查更新", "设置","退出登录",};
    private  int[] img = new int[]{R.drawable.ic_action_record,R.drawable.ic_action_log,
            R.drawable.ic_action_update,R.drawable.ic_action_setting, R.drawable.ic_action_quit};
    private List<Map<String, Object>> data;

    private int itemImages[] = {
            R.drawable.fielddetails,
            R.drawable.control2,
            R.drawable.robotcontrol,
            R.drawable.alert,
            R.drawable.chart,
            R.drawable.pen,
            R.drawable.setting,
            R.drawable.aboutus
    };

    private Intent i;
    private ImageView menu;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private LinearLayout mDrawerLinearLayout;
    private ImageView mDrawerImageView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerArrowDrawable drawerArrow;
    private boolean drawerArrowColor;
    public static int selectItem;
    private int itemHeight;

    private static boolean isExit = false;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfarm);

        adjItemHeight();
        getWindow().setStatusBarColor(getResources().getColor(R.color.app_green));

        data = getData();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.my_farm_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        recyclerView.addItemDecoration(new MyFarmItemDecoration(2));
        recyclerView.setAdapter(new SoundAdapter());
        InitMenuBar();
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    private class SoundHodler extends RecyclerView.ViewHolder implements View.OnClickListener{


        private LinearLayout listLinearLayout;
        private ImageView listImageView;
        private TextView listTextView;

        public SoundHodler(LayoutInflater inflater, ViewGroup container) {
            super(inflater.inflate(R.layout.myfarm_list_item,container,false));

            listLinearLayout = (LinearLayout)itemView.findViewById(R.id.list_item_linearlayout);
            listImageView = (ImageView)itemView.findViewById(R.id.list_item_imageview);
            listTextView = (TextView)itemView.findViewById(R.id.list_item_textview);
            listLinearLayout.setOnClickListener(this);

            FrameLayout.LayoutParams param = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,itemHeight);
            listLinearLayout.setLayoutParams(param);

        }


        @Override
        public void onClick(View view) {
            selectItem = getPosition();
            i= FragmentActivity.newIntent(MyFarmActivity.this);
            startActivity(i);

        }

    }


    private class SoundAdapter extends RecyclerView.Adapter<SoundHodler>{

        @Override
        public SoundHodler onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater= LayoutInflater.from(MyFarmActivity.this);
            return new SoundHodler(inflater,parent);
        }

        @Override
        public void onBindViewHolder(SoundHodler soundHodler, int position) {
            soundHodler.listTextView.setText(name[position]);

            soundHodler.listImageView.setImageDrawable(getDrawable(itemImages[position]));

        }

        @Override
        public int getItemCount() {
            return name.length;
        }
    }

    private class MyFarmItemDecoration extends RecyclerView.ItemDecoration{
        int mSpace;

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            if (parent.getChildAdapterPosition(view)%4 == 0) {
                outRect.bottom = mSpace;
            } else{
                outRect.left = mSpace;
                outRect.bottom = mSpace;
            }

        }

        public MyFarmItemDecoration(int space) {
            this.mSpace = space;
        }

    }

    private void adjItemHeight(){
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        int result = getResources().getDimensionPixelSize(resourceId);

        WindowManager wm = getWindowManager();
        int height = wm.getDefaultDisplay().getHeight();
        itemHeight = (height - result - DensityUtils.dip2px(this,80) - 4)/2;
    }


    public static Intent newIntent(Context packageContext){
        Intent i = new Intent(packageContext,MyFarmActivity.class);
        return i;
    }

    private void InitMenuBar(){

        menu = (ImageView) findViewById(R.id.my_farm_menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mDrawerLayout.isDrawerOpen(mDrawerLinearLayout)) {
                    mDrawerLayout.closeDrawer(mDrawerLinearLayout);
                } else {
                    mDrawerLayout.openDrawer(mDrawerLinearLayout);
                }

            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLinearLayout = (LinearLayout) findViewById(R.id.nav_drawer_linear_layout);
        mDrawerList = (ListView) findViewById(R.id.nav_drawer);
        mDrawerImageView =(ImageView)findViewById(R.id.nav_drawer_image_view);

        mDrawerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"还未设置",Toast.LENGTH_LONG).show();
            }
        });

        mDrawerLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        drawerArrow = new DrawerArrowDrawable(this) {
            @Override
            public boolean isLayoutRtl() {
                return false;
            }
        };
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                drawerArrow, R.string.drawer_open,
                R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };


        String[] from = {"img","text"};
        int[] to = {R.id.drawer_layout_image,R.id.drawer_layout_text};
        mDrawerList.setAdapter(new SimpleAdapter(this,data,R.layout.drawer_layout_list_item,from,to));
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        Toast.makeText(getApplicationContext(),"还未设置",Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(),"还未设置",Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(),"还未设置",Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        Toast.makeText(getApplicationContext(),"还未设置",Toast.LENGTH_LONG).show();
                        break;
                    case 4:
                        Toast.makeText(getApplicationContext(),"还未设置",Toast.LENGTH_LONG).show();
                        break;
                    case 5:
                        Toast.makeText(getApplicationContext(),"还未设置",Toast.LENGTH_LONG).show();
                        break;
                }

            }
        });

    }

    private List<Map<String, Object>> getData()
    {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        for(int i = 0;i<values.length;i++)
        {
            map = new HashMap<String, Object>();
            map.put("img", img[i]);
            map.put("text", values[i]);
            list.add(map);
        }
        return list;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }
}
