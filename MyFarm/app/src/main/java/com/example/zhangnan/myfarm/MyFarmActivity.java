package com.example.zhangnan.myfarm;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;


public class MyFarmActivity extends AppCompatActivity {

    private String[] name={"My Farm","远程控制","机器人控制","报警信息","历史图表","历史数据","设置","关于我们"};
    private  String[] values = new String[]{
            "Stop Animation (Back icon)",
            "Stop Animation (Home icon)",
            "Start Animation",
            "Change Color",
            "GitHub Page",
            "Share",
            "Rate"
    };

    private int itemImages[] = {
            R.drawable.farm,
            R.drawable.kongzhi,
            R.drawable.jiankong,
            R.drawable.jinggao,
            R.drawable.huanjing,
            R.drawable.shuju,
            R.drawable.shezhi,
            R.drawable.image_1
    };

    private Intent i;
    private ImageView menu;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerArrowDrawable drawerArrow;
    private boolean drawerArrowColor;

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

        getWindow().setStatusBarColor(getResources().getColor(R.color.app_green));

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.my_farm_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.addItemDecoration(new MyFarmItemDecoration(2));
        recyclerView.setAdapter(new SoundAdapter());
        InitMenuBar();


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
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
        }


        @Override
        public void onClick(View view) {
//            listLinearLayout.setBackground(getResources().getDrawable(R.drawable.my_farm_list_item_background));
            switch (getPosition()){
                case 0:i = FieldsActivity.newIntent(MyFarmActivity.this);
                    startActivity(i);overridePendingTransition(R.anim.zoomin,R.anim.zoomout);break;
                case 1:i = ControlActivity.newIntent(MyFarmActivity.this);
                    startActivity(i);overridePendingTransition(R.anim.zoomin,R.anim.zoomout);break;
            }
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

    public class MyFarmItemDecoration extends RecyclerView.ItemDecoration{
        int mSpace;

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            if (parent.getChildAdapterPosition(view)%2 == 0) {
                outRect.right = mSpace;
                outRect.bottom = mSpace;
                outRect.top = mSpace;
            } else{
                outRect.left = mSpace;
                outRect.bottom = mSpace;
                outRect.top = mSpace;
            }

        }

        public MyFarmItemDecoration(int space) {
            this.mSpace = space;
        }

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

                if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                    mDrawerLayout.closeDrawer(mDrawerList);
                } else {
                    mDrawerLayout.openDrawer(mDrawerList);
                }

            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.navdrawer);


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
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        mDrawerList.setAdapter(adapter);
//        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                switch (position) {
//                    case 0:
//                        mDrawerToggle.setAnimateEnabled(false);
//                        drawerArrow.setProgress(1f);
//                        break;
//                    case 1:
//                        mDrawerToggle.setAnimateEnabled(false);
//                        drawerArrow.setProgress(0f);
//                        break;
//                    case 2:
//                        mDrawerToggle.setAnimateEnabled(true);
//                        mDrawerToggle.syncState();
//                        break;
//                    case 3:
//                        if (drawerArrowColor) {
//                            drawerArrowColor = false;
//                            drawerArrow.setColor(R.color.ldrawer_color);
//                        } else {
//                            drawerArrowColor = true;
//                            drawerArrow.setColor(R.color.drawer_arrow_second_color);
//                        }
//                        mDrawerToggle.syncState();
//                        break;
//                    case 4:
//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/IkiMuhendis/LDrawer"));
//                        startActivity(browserIntent);
//                        break;
//                    case 5:
//                        Intent share = new Intent(Intent.ACTION_SEND);
//                        share.setType("text/plain");
//                        share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        share.putExtra(Intent.EXTRA_SUBJECT,
//                                getString(R.string.app_name));
//                        share.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_description) + "\n" +
//                                "GitHub Page :  https://github.com/IkiMuhendis/LDrawer\n" +
//                                "Sample App : https://play.google.com/store/apps/details?id=" +
//                                getPackageName());
//                        startActivity(Intent.createChooser(share,
//                                getString(R.string.app_name)));
//                        break;
//                    case 6:
//                        String appUrl = "https://play.google.com/store/apps/details?id=" + getPackageName();
//                        Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(appUrl));
//                        startActivity(rateIntent);
//                        break;
//                }
//
//            }
//        });

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
