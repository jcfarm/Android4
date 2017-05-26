package com.example.zhangnan.myfarm;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.zhangnan.myfarm.ChartUtils.ChartUtils;
import com.example.zhangnan.myfarm.activity_information.FieldsDetailsInfo;
import com.example.zhangnan.myfarm.activity_information.co2;
import com.example.zhangnan.myfarm.activity_information.lamp;
import com.example.zhangnan.myfarm.activity_information.light;
import com.example.zhangnan.myfarm.activity_information.nmembrane;
import com.example.zhangnan.myfarm.activity_information.salt;
import com.example.zhangnan.myfarm.activity_information.tmembrane;
import com.example.zhangnan.myfarm.activity_information.water;
import com.example.zhangnan.myfarm.activity_information.web;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.google.gson.Gson;
import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ObservableScrollView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangnan on 17/4/28.
 */

public class FieldsDetailsActivity extends AppCompatActivity {


    private ViewPager viewPager_banner;
    private ImageView[] mImageViews;
    private int[] imgIdArray;
    private LineChart lineChart;
    private RecyclerView fieldsDetailsRecyclerView;
    private String[] name ={"light","co2","water","salt"};
    private SeekBar tmembraneSeekBar;
    private SeekBar nmembraneSeekBar;
    private Switch lightSwitch;
    private Switch lampSwitch;
    private Intent intentMessage;
    private TextView nameTextView;
    private String fieldsName;
    private String TGA="FDA";
    private Button fild_sideButton;
    private Button fild_topButton;

    private FieldsDetailsInfo fieldsDetailsInfo;
    private int fieldsDetailsInfoCount = 0;
    private int defaultCount = 4;
    public Map<Integer, String> fieldsDetailsSensorsInfoMap = new HashMap();
    public Map<Integer, Float> fieldsDetailsControlsInfoMap = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fields_details);
        getWindow().setStatusBarColor(getResources().getColor(R.color.app_green));

        nameTextView = (TextView) findViewById(R.id.fields_name);
        imgIdArray = new int[]{R.drawable.img1, R.drawable.img2, R.drawable.img3,R.drawable.img4,R.drawable.img5};
        mImageViews = new ImageView[imgIdArray.length];
        for(int i=0; i<mImageViews.length; i++){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imgIdArray[i]);
            mImageViews[i] = imageView;

        }

        fieldsDetailsRecyclerView = (RecyclerView)findViewById(R.id.fields_details_recycler_view);
        fieldsDetailsRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        fieldsDetailsRecyclerView.addItemDecoration(new MyFieldsDetailsItemDecoration(5));
        fieldsDetailsRecyclerView.setAdapter(new FieldsDetailsAdapter());

        viewPager_banner = (ViewPager) findViewById(R.id.fields_list_item_view_pager_banner);
        viewPager_banner.setAdapter(new BannerViewPagerAdapter());

        lineChart = (LineChart) findViewById(R.id.line_chart);
        ChartUtils.initChart(lineChart);
        ChartUtils.notifyDataSetChanged(lineChart, getData(), ChartUtils.dayValue);

        initFloatingActionButton();
        getIntentMessage();
        fieldsDetailsSensorsInfoToString();
        fieldsDetailsControlInfoToString();
        initControls();
        changeSwitchText();

        nameTextView.setText(fieldsName);


    }

    private List<Entry> getData() {
        List<Entry> values = new ArrayList<>();
        values.add(new Entry(0, 13));
        values.add(new Entry(1, 14));
        values.add(new Entry(2, 15));
        values.add(new Entry(3, 30));
        values.add(new Entry(4, 25));
        values.add(new Entry(5, 1));
        values.add(new Entry(6, 20));
        return values;
    }

    private  class FieldsDetailsHodler extends RecyclerView.ViewHolder {

        public  TextView sensorsNameTextView;
        public  TextView sensorsDetailsTextView;

        public FieldsDetailsHodler(LayoutInflater inflater, ViewGroup container) {
            super(inflater.inflate(R.layout.fields_details_list_item,container,false));
            sensorsNameTextView = (TextView)itemView.findViewById(R.id.sensors_name);
            sensorsDetailsTextView = (TextView)itemView.findViewById(R.id.sensors_details);
        }
    }

    private class FieldsDetailsAdapter extends RecyclerView.Adapter<FieldsDetailsHodler>{

        @Override
        public FieldsDetailsHodler onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater= LayoutInflater.from(FieldsDetailsActivity.this);
            return new FieldsDetailsHodler(inflater,parent);
        }

        @Override
        public void onBindViewHolder(FieldsDetailsHodler fieldsDetailsHodler, int position) {
            fieldsDetailsHodler.sensorsNameTextView.setTextSize(15);
            fieldsDetailsHodler.sensorsNameTextView.setTextColor(Color.parseColor("#e0e0e0"));
            fieldsDetailsHodler.sensorsNameTextView.setText("正在加载...");

            //从fieldsDetailsSensorsInfoMap取出值给每一个item更新数据
            if (!fieldsDetailsControlsInfoMap.isEmpty()) {
                fieldsDetailsHodler.sensorsDetailsTextView.setText(fieldsDetailsSensorsInfoMap.get(position));
            }

        }

        @Override
        public int getItemCount() {
            if (fieldsDetailsInfoCount == 0){
                fieldsDetailsInfoCount = defaultCount;
            }
            return fieldsDetailsInfoCount;
        }
    }

    private class BannerViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imgIdArray.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager)container).removeView(mImageViews[position % mImageViews.length]);
        }

        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager)container).addView(mImageViews[position % mImageViews.length], 0);
            return mImageViews[position % mImageViews.length];
        }
    }



    public static Intent newIntent(Context packageContext){
        Intent i = new Intent(packageContext,FieldsDetailsActivity.class);
        return i;
    }

    public void initFloatingActionButton(){
        ObservableScrollView scrollView = (ObservableScrollView) findViewById(R.id.fab_scroll_view);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floating_action_button);
        fab.attachToScrollView(scrollView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = MonitorActivity.newIntent(FieldsDetailsActivity.this);
                startActivity(i);
            }
        });
    }


    public class MyFieldsDetailsItemDecoration extends RecyclerView.ItemDecoration{
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

        public MyFieldsDetailsItemDecoration(int space) {
            this.mSpace = space;
        }

    }

    public void initControls(){
        int k = 0;
        lampSwitch = (Switch) findViewById(R.id.fields_details_fan_switch);
        lightSwitch = (Switch) findViewById(R.id.fields_details_light_switch);
        tmembraneSeekBar = (SeekBar) findViewById(R.id.tmembrane_seek_bar);
        nmembraneSeekBar = (SeekBar) findViewById(R.id.nmembrane_seek_bar);
        fild_sideButton=(Button)findViewById(R.id.fields_details_film_side_sure_button);
        fild_topButton=(Button)findViewById(R.id.fields_details_film_top_sure_button);
        if (!fieldsDetailsControlsInfoMap.isEmpty()){
                if ((float)fieldsDetailsControlsInfoMap.get(k++) == 1.0f){
                    lampSwitch.setChecked(true);
                }else {
                    lampSwitch.setChecked(false);
                }

                if ((float)fieldsDetailsControlsInfoMap.get(k++) == 1.0f){
                    lightSwitch.setChecked(true);
                }else lampSwitch.setChecked(false);


                tmembraneSeekBar.setProgress((int) (fieldsDetailsControlsInfoMap.get(k++) * 100));
                nmembraneSeekBar.setProgress((int) (fieldsDetailsControlsInfoMap.get(k++) * 100));
            }
    }

    public void changeSwitchText(){
        final String[] s = new String[1];
        final String[] t = new String[1];
            fild_sideButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(fieldsDetailsInfo!=null){
                    new ControlDetalisActivity().postJsonTask(s[0]);}
                }
            });
            fild_topButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(fieldsDetailsInfo!=null){
                    new ControlDetalisActivity.postJsonTask().execute(t[0]);}
                }
            });
            lightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                            lightSwitch.setText("开");
                            if (fieldsDetailsInfo!=null) {
                            LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                            map.put("type", "light");
                            map.put("target", String.valueOf(fieldsDetailsInfo.getWeb()[0].getId()));
                            map.put("commond", "1");
                            String jsonString = new Gson().toJson(map);
                            Log.d(TGA, "switchClick:" + jsonString);
                            new ControlDetalisActivity.postJsonTask().execute(jsonString);}

                    } else {
                            lightSwitch.setText("关");
                            if (fieldsDetailsInfo!=null) {
                            LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                            map.put("type", "light");
                            map.put("target", String.valueOf(fieldsDetailsInfo.getWeb()[0].getId()));
                            map.put("commond", "0");
                            String jsonString = new Gson().toJson(map);
                            Log.d(TGA, "switchClick:" + jsonString);
                            new ControlDetalisActivity.postJsonTask().execute(jsonString);}
                    }

                }});
            lampSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        lampSwitch.setText("开");
                        if (fieldsDetailsInfo!=null) {
                            LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                            map.put("type", "draught_fan");
                            map.put("target", String.valueOf(fieldsDetailsInfo.getLamp()[0].getId()));
                            map.put("commond", "1");
                            String jsonString = new Gson().toJson(map);
                            Log.d(TGA, "switchClick:" + jsonString);
                            new ControlDetalisActivity.postJsonTask().execute(jsonString);}

                    } else {
                        lampSwitch.setText("关");
                        if (fieldsDetailsInfo!=null) {
                            LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                            map.put("type", "draught_fan");
                            map.put("target", String.valueOf(fieldsDetailsInfo.getLamp()[0].getId()));
                            map.put("commond", "0");
                            String jsonString = new Gson().toJson(map);
                            Log.d(TGA, "switchClick:" + jsonString);
                            new ControlDetalisActivity.postJsonTask().execute(jsonString);}
                    }

                }});
            tmembraneSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if(fieldsDetailsInfo!=null){
                        LinkedHashMap<String,String>map=new LinkedHashMap<String,String>();
                        map.put("type","film_side");
                        map.put("target", String.valueOf(fieldsDetailsInfo.getTmembrane()[0].getId()));
                        map.put("commond", String.valueOf(seekBar.getProgress()));
                        s[0] =new Gson().toJson(map);
                        Log.d(TGA, "onProgressChanged: "+ s[0]);
                        }
                }
            });
            nmembraneSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if(fieldsDetailsInfo!=null){
                        LinkedHashMap<String,String>map=new LinkedHashMap<String,String>();
                        map.put("type","film_top");
                        map.put("target", String.valueOf(fieldsDetailsInfo.getNmembrane()[0].getId()));
                        map.put("commond", String.valueOf(seekBar.getProgress()));
                        t[0] =new Gson().toJson(map);
                        Log.d(TGA, "onProgressChanged: "+t);
                        }
                }
            });

            //new ControlDetalisActivity().switchClick(lightSwitch,fieldsDetailsInfo.getWeb()[0].getId(),"light");
            //new ControlDetalisActivity().switchClick(lampSwitch,fieldsDetailsInfo.getWeb()[0].getId(),"draught");



    }



    //得到上一个页面intent传来的FieldsDetailsInfo
    private void getIntentMessage(){
        intentMessage = getIntent();
        if (intentMessage.getExtras()!=null){
            int position = (int) intentMessage.getExtras().get("position");
            fieldsName = (String) intentMessage.getExtras().get("name");
            System.out.println("*******************"+fieldsName);
            if (!MqttMessages.messageMap.isEmpty())
            {
                fieldsDetailsInfo =  MqttMessages.messageMap.get(position);
                fieldsDetailsInfoCount = fieldsDetailsInfo.getCount();
            }
        }

    }

    //将FieldsDetailsInfo对象中的sensor元素的每个数组以String存放在fieldsDetailsSensorsInfoMap中
    private void fieldsDetailsSensorsInfoToString(){
        if (fieldsDetailsInfo != null){
            int k = 0;
            for (int i = 0;i < fieldsDetailsInfo.getLight().length;i++){
                light[] l = fieldsDetailsInfo.getLight();
                fieldsDetailsSensorsInfoMap.put(k++,String.valueOf(l[i].getC())+
                        String.valueOf(l[i].getLux())+
                        String.valueOf(l[i].getPh()));
            }

            for (int i = 0;i < fieldsDetailsInfo.getCo2().length;i++){
                co2[] c = fieldsDetailsInfo.getCo2();
                fieldsDetailsSensorsInfoMap.put(k++,String.valueOf(c[i].getC())+
                        String.valueOf(c[i].getCo2())+
                        String.valueOf(c[i].getPh()));
            }

            for (int i = 0;i < fieldsDetailsInfo.getWater().length;i++){
                water[] w = fieldsDetailsInfo.getWater();
                fieldsDetailsSensorsInfoMap.put(k++,String.valueOf(w[i].getC())+
                        String.valueOf(w[i].getPe()));
            }

            for (int i = 0;i < fieldsDetailsInfo.getSalt().length;i++){
                salt[] s = fieldsDetailsInfo.getSalt();
                fieldsDetailsSensorsInfoMap.put(k++,String.valueOf(s[i].getMg()+
                        s[i].getUs()));
            }

        }

    }

    //同上
    private void fieldsDetailsControlInfoToString(){

        if (fieldsDetailsInfo != null){
            int k = 0;

            for (int i = 0;i < fieldsDetailsInfo.getLamp().length;i++){
                lamp[] l = fieldsDetailsInfo.getLamp();
                fieldsDetailsControlsInfoMap.put(k++, (float) l[i].getValue());
            }

            for (int i = 0;i < fieldsDetailsInfo.getWeb().length;i++){
                web[] w = fieldsDetailsInfo.getWeb();
                fieldsDetailsControlsInfoMap.put(k++, (float) w[i].getValue());
            }

            for (int i = 0;i < fieldsDetailsInfo.getNmembrane().length;i++){
                nmembrane[] n = fieldsDetailsInfo.getNmembrane();
                fieldsDetailsControlsInfoMap.put(k++,n[i].getValue());
            }

            for (int i = 0;i < fieldsDetailsInfo.getTmembrane().length;i++){
                tmembrane[] t = fieldsDetailsInfo.getTmembrane();
                fieldsDetailsControlsInfoMap.put(k++,t[i].getValue());
            }
        }

    }


}


