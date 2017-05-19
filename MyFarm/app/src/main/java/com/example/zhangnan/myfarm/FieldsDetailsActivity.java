package com.example.zhangnan.myfarm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ObservableScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangnan on 17/4/28.
 */

public class FieldsDetailsActivity extends AppCompatActivity {


    //private ViewPager viewPager_chart;
    private ViewPager viewPager_banner;
//    private ImageView[] mImageViews;
    private int[] imgIdArray;

    private ImageView[] mImageViews1;
    private int[] imgIdArray1;

    private BarChart barChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fields_details);
        getWindow().setStatusBarColor(getResources().getColor(R.color.app_green));

        imgIdArray = new int[]{R.drawable.test_1, R.drawable.test_2, R.drawable.test_1,R.drawable.test_2};

        imgIdArray1 = new int[]{R.drawable.img1, R.drawable.img2, R.drawable.img3,R.drawable.img4,R.drawable.img5};
        mImageViews1 = new ImageView[imgIdArray1.length];
        for(int i=0; i<mImageViews1.length; i++){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imgIdArray1[i]);
            mImageViews1[i] = imageView;

        }

        //viewPager_chart = (ViewPager) findViewById(R.id.fields_list_item_view_pager_chart);
        //viewPager_chart.setAdapter(new ChartViewPagerAdapter());

        viewPager_banner = (ViewPager) findViewById(R.id.fields_list_item_view_pager_banner);
        viewPager_banner.setAdapter(new BannerViewPagerAdapter());


        drawChart();
        initFloatingActionButton();
    }

    private class BannerViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imgIdArray1.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager)container).removeView(mImageViews1[position % mImageViews1.length]);
        }

        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager)container).addView(mImageViews1[position % mImageViews1.length], 0);
            return mImageViews1[position % mImageViews1.length];
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

    public void drawChart(){
        barChart = (BarChart)findViewById(R.id.bar_chart);

        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1,1));
        entries.add(new BarEntry(2,2));
        entries.add(new BarEntry(3,3));
        entries.add(new BarEntry(4,4));
        entries.add(new BarEntry(5,5));
        entries.add(new BarEntry(6,6));
        BarDataSet dataSet = new BarDataSet(entries,"");
       dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData data = new BarData(dataSet);

        barChart.setDescription(null);
        barChart.setDrawValueAboveBar(false);
        barChart.setData(data);
    }


}


