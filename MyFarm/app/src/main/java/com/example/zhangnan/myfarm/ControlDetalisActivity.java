package com.example.zhangnan.myfarm;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhangnan.myfarm.activity_information.Control_all;
import com.example.zhangnan.myfarm.activity_information.Controller;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static com.example.zhangnan.myfarm.R.drawable.green;
import static com.example.zhangnan.myfarm.R.drawable.view_pager_bottom_raduis_5dp;

/**
 * Created by zhangnan on 17/5/3.
 */

public class ControlDetalisActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private  static String  TAG="CDA";
    private static final String EXTRA_POSITION="com.example.zhangnan.myfarm.position";
    private int namePosition;
    private TextView controllerTitleTextView;
    private Button all_off_btn;
    private Button all_on_btn;
    private Context context=this;
    private String type;
    private int count;
    public static int Tag;
    private Controller c;
    private boolean ischanged=false;
    private boolean isclicked=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_details);
        getWindow().setStatusBarColor(getResources().getColor(R.color.app_green));
        namePosition=getIntent().getIntExtra(EXTRA_POSITION,0);
        controllerTitleTextView= (TextView) findViewById(R.id.control_details_title_text_view);
        controllerTitleTextView.setText(new ControlActivity().name[namePosition].toString());
        new getControllerInfoTask().execute();
        recyclerView = (RecyclerView) findViewById(R.id.control_details_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        all_on_btn= (Button) findViewById(R.id.control_details_list_item_select_all_on_btn);
        all_off_btn= (Button) findViewById(R.id.control_details_list_item_select_all_off_btn);
        all_on_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Control_all control_all=new Control_all();
                all_off_btn.setBackground(getResources().getDrawable(R.drawable.green));
                control_all.setType(type);
                control_all.setIscheck("1");
                for(int i=0;i<count;i++){
                    control_all.getId().add(String.valueOf(i+1));
                }
                postJsonTask(creatJsonString(control_all));
                ischanged=true;
                isclicked=true;
                recyclerView.setAdapter(new SoundAdapter(c));
                Toast.makeText(context,control_all.getId().toString(),Toast.LENGTH_SHORT).show();
                Toast.makeText(context,"已全部开启",Toast.LENGTH_LONG).show();
            }


        });
        all_off_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Control_all control_all=new Control_all();
                all_off_btn.setBackground(getResources().getDrawable(R.drawable.green));
                control_all.setType(type);
                control_all.setIscheck("0");
                for(int i=0;i<count;i++){
                    control_all.getId().add(String.valueOf(i+1));
                }
                postJsonTask(creatJsonString(control_all));
                ischanged=false;
                isclicked=true;
                recyclerView.setAdapter(new SoundAdapter(c));
                Toast.makeText(context,control_all.getId().toString(),Toast.LENGTH_SHORT).show();
                Toast.makeText(context,"已全部关闭",Toast.LENGTH_LONG).show();

            }
        });


    }

    private class SoundHolder extends RecyclerView.ViewHolder {
        private TextView idTextView;
        private Switch aSwitch;
        private SeekBar aSeekBar;
        private Button abutton;

        public SoundHolder(final View view) {
            super(view);

            switch (namePosition){
                case 0:case 1:case 2:case 3:{
                    idTextView= (TextView) view.findViewById(R.id.control_list_item_id_text_view);
                    aSwitch= (Switch) view.findViewById(R.id.control_list_item_switch);
                }break;
                default:{
                    idTextView= (TextView) view.findViewById(R.id.control_details_list_item2_id_text_view);
                    aSeekBar= (SeekBar) view.findViewById(R.id.control_details_list_item2_seek_bar);
                    abutton=(Button)view.findViewById(R.id.conrrol_details_list_item2_button);
                }break;
            }

        }

        public void bindHolder(Controller controller,int position){
            switch (namePosition){
                case 0:{
                    idTextView.setTag(0);
                    idTextView.setText(controller.getWater_pumpControllers().get(position).getId());
                    aSwitch.setChecked(controller.getWater_pumpControllers().get(position).getState());
                    controller.setCurrentController("water_pump");

                }break;
                case 1:{
                    idTextView.setTag(0);
                    idTextView.setText(controller.getDraught_fansController().get(position).getId());
                    aSwitch.setChecked(controller.getDraught_fansController().get(position).getState());
                    controller.setCurrentController("draught_fans");
                }break;
                case 2:{
                    idTextView.setTag(0);
                    idTextView.setText(controller.getLightControllers().get(position).getId());
                    aSwitch.setChecked(controller.getLightControllers().get(position).getState());
                    controller.setCurrentController("light");
                }break;
                case 3:{
                    idTextView.setTag(0);
                    idTextView.setText(controller.getWaningControllers().get(position).getId());
                    aSwitch.setChecked(controller.getWaningControllers().get(position).getState());
                    controller.setCurrentController("waning");
                }break;
                case 4:{
                    idTextView.setText(controller.getFilm_sideControllers().get(position).getId());
                    aSeekBar.setProgress(controller.getFilm_sideControllers().get(position).getState());
                    controller.setCurrentController("film_side");
                }break;
                case 5:{
                    idTextView.setText(controller.getFilm_topControllers().get(position).getId());
                    aSeekBar.setProgress(controller.getFilm_topControllers().get(position).getState());
                    controller.setCurrentController("film_top");
                }break;

            }
            type=controller.getCurrentController();
        }

    }
    private class SoundAdapter extends RecyclerView.Adapter<SoundHolder> {
            Controller controller;

        public SoundAdapter(Controller controller) {
            this.controller = controller;
        }
        @Override
        public SoundHolder onCreateViewHolder(ViewGroup parent, int viewTyp) {
            LayoutInflater inflater= LayoutInflater.from(ControlDetalisActivity.this);
            switch (namePosition){
                case 0:case 1:case 2:case 3:{
                    Tag = 0;
                   return new SoundHolder(inflater.inflate(R.layout.control_details_list_item,parent,false));
                }
                default: {
                    Tag = 1;
                    return new SoundHolder(inflater.inflate(R.layout.control_details_list_item2,parent,false));
                }

            }


        }

        @Override
        public void onBindViewHolder(final SoundHolder soundHodler, final int position) {
            soundHodler.bindHolder(controller,position);
            Log.d(TAG, "ischanged: "+ischanged);
            if(isclicked) {
                if (ischanged) {
                    soundHodler.aSwitch.setChecked(true);
                } else {
                    soundHodler.aSwitch.setChecked(false);
                }
            }

            if (Tag == 0){
                soundHodler.aSwitch.setTag(position);
                if (soundHodler.aSwitch.isChecked()){
                    soundHodler.aSwitch.setText("开");
                }else{
                    soundHodler.aSwitch.setText("关");
                }
                switchClick(soundHodler.aSwitch,position,controller.getCurrentController());
            }else {
                seekBarClick(soundHodler.abutton,soundHodler.aSeekBar,position,controller.getCurrentController());
            }


        }

        @Override
        public int getItemCount() {
            switch (namePosition){
                case 0:return controller.getWater_pumpControllers().size();
                case 1:return controller.getDraught_fansController().size();
                case 2:return controller.getLightControllers().size();
                case 3:return controller.getWaningControllers().size();
                case 4:return controller.getFilm_sideControllers().size();
                case 5:return controller.getFilm_topControllers().size();
                default:return controller.getWater_pumpControllers().size();
            }
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }
    }

    public static Intent newIntent(Context packageContext,int position){
        Intent i = new Intent(packageContext,ControlDetalisActivity.class);
        i.putExtra(EXTRA_POSITION,position);
        return i;
    }

    public class getControllerInfoTask extends AsyncTask<Void,Void,Controller>{


        @Override
        protected Controller doInBackground(Void... params) {
            return new VisitServer().getControllers();
        }

        @Override
        protected void onPostExecute(Controller controller) {
            SoundAdapter adapter=new SoundAdapter(controller);
            recyclerView.setAdapter(adapter);
            count=adapter.getItemCount();
            c=controller;
            Log.d(TAG, "onCreate: " +adapter.getItemCount());

        }
    }
    public static class postJsonTask extends AsyncTask<String,Void,String> {


        @Override
        protected String doInBackground(String... params) {
            Log.d(TAG, "doInBackground: 1!!!!!!!!!");
            return new VisitServer().postJson(params[0]);
        }
    }
    public void switchClick(final Switch s , final int position, final String type){

        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    s.setText("开");
                    LinkedHashMap<String,String> map=new LinkedHashMap<String, String>();
                    map.put("type",type);
                    map.put("target", String.valueOf(position+1));
                    map.put("commond","1");
                    String jsonString =new Gson().toJson(map);
                    Log.d(TAG, "switchClick:"+jsonString);
                    new postJsonTask().execute(jsonString);

                }else {
                    s.setText("关");
                    LinkedHashMap<String,String>map=new LinkedHashMap<String, String>();
                    map.put("type",type);
                    map.put("target", String.valueOf(position+1));
                    map.put("commond","0");
                    String jsonString =new Gson().toJson(map);
                    Log.d(TAG, "switchClick:"+jsonString);
                    new postJsonTask().execute(jsonString);
                }
            }});
    }
    public void seekBarClick(Button button,final SeekBar seekBar,final int position,final String type){
        final String[] s = new String[1];
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postJsonTask(s[0]);
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                LinkedHashMap<String,String>map=new LinkedHashMap<String,String>();
                map.put("type",type);
                map.put("target", String.valueOf(position+1));
                map.put("commond", String.valueOf(seekBar.getProgress()));
                s[0] =new Gson().toJson(map);
                Log.d(TAG, "onProgressChanged: "+ s[0]);


            }
        });
    }
    public void postJsonTask(String jsonstring){
        new postJsonTask().execute(jsonstring);
    }

    public String creatJsonString(Control_all control_all){
        String jsonString=new Gson().toJson(control_all);
        Log.d(TAG, "creatJsonString: " + jsonString);
        return jsonString;
    }

}
