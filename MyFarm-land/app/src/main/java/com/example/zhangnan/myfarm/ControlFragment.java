package com.example.zhangnan.myfarm;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhangnan.myfarm.Utils.DensityUtils;

/**
 * Created by zhangnan on 17/4/25.
 */

public class ControlFragment extends Fragment {

    private RecyclerView recyclerView;
    public String[] name = {"水泵","环流风机","照明灯","遮阳网","侧卷膜","顶卷膜"};
    private SoundAdapter soundAdapter;
    private String TAG="ControlActivity";
    private Intent i;
    private int itemHeight;

    public static  int controlItemClickPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_control, container, false);

        adjItemHeight();

        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_control_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        recyclerView.addItemDecoration(new MyFarmItemDecoration(2));
        recyclerView.setAdapter(soundAdapter = new SoundAdapter());
        return view;
    }


    private class SoundHodler extends RecyclerView.ViewHolder{

        private TextView controlTextViewName;
        private LinearLayout controlLinearLayout;
        private String controllerName;


        public SoundHodler(View view) {
            super(view);
            controlTextViewName = (TextView)itemView.findViewById(R.id.control_list_item_textview_name);

            controlLinearLayout = (LinearLayout)itemView.findViewById(R.id.control_list_item);

            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,itemHeight);
            controlLinearLayout.setLayoutParams(param);

            controlLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentActivity.fragmentContainerDetalis.removeView(FragmentActivity.mTextView);
                    controlItemClickPosition = getPosition();
                    replaceFragment(new ControlDetalisFragment(),"ControlDetalisFragment");
                }
            });

        }

    }


    private class SoundAdapter extends RecyclerView.Adapter<SoundHodler>{


        @Override
        public SoundHodler onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater= LayoutInflater.from(getActivity());
            View view=inflater.inflate(R.layout.control_list_item,parent,false);
            return new SoundHodler(view);
        }

        @Override
        public void onBindViewHolder(SoundHodler soundHodler, int position) {
            soundHodler.controlTextViewName.setText(name[position]);
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
            outRect.bottom = mSpace;
        }

        public MyFarmItemDecoration(int space) {
            this.mSpace = space;
        }

    }

    private void adjItemHeight(){
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        int result = getResources().getDimensionPixelSize(resourceId);

        WindowManager wm = getActivity().getWindowManager();
        int height = wm.getDefaultDisplay().getHeight();
        itemHeight = (height - result - 2 * 6 ) / 6;
    }


    public void replaceFragment(Fragment fragment, String tag) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container_detalis, fragment, tag);
        transaction.commit();
    }


}
