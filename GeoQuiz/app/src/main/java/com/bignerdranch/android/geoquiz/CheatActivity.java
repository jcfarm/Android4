package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {
    private static final  String EXTRA_ANSWER_IS_TRUE="com.bignerdranch.android.geoquiz.answer_is_true";
    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswer;
    private static final String EXTRA_ANSWER_SHOWN="com.bignerdranch.android.geoquiz.answer_shown";
    private static final String KEY_ISCheat="ischeat";
    private boolean isCheat;
    private static final String TAG="CheatActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false);
        mAnswerTextView=(TextView)findViewById(R.id.answer_Text_View);
        mShowAnswer=(Button)findViewById(R.id.show_Answer_Button);
        isCheat=false;
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue){
                    mAnswerTextView.setText(R.string.true_button);
                }else{
                    mAnswerTextView.setText(R.string.false_button);
                }
                isCheat=true;
                setAnswerShownResult(isCheat);




            }
        });
        if(savedInstanceState!=null){
            isCheat=savedInstanceState.getBoolean(KEY_ISCheat,false);

        }

    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data=new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN,isAnswerShown);
        setResult(RESULT_OK,data);

    }
    public static boolean wasAnswerShown(Intent result){

        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN,false);

    }
    public static Intent newIntent(Context packageContext,boolean answerIsTrue){
        Intent i=new Intent(packageContext,CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE,answerIsTrue);
        return i;
    }
    @Override
    public void onSaveInstanceState(Bundle savedIntenceState){
        super.onSaveInstanceState(savedIntenceState);
        savedIntenceState.putBoolean(KEY_ISCheat,isCheat);


    }
}
