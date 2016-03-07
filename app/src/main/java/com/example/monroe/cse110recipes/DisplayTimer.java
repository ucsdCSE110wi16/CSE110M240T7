package com.example.monroe.cse110recipes;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.os.CountDownTimer;
import android.view.View.OnClickListener;

public class DisplayTimer extends AppCompatActivity{

    private CountDownTimer countDownTimer;
    private boolean timerStarted = false;
    private Button buttonStart;
    private TextView textView;
    private final long startTime = 100 * 1000;
    private final long interval = 1 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_timer);
        buttonStart  = (Button) findViewById(R.id.timerButton);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v){
                if(!timerStarted){
                    countDownTimer.start();
                    timerStarted=true;
                    buttonStart.setText("STOP");
                }
                else{
                    countDownTimer.cancel();
                    timerStarted=false;
                    buttonStart.setText("RESTART");
                }

            }//end of onClick Method
        });
        textView = (TextView)this.findViewById(R.id.timerTextView);
        countDownTimer = new CountDownTimerActivity(startTime,interval);
        textView.setText(textView.getText()+String.valueOf(startTime / 1000));
        }
        //setSupportActionBar(toolbar);

       /*loatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


    public class CountDownTimerActivity extends CountDownTimer{

        public CountDownTimerActivity(long startTime, long interval){
            super(startTime,interval);
        }
        @Override
        public void onFinish(){
            textView.setText("Time's up!");
        }
        @Override
        public void onTick(long millisUntilFinished){
            textView.setText(""+millisUntilFinished);
        }
    }
    }


