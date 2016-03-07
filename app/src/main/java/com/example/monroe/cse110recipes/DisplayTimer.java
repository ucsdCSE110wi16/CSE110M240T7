package com.example.monroe.cse110recipes;

import android.media.MediaPlayer;
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

/**
 * Created by Hannah-Marie
 * Class that handles how the timer is displayed for the individual instructions
 */
public class DisplayTimer extends AppCompatActivity{

    private CountDownTimer countDownTimer;
    private boolean timerStarted = false;
    private Button buttonStart; //button to start, stop and restart the timer
    private TextView textView; //textView to display the amount of time remaining
    private int startTime; //time at which the timer starts
    private int interval = 500; //how much time the timer will be decreased
    private MediaPlayer mp;
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
                    mp.stop();
                    mp.prepareAsync();
                }

            }//end of onClick Method


        });

        String tempString = getIntent().getExtras().getString("string");
        startTime = Integer.parseInt(tempString.replaceAll("[\\D]",""));
        startTime = startTime * 1000 * 60;
        mp = MediaPlayer.create(getBaseContext(),R.raw.theonetteshort);


        textView = (TextView)this.findViewById(R.id.timerTextView);
        countDownTimer = new CountDownTimerActivity(startTime,interval);
        textView.setText(textView.getText() + String.valueOf(startTime / (1000 * 60)));
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

    /**
     * Method to play Timer sound
     * Called in CountDownTimerActivity

    public void playSound(){
        MediaPlayer mp = MediaPlayer.create(getBaseContext(),R.raw.onetteshort);
        mp.start();
    }*/

    public class CountDownTimerActivity extends CountDownTimer{

        public CountDownTimerActivity(int startTime, int interval){
            super(startTime,interval);
        }
        @Override
        public void onFinish(){
            textView.setText("Time's up!");
            mp.start();
        }
        @Override
        public void onTick(long millisUntilFinished){
            long seconds = millisUntilFinished/1000;
            textView.setText(String.format("%02d",seconds/60) + ":" + String.format("%02d",seconds%60));
        }
    }
    }


