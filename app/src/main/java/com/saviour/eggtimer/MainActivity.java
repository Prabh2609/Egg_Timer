package com.saviour.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SeekBar timerSeekBar;
    private TextView countDown;
    private Button button;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = findViewById(R.id.timerSeekBar);
        countDown = findViewById(R.id.countDownTextView);
        button = findViewById(R.id.button);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int minutes = progress/60;
                int seconds = progress - 60*minutes;

                String secondString = String.valueOf(seconds);
                if(secondString.equals("0")){
                    secondString = "00";
                }
                if (seconds/10 == 0)
                {
                    secondString = "0"+String.valueOf(seconds);
                }
                countDown.setText(String.valueOf(minutes)+ " : " + secondString);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setText("Stop");
                if(timerSeekBar.isEnabled()){
                    countDownTimer = new CountDownTimer(Integer.valueOf(timerSeekBar.getProgress())*1000 - 900,1000){

                        @Override
                        public void onTick(long millisUntilFinished) {
                            timerSeekBar.setProgress((Integer.valueOf(timerSeekBar.getProgress())*1000-1000)/1000);
                        }

                        @Override
                        public void onFinish() {
                            timerSeekBar.setEnabled(true);
                            button.setText("Go");
                            timerSeekBar.setProgress(30);
                            MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.air_horn);
                            mediaPlayer.start();
                        }
                    };
                    countDownTimer.start();
                    timerSeekBar.setEnabled(false);
                }
                else{
                    countDownTimer.cancel();
                    timerSeekBar.setEnabled(true);
                    button.setText("Go");
                }
            }
        });
    }
}