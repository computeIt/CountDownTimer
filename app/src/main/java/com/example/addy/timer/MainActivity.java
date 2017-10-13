package com.example.addy.timer;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int MILL_PER_SEC = 1000;
    private TextView display;
    private CountDownTimer timer;
    private EditText startPoint;
    private SoundPool soundPool;
    private Button start, stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = (TextView) findViewById(R.id.timer);
        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);

        startPoint = (EditText) findViewById(R.id.input);
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);       //add some music
        soundPool.load(this, R.raw.mysound, 1);


        start.setOnClickListener(this);
        stop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start: {
                long begin = Long.parseLong(startPoint.getText().toString());
                display.setVisibility(View.VISIBLE);
                startPoint.setVisibility(View.INVISIBLE);
                showTimer(MILL_PER_SEC * begin);
                break;
            }
            case R.id.stop:{
                startPoint.setText("0");
                startPoint.setVisibility(View.VISIBLE);
                display.setVisibility(View.INVISIBLE);
                break;
            }
            default:
                break;
        }
    }

    private void showTimer(long countdownMillis) {
        if(timer != null)
            timer.cancel();
        timer = new CountDownTimer(countdownMillis, MILL_PER_SEC) {
            @Override
            public void onTick(long millisUntilFinished) {
                display.setText("counting down: " + millisUntilFinished/MILL_PER_SEC);
            }

            @Override
            public void onFinish() {
                int i = soundPool.play(1, 1, 1, 1, 0, 1);       //added some music
                display.setText("finish!!!");
                startPoint.setVisibility(View.VISIBLE);
            }
        }.start();
    }
}
