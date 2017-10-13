package com.example.addy.timer;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {
    public static final int MILL_PER_SEC = 1000;
    private TextView display;
    private CountDownTimer timer;
    private EditText startPoint;
    private SoundPool soundPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.timer);
        Button button = findViewById(R.id.button);
        startPoint = findViewById(R.id.input);
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);       //add some music
        soundPool.load(this, R.raw.mysound, 1);

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        long begin = Long.parseLong(startPoint.getText().toString());
        display.setVisibility(View.VISIBLE);
        startPoint.setVisibility(View.INVISIBLE);
        showTimer(MILL_PER_SEC * begin);
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
