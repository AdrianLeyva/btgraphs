package com.viintec.btgraphs.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.viintec.btgraphs.R;
import com.viintec.btgraphs.commons.Util;
import com.viintec.btgraphs.presentation.finder.FinderActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        throwSplash();
    }

    private void throwSplash(){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Util.sendAndFinish(SplashActivity.this, FinderActivity.class);
            }
        };

        timer.schedule(timerTask, 3000);
    }
}
