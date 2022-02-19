package com.example.usainbolty;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.constraintlayout.motion.widget.MotionLayout;

public class SplashScreen extends Activity {

    Handler handler,handlerer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                
            }
        },1000);
        handlerer=new Handler();
        handlerer.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashScreen.this , MainActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);

    }
}
