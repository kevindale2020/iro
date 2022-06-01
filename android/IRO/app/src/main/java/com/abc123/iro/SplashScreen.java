package com.abc123.iro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class SplashScreen extends AppCompatActivity {
    private ImageView imageView;
    //private ProgressBar loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        //loading = findViewById(R.id.loading);
        imageView = findViewById(R.id.image);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        imageView.startAnimation(animation);
        Thread timer= new Thread(){
            public void run() {
                try {
                    sleep(3000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
        //loading.setVisibility(View.VISIBLE);
    }
}
