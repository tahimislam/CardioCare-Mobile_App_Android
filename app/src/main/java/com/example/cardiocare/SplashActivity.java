package com.example.cardiocare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.cardiocare.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding binding;
    Animation topAni,botAni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        topAni= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        botAni=AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        binding.appicon.setAnimation(topAni);
        binding.appname.setAnimation(botAni);


        //create object of Handler class object
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this,WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        },5000);
    }
}