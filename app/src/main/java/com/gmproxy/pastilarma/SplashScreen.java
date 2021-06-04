package com.gmproxy.pastilarma;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.DisplayMetrics;

import com.gmproxy.Util.Constants;

/**
 * It's just a splash screen.
 */
public class SplashScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainAct = new Intent(SplashScreen.this, UserListScreen.class);
                startActivity(mainAct);
                finish();
            }
        }, 1500);
        }

    }

