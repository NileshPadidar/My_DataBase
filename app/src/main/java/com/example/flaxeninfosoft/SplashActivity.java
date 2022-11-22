package com.example.flaxeninfosoft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    SplashActivity activity = SplashActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(2000);

                } catch (Exception e) {
                    e.printStackTrace();

                } finally {
                    Intent i = new Intent(activity, LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    activity.startActivity(i);

                }
            }

        };
        thread.start();
    }


}