package com.example.flaxeninfosoft;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.flaxeninfosoft.databinding.ActivityOtpBinding;

import java.text.DecimalFormat;
import java.util.Random;

public class OtpActivity extends AppCompatActivity {
    Activity activity = OtpActivity.this;
    ActivityOtpBinding binding ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String otp= new DecimalFormat("000000").format(new Random().nextInt(999999));
        Log.e("TAG","otp"+otp);
        binding.etOtp.setText(otp);

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                activity.startActivity(i);
                //startActivity(new Intent(activity,LoginActivity.class));
            }
        });

        binding.tvResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp= new DecimalFormat("000000").format(new Random().nextInt(999999));
                binding.etOtp.setText(otp);
            }
        });

    }
}