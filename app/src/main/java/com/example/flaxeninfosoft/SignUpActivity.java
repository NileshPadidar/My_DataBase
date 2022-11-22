package com.example.flaxeninfosoft;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flaxeninfosoft.database.DbHelper;
import com.example.flaxeninfosoft.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    Activity activity = SignUpActivity.this;
    ActivitySignUpBinding binding;
    String gender;
    boolean ganderBoolean = true;
    DbHelper myDb;
    String password;
    String confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        myDb = new DbHelper(this);
        init();

    }

    @Override
    public void onClick(View view) {
        if (view == binding.SignInId) {
            velidetion();
        }else if (view == binding.Rfemle){
            ganderBoolean = false;
        }else if (view == binding.Rmale){
            ganderBoolean = true;
        }
    }

    private void velidetion() {

        password = binding.etPassword.getText().toString().trim();
        confirmPassword = binding.etConfirmPassword.getText().toString();

        if (binding.userFirstName.getText().toString().trim().isEmpty()) {
            binding.userFirstName.requestFocus();
            binding.userFirstName.setError("First Name is required");
        } else if (binding.userLastName.getText().toString().trim().isEmpty()) {
            binding.userLastName.requestFocus();
            binding.userLastName.setError("Last Name is Required");
        } else if (binding.userAge.getText().toString().trim().isEmpty()) {
            binding.userAge.requestFocus();
            binding.userAge.setError("Age is Required");
        }  else if (gender == null) {
            Toast.makeText(activity, "Gender Required", Toast.LENGTH_LONG).show();
        }
        else if (binding.etDoB.getText().toString().trim().isEmpty()) {
            binding.etDoB.requestFocus();
            binding.etDoB.setError("Pleas Enter DOB");
        } else if (binding.etConte.getText().toString().trim().isEmpty()) {
            binding.etConte.requestFocus();
            binding.etConte.setError("Contact No. is Required");
        } else if (binding.etAddress.getText().toString().trim().isEmpty()) {
            binding.etAddress.requestFocus();
            binding.etAddress.setError("Address is Required");
        } else if (password.isEmpty()) {
            binding.etPassword.requestFocus();
            binding.etPassword.setError("Enter Password");
        } else if (confirmPassword.isEmpty()) {
            binding.etConfirmPassword.requestFocus();
            binding.etConfirmPassword.setError("Enter Confirm Password");
        } else if (!(password.equals(confirmPassword))){
            binding.etConfirmPassword.requestFocus();
            binding.etConfirmPassword.setError("Pleas Enter Correct Password");
        } else {
            boolean usercheckResult = myDb.checkuserMobile(binding.etConte.getText().toString().trim());
            Log.e("TEG","usercheckResult::"+usercheckResult);
            if (!usercheckResult) {
                boolean result = myDb.insertData(binding.userFirstName.getText().toString().trim(), binding.userLastName.getText().toString().trim(),
                        binding.userAge.getText().toString().trim(), binding.etDoB.getText().toString().trim(),
                        binding.etAddress.getText().toString().trim(), binding.etConfirmPassword.getText().toString().trim(), binding.etConte.getText().toString().trim(), ganderBoolean);
                if (result) {
                    Log.e("TEG","Intent on Otp::");
                    Toast.makeText(activity, "Registration successful", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(activity, OtpActivity.class));

                    Intent i = new Intent(activity, OtpActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    activity.startActivity(i);
                } else
                    Toast.makeText(activity, "Something Wrong", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, "Mobile No. is all ready exist ", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void init() {
        binding.SignInId.setOnClickListener(this);
        binding.Rfemle.setOnClickListener(this);
        binding.Rmale.setOnClickListener(this);
        binding.rgRadioG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(i);
                gender = radioButton.getText().toString();
            }
        });
    }
}