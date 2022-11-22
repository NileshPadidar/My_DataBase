package com.example.flaxeninfosoft;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.flaxeninfosoft.database.DbHelper;
import com.example.flaxeninfosoft.databinding.ActivityForgotPasswordBinding;

public class ForgotPasswordActivity extends AppCompatActivity {
  Activity activity = ForgotPasswordActivity.this;
  ActivityForgotPasswordBinding binding;
  DbHelper myDb = new DbHelper(activity);
    String Mo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

    }

    private void init(){
        Intent intent = getIntent();
        if (intent != null){
            Mo =  intent.getStringExtra("Mobile");
        }

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean is = myDb.checkuserMobile(Mo);

                Log.e("TAG", "onClick: "+ is);
                if (is){
                    if (binding.etUserPassword.getText().toString().trim().equals(binding.etUserConfirmPassword.getText().toString().trim())){
                       int result = myDb.forgetPassword(Mo,binding.etUserConfirmPassword.getText().toString().trim());

                       if (result != -1){
                           Toast.makeText(activity, "Password Change Successfully!!", Toast.LENGTH_SHORT).show();
                           Intent i = new Intent(activity, LoginActivity.class);
                           i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                           activity.startActivity(i);
                       }else {
                           Toast.makeText(activity, "Something want wrong!!", Toast.LENGTH_SHORT).show();
                       }
                    }else {
                        Toast.makeText(activity, "Pleas enter correct Password", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(activity, "Mobile No. is not Exist!! \n Pleas enter correct Mo.No.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}