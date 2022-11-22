package com.example.flaxeninfosoft;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flaxeninfosoft.database.DbHelper;
import com.example.flaxeninfosoft.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String myData = "myData";
    Activity activity = LoginActivity.this;
    ActivityLoginBinding binding;
    DbHelper myDb;
    Boolean isClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       /* requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
*/
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        myDb = new DbHelper(this);

        init();
    }

    private void init() {

        binding.signUp.setOnClickListener(this);
        binding.tvForgotPassword.setOnClickListener(this);
        binding.tvLoginBtn.setOnClickListener(this);
        binding.ivShowHidePassword.setOnClickListener(this);
        binding.tvNew.setOnClickListener(this);

        //session manegmant
        SharedPreferences sharedPreferences = this.getSharedPreferences(myData, MODE_PRIVATE);
        if (sharedPreferences.contains("UFirstName") && sharedPreferences.contains("ULastName")) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

    }

    @Override
    public void onClick(View view) {
        if (view == binding.signUp) {
            startActivity(new Intent(activity, SignUpActivity.class));
        } else if (view == binding.tvForgotPassword) {
            if (binding.etUserEmail.getText().toString().trim().isEmpty()) {
                Toast.makeText(activity, "Pleas Enter Mo. No.!!", Toast.LENGTH_SHORT).show();
            } else {
                Intent i = new Intent(activity, ForgotPasswordActivity.class);
                i.putExtra("Mobile", binding.etUserEmail.getText().toString().trim());
                startActivity(i);
            }
        } else if (view == binding.tvNew) {
            startActivity(new Intent(activity, SpinnerActivity.class));
        } else if (view == binding.ivShowHidePassword) {
            isClicked = !isClicked;
            if (isClicked) {
                binding.ivShowHidePassword.setImageResource(R.drawable.ic_eye_off_1);
                binding.etUserPassword.setTransformationMethod
                        (HideReturnsTransformationMethod.getInstance());
                binding.etUserPassword.setSelection(binding.etUserPassword.length());
            } else {
                binding.ivShowHidePassword.setImageResource(R.drawable.ic_visibility);
                binding.etUserPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                binding.etUserPassword.setSelection(binding.etUserPassword.length());
            }
        } else if (view == binding.tvLoginBtn) {
            if (binding.etUserEmail.getText().toString().trim().isEmpty()) {
                binding.etUserEmail.requestFocus();
                binding.etUserEmail.setError("Contact is required");
            } else if (binding.etUserPassword.getText().toString().trim().isEmpty()) {
                binding.etUserPassword.requestFocus();
                binding.etUserPassword.setError("Password Required");
            } else {
                Boolean result = myDb.checkuserMobilePassword(binding.etUserEmail.getText().toString().trim(), binding.etUserPassword.getText().toString().trim());
                Log.e("TAG", "login result:::" + result);
                if (result) {

                    Cursor cursor = myDb.getdataformobile(binding.etUserEmail.getText().toString().trim());

                    String UserId = cursor.getString(0);
                    String firstName = cursor.getString(1);
                    String lastName = cursor.getString(2);
                    String Age = cursor.getString(3);
                    String DoB = cursor.getString(4);
                    String gender = cursor.getString(5);
                    String mobileNo = cursor.getString(6);
                    String Address = cursor.getString(7);
                    String Password = cursor.getString(8);
                    Log.d("check", "data get " + firstName);

                    SharedPreferences sharedPreferences = getSharedPreferences("myData", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("UId", UserId);
                    editor.putString("UFirstName", firstName);
                    editor.putString("ULastName", lastName);
                    editor.putString("UPassword", Password);
                    editor.putString("UAge", Age);
                    editor.putString("UMobile", mobileNo);
                    editor.putString("Gender", gender);
                    editor.putString("UDOB", DoB);
                    editor.putString("UAddress", Address);
                    editor.apply();
                    Log.d("check", "data shared " + editor);

                    Toast.makeText(activity, "--Successful--", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(activity, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    activity.startActivity(i);
                } else {
                    Toast.makeText(activity, "Invalid user please registration", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
}