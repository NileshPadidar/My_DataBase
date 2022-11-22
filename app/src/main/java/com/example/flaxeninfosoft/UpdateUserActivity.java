package com.example.flaxeninfosoft;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flaxeninfosoft.database.DbHelper;
import com.example.flaxeninfosoft.databinding.ActivityUpdateUserBinding;

public class UpdateUserActivity extends AppCompatActivity implements View.OnClickListener {
    Activity activity = UpdateUserActivity.this;
    ActivityUpdateUserBinding binding;
    DbHelper myDb;
    String NAME, AGE, ADDRESS,MONO;
    int Id;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        myDb = new DbHelper(activity);
        init();
    }


    private void init() {

        Intent intent = getIntent();
        if (intent != null){
            NAME = intent.getStringExtra("name");
            AGE = intent.getStringExtra("age");
            ADDRESS = intent.getStringExtra("address");
            MONO = intent.getStringExtra("mono");
            Id = intent.getIntExtra("id",1);
            Log.e("TAG", "initId: "+ Id );
        }

        binding.tvUpdateBtn.setOnClickListener(this);

        binding.etUserName.setText(NAME);
        binding.etUserAge.setText(AGE);
        binding.etUserAddress.setText(ADDRESS);
        binding.etUserMobile.setText(MONO);

    }


    @Override
    public void onClick(View v) {
        if (v == binding.tvUpdateBtn) {
            if (binding.etUserName.getText().toString().trim().isEmpty()) {
                binding.etUserName.requestFocus();
                binding.etUserName.setError("This field can't be empty");
            } else if (binding.etUserAge.getText().toString().trim().isEmpty()) {
                binding.etUserAge.requestFocus();
                binding.etUserAge.setError("This field can't be empty");
            } else if (binding.etUserAddress.getText().toString().trim().isEmpty()) {
                binding.etUserAddress.requestFocus();
                binding.etUserAddress.setError("This field can't be empty");
            }else {
                sharedPreferences = this.getSharedPreferences("myData", MODE_PRIVATE);
                if (NAME.equals(sharedPreferences.getString("UFirstName", "User Not Exist"))){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("UFirstName", binding.etUserName.getText().toString().trim());
                    editor.apply();
                }
                upDateData();
            }
        }
    }

    private void upDateData() {
        userModel userModel = new userModel();
        userModel.Id = Id;
        userModel.userMobileNo = binding.etUserMobile.getText().toString().trim();
        userModel.userFirstName = binding.etUserName.getText().toString().trim();
        userModel.userAge = binding.etUserAge.getText().toString().trim();
        userModel.userAddress = binding.etUserAddress.getText().toString();

      long result =  myDb.updateContact(userModel);

      if(result != -1){
          Intent i = new Intent(activity, MainActivity.class);
          i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
          activity.startActivity(i);
      }else {
          Toast.makeText(activity, "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
      }

    }
}