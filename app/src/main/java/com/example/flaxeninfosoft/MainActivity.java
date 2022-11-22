package com.example.flaxeninfosoft;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flaxeninfosoft.database.DbHelper;
import com.example.flaxeninfosoft.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public UserDataAdapter adapter;
    Activity activity = MainActivity.this;
    ActivityMainBinding binding;
    String firstName, lastName;
    DbHelper myDb;
    ArrayList<userModel> userModelArrayList = new ArrayList<>();
    SharedPreferences sharedPreferences;
    private long pressedTime;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = this.getSharedPreferences("myData", MODE_PRIVATE);
        firstName = sharedPreferences.getString("UFirstName", "User Not Exist");
        lastName = sharedPreferences.getString("ULastName", "");
        Log.e("TAG", "NAME::" + firstName + " " + lastName);
        binding.loginUser.setText(firstName + " " + lastName);
        init();
    }

    private void init() {
        binding.llDeleteAll.setOnClickListener(this);
        binding.llLogOut.setOnClickListener(this);

        myDb = new DbHelper(activity);
        Cursor cursor = myDb.getdata();

        if (cursor.getCount() == 0) {
            Toast.makeText(activity, "No data", Toast.LENGTH_SHORT).show();
        } else {
            userModelArrayList.clear();
            while (cursor.moveToNext()) {
                userModel userModel = new userModel();
                userModel.Id = cursor.getInt(0);
                userModel.userFirstName = cursor.getString(1);
                userModel.userLastName = cursor.getString(2);
                userModel.userAge = cursor.getString(3);
                userModel.userMobileNo = cursor.getString(6);
                userModel.userAddress = cursor.getString(7);
                userModelArrayList.add(userModel);
            }
        }
        Collections.reverse(userModelArrayList);

        adapter = new UserDataAdapter(activity, userModelArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }


    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    @Override
    public void onClick(View v) {
        if (v == binding.llDeleteAll) {
            myDb.deleteAll();
            userModelArrayList.clear();
            adapter.notifyDataSetChanged();
            ClearShar();
            binding.loginUser.setText("No Data!!");
            Toast.makeText(activity, "Delete All!!", Toast.LENGTH_SHORT).show();
        } else if (v == binding.llLogOut) {
            ClearShar();
            Intent i = new Intent(activity, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            activity.startActivity(i);
        }
    }

    private void ClearShar() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}