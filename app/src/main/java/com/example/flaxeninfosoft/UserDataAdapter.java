package com.example.flaxeninfosoft;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flaxeninfosoft.database.DbHelper;
import com.example.flaxeninfosoft.databinding.ItemListBinding;

import java.util.ArrayList;

public class UserDataAdapter extends RecyclerView.Adapter<UserDataAdapter.MyHolder> {
    Context context;
    ArrayList<userModel> userModelArrayList;
    DbHelper dbHelper;
    SharedPreferences sharedPreferences;

    public UserDataAdapter(Context context, ArrayList<userModel> userModelArrayList) {
        this.userModelArrayList = userModelArrayList;
        this.context = context;
        dbHelper = new DbHelper(context);
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListBinding binding = ItemListBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        userModel userModel = userModelArrayList.get(position);

        holder.binding.firstName.setText(userModel.userFirstName);
        holder.binding.lastNameName.setText(userModel.userLastName);
        holder.binding.age.setText(userModel.userAge);
        holder.binding.mobileNo.setText(userModel.userMobileNo);
        holder.binding.address.setText(userModel.userAddress);

        holder.binding.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Delete data", Toast.LENGTH_SHORT).show();
                Log.e("TAG", "onClick userMobileNo:: " + userModel.userMobileNo);
               long result = dbHelper.deleteUser(userModel.Id);

                if (userModelArrayList.size() == 1) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("myData", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                }

               if (result != -1){
                   sharedPreferences = context.getSharedPreferences("myData", MODE_PRIVATE);
                   int uid = Integer.parseInt(sharedPreferences.getString("UId", "0"));
                   if (userModel.Id == uid){
                       SharedPreferences.Editor editor = sharedPreferences.edit();
                       editor.clear();
                       editor.apply();
                   }
                   Intent i = new Intent(context, MainActivity.class);
                   i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   context.startActivity(i);
               }else {
                   Toast.makeText(context, "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
               }

            }
        });

        holder.binding.tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateUserActivity.class);
                intent.putExtra("name", userModel.userFirstName);
                intent.putExtra("age", userModel.userAge);
                intent.putExtra("address", userModel.userAddress);
                intent.putExtra("mono", userModel.userMobileNo);
                intent.putExtra("id", userModel.Id);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userModelArrayList.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        ItemListBinding binding;

        public MyHolder(@NonNull ItemListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
