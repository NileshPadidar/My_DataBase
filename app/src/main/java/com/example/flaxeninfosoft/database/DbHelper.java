package com.example.flaxeninfosoft.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.flaxeninfosoft.userModel;

public class DbHelper extends SQLiteOpenHelper {

    public static final String Table_Name = "users";
    public static final String KEY_ID = "KEY_ID";
    public static final String usernameFirst = "usernameFirst";
    public static final String usernameLast = "usernameLast";
    public static final String userAge = "userAge";
    public static final String userDOB = "userDOB";
    public static final String userAddress = "userAddress";
    public static final String userMobile = "userMobile";
    public static final String userPassword = "userPassword";
    public static final String gender = "gender";

    public DbHelper(@Nullable Context context) {
        super(context, "User_DataBase", null, 3);

    }

    @Override
    public void onCreate(SQLiteDatabase myDb) {
        String InsertData = "CREATE TABLE " +
                Table_Name + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                usernameFirst + " text, " +
                usernameLast + " text, " +
                userAge + " text, " +
                userDOB + " text, " +
                gender + " boolean, " +
                userMobile + " text, " +
                userAddress + " text, " +
                userPassword + " text)";

        myDb.execSQL(InsertData);
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDb, int oldVersion, int newVersion) {
        myDb.execSQL("drop Table if exists " + Table_Name);
    }

    public Boolean insertData(String FirstName, String LastName, String Age, String DoB, String Address, String password, String Mobile, boolean genderIn) {
        SQLiteDatabase myDb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(usernameFirst, FirstName);
        contentValues.put(usernameLast, LastName);
        contentValues.put(userAge, Age);
        contentValues.put(userDOB, DoB);
        contentValues.put(gender, genderIn);
        contentValues.put(userMobile, Mobile);
        contentValues.put(userAddress, Address);
        contentValues.put(userPassword, password);
        long result = myDb.insert("users", null, contentValues);
        if (result == -1) {
            Log.e("TAG", "Not Insert Data ::: "+result);
            return false;
        } else {
            Log.e("TAG", "Insert Data successfully::: "+result);
            return true;
        }
    }

    public Boolean checkuserMobile(String userMobile) {
        SQLiteDatabase myDb = this.getWritableDatabase();
        Cursor cursor = myDb.rawQuery("select * from "+ Table_Name +" where userMobile=?", new String[]{userMobile});

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkuserMobilePassword(String userMobile, String password) {
        SQLiteDatabase myDb = this.getWritableDatabase();
        Cursor cursor = myDb.rawQuery("select * from "+ Table_Name +" where userMobile=? and userPassword=?", new String[]{userMobile, password});
        Log.e("TAG", "cursor:::" + cursor.getCount());
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }

    }

    public Cursor getdataformobile(String mobile) {
        SQLiteDatabase myDb = this.getReadableDatabase();
        String sql = "select * from "+ Table_Name +" where userMobile=?";
        Cursor cursor = myDb.rawQuery(sql, new String[]{mobile});
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getdata() {
        SQLiteDatabase myDb = this.getWritableDatabase();
        Cursor cursor = myDb.rawQuery("Select * from " + Table_Name, null);
        return cursor;
    }

    public void deleteAll() {
        SQLiteDatabase myDb = this.getWritableDatabase();
        myDb.execSQL("delete from " + Table_Name);
        myDb.close();
    }

    @SuppressLint("NotifyDataSetChanged")
    public long deleteUser(int ID) {
        SQLiteDatabase myDb = this.getWritableDatabase();
      long r =   myDb.delete(Table_Name, KEY_ID + " = " + ID, null);
        Log.e("TAG", "deleteUser: "+ r );
        return r;
    }

    public long updateContact(userModel userModel) {
        SQLiteDatabase myDb = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(usernameFirst, userModel.userFirstName);
        cv.put(userAge, userModel.userAge);
        cv.put(userAddress, userModel.userAddress);
        cv.put(userMobile, userModel.userMobileNo);

        Log.e("TAG", "updateContactId: "+ userModel.Id);
        long up =   myDb.update(Table_Name, cv, KEY_ID + " = " + userModel.Id, null);
        Log.e("TAG", "UpDateUser: "+ up );
        return  up;
    }

    public int forgetPassword(String mobile , String password) {
        SQLiteDatabase myDb = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(userPassword, password);

      return myDb.update(Table_Name, cv, userMobile + " = " + mobile, null);
    }

}
