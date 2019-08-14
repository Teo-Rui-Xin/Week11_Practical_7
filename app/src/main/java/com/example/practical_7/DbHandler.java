package com.example.practical_7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHandler extends SQLiteOpenHelper {
    //DATABASE NAME
    public static final String DATABASE_NAME = "users.db";

    //DATABASE VERSION
    public static final int DATABASE_VERSION = 1;

    //TABLE NAME
    public static final String TABLE_USERS = "users";

    //TABLE USERS COLUMNS
    public static final String COLUMN_USERNAME = "UserName";
    public static final String COLUMN_PASSWORD = "Password";

    public DbHandler(Context c, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
                " (" + COLUMN_USERNAME + " TEXT," +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //(CREATE TABLE "Status") if you want to add a new table for the next version e.g. version = 2
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS); //removes all user data as compared to adding new additions to the database
        onCreate(db);
    }

    //using this method we can add users to user table
    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        //create content values to insert
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_USERNAME, user.getUsername());

        // insert row
        long todo_id = db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public User Authenticate(User user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{COLUMN_USERNAME, COLUMN_PASSWORD},//Selecting columns want to query
                COLUMN_USERNAME + "=?",
                new String[]{user.username},//Where clause
                null, null, null);

        if(cursor!=null && cursor.moveToFirst() && cursor.getCount()>0){
            //if cursor has value then in user database there is user associated with this given username
            User user1 = new User(cursor.getString(0), cursor.getString(1));

            //Match both passwords check they are same or not
            if(user.password.equalsIgnoreCase(user1.password)){
                return user1;
            }
        }
        //if user password does not matches or there is no record with that email then return @false
        return null;
    }

    public boolean isUsernameExists(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{COLUMN_USERNAME, COLUMN_PASSWORD},//Selecting columns want to query
                COLUMN_USERNAME + "=?",
                new String[]{username},//Where clause
                null, null, null);

        if(cursor!=null && cursor.moveToFirst() && cursor.getCount()>0){
            //if cursor has value then in user database there is user associated with this given username so return true
            return true;
        }
        //if username does not exist return false
        return false;
    }

    public void updateUser(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, password);
        db.execSQL("UPDATE " + TABLE_USERS + " SET " + COLUMN_PASSWORD
                + " = " + "'" + password + "'"
                + " WHERE " + COLUMN_USERNAME
                + " = " + "'" + username + "'");
        db.close();
    }

    public void deleteUser(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = " + "'" + username + "'"); //+ COLUMN_QUEUETICKET + " = '" + tix + "'");
        //return db.delete(TICKETS, COLUMN_QUEUETICKET + "=" + tix, null) > 0;
        db.close();
    }
}
