package com.example.practical_7;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    EditText etUsername;
    EditText etPassword;
    Button btnCancel;
    Button btnCreate;
    DbHandler db;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DbHandler(this, null, null, 1);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnCreate = findViewById(R.id.btnCreate);
        btnCancel = findViewById(R.id.btnCancel);

        btnCancel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                return true;
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username = etUsername.getText().toString();
                String Password = etPassword.getText().toString();

                if(!db.isUsernameExists(Username)){
                    Pattern pUsername = Pattern.compile("^([a-zA-Z0-9]*){6,12}$");
                    Pattern pPassword = Pattern.compile("^(?=.*[@$!%*#?&+=])((?=.*[A-Z])(?=.*\\d).{8,})$");

                    boolean validUsername = pUsername.matcher(Username).matches();
                    boolean validPassword = pPassword.matcher(Password).matches();

                    if(validUsername && validPassword) {
                        db.addUser(new User(Username, Password));
                        Toast tt = Toast.makeText(RegisterActivity.this, "New User Created Successfully", Toast.LENGTH_LONG);
                        tt.show();
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    }
                    else{
                        Toast tt = Toast.makeText(RegisterActivity.this, "Invalid User Creation. Please Try Again.", Toast.LENGTH_LONG);
                        tt.show();
                    }
                }
                else{
                    Toast tt = Toast.makeText(RegisterActivity.this, "Invalid User Creation. Please Try Again.", Toast.LENGTH_LONG);
                    tt.show();
                }
            }
        });
    }
}
