package com.example.practical_7;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    EditText etUsername;
    EditText etPassword;
    TextView tvRegister;
    Button btnLogin;
    Button btnUpdate;
    Button btnDelete;
    DbHandler db;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DbHandler(this, null, null, 1);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        tvRegister = findViewById(R.id.tvNewUser);

        tvRegister.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                return true;
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                User currentUser = db.Authenticate(new User(username, password));

                if(currentUser != null){
                    Toast.makeText(MainActivity.this, "Valid", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                User currentUser = db.Authenticate(new User(username, password));

                Pattern pPassword = Pattern.compile("^(?=.*[@$!%*#?&+=])((?=.*[A-Z])(?=.*\\d).{8,})$");
                Matcher matcher = pPassword.matcher(password);

                if (matcher.matches() == true) {
                    db.updateUser(username, password);
                    Toast tt = Toast.makeText(MainActivity.this, "Password updated successfully.", Toast.LENGTH_LONG); //LENGTH_LONG - toast appear for 3 seconds
                    tt.show();
                }
                else if (currentUser.getUsername() == null){
                    /**Show toast message saying that account is not found**/
                    Toast tt = Toast.makeText(MainActivity.this, "Account not found.", Toast.LENGTH_LONG); //LENGTH_LONG - toast appear for 3 seconds
                    tt.show();
                }
                else{
                    /**Show toast message saying that inputs are invalid**/
                    Toast tt = Toast.makeText(MainActivity.this, "Invalid Input. Password must contain minimally 1 symbol, 1 uppercase and 1 numeric", Toast.LENGTH_LONG); //LENGTH_LONG - toast appear for 3 seconds
                    tt.show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                db.deleteUser(username);
                Toast tt = Toast.makeText(MainActivity.this, "Account has been deleted.", Toast.LENGTH_LONG); //LENGTH_LONG - toast appear for 3 seconds
                tt.show();
            }
        });
    }


}
