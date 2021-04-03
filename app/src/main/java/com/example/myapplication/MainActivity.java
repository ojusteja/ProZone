package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.facebook.FacebookSdk;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
public class MainActivity extends AppCompatActivity{
    private TextView joinNowButton,LoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        joinNowButton = (TextView) findViewById(R.id.main_join_now_button);
        LoginButton = (TextView) findViewById(R.id.main_login_button);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(),LOGIN.class);
                startActivity(myIntent);
            }
        });
    }
   /* public void login(View view) {
        startActivity(new Intent(getApplicationContext(), LOGIN.class));
    }*/


}