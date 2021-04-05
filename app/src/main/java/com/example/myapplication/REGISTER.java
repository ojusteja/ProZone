package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;


public class REGISTER extends AppCompatActivity {

    EditText mUsername, mEmail, mPhone, mPassword;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r_e_g_i_s_t_e_r);
        mUsername = findViewById(R.id.username);
        mEmail = findViewById(R.id.emailId);
        mPhone = findViewById(R.id.phone);
        mPassword = findViewById(R.id.password);
        mRegisterBtn = findViewById(R.id.SignUpBtn);
        mLoginBtn = findViewById(R.id.loginText);

        fAuth = FirebaseAuth.getInstance();
        progressbar = findViewById(R.id.progressBar);


        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(),LOGIN.class));
            finish();
        }
        mRegisterBtn.setOnClickListener(v -> {
            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                mEmail.setError("Email cannot be empty");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                mPassword.setError("Password cannot be empty");
                return;
            }
            if (password.length() < 6) {
                mPassword.setError("password should contain minimum of 6 characters");
                return;
            }

            progressbar.setVisibility(View.VISIBLE);
            //register the user in firebase

            fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    startActivity(new Intent(getApplicationContext(), otp_phone.class));
                } else {
                    Toast.makeText(REGISTER.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        });



        mLoginBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), LOGIN.class)));

    }
}

