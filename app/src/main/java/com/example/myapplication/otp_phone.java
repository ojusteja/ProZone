package com.example.myapplication;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class otp_phone extends AppCompatActivity {

    Button btnGenerateOtp,btnSignIn;
    EditText phoneNumber , Otp;
    TextView timer;
    Spinner spinner;
    FirebaseAuth auth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private String verificationCodeSent;
    String getPhoneNumber,getOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_phone);

        spinner = findViewById(R.id.spinner);
        btnGenerateOtp = findViewById(R.id.btn_generate_otp);
        btnSignIn = findViewById(R.id.btn_sign_in);

        phoneNumber = findViewById(R.id.phone);
        Otp = findViewById(R.id.otpEditText);
        timer = findViewById(R.id.timer);

        ArrayAdapter<String> countryCodes = new ArrayAdapter<String>( this,
                R.layout.spinner_item, Country_details.countrycodes);
        spinner.setAdapter(countryCodes);


        firebaseLogin();

        btnGenerateOtp.setOnClickListener(view -> {
            String spinnerText = spinner.getSelectedItem().toString();
            String phone = phoneNumber.getText().toString();

            if (phone == null || phone.trim().isEmpty()) {
                phoneNumber.setError("Provide Phone Number");
                return;
            }

            getPhoneNumber = spinnerText + phone;
            btnSignIn.setVisibility(View.VISIBLE);
            Otp.setVisibility(View.VISIBLE);

            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    getPhoneNumber,
                    60,
                    TimeUnit.SECONDS,
                    otp_phone.this,
                    callbacks

            );

            startTimer(60 * 1000, 1000);
            btnGenerateOtp.setVisibility(View.INVISIBLE);
        });

        btnSignIn.setOnClickListener(view -> {
            getOtp = Otp.getText().toString();
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(
                    verificationCodeSent,getOtp
            );

            SignInWithPhoneNumber(credential);
        });

    }

    private void SignInWithPhoneNumber(PhoneAuthCredential credential) {

        auth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful())
                    {
                        startActivity(new Intent(otp_phone.this,
                                MainActivity.class));
                        finish();
                    }
                    else {
                        Toast.makeText(otp_phone.this,"Incorrect OTP",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void startTimer(final long finish, final long tick) {

        timer.setVisibility(View.VISIBLE);
        CountDownTimer countDownTimer;

        countDownTimer = new CountDownTimer(finish, tick) {
            @Override
            public void onTick(long l) {
                long remindSec = l / 1000;
                timer.setText("Retry again in "
                        + (remindSec % 60)+" seconds");
            }

            @Override
            public void onFinish() {
                btnGenerateOtp.setText("Re-generate OTP");
                btnGenerateOtp.setVisibility(View.VISIBLE);
                Toast.makeText(otp_phone.this, "Finish",
                        Toast.LENGTH_LONG).show();

                timer.setVisibility(View.INVISIBLE);
                cancel();
            }
        }.start();

    }





    private void firebaseLogin() {
        auth = FirebaseAuth.getInstance();

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(otp_phone.this,"Verification Completed",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(otp_phone.this,"Verification Failed"+ e,
                        Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationCodeSent = s;
                Toast.makeText(otp_phone.this, "OTP Sent",
                        Toast.LENGTH_LONG).show();

            }
        };
    }
}