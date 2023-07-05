package com.example.cardiocare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyOTP1Activity extends AppCompatActivity {

    EditText enternumber;
    Button otpbtn;
    ProgressBar progbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp1);
        getSupportActionBar().hide();

        enternumber =findViewById(R.id.input_mobile_number);
        otpbtn=findViewById(R.id.otpbtn);

        progbar=findViewById(R.id.progbar);

        otpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!enternumber.getText().toString().trim().isEmpty()){
                    if ((enternumber.getText().toString().trim()).length() == 11) {

                        progbar.setVisibility(View.VISIBLE);
                        otpbtn.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+88" + enternumber.getText().toString().trim(),
                                30,
                                TimeUnit.SECONDS,
                                VerifyOTP1Activity.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progbar.setVisibility(View.GONE);
                                        otpbtn.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progbar.setVisibility(View.GONE);
                                        otpbtn.setVisibility(View.VISIBLE);
                                        Toast.makeText(VerifyOTP1Activity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String otp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progbar.setVisibility(View.GONE);
                                        otpbtn.setVisibility(View.VISIBLE);

                                        Intent intent =new Intent(getApplicationContext(),VerifyOTP2Activity.class);
                                        intent.putExtra("mobile", enternumber.getText().toString().trim());
                                        intent.putExtra("OTP",otp);
                                        startActivity(intent);
                                    }
                                }
                        );

                    }
                    else
                    {
                        Toast.makeText(VerifyOTP1Activity.this, "Mobile number should be 11 digits!", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(VerifyOTP1Activity.this, "Enter Mobile Number!", Toast.LENGTH_SHORT).show();
                    enternumber.setError("Mobile number required");
                    enternumber.requestFocus();
                }
            }
        });
    }
}