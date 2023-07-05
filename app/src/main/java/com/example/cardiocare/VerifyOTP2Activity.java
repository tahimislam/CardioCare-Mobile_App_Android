package com.example.cardiocare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyOTP2Activity extends AppCompatActivity {

    EditText input1,input2,input3,input4, input5,input6;
    Button verifybtn;
    String getOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp2);
        getSupportActionBar().hide();

        input1=findViewById(R.id.inputotp1);
        input2=findViewById(R.id.inputotp2);
        input3=findViewById(R.id.inputotp3);
        input4=findViewById(R.id.inputotp4);
        input5=findViewById(R.id.inputotp5);
        input6=findViewById(R.id.inputotp6);

        verifybtn=findViewById(R.id.verifybtn);
        final ProgressBar progbar=findViewById(R.id.progbar);

        TextView textView=findViewById(R.id.textmobile);
        textView.setText(String.format("+88 %s",getIntent().getStringExtra("mobile")));

        getOTP = getIntent().getStringExtra("OTP");

        verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!input1.getText().toString().trim().isEmpty() && !input2.getText().toString().trim().isEmpty() &&
                        !input3.getText().toString().trim().isEmpty() && !input4.getText().toString().trim().isEmpty() &&
                        !input5.getText().toString().trim().isEmpty() && !input6.getText().toString().trim().isEmpty())
                {

                    String enterOTP = input1.getText().toString().trim()+input2.getText().toString().trim()+input3.getText().toString().trim()+
                            input4.getText().toString().trim()+input5.getText().toString().trim()+input6.getText().toString().trim();

                    if(getOTP!=null)
                    {
                        progbar.setVisibility(View.VISIBLE);
                        verifybtn.setVisibility(View.INVISIBLE);

                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                                getOTP,enterOTP
                        );
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progbar.setVisibility(View.GONE);
                                        verifybtn.setVisibility(View.VISIBLE);

                                        if(task.isSuccessful())
                                        {
                                            Toast.makeText(VerifyOTP2Activity.this, "Verified! Login Successful!", Toast.LENGTH_LONG).show();
                                            Intent intent=new Intent(VerifyOTP2Activity.this, RecordActivity.class);

                                            //To prevent user from returning back to Login Activity on pressing back button after Login
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                    | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);

                                            finish();               //To close Login Activity
                                        }
                                        else
                                        {
                                            Toast.makeText(VerifyOTP2Activity.this, "Enter the correct OTP!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                    else
                    {
                        Toast.makeText(VerifyOTP2Activity.this, "Something went wrong. Please check internet connection!", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(VerifyOTP2Activity.this, "OTP Verifying", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(VerifyOTP2Activity.this, "Please enter all number!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        numberotpmove();

        findViewById(R.id.resend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+88" + getIntent().getStringExtra("mobile"),
                        30,
                        TimeUnit.SECONDS,
                        VerifyOTP2Activity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(VerifyOTP2Activity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                getOTP=newotp;
                                Toast.makeText(VerifyOTP2Activity.this,"Resending OTP Successful!",Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });
    }

    private void numberotpmove() {

        input1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty())
                {
                    input2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        input2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty())
                {
                    input3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        input3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty())
                {
                    input4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        input4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty())
                {
                    input5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        input5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty())
                {
                    input6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}