package com.example.cookingrecipegood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Adminsendotp extends AppCompatActivity {

    private EditText otpLogin;
    private Button VerifyLogin;
    private TextView resendLogin;
    private String numberphoneLogin,idLogin;
    private FirebaseAuth Fauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminsendotp);

        otpLogin=(EditText)findViewById(R.id.codeLogin);
        VerifyLogin=(Button)findViewById(R.id.VerifyMoLogin);
        resendLogin=(TextView)findViewById(R.id.AdminResendLogin);
        Fauth=FirebaseAuth.getInstance();

        numberphoneLogin=getIntent().getStringExtra("phonenumberLogin");


        sendVerificationCode();

        VerifyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(otpLogin.getText().toString())){
                    Toast.makeText(Adminsendotp.this,"Enter OTP",Toast.LENGTH_SHORT).show();
                }
                else if(otpLogin.getText().toString().replace(" ","").length()!=6){

                    Toast.makeText(Adminsendotp.this,"Please enter right OTP",Toast.LENGTH_SHORT).show();
                }
                else{
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(idLogin, otpLogin.getText().toString().replace(" ",""));
                    signInWithPhone(credential);
                }
            }
        });
        resendLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCode();
            }
        });



    }

    private void sendVerificationCode() {
        new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                resendLogin.setText(""+millisUntilFinished/1000);
                resendLogin.setEnabled(false);
            }

            @Override
            public void onFinish() {
                resendLogin.setText(" Resend");
                resendLogin.setEnabled(true);

            }
        }.start();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                numberphoneLogin,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    public void onCodeSent(@NonNull String id, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        Adminsendotp.this.idLogin = id;
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                        signInWithPhone(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                        Toast.makeText(Adminsendotp.this,"Failed",Toast.LENGTH_SHORT).show();
                    }
                });              // Activity (for callback binding)

    }
    private void signInWithPhone(PhoneAuthCredential credential) {

        Fauth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            startActivity(new Intent(Adminsendotp.this,AdminPanel.class));
                            finish();

                        }else{
                            ReusableCodeForAll.ShowAlert(Adminsendotp.this,"Error",task.getException().getMessage());
                        }

                    }
                });

    }

}