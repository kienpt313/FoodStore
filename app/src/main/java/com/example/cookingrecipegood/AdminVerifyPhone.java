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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;


public class AdminVerifyPhone extends AppCompatActivity {
    private EditText otp;
    private Button Verify;
    private TextView resend;
    private String numberphone,id;
    private FirebaseAuth Fbauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_verify_phone);
        otp=(EditText)findViewById(R.id.code);
        Verify=(Button)findViewById(R.id.VerifyMo);
        resend=(TextView)findViewById(R.id.AdminResend);
        Fbauth=FirebaseAuth.getInstance();

        numberphone=getIntent().getStringExtra("phonenumber");
        sendVerificationCode();

        Verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(otp.getText().toString())){
                    Toast.makeText(AdminVerifyPhone.this,"Enter OTP",Toast.LENGTH_SHORT).show();
                }
                else if(otp.getText().toString().replace(" ","").length()!=6){

                    Toast.makeText(AdminVerifyPhone.this,"Please enter right OTP",Toast.LENGTH_SHORT).show();
                }
                else{
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(id, otp.getText().toString().replace(" ",""));
                    linkCredential(credential);
                }
            }
        });
        resend.setOnClickListener(new View.OnClickListener() {
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
                resend.setText(""+millisUntilFinished/1000);
                resend.setEnabled(false);
            }

            @Override
            public void onFinish() {
                resend.setText(" Resend");
                resend.setEnabled(true);

            }
        }.start();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                numberphone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    public void onCodeSent(@NonNull String id, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        AdminVerifyPhone.this.id = id;
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                        linkCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                        Toast.makeText(AdminVerifyPhone.this,"Failed",Toast.LENGTH_SHORT).show();
                    }
                });              // Activity (for callback binding)

    }
    private void linkCredential(PhoneAuthCredential credential) {

        Fbauth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(AdminVerifyPhone.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            Intent intent = new Intent(AdminVerifyPhone.this , MainStart.class);
                            startActivity(intent);
                            finish();
                        }else{
                            ReusableCodeForAll.ShowAlert(AdminVerifyPhone.this,"Error",task.getException().getMessage());
                        }
                    }
                });

    }

}