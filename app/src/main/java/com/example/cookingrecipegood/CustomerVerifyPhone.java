package com.example.cookingrecipegood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class CustomerVerifyPhone extends AppCompatActivity {

    private EditText otpCus;
    private Button VerifyCus;
    private TextView resendCus;
    private String numberphoneCus,idCus;
    private FirebaseAuth FbauthCus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_verify_phone);
        otpCus=(EditText)findViewById(R.id.codeCus);
        VerifyCus=(Button)findViewById(R.id.VerifyMoCus);
        resendCus=(TextView)findViewById(R.id.CusResend);
        FbauthCus=FirebaseAuth.getInstance();

        numberphoneCus=getIntent().getStringExtra("phonenumberCustomer");
        sendVerificationCode();

        VerifyCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(otpCus.getText().toString())){
                    Toast.makeText(CustomerVerifyPhone.this,"Enter OTP",Toast.LENGTH_SHORT).show();
                }
                else if(otpCus.getText().toString().replace(" ","").length()!=6){

                    Toast.makeText(CustomerVerifyPhone.this,"Please enter right OTP",Toast.LENGTH_SHORT).show();
                }
                else{
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(idCus, otpCus.getText().toString().replace(" ",""));
                    linkCredential(credential);
                }
            }
        });
        resendCus.setOnClickListener(new View.OnClickListener() {
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
                resendCus.setText(""+millisUntilFinished/1000);
                resendCus.setEnabled(false);
            }

            @Override
            public void onFinish() {
                resendCus.setText(" Resend");
                resendCus.setEnabled(true);

            }
        }.start();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                numberphoneCus,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    public void onCodeSent(@NonNull String id, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        CustomerVerifyPhone.this.idCus = id;
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                        linkCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                        Toast.makeText(CustomerVerifyPhone.this,"Failed",Toast.LENGTH_SHORT).show();
                    }
                });              // Activity (for callback binding)

    }
    private void linkCredential(PhoneAuthCredential credential) {

        FbauthCus.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(CustomerVerifyPhone.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            Intent intent = new Intent(CustomerVerifyPhone.this , MainStart.class);
                            startActivity(intent);
                            finish();
                        }else{
                            ReusableCodeForAll.ShowAlert(CustomerVerifyPhone.this,"Error",task.getException().getMessage());
                        }
                    }
                });

    }

}