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

public class CustomerSendOTP extends AppCompatActivity {
    private EditText otpLogincus;
    private Button VerifyLogincus;
    private TextView resendLogincus;
    private String numberphoneLogincus,idLogincus;
    private FirebaseAuth Fauthcus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_send_o_t_p);
        otpLogincus=(EditText)findViewById(R.id.codeLoginCus);
        VerifyLogincus=(Button)findViewById(R.id.VerifyMoLoginCus);
        resendLogincus=(TextView)findViewById(R.id.AdminResendLoginCus);
        Fauthcus=FirebaseAuth.getInstance();

        numberphoneLogincus=getIntent().getStringExtra("phonenumberLoginCus");


        sendVerificationCode();

        VerifyLogincus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(otpLogincus.getText().toString())){
                    Toast.makeText(CustomerSendOTP.this,"Enter OTP",Toast.LENGTH_SHORT).show();
                }
                else if(otpLogincus.getText().toString().replace(" ","").length()!=6){

                    Toast.makeText(CustomerSendOTP.this,"Please enter right OTP",Toast.LENGTH_SHORT).show();
                }
                else{
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(idLogincus, otpLogincus.getText().toString().replace(" ",""));
                    signInWithPhone(credential);
                }
            }
        });
        resendLogincus.setOnClickListener(new View.OnClickListener() {
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
                resendLogincus.setText(""+millisUntilFinished/1000);
                resendLogincus.setEnabled(false);
            }

            @Override
            public void onFinish() {
                resendLogincus.setText(" Resend");
                resendLogincus.setEnabled(true);

            }
        }.start();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                numberphoneLogincus,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    public void onCodeSent(@NonNull String id, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        CustomerSendOTP.this.idLogincus = id;
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                        signInWithPhone(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                        Toast.makeText(CustomerSendOTP.this,"Failed",Toast.LENGTH_SHORT).show();
                    }
                });              // Activity (for callback binding)

    }
    private void signInWithPhone(PhoneAuthCredential credential) {

        Fauthcus.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            startActivity(new Intent(CustomerSendOTP.this,CustomerPanel.class));
                            finish();

                        }else{
                            ReusableCodeForAll.ShowAlert(CustomerSendOTP.this,"Error",task.getException().getMessage());
                        }

                    }
                });

    }

}