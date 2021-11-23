package com.example.cookingrecipegood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class AdminForgotPassword extends AppCompatActivity {

    private EditText  emailEditText;
    private Button resetPassButton;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_forgot_password);
        emailEditText=(EditText)findViewById(R.id.EmailResetAd);
        resetPassButton=(Button)findViewById(R.id.buttonResetAd);
        auth=FirebaseAuth.getInstance();
        resetPassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resetpassword();
            }
        });
    }
    private void Resetpassword(){
        String email=emailEditText.getText().toString().trim();
        if(email.isEmpty()){
            emailEditText.setError("Email is required!");
            emailEditText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Please provide valid email!");
            emailEditText.requestFocus();
            return;
        }


        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AdminForgotPassword.this,"Check your email to reset your password",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(AdminForgotPassword.this,"Try agin!!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}