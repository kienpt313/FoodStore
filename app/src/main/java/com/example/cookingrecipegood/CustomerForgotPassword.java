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

public class CustomerForgotPassword extends AppCompatActivity {
    private EditText emailEditTextcus;
    private Button resetPassButtoncus;
    FirebaseAuth authcus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_forgot_password);
        emailEditTextcus=(EditText)findViewById(R.id.Emailresetcus);
        resetPassButtoncus=(Button)findViewById(R.id.buttonresetcus);
        authcus=FirebaseAuth.getInstance();
        resetPassButtoncus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resetpassword();
            }
        });
    }
    private void Resetpassword(){
        String email=emailEditTextcus.getText().toString().trim();
        if(email.isEmpty()){
            emailEditTextcus.setError("Email is required!");
            emailEditTextcus.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditTextcus.setError("Please provide valid email!");
            emailEditTextcus.requestFocus();
            return;
        }


        authcus.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CustomerForgotPassword.this,"Check your email to reset your password",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(CustomerForgotPassword.this,"Try agin!!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}