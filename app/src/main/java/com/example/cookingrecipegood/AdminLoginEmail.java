package com.example.cookingrecipegood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLoginEmail extends AppCompatActivity {


    TextInputLayout email,pass;
    Button Signin,Signinphone;
    TextView Forgotpassword,signup;
    FirebaseAuth Fbauth;
    String emailid,pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login_email);
        try {
            email=(TextInputLayout)findViewById(R.id.Lemail);
            pass=(TextInputLayout)findViewById(R.id.Lpassword);
            Signin=(Button)findViewById(R.id.button4);
            signup=(TextView)findViewById(R.id.textView3);
            Forgotpassword=(TextView)findViewById(R.id.forgotpass);
            Signinphone=(Button)findViewById(R.id.buttonPhone);

            Fbauth=FirebaseAuth.getInstance();
            Signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    emailid=email.getEditText().getText().toString().trim();
                    pwd=pass.getEditText().getText().toString().trim();
                    if(isValid()){
                        final ProgressDialog mDialog=new ProgressDialog(AdminLoginEmail.this);
                        mDialog.setCanceledOnTouchOutside(false);
                        mDialog.setCancelable(false);
                        mDialog.setMessage(" Please Wait");
                        mDialog.show();

                        Fbauth.signInWithEmailAndPassword(emailid,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    mDialog.dismiss();
                                    if(Fbauth.getCurrentUser().isEmailVerified()){
                                        mDialog.dismiss();
                                        Toast.makeText(AdminLoginEmail.this,"You are successfully login",Toast.LENGTH_SHORT).show();
                                        Intent Z=new Intent(AdminLoginEmail.this,AdminPanel.class);
                                        startActivity(Z);
                                        finish();
                                    }
                                    else{
                                        ReusableCodeForAll.ShowAlert(AdminLoginEmail.this,"Verification failed","You have not verified your Email");

                                    }
                                }else {
                                    mDialog.dismiss();
                                    ReusableCodeForAll.ShowAlert(AdminLoginEmail.this,"Error",task.getException().getMessage());

                                }
                            }
                        });
                    }
                }
            });
            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(AdminLoginEmail.this,AdminRegister.class));
                    finish();
                }
            });
            Forgotpassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(AdminLoginEmail.this,AdminForgotPassword.class));
                    finish();
                }
            });
            Signinphone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(AdminLoginEmail.this,AdminLoginPhone.class));
                    finish();
                }
            });

        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    String emailpattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public boolean isValid(){
        email.setErrorEnabled(false);
        email.setError("");
        pass.setErrorEnabled(false);
        pass.setError("");
        boolean isvaild=false,isvalidemail=false,isvaildpassword=false;
        if(TextUtils.isEmpty(emailid)){
            email.setErrorEnabled(true);
            email.setError("Email is required");
        }
        else{
            if(emailid.matches(emailpattern)){
                isvalidemail=true;
            }else {
                email.setErrorEnabled(true);
                email.setError("Invaild email address");
            }
        }
        if(TextUtils.isEmpty(pwd)){
            pass.setErrorEnabled(true);
            pass.setError("password is required");
        }
        else {
            isvaildpassword=true;
        }
        isvaild=(isvalidemail && isvaildpassword)?true:false;
        return isvaild;
    }
}