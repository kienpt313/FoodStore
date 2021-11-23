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

public class CustomerLoginEmail extends AppCompatActivity {
    TextInputLayout emailcus,passcus;
    Button Signincus,Signinphonecus;
    TextView Forgotpasswordcus,signupcus;
    FirebaseAuth Fbauthcus;
    String emailidcus,pwdcus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login_email);
        try {
            emailcus=(TextInputLayout)findViewById(R.id.Lemailcus);
            passcus=(TextInputLayout)findViewById(R.id.Lpasswordcus);
            Signincus=(Button)findViewById(R.id.button4cus);
            signupcus=(TextView)findViewById(R.id.textView3cus);
            Forgotpasswordcus=(TextView)findViewById(R.id.forgotpasscus);
            Signinphonecus=(Button)findViewById(R.id.buttonPhonecus);

            Fbauthcus=FirebaseAuth.getInstance();
            Signincus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    emailidcus=emailcus.getEditText().getText().toString().trim();
                    pwdcus=passcus.getEditText().getText().toString().trim();
                    if(isValid()){
                        final ProgressDialog mDialog=new ProgressDialog(CustomerLoginEmail.this);
                        mDialog.setCanceledOnTouchOutside(false);
                        mDialog.setCancelable(false);
                        mDialog.setMessage(" Please Wait");
                        mDialog.show();

                        Fbauthcus.signInWithEmailAndPassword(emailidcus,pwdcus).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    mDialog.dismiss();
                                    if(Fbauthcus.getCurrentUser().isEmailVerified()){
                                        mDialog.dismiss();
                                        Toast.makeText(CustomerLoginEmail.this,"You are successfully login",Toast.LENGTH_SHORT).show();
                                        Intent Z=new Intent(CustomerLoginEmail.this,CustomerPanel.class);
                                        startActivity(Z);
                                        finish();
                                    }
                                    else{
                                        ReusableCodeForAll.ShowAlert(CustomerLoginEmail.this,"Verification failed","You have not verified your Email");

                                    }
                                }else {
                                    mDialog.dismiss();
                                    ReusableCodeForAll.ShowAlert(CustomerLoginEmail.this,"Error",task.getException().getMessage());

                                }
                            }
                        });
                    }
                }
            });
            signupcus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(CustomerLoginEmail.this,AdminRegister.class));
                    finish();
                }
            });
            Forgotpasswordcus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(CustomerLoginEmail.this,CustomerForgotPassword.class));
                    finish();
                }
            });
            Signinphonecus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(CustomerLoginEmail.this,CustomerLoginPhone.class));
                    finish();
                }
            });

        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    String emailpattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public boolean isValid(){
        emailcus.setErrorEnabled(false);
        emailcus.setError("");
        passcus.setErrorEnabled(false);
        passcus.setError("");
        boolean isvaild=false,isvalidemail=false,isvaildpassword=false;
        if(TextUtils.isEmpty(emailidcus)){
            emailcus.setErrorEnabled(true);
            emailcus.setError("Email is required");
        }
        else{
            if(emailidcus.matches(emailpattern)){
                isvalidemail=true;
            }else {
                emailcus.setErrorEnabled(true);
                emailcus.setError("Invaild email address");
            }
        }
        if(TextUtils.isEmpty(pwdcus)){
            passcus.setErrorEnabled(true);
            passcus.setError("password is required");
        }
        else {
            isvaildpassword=true;
        }
        isvaild=(isvalidemail && isvaildpassword)?true:false;
        return isvaild;
    }
}