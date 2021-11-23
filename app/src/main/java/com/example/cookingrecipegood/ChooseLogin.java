package com.example.cookingrecipegood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseLogin extends AppCompatActivity {

    Button admin,customer;
    Intent intent;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_login);
        intent=getIntent();
        type=intent.getStringExtra("Home").toString().trim();
        admin=(Button)findViewById(R.id.Admin);
        customer=(Button)findViewById(R.id.Customer);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("Email")){
                    Intent loginemailAd=new Intent(ChooseLogin.this,AdminLoginEmail.class);
                    startActivity(loginemailAd);
                    finish();
                }
                if (type.equals("Phone")){
                    Intent loginphoneAd=new Intent(ChooseLogin.this,AdminLoginPhone.class);
                    startActivity(loginphoneAd);
                    finish();
                }
                if (type.equals("SignUp")){
                    Intent registerAd=new Intent(ChooseLogin.this,AdminRegister.class);
                    startActivity(registerAd);
                }
            }
        });
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("Email")){
                    Intent loginemailCus=new Intent(ChooseLogin.this,CustomerLoginEmail.class);
                    startActivity(loginemailCus);
                    finish();
                }
                if (type.equals("Phone")){
                    Intent loginphoneCus=new Intent(ChooseLogin.this,CustomerLoginPhone.class);
                    startActivity(loginphoneCus);
                    finish();
                }
                if (type.equals("SignUp")){
                    Intent registerCus=new Intent(ChooseLogin.this,CustomerRegister.class);
                    startActivity(registerCus);
                }
            }
        });
    }
}