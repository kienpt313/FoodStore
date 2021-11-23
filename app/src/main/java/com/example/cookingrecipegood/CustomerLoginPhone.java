package com.example.cookingrecipegood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

public class CustomerLoginPhone extends AppCompatActivity {
    EditText numcus;
    Button sendotpcus,signinemailcus;
    TextView signupcus;
    CountryCodePicker cppcus;
    String numbercus;
    FirebaseAuth Fbauthcus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login_phone);
        numcus=(EditText)findViewById(R.id.numberCus);
        sendotpcus=(Button)findViewById(R.id.otpCus);
        cppcus=(CountryCodePicker)findViewById(R.id.CountryCodecCus);
        signinemailcus=(Button)findViewById(R.id.buttonEmailCus);
        signupcus=(TextView)findViewById(R.id.acsignupCus);
        Fbauthcus=FirebaseAuth.getInstance();
        cppcus.registerCarrierNumberEditText(numcus);
        sendotpcus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numbercus=numcus.getText().toString().trim();
                String Phonenum = cppcus.getSelectedCountryCodeWithPlus()+numbercus;
                Intent b = new Intent(CustomerLoginPhone.this,CustomerSendOTP.class);

                b.putExtra("phonenumberLoginCus",Phonenum);
                startActivity(b);
                finish();



            }
        });
        signupcus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerLoginPhone.this,CustomerRegister.class));
                finish();

            }
        });
        signinemailcus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerLoginPhone.this,CustomerLoginEmail.class));
                finish();
            }
        });
    }
}