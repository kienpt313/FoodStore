package com.example.cookingrecipegood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

public class AdminLoginPhone extends AppCompatActivity {

    EditText num;
    Button sendotp,signinemail;
    TextView signup;
    CountryCodePicker cpp;
    String number;
    FirebaseAuth Fbauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login_phone);
        num=(EditText)findViewById(R.id.number);
        sendotp=(Button)findViewById(R.id.otp);
        cpp=(CountryCodePicker)findViewById(R.id.CountryCode);
        signinemail=(Button)findViewById(R.id.buttonEmail);
        signup=(TextView)findViewById(R.id.acsignup);
        Fbauth=FirebaseAuth.getInstance();
        cpp.registerCarrierNumberEditText(num);
        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number=num.getText().toString().trim();
                String Phonenum = cpp.getSelectedCountryCodeWithPlus()+number;
                Intent b = new Intent(AdminLoginPhone.this,Adminsendotp.class);

                b.putExtra("phonenumberLogin",Phonenum);
                startActivity(b);
                finish();



            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminLoginPhone.this,AdminRegister.class));
                finish();

            }
        });
        signinemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminLoginPhone.this,AdminLoginEmail.class));
                finish();
            }
        });
    }
}