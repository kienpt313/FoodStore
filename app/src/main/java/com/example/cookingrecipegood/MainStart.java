package com.example.cookingrecipegood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainStart extends AppCompatActivity {

    Button signemail,signphone,signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_start);
        signemail=(Button)findViewById(R.id.SignEmail);
        signphone=(Button)findViewById(R.id.SignPhone);
        signup=(Button)findViewById(R.id.SignUP);
        signemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signemail =new Intent(MainStart.this,ChooseLogin.class);
                signemail.putExtra("Home","Email");
                startActivity(signemail);
                finish();
            }
        });
        signphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signphone =new Intent(MainStart.this,ChooseLogin.class);
                signphone.putExtra("Home","Phone");
                startActivity(signphone);
                finish();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup =new Intent(MainStart.this,ChooseLogin.class);
                signup.putExtra("Home","SignUp");
                startActivity(signup);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}