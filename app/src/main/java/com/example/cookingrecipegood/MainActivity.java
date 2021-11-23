package com.example.cookingrecipegood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    FirebaseAuth Fbauth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=(ImageView)findViewById(R.id.imageView2);
        textView=(TextView)findViewById(R.id.textView);
        imageView.animate().alpha(0f).setDuration(0);
        textView.animate().alpha(0f).setDuration(0);

       imageView.animate().alpha(1f).setDuration(1000).setListener(new AnimatorListenerAdapter() {
           @Override
           public void onAnimationEnd(Animator animation) {
               textView.animate().alpha(1f).setDuration(800);
           }
       });
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {


               Intent intent=new Intent(MainActivity.this,MainStart.class);
               startActivity(intent);
               finish();}

       },2500);
    }
}