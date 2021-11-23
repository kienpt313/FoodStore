package com.example.cookingrecipegood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminRegister extends AppCompatActivity {

    String[] HaNoi={"Hai Bà Trưng","Hoàng Mai","Cầu Giấy","Thanh Xuân","Long Biên"};
    String[]HaiPhong={"Hồng Bàng","Lê Chân","Ngô Quyền","Kiến An","Hải An","Đồ Sơn"};
    String[]HoChiMinh={"Bình Thạnh","Tân Phú","Tân Bình","Gò Vấp"};
    TextInputLayout Fname,Lname,Email,Pass,Cpass,MoblieNum,Address;
    Spinner City,District;
    Button signup,Emaill,Phonee;
    CountryCodePicker cpp;
    FirebaseAuth Fbauth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    String fname,lname,emaild,password,confirmpassword,moblie,address,cityy,districtt;
    String role="Admin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);
        Fname = (TextInputLayout)findViewById(R.id.FirstnameStr);
        Lname = (TextInputLayout)findViewById(R.id.LastnameStr);
        Email = (TextInputLayout)findViewById(R.id.EmailStr);
        Pass = (TextInputLayout)findViewById(R.id.PasswordStr);
        Cpass = (TextInputLayout) findViewById(R.id.ConfirmPassStr);
        MoblieNum = (TextInputLayout) findViewById(R.id.MobileNumberStr);
        Address = (TextInputLayout) findViewById(R.id.AddressStr);
        City = (Spinner) findViewById(R.id.CityStr);
        District=(Spinner) findViewById(R.id.DistrictStr);
        signup = (Button) findViewById(R.id.SignupStr);
        Emaill = (Button) findViewById(R.id.emailConfirmStr);
        Phonee = (Button) findViewById(R.id.phoneStr);
        cpp = (CountryCodePicker) findViewById(R.id.CountryCodeStr);
        Emaill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminRegister.this,AdminLoginEmail.class));
                finish();
            }
        });
        Phonee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminRegister.this,AdminLoginPhone.class));
                finish();
            }
        });


        City.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object value = parent.getItemAtPosition(position);
                cityy=value.toString().trim();
                if(cityy.equals("Hà Nội")){
                    ArrayList<String> list=new ArrayList<>();
                    for(String districtes : HaNoi){
                        list.add(districtes);
                    }
                    ArrayAdapter<String>arrayAdapter=new ArrayAdapter<String>(AdminRegister.this, android.R.layout.simple_spinner_item,list);
                    District.setAdapter(arrayAdapter);
                }
                if(cityy.equals("Hải Phòng")){
                    ArrayList<String> list=new ArrayList<>();
                    for(String districtes : HaiPhong){
                        list.add(districtes);
                    }
                    ArrayAdapter<String>arrayAdapter=new ArrayAdapter<String>(AdminRegister.this, android.R.layout.simple_spinner_item,list);
                    District.setAdapter(arrayAdapter);
                }
                if(cityy.equals("Hồ Chí Minh")){
                    ArrayList<String> list=new ArrayList<>();
                    for(String districtes : HoChiMinh){
                        list.add(districtes);
                    }
                    ArrayAdapter<String>arrayAdapter=new ArrayAdapter<String>(AdminRegister.this, android.R.layout.simple_spinner_item,list);
                    District.setAdapter(arrayAdapter);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        District.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object value=parent.getItemAtPosition(position);
                districtt=value.toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        databaseReference = firebaseDatabase.getInstance().getReference("Admin");
        Fbauth = FirebaseAuth.getInstance();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname = Fname.getEditText().getText().toString().trim();
                lname = Lname.getEditText().getText().toString().trim();
                moblie = MoblieNum.getEditText().getText().toString().trim();
                emaild = Email.getEditText().getText().toString().trim();
                password = Pass.getEditText().getText().toString().trim();
                confirmpassword = Cpass.getEditText().getText().toString().trim();
                address = Address.getEditText().getText().toString().trim();


    if(isValid()){
        final ProgressDialog mDialog=new ProgressDialog(AdminRegister.this);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setMessage("please wait...");
        mDialog.show();
        Fbauth.createUserWithEmailAndPassword(emaild,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String UserIdd=FirebaseAuth.getInstance().getCurrentUser().getUid();
                    databaseReference=FirebaseDatabase.getInstance().getReference("User").child(UserIdd);
                    final HashMap<String, String> hashMap=new HashMap<>();
                    hashMap.put("Role",role);
                    databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            HashMap<String, String>hashMap1=new HashMap<>();
                            hashMap1.put("First name",fname);
                            hashMap1.put("Last name",lname);
                            hashMap1.put("Email",emaild);
                            hashMap1.put("Password",password);
                            hashMap1.put("Confirm pass",confirmpassword);
                            hashMap1.put("Mbnumber",moblie);
                            hashMap1.put("Address",address);
                            hashMap1.put("City",cityy);
                            hashMap1.put("District",districtt);
                            firebaseDatabase.getInstance().getReference("Admin").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    mDialog.dismiss();
                                    Fbauth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                AlertDialog.Builder builder=new AlertDialog.Builder(AdminRegister.this);
                                                builder.setMessage("You have Register!");
                                                builder.setCancelable(false);
                                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                        Intent intent=new Intent(AdminRegister.this,AdminVerifyPhone.class);
                                                        String phonenumber = cpp.getSelectedCountryCodeWithPlus() + moblie;
                                                        intent.putExtra("phonenumber",phonenumber);
                                                        startActivity(intent);

                                                    }
                                                });
                                                AlertDialog Alert=builder.create();
                                                Alert.show();
                                            }
                                            else {
                                                mDialog.dismiss();
                                                ReusableCodeForAll.ShowAlert(AdminRegister.this,"Error",task.getException().getMessage());
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            }
        });
    }
            }
        });
    }
    String emailpattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public boolean isValid(){

        Fname.setErrorEnabled(false);
        Fname.setError("");
        Lname.setErrorEnabled(false);
        Lname.setError("");
        Email.setErrorEnabled(false);
        Email.setError("");
        Pass.setErrorEnabled(false);
        Pass.setError("");
        Cpass.setErrorEnabled(false);
        Cpass.setError("");
        MoblieNum.setErrorEnabled(false);
        MoblieNum.setError("");
        Address.setErrorEnabled(false);
        Address.setError("");

        boolean isValid =false, isValidfname=false,isValidlname=false, isValidemail=false, isValidpassword=false, isValidconfpassword=false,isValidmoblienum=false,isValidaddress=false;
   if(TextUtils.isEmpty(fname)){
       Fname.setErrorEnabled(true);
       Fname.setError("Please enter to first name");
   }
   else{
       isValidfname=true;
   }
        if(TextUtils.isEmpty(lname)){
            Lname.setErrorEnabled(true);
            Lname.setError("Please enter to last name");
        }
        else{
            isValidlname=true;
        }
        if(TextUtils.isEmpty(emaild)){
            Email.setErrorEnabled(true);
            Email.setError("Email is Required");
        }
        else{
            if (emaild.matches(emailpattern)) {
                isValidemail = true;
            }
            else {
                Email.setErrorEnabled(true);
                Email.setError("Please enter to email");
            }
        }
        if(TextUtils.isEmpty(password)){
            Pass.setErrorEnabled(true);
            Pass.setError("Please enter to first name");
        }
        else{
            if (password.length()<8){
                Pass.setErrorEnabled(true);
                Pass.setError("Password is not safe");
            }
            else {
                isValidpassword = true;
            }

        }
        if(TextUtils.isEmpty(confirmpassword)){
            Cpass.setErrorEnabled(true);
            Cpass.setError("Please enter Passowrd again");
        }
        else {
            if (!password.equals(confirmpassword)) {
                Cpass.setErrorEnabled(true);
                Cpass.setError("passowrd is not equals");
            } else {
                isValidconfpassword = true;
            }
        }
            if (TextUtils.isEmpty(moblie)) {
                MoblieNum.setErrorEnabled(true);
                MoblieNum.setError("Please enter Mobilenumber");
            } else {
                if (moblie.length() < 10) {
                    MoblieNum.setErrorEnabled(true);
                    MoblieNum.setError("Invaild moblie number");
                } else {
                    isValidmoblienum = true;
                }}
                if (TextUtils.isEmpty(address)) {
                    Address.setErrorEnabled(true);
                    Address.setError("Please enter Address");
                } else {
                    isValidaddress = true;
                }
                isValid = (isValidaddress && isValidconfpassword && isValidpassword && isValidemail && isValidmoblienum && isValidfname && isValidlname) ? true : false;
                return isValid;
            }

}