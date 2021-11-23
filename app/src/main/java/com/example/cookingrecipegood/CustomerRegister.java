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
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerRegister extends AppCompatActivity {
    String[] HaNoi={"Hai Bà Trưng","Hoàng Mai","Cầu Giấy","Thanh Xuân","Long Biên"};
    String[]HaiPhong={"Hồng Bàng","Lê Chân","Ngô Quyền","Kiến An","Hải An","Đồ Sơn"};
    String[]HoChiMinh={"Bình Thạnh","Tân Phú","Tân Bình","Gò Vấp"};
    TextInputLayout FnameCus,LnameCus,EmailCus,PassCus,CpassCus,MoblieNumCus,AddressCus;
    Spinner CityCus,DistrictCus;
    Button signupCus,emaillCus,PhoneCus;
    CountryCodePicker cppCus;
    FirebaseAuth FbauthCus;
    DatabaseReference databaseReferenceCus;
    FirebaseDatabase firebaseDatabaseCus;
    String fnamecus,lnamecus,emaildcus,passwordcus,confirmpasswordcus,mobliecus,addresscus,cityycus,districttcus;
    String role="Customer";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);
        FnameCus=(TextInputLayout)findViewById(R.id.FirstnameCus);
        LnameCus = (TextInputLayout)findViewById(R.id.LastnameCus);
        EmailCus = (TextInputLayout)findViewById(R.id.EmailCus);
        PassCus = (TextInputLayout)findViewById(R.id.PasswordCus);
        CpassCus = (TextInputLayout) findViewById(R.id.ConfirmPassCus);
        MoblieNumCus = (TextInputLayout) findViewById(R.id.MobileNumberCus);
        AddressCus = (TextInputLayout) findViewById(R.id.AddressCus);
        CityCus = (Spinner) findViewById(R.id.CitysCus);
        DistrictCus=(Spinner) findViewById(R.id.DistrictCus);
        signupCus = (Button) findViewById(R.id.SignupCus);
        emaillCus = (Button) findViewById(R.id.emailCus);
        PhoneCus = (Button) findViewById(R.id.phoneCus);
        cppCus= (CountryCodePicker) findViewById(R.id.CountryCodeCus);
        emaillCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerRegister.this,CustomerLoginEmail.class));
                finish();
            }
        });
        PhoneCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerRegister.this,CustomerLoginPhone.class));
                finish();
            }
        });

        CityCus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object value = parent.getItemAtPosition(position);
                cityycus=value.toString().trim();
                if(cityycus.equals("Hà Nội")){
                    ArrayList<String> list=new ArrayList<>();
                    for(String districtes : HaNoi){
                        list.add(districtes);
                    }
                    ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(CustomerRegister.this, android.R.layout.simple_spinner_item,list);
                    DistrictCus.setAdapter(arrayAdapter);
                }
                if(cityycus.equals("Hải Phòng")){
                    ArrayList<String> list=new ArrayList<>();
                    for(String districtes : HaiPhong){
                        list.add(districtes);
                    }
                    ArrayAdapter<String>arrayAdapter=new ArrayAdapter<String>(CustomerRegister.this, android.R.layout.simple_spinner_item,list);
                    DistrictCus.setAdapter(arrayAdapter);
                }
                if(cityycus.equals("Hồ Chí Minh")){
                    ArrayList<String> list=new ArrayList<>();
                    for(String districtes : HoChiMinh){
                        list.add(districtes);
                    }
                    ArrayAdapter<String>arrayAdapter=new ArrayAdapter<String>(CustomerRegister.this, android.R.layout.simple_spinner_item,list);
                    DistrictCus.setAdapter(arrayAdapter);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        DistrictCus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object value=parent.getItemAtPosition(position);
                districttcus=value.toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        databaseReferenceCus = firebaseDatabaseCus.getInstance().getReference("Customer");
        FbauthCus = FirebaseAuth.getInstance();
        signupCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fnamecus = FnameCus.getEditText().getText().toString().trim();
                lnamecus = LnameCus.getEditText().getText().toString().trim();
                mobliecus = MoblieNumCus.getEditText().getText().toString().trim();
                emaildcus = EmailCus.getEditText().getText().toString().trim();
                passwordcus = PassCus.getEditText().getText().toString().trim();
                confirmpasswordcus = CpassCus.getEditText().getText().toString().trim();
                addresscus = AddressCus.getEditText().getText().toString().trim();


                if(isValid()){
                    final ProgressDialog mDialog=new ProgressDialog(CustomerRegister.this);
                    mDialog.setCancelable(false);
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setMessage("please wait...");
                    mDialog.show();
                    FbauthCus.createUserWithEmailAndPassword(emaildcus,passwordcus).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                String UserIdd=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                databaseReferenceCus=FirebaseDatabase.getInstance().getReference("User").child(UserIdd);
                                final HashMap<String, String> hashMap=new HashMap<>();
                                hashMap.put("Role",role);
                                databaseReferenceCus.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        HashMap<String, String>hashMap1=new HashMap<>();
                                        hashMap1.put("Firstname",fnamecus);
                                        hashMap1.put("Lastname",lnamecus);
                                        hashMap1.put("Email",emaildcus);
                                        hashMap1.put("Password",passwordcus);
                                        hashMap1.put("Confirmpass",confirmpasswordcus);
                                        hashMap1.put("Mbnumber",mobliecus);
                                        hashMap1.put("Address",addresscus);
                                        hashMap1.put("City",cityycus);
                                        hashMap1.put("District",districttcus);
                                        firebaseDatabaseCus.getInstance().getReference("Customer").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                mDialog.dismiss();
                                                FbauthCus.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            AlertDialog.Builder builder=new AlertDialog.Builder(CustomerRegister.this);
                                                            builder.setMessage("You have Register!");
                                                            builder.setCancelable(false);
                                                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    dialog.dismiss();
                                                                    Intent intent=new Intent(CustomerRegister.this,CustomerVerifyPhone.class);
                                                                    String phonenumber = cppCus.getSelectedCountryCodeWithPlus() + mobliecus;
                                                                    intent.putExtra("phonenumberCustomer",phonenumber);
                                                                    startActivity(intent);

                                                                }
                                                            });
                                                            AlertDialog Alert=builder.create();
                                                            Alert.show();
                                                        }
                                                        else {
                                                            mDialog.dismiss();
                                                            ReusableCodeForAll.ShowAlert(CustomerRegister.this,"Error",task.getException().getMessage());
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

        FnameCus.setErrorEnabled(false);
        FnameCus.setError("");
        LnameCus.setErrorEnabled(false);
        LnameCus.setError("");
        EmailCus.setErrorEnabled(false);
        EmailCus.setError("");
        PassCus.setErrorEnabled(false);
        PassCus.setError("");
        CpassCus.setErrorEnabled(false);
        CpassCus.setError("");
        MoblieNumCus.setErrorEnabled(false);
        MoblieNumCus.setError("");
        AddressCus.setErrorEnabled(false);
        AddressCus.setError("");

        boolean isValid =false, isValidfname=false,isValidlname=false, isValidemail=false, isValidpassword=false, isValidconfpassword=false,isValidmoblienum=false,isValidaddress=false;
        if(TextUtils.isEmpty(fnamecus)){
            FnameCus.setErrorEnabled(true);
            FnameCus.setError("Please enter to first name");
        }
        else{
            isValidfname=true;
        }
        if(TextUtils.isEmpty(lnamecus)){
            LnameCus.setErrorEnabled(true);
            LnameCus.setError("Please enter to last name");
        }
        else{
            isValidlname=true;
        }
        if(TextUtils.isEmpty(emaildcus)){
            EmailCus.setErrorEnabled(true);
            EmailCus.setError("Email is Required");
        }
        else{
            if (emaildcus.matches(emailpattern)) {
                isValidemail = true;
            }
            else {
                EmailCus.setErrorEnabled(true);
                EmailCus.setError("Please enter to email");
            }
        }
        if(TextUtils.isEmpty(passwordcus)){
            PassCus.setErrorEnabled(true);
            PassCus.setError("Please enter to first name");
        }
        else{
            if (passwordcus.length()<8){
                PassCus.setErrorEnabled(true);
                PassCus.setError("Password is not safe");
            }
            else {
                isValidpassword = true;
            }

        }
        if(TextUtils.isEmpty(confirmpasswordcus)){
            CpassCus.setErrorEnabled(true);
            CpassCus.setError("Please enter Passowrd again");
        }
        else {
            if (!passwordcus.equals(confirmpasswordcus)) {
                CpassCus.setErrorEnabled(true);
                CpassCus.setError("passowrd is not equals");
            } else {
                isValidconfpassword = true;
            }
        }
        if (TextUtils.isEmpty(mobliecus)) {
            MoblieNumCus.setErrorEnabled(true);
            MoblieNumCus.setError("Please enter Mobilenumber");
        } else {
            if (mobliecus.length() < 10) {
                MoblieNumCus.setErrorEnabled(true);
                MoblieNumCus.setError("Invaild moblie number");
            } else {
                isValidmoblienum = true;
            }}
        if (TextUtils.isEmpty(addresscus)) {
            AddressCus.setErrorEnabled(true);
            AddressCus.setError("Please enter Address");
        } else {
            isValidaddress = true;
        }
        isValid = (isValidaddress && isValidconfpassword && isValidpassword && isValidemail && isValidmoblienum && isValidfname && isValidlname) ? true : false;
        return isValid;
    }

}