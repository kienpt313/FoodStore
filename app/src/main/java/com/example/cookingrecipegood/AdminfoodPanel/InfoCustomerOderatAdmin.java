package com.example.cookingrecipegood.AdminfoodPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookingrecipegood.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.UUID;

import CustomerFoodPanel.Customer;

public class InfoCustomerOderatAdmin extends AppCompatActivity {

    TextView firstnameorder,lastnameorder,phone,address;
    Button acpectorder,deleteorder;
    FirebaseDatabase firebaseDatabase;
    String Customerid,cityCus,districtCus,addressCus,phoneCus,first,last;
    DatabaseReference databaseReference,data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_customer_oderat_admin);
        firstnameorder=findViewById(R.id.FirstnameofCustomerinorder);
        lastnameorder=findViewById(R.id.LastnameofCustomerinorder);
        phone=findViewById(R.id.PhoneofCustomerinorder);
        address=findViewById(R.id.addressofCustomerinorder);
        acpectorder=findViewById(R.id.buttonacpectorder);
        deleteorder=findViewById(R.id.buttondeleteorder);
        String RamdomUid= UUID.randomUUID().toString();
        String Userid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        Intent intent=getIntent();
        String mRamdomID=intent.getStringExtra("RAMDOMID");
        String useid= intent.getStringExtra("customerid");
        data=firebaseDatabase.getInstance().getReference("Customer").child(useid);
        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Customer customer=snapshot.getValue(Customer.class);
                cityCus=customer.getCity();
                addressCus=customer.getAddress();
                districtCus=customer.getDistrict();
                phoneCus=customer.getMbnumber();
                last=customer.getLastname();
                first=customer.getFirstname();
                String AddressCustomer=addressCus+","+districtCus+","+cityCus;
                firstnameorder.setText("First Name:"+first);
                lastnameorder.setText("Last Name:"+last);
                phone.setText("Phone:"+phoneCus);
                address.setText("Address:"+AddressCustomer);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        acpectorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mTotalorder=intent.getStringExtra("Totalcount");
                String mFoodorder=intent.getStringExtra("Foodname");
                final HashMap<String,Object> hartMap=new HashMap<>();
                hartMap.put("Foodname",mFoodorder);
                hartMap.put("Phonenumber",phoneCus);
                hartMap.put("TotalCount",mTotalorder);
                hartMap.put("ramdomidOldorder",RamdomUid);
                firebaseDatabase.getInstance().getReference("AdminView").child(Userid).child(mRamdomID).removeValue();
                firebaseDatabase.getInstance().getReference("AdminOldOrder").child(Userid).child(RamdomUid).setValue(hartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(InfoCustomerOderatAdmin.this,"Accept Order!",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

            }
        });
        deleteorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseDatabase.getInstance().getReference("AdminView").child(Userid).child(mRamdomID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(InfoCustomerOderatAdmin.this,"Delete Order!",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });


    }
}