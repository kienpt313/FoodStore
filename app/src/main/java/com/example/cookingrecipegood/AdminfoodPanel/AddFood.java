package com.example.cookingrecipegood.AdminfoodPanel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.cookingrecipegood.Admin;
import com.example.cookingrecipegood.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


import java.util.UUID;

public class AddFood extends AppCompatActivity {
    ImageButton imageButton;
    Button add_food;
    EditText food;
    TextInputLayout des,qty,pri;
    String description,quantity,price,namefood;
    Uri imageuri;
    private Uri mcropimageuri;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,data;
    FirebaseAuth Fbauth;
    StorageReference ref;
    String Adminid,RamdomUid,City,district,address;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        food=(EditText)findViewById(R.id.NameFood);
        des=(TextInputLayout)findViewById(R.id.description);
        qty=(TextInputLayout)findViewById(R.id.Quantity);
        pri=(TextInputLayout)findViewById(R.id.price);
        add_food=(Button)findViewById(R.id.post);
        Fbauth=FirebaseAuth.getInstance();
        databaseReference=firebaseDatabase.getInstance().getReference("FoodDetails");
        try {
            String useid=FirebaseAuth.getInstance().getCurrentUser().getUid();
            data=firebaseDatabase.getInstance().getReference("Admin").child(useid);
            data.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    Admin admin=snapshot.getValue(Admin.class);
                    City=admin.getCity();
                    district=admin.getDistrict();
                    address=admin.getAddress();
                    imageButton=(ImageButton)findViewById(R.id.image_upload);
                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ONSelectImageclick(v);
                        }
                    });
                    add_food.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            description=des.getEditText().getText().toString().trim();
                            quantity=qty.getEditText().getText().toString().trim();
                            price=pri.getEditText().getText().toString().trim();
                            namefood=food.getText().toString().trim();
                            if(isValid()){
                                uploadImage();
                            }
                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch (Exception e){
         Log.e("Error",e.getMessage());
        }
    }

    private void uploadImage() {
        if (imageuri!=null){
            final ProgressDialog progressDialog=new ProgressDialog(AddFood.this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            RamdomUid= UUID.randomUUID().toString();
            ref=storageReference.child(RamdomUid);
            Adminid=FirebaseAuth.getInstance().getCurrentUser().getUid();
            ref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            FoodDetails info=new FoodDetails(namefood,quantity,price,description,String.valueOf(uri),RamdomUid,Adminid,City,district,address);
                            firebaseDatabase.getInstance().getReference("FoodOfAdmin").child(Adminid).child(RamdomUid).setValue(info);
                            firebaseDatabase.getInstance().getReference("FoodDetails").child(RamdomUid).setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressDialog.dismiss();
                                    Toast.makeText(AddFood.this,"add food successfully!",Toast.LENGTH_SHORT).show();

                                }
                            });


                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(AddFood.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progess=(100.0*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded"+(int)progess+"%");
                    progressDialog.setCanceledOnTouchOutside(false);
                }
            });
        }
    }

    private boolean isValid() {
        des.setErrorEnabled(false);
        des.setError("");
        qty.setErrorEnabled(false);
        qty.setError("");
        pri.setErrorEnabled(false);
        pri.setError("");
        boolean isValidDescription=false,isValidPrice=false,isValidQuantity=false,isValidfood=false,isValid=false;
        if(TextUtils.isEmpty(description)){
            des.setErrorEnabled(true);
            des.setError("Description is Required");
        } else {
            des.setError(null);
            isValidDescription=true;
        }
        if(TextUtils.isEmpty(quantity)){
            qty.setErrorEnabled(true);
            qty.setError("Enter number of plate or Items");
        }else {
            isValidQuantity=true;
        }
        if (TextUtils.isEmpty(price)){
            pri.setErrorEnabled(true);
            pri.setError("Please mention Price");
        }else{
            isValidPrice=true;
        }
        if(TextUtils.isEmpty(food.getText().toString())){
            Toast.makeText(AddFood.this,"Enter name Food",Toast.LENGTH_SHORT).show();
        }
        else{
            isValidfood=true;
        }
        isValid=(isValidDescription && isValidfood && isValidPrice && isValidQuantity)?true:false;
        return isValid;

    }
    private void startCropImageActivity(Uri imageuri){
        CropImage.activity(imageuri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
            }
            private void ONSelectImageclick(View v){
        CropImage.startPickImageActivity(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(mcropimageuri !=null && grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            startCropImageActivity(mcropimageuri);
        }else {
            Toast.makeText(this,"Cancelling !Permission not granted",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode== Activity.RESULT_OK){
            imageuri = CropImage.getPickImageResultUri(this,data);
            if(CropImage.isReadExternalStoragePermissionsRequired(this,imageuri)){
                mcropimageuri = imageuri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
            }else{
                startCropImageActivity(imageuri);
            }
        }
if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)  {
    CropImage.ActivityResult result=CropImage.getActivityResult(data);
    if(resultCode==RESULT_OK){
        ((ImageButton)findViewById(R.id.image_upload)).setImageURI(result.getUri());
        Toast.makeText(this,"Cropped Successfully!",Toast.LENGTH_SHORT).show();
    }else if(resultCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
       Toast.makeText(this,"Failed to Crop"+result.getError(),Toast.LENGTH_SHORT).show();
    }
}
        super.onActivityResult(requestCode, resultCode, data);
    }
}