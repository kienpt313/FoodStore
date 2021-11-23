package com.example.cookingrecipegood.AdminfoodPanel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cookingrecipegood.Admin;
import com.example.cookingrecipegood.AdminPanel;
import com.example.cookingrecipegood.R;
import com.example.cookingrecipegood.UpdateFoodModel;
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

public class Update_food extends AppCompatActivity {
    TextInputLayout desc,qty,pri;
    TextView FoodNAME;
    ImageButton imageButton;
    Uri imageuri;
    String duri;
    private Uri mCropimageuri;
    Button Updatefood,DeleteFood;
    String description,quantity,price,foods,AdminID;
    String RandomUID;
    StorageReference ref;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseAuth fauth;
    String ID;
    private ProgressDialog progressDialog;
    DatabaseReference dataa,databaseReference;
    String City,Districts,Address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_food);
        desc=(TextInputLayout)findViewById(R.id.description1);
        qty=(TextInputLayout)findViewById(R.id.Quantity1);
        pri=(TextInputLayout)findViewById(R.id.price1);
        FoodNAME=(TextView)findViewById(R.id.Food_Name);
        imageButton=(ImageButton)findViewById(R.id.image_upload1);
        Updatefood=(Button)findViewById(R.id.AdUpdateFood);
        DeleteFood=(Button)findViewById(R.id.AdDeleteFood);
        ID=getIntent().getStringExtra("updatedeletefood");
        String userid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        dataa=firebaseDatabase.getInstance().getReference("Admin").child(userid);
        dataa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Admin adminn=snapshot.getValue(Admin.class);
                City=adminn.getCity();
                Districts=adminn.getDistrict();
                Address=adminn.getAddress();
                Updatefood.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        description=desc.getEditText().getText().toString().trim();
                        quantity=qty.getEditText().getText().toString().trim();
                        price=pri.getEditText().getText().toString().trim();
                        if(isValid()){
                            if (imageuri!=null){
                                uploadImage();
                            }else {
                                updatedesc(duri);
                            }
                        }

                    }
                });
                DeleteFood.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder=new AlertDialog.Builder(Update_food.this);
                        builder.setMessage("Do you want Delete it?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                firebaseDatabase.getInstance().getReference("FoodOfAdmin").child(userid).child(ID).removeValue();
                                firebaseDatabase.getInstance().getReference("FoodDetails").child(ID).removeValue();
                                AlertDialog.Builder food=new AlertDialog.Builder(Update_food.this);
                                food.setMessage("Delete Successfully!");
                                food.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(Update_food.this, AdminPanel.class));
                                    }
                                });
                                AlertDialog alert=food.create();
                                alert.show();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alert=builder.create();
                        alert.show();
                    }
                });

                progressDialog=new ProgressDialog(Update_food.this);
                databaseReference =FirebaseDatabase.getInstance().getReference("FoodDetails").child(ID);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UpdateFoodModel updateFoodModel=snapshot.getValue(UpdateFoodModel.class);
                        desc.getEditText().setText(updateFoodModel.getDescription());
                        qty.getEditText().setText(updateFoodModel.getQuantity());
                        FoodNAME.setText("Food name:"+updateFoodModel.getFood());
                        foods=updateFoodModel.getFood();
                        pri.getEditText().setText(updateFoodModel.getPrice());
                        Glide.with(Update_food.this).load(updateFoodModel.getImageURL()).into(imageButton);
                        duri=updateFoodModel.getImageURL();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                FirebaseDatabase.getInstance().getReference("FoodOfAdmin").child(userid).child(ID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UpdateFoodModel updateFoodModel=snapshot.getValue(UpdateFoodModel.class);
                        desc.getEditText().setText(updateFoodModel.getDescription());
                        qty.getEditText().setText(updateFoodModel.getQuantity());
                        FoodNAME.setText("Food name:"+updateFoodModel.getFood());
                        foods=updateFoodModel.getFood();
                        pri.getEditText().setText(updateFoodModel.getPrice());
                        Glide.with(Update_food.this).load(updateFoodModel.getImageURL()).into(imageButton);
                        duri=updateFoodModel.getImageURL();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                fauth=FirebaseAuth.getInstance();
                databaseReference=firebaseDatabase.getInstance().getReference("FoodDetails");
                storage=FirebaseStorage.getInstance();
                storageReference=storage.getReference();
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ONSelectImageclick(v);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updatedesc(String duri) {
        AdminID=FirebaseAuth.getInstance().getCurrentUser().getUid();
        FoodDetails info=new FoodDetails(foods,quantity,price,description,duri,ID,AdminID,City,Districts,Address);
        firebaseDatabase.getInstance().getReference("FoodOfAdmin").child(AdminID).child(ID).setValue(info);
        firebaseDatabase.getInstance().getReference("FoodDetails").child(ID)
                .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                Toast.makeText(Update_food.this,"Food Update Successfully!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadImage() {
        if (imageuri!=null){
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            RandomUID= UUID.randomUUID().toString();
            ref=storageReference.child(RandomUID);
            ref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            updatedesc(String.valueOf(uri));

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(Update_food.this,"Failed"+e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progess=(100.0 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                    progressDialog.setMessage("Upload"+(int)progess + "%");
                    progressDialog.setCanceledOnTouchOutside(false);
                }
            });
        }
    }

    private boolean isValid() {
        desc.setErrorEnabled(false);
        desc.setError("");
        qty.setErrorEnabled(false);
        qty.setError("");
        pri.setErrorEnabled(false);
        pri.setError("");
        boolean isValidDescription=false,isValidPrice=false,isValidQuantity=false,isValid=false;
        if(TextUtils.isEmpty(description)){
            desc.setErrorEnabled(true);
            desc.setError("Description is Required");
        } else {
            desc.setError(null);
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

        isValid=(isValidDescription  && isValidPrice && isValidQuantity)?true:false;
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
        if(mCropimageuri !=null && grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            startCropImageActivity(mCropimageuri);
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
                mCropimageuri = imageuri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
            }else{
                startCropImageActivity(imageuri);
            }
        }
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)  {
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK){
                ((ImageButton)findViewById(R.id.image_upload1)).setImageURI(result.getUri());
                Toast.makeText(this,"Cropped Successfully!"+result.getSampleSize(),Toast.LENGTH_SHORT).show();
            }else if(resultCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Toast.makeText(this,"Failed to Crop"+result.getError(),Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}