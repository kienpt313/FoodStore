package CustomerFoodPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookingrecipegood.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class FoodOderInfo extends AppCompatActivity {

    TextView countFoodorder;
    ImageView addcountfood,removecountfood;
    Button addfoodtocart;
    int totalCoutnfoodorder=1;
    private ImageView imageViewfood;
    private TextView namefoodorder,pricefoodorder,descriptionfoodorder,addressfood;
    String CustomerID;
    FirebaseAuth Fbauth;

    DatabaseReference databaseReference0,databaseReference1;
    FirebaseDatabase firebaseDatabase0,firebaseDatabase1,firebaseDatabase2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_oder_info);
        countFoodorder=findViewById(R.id.countitemorder);
        addcountfood=findViewById(R.id.addfoodcount);
        addfoodtocart=findViewById(R.id.addfood_to_card);
        removecountfood=findViewById(R.id.removefoodcount);
        imageViewfood=findViewById(R.id.detailsorderfood);
        namefoodorder=findViewById(R.id.namedetailsFood);
        pricefoodorder=findViewById(R.id.pricedetailsFood);
        descriptionfoodorder=findViewById(R.id.descriptionfoodorder);
        addressfood=findViewById(R.id.addressfoodorderinfo);
        Fbauth=FirebaseAuth.getInstance();

        databaseReference0=firebaseDatabase0.getInstance().getReference("CustomerCart");
        //databaseReference1=firebaseDatabase1.getInstance().getReference("CustomerOrder");
        //String useid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        //data=firebaseDatabase.getInstance().getReference("Customer").child(useid);
        addcountfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalCoutnfoodorder<100) {
                    totalCoutnfoodorder++;
                    countFoodorder.setText(String.valueOf(totalCoutnfoodorder));
                }

            }
        });
        removecountfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalCoutnfoodorder>1){
                    totalCoutnfoodorder--;
                    countFoodorder.setText(String.valueOf(totalCoutnfoodorder));
                }

            }
        });

        addfoodtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Customeraddtocart();
            }
        });
        Intent intent=getIntent();
        String mNamefoodorder=intent.getStringExtra("namefoodoreder");
        String mPricefoodorder=intent.getStringExtra("pricefoodorder");
        String mDescripfoodorder=intent.getStringExtra("descriptionfoodorder");
        String mAddressfoodorder=intent.getStringExtra("addressofadmin");
        String mDistrictfoodorder=intent.getStringExtra("districtofadmin");
        String mCityfoodorder=intent.getStringExtra("cityofadmin");
        String AddressAdmin="Address:"+mAddressfoodorder+","+mDistrictfoodorder+","+mCityfoodorder;
        final String mImagefoodorder=intent.getStringExtra("imagefoodorder");
        namefoodorder.setText(mNamefoodorder);
        descriptionfoodorder.setText(mDescripfoodorder);
        pricefoodorder.setText(mPricefoodorder);
        addressfood.setText(AddressAdmin);

        Picasso.get().load(mImagefoodorder).networkPolicy(NetworkPolicy.OFFLINE).into(imageViewfood, new Callback() {
            @Override
            public void onSuccess() {
            }
            @Override
            public void onError(Exception e) {
                Picasso.get().load(mImagefoodorder).into(imageViewfood);
            }
        });
    }

    private void Customeraddtocart() {
        String saveCurrentDate,saveCurrentTime;
        String RamdomUid;
        String AdminIDoffood=getIntent().getStringExtra("adminIDofFood");
        RamdomUid= UUID.randomUUID().toString();
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calendar.getTime());
        final HashMap<String,Object>cartMap=new HashMap<>();
        String useid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        cartMap.put("NameFood",namefoodorder.getText().toString());
        cartMap.put("Price",pricefoodorder.getText().toString());
        cartMap.put("CurrentDate",saveCurrentDate);
        cartMap.put(("CurrentTime"),saveCurrentTime);
        cartMap.put("TotalCount",countFoodorder.getText().toString());
        cartMap.put("RamdomFoodOderID",RamdomUid);
        cartMap.put("AdminID",AdminIDoffood);
        cartMap.put("CustomerID",useid);
        CustomerID=FirebaseAuth.getInstance().getCurrentUser().getUid();
        firebaseDatabase1.getInstance().getReference("AdminView").child(AdminIDoffood).child(RamdomUid).setValue(cartMap);
        firebaseDatabase2.getInstance().getReference("HistoryCart").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RamdomUid).setValue(cartMap);

        firebaseDatabase0.getInstance().getReference("CustomerCart").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RamdomUid).setValue(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(FoodOderInfo.this,"Added to a Cart",Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }
}