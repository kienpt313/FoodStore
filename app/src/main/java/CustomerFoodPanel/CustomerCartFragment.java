package CustomerFoodPanel;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingrecipegood.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CustomerCartFragment extends Fragment {
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference,data;
    private Button SendAcceptFood;
    FirebaseDatabase firebaseDatabase;
    String Customerid,cityCus,districtCus,addressCus,phoneCus,firstname,lastname;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_cus_cart,null);
        getActivity().setTitle("My Cart");
        String Userid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference=FirebaseDatabase.getInstance().getReference("CustomerCart").child(Userid);
        recyclerView=v.findViewById(R.id.recycleviewcart);
        SendAcceptFood=v.findViewById(R.id.Sendtohis);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        String useid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        data=firebaseDatabase.getInstance().getReference("Customer").child(useid);
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Customer customer=snapshot.getValue(Customer.class);
                phoneCus=customer.getMbnumber();
                cityCus=customer.getCity();
                districtCus=customer.getDistrict();
                addressCus=customer.getAddress();
                firstname=customer.getFirstname();
                lastname=customer.getLastname();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        SendAcceptFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String AddressCustomer=addressCus+","+districtCus+","+cityCus;
                final HashMap<String,Object> hartMap=new HashMap<>();
                hartMap.put("Address",AddressCustomer);
                hartMap.put("Phonenumber",phoneCus);
                hartMap.put("CustomerID",useid);
                hartMap.put("Firstname",firstname);
                hartMap.put("Lastname",lastname);

                firebaseDatabase.getInstance().getReference("CustomerCart").child(useid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getActivity(),"Order Success!!!",Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        return v;
        
    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<MyCartModel,CartViewHolder>adapterCart=new FirebaseRecyclerAdapter<MyCartModel, CartViewHolder>(
                    MyCartModel.class,
                    R.layout.customercartfood,
                    CartViewHolder.class,
                    databaseReference

        ) {
            @Override
            protected void populateViewHolder(CartViewHolder cartViewHolder, MyCartModel myCartModel, int i) {
             cartViewHolder.setNamecart(myCartModel.getNameFood());
             cartViewHolder.setPricecart("Price:"+myCartModel.getPrice());
             cartViewHolder.setTimecart("Time:"+myCartModel.getCurrentTime());
             cartViewHolder.setDatecart("Date:"+myCartModel.getCurrentDate());
             cartViewHolder.setTotalcart("Total:"+myCartModel.getTotalCount());
             cartViewHolder.setDeletecart(myCartModel.getAdminID(),myCartModel.getRamdomFoodOderID());




            }
        };
        recyclerView.setAdapter(adapterCart);
    }
    public static class CartViewHolder extends RecyclerView.ViewHolder{
        View myview;


        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            myview=itemView;
        }
        public void setDeletecart(String adminid,String randomID){
            DatabaseReference databaseReference1,databaseReference2,databaseReference3;
            String Userid1=FirebaseAuth.getInstance().getCurrentUser().getUid();
            databaseReference1=FirebaseDatabase.getInstance().getReference("CustomerCart").child(Userid1);
            databaseReference2=FirebaseDatabase.getInstance().getReference("HistoryCart").child(Userid1);
            databaseReference3=FirebaseDatabase.getInstance().getReference("AdminView").child(adminid);
            ImageView mDelete=myview.findViewById(R.id.deletecard);
            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   databaseReference1.child(randomID).removeValue();
                    databaseReference2.child(randomID).removeValue();
                    databaseReference3.child(randomID).removeValue();
                }
            });

        }
        public void setNamecart(String name){
            TextView mName=myview.findViewById(R.id.productfoodname);
            mName.setText(name);
        }
        public void setPricecart(String price){
            TextView mPrice=myview.findViewById(R.id.productfoodprice);
            mPrice.setText(price);
        }
        public void setTimecart(String time){
            TextView mTime=myview.findViewById(R.id.productfoodtime);
            mTime.setText(time);
        }
        public void setDatecart(String date){
            TextView mDate=myview.findViewById(R.id.productfoodate);
            mDate.setText(date);
        }
        public void setTotalcart(String total){
            TextView mTotal=myview.findViewById(R.id.productfoodcount);
            mTotal.setText(total);
        }

    }
}
