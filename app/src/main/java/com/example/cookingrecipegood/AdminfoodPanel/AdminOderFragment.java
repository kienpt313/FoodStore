package com.example.cookingrecipegood.AdminfoodPanel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingrecipegood.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminOderFragment extends Fragment {
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference,data;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_admin_order,null);
        getActivity().setTitle("New Orders");
        recyclerView=v.findViewById(R.id.recyclecustomOldorderAD);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        String Userid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        databaseReference= FirebaseDatabase.getInstance().getReference("AdminOldOrder").child(Userid);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<dataitemoldoderatadmin,CartViewHolder1>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<dataitemoldoderatadmin, CartViewHolder1>(
                dataitemoldoderatadmin.class,
                R.layout.datafoodoldorder,
                CartViewHolder1.class,
                databaseReference
        ) {
            @Override
            protected void populateViewHolder(CartViewHolder1 cartViewHolder1, dataitemoldoderatadmin dataitemoldoderatadmin, int i) {
                cartViewHolder1.setNamecart("Food:"+dataitemoldoderatadmin.getFoodname());
                cartViewHolder1.setphone("Phone:"+dataitemoldoderatadmin.getPhonenumber());
                cartViewHolder1.setTotalcart("Total:"+dataitemoldoderatadmin.getTotalCount());
                cartViewHolder1.setDeletecart(dataitemoldoderatadmin.getRamdomidOldorder());

            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    public static class CartViewHolder1 extends RecyclerView.ViewHolder{
        View myview;
        public CartViewHolder1(@NonNull View itemView) {
            super(itemView);
            myview=itemView;
        }
        public void setNamecart(String name){
            TextView mName=myview.findViewById(R.id.foodinviewOldAD);
            mName.setText(name);
        }
        public void setTotalcart(String total){
            TextView mTotal=myview.findViewById(R.id.totalinviewOldAD);
            mTotal.setText(total);
        }
        public void setphone(String phone){
            TextView mPhone=myview.findViewById(R.id.phoneinviewOldAD);
            mPhone.setText(phone);
        }
        public void setDeletecart(String randomID){
            DatabaseReference databaseReference1;
            String Userid= FirebaseAuth.getInstance().getCurrentUser().getUid();
            databaseReference1=FirebaseDatabase.getInstance().getReference("AdminOldOrder").child(Userid);
            ImageView mDelete=myview.findViewById(R.id.deletecardviewOldAD);
            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databaseReference1.child(randomID).removeValue();
                }
            });
        }
    }
}
