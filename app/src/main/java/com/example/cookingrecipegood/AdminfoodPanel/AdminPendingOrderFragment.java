package com.example.cookingrecipegood.AdminfoodPanel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

import CustomerFoodPanel.FoodOderInfo;

public class  AdminPendingOrderFragment extends Fragment {
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_admin_pendingoder,null);
        getActivity().setTitle("Pending Orders");
        recyclerView=v.findViewById(R.id.recyclecustomorder);
        String Adminid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference("AdminView").child(Adminid);
        databaseReference.keepSynced(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<DataPendingOrderAdmin,AdViewHolder>adapter1=new FirebaseRecyclerAdapter<DataPendingOrderAdmin, AdViewHolder>(
                DataPendingOrderAdmin.class,
                R.layout.newfoodorderofadmin,
                AdminPendingOrderFragment.AdViewHolder.class,
                databaseReference

        ) {
            @Override
            protected void populateViewHolder(AdViewHolder adViewHolder, DataPendingOrderAdmin dataPendingOrderAdmin, int i) {
                adViewHolder.setTotal("Total:"+dataPendingOrderAdmin.getTotalCount());
                adViewHolder.setFoodname("Name Food:"+dataPendingOrderAdmin.getNameFood());
                adViewHolder.myview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getActivity(), InfoCustomerOderatAdmin.class);
                        intent.putExtra("Totalcount",dataPendingOrderAdmin.getTotalCount());
                        intent.putExtra("Foodname",dataPendingOrderAdmin.getNameFood());
                        intent.putExtra("customerid",dataPendingOrderAdmin.getCustomerID());
                        intent.putExtra("RAMDOMID",dataPendingOrderAdmin.getRamdomFoodOderID());
                        startActivity(intent);
                    }
                });


            }
        };
        recyclerView.setAdapter(adapter1);
    }
    public static class AdViewHolder extends RecyclerView.ViewHolder{

        View myview;

        public AdViewHolder(@NonNull View itemView) {
            super(itemView);
            myview=itemView;
        }
        public void setTotal(String total){
            TextView mTotalname=myview.findViewById(R.id.totalinviewAD);
            mTotalname.setText(total);
        }
        public void setFoodname(String foodname){
            TextView mFoodname=myview.findViewById(R.id.foodinviewAD);
            mFoodname.setText(foodname);
        }




    }
}
