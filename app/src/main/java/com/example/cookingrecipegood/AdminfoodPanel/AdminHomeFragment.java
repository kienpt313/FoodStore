package com.example.cookingrecipegood.AdminfoodPanel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingrecipegood.Admin;
import com.example.cookingrecipegood.MainStart;
import com.example.cookingrecipegood.R;
import com.example.cookingrecipegood.UpdateFoodModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminHomeFragment extends Fragment {
    RecyclerView recyclerView;
    private List<UpdateFoodModel>updateFoodModelList;
    private AdminHomeAdapter adapter;
    DatabaseReference dataa;
    private String City,Districts,Address;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_admin_home,null);
        getActivity().setTitle("Home");
        setHasOptionsMenu(true);
        recyclerView =v.findViewById(R.id.recycle_menu);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        updateFoodModelList=new ArrayList<>();
        String userid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        dataa= FirebaseDatabase.getInstance().getReference("Admin").child(userid);
        dataa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Admin adminn=snapshot.getValue(Admin.class);
                City=adminn.getCity();
                Districts=adminn.getDistrict();
                Address=adminn.getAddress();
                AdminFood();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return v;
    }

    private void AdminFood() {
        String useridd=FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("FoodOfAdmin").child(useridd);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                updateFoodModelList.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    UpdateFoodModel updateFoodModel=snapshot1.getValue(UpdateFoodModel.class);
                    updateFoodModelList.add(updateFoodModel);
                }
                adapter=new AdminHomeAdapter(getContext(),updateFoodModelList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.logout,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idd=item.getItemId();
        if (idd==R.id.LOGOUT){
            Logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent=new Intent(getActivity(), MainStart.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
