package com.example.cookingrecipegood.AdminfoodPanel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cookingrecipegood.R;

public class AdminProfileFrament extends Fragment {
    Button addFood;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_admin_profile,null);
        getActivity().setTitle("Add Food");
        addFood=(Button)v.findViewById(R.id.add_food);
        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),AddFood.class));
            }
        });
        return v;

    }
}
