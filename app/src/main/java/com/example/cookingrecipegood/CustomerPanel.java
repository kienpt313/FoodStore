package com.example.cookingrecipegood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.cookingrecipegood.AdminfoodPanel.AdminHomeFragment;
import com.example.cookingrecipegood.AdminfoodPanel.AdminOderFragment;
import com.example.cookingrecipegood.AdminfoodPanel.AdminPendingOrderFragment;
import com.example.cookingrecipegood.AdminfoodPanel.AdminProfileFrament;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import CustomerFoodPanel.CustomerCartFragment;
import CustomerFoodPanel.CustomerHistoryFrament;
import CustomerFoodPanel.CustomerHomeFragment;
import CustomerFoodPanel.CustomerProfileFragment;

public class CustomerPanel extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_panel);
        BottomNavigationView navigationView=findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment=null;
        switch (item.getItemId()){
            case R.id.Homecus:
            {fragment=new CustomerHomeFragment();
                break;}
            case R.id.cartcus:
            { fragment=new CustomerCartFragment();
                break;}
            case R.id.ordercus:
            {   fragment=new CustomerHistoryFrament();
                break;}
            case R.id.profilecus:
            { fragment=new CustomerProfileFragment();
                break;}
        }
        return loadcustomerfragment(fragment);
    }

    private boolean loadcustomerfragment(Fragment fragment) {
        if (fragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            return true;
        }
        return false;
    }
}