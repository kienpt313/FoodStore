package com.example.cookingrecipegood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.cookingrecipegood.AdminfoodPanel.AdminHomeFragment;
import com.example.cookingrecipegood.AdminfoodPanel.AdminOderFragment;
import com.example.cookingrecipegood.AdminfoodPanel.AdminPendingOrderFragment;
import com.example.cookingrecipegood.AdminfoodPanel.AdminProfileFrament;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminPanel extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        BottomNavigationView navigationView=findViewById(R.id.admin_bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment =null;
        switch (item.getItemId()){
            case R.id.AdminHome:
            {fragment=new AdminHomeFragment();
                break;}
            case R.id.AdminPendingOder:
            { fragment=new AdminPendingOrderFragment();
                break;}
            case R.id.AdminOder:
            {   fragment=new AdminOderFragment();
                break;}
            case R.id.AdminProfile:
            { fragment=new AdminProfileFrament();
                break;}
        }
        return loadAdminfragment(fragment);
    }

    private boolean loadAdminfragment(Fragment fragment) {
        if (fragment !=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            return true;
        }
        return false;
    }
}