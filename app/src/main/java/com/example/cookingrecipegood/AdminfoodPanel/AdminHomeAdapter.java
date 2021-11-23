package com.example.cookingrecipegood.AdminfoodPanel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingrecipegood.R;
import com.example.cookingrecipegood.UpdateFoodModel;

import java.util.List;

public class AdminHomeAdapter extends RecyclerView.Adapter<AdminHomeAdapter.ViewHolder> {
    private Context mcont;
    private List<UpdateFoodModel>updateFoodModelList;
    public AdminHomeAdapter(Context context,List<UpdateFoodModel>updateFoodModelList){
        this.updateFoodModelList=updateFoodModelList;
        this.mcont=context;
    }
    @NonNull
    @Override
    public AdminHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcont).inflate(R.layout.adminupdatefood,parent,false);
        return new AdminHomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminHomeAdapter.ViewHolder holder, int position) {

        final UpdateFoodModel updateFoodModel=updateFoodModelList.get(position);
        holder.foods.setText(updateFoodModel.getFood());
        updateFoodModel.getRandomUID();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcont,Update_food.class);
                intent.putExtra("updatedeletefood",updateFoodModel.getRandomUID());
               mcont.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return updateFoodModelList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView foods;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foods=itemView.findViewById(R.id.dish_name);
        }
    }
}
