package CustomerFoodPanel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cookingrecipegood.R;
import com.example.cookingrecipegood.UpdateFoodModel;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class CustomerHomeAdapter extends RecyclerView.Adapter<CustomerHomeAdapter.ViewHolder> {
    private Context mcontext;
    private List<UpdateFoodModel>updateFoodModelList;
    DatabaseReference databaseReference;
    public CustomerHomeAdapter(Context context,List<UpdateFoodModel>updateFoodModelList){
        this.updateFoodModelList=updateFoodModelList;
        this.mcontext=context;
    }
    @NonNull
    @Override
    public CustomerHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view= LayoutInflater.from(mcontext).inflate(R.layout.itemoderfood_data,parent,false);
      return new CustomerHomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerHomeAdapter.ViewHolder holder, int position) {

        final UpdateFoodModel updateFoodModel=updateFoodModelList.get(position);
        Glide.with(mcontext).load(updateFoodModel.getImageURL()).into(holder.imageView);
        holder.namefoodorder.setText(updateFoodModel.getFood());
         updateFoodModel.getRandomUID();
         updateFoodModel.getAdminID();
         holder.pircefoodorder.setText("Price"+updateFoodModel.getPrice());
    }

    @Override
    public int getItemCount() {
        return updateFoodModelList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView namefoodorder,pircefoodorder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.Imagevieworderfood);
            namefoodorder=itemView.findViewById(R.id.nameorderfood);
            pircefoodorder=itemView.findViewById(R.id.priceorderfood);
        }
    }
}
