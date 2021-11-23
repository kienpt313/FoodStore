package CustomerFoodPanel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingrecipegood.R;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHodlder>{
    Context context;
    List<MyCartModel> cartModelList;
    public MyCartAdapter(Context context,List<MyCartModel>cartModelList){
        this.context=context;
        this.cartModelList=cartModelList;
    }
    @NonNull
    @Override
    public ViewHodlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHodlder(LayoutInflater.from(parent.getContext()).inflate(R.layout.customercartfood,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodlder holder, int position) {

        holder.name.setText(cartModelList.get(position).getNameFood());
        holder.price.setText(cartModelList.get(position).getPrice());
        holder.date.setText(cartModelList.get(position).getCurrentDate());
        holder.total.setText(cartModelList.get(position).getTotalCount());
        holder.time.setText(cartModelList.get(position).getCurrentTime());


    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class ViewHodlder extends RecyclerView.ViewHolder {
        TextView name,price,date,time,total;
        public ViewHodlder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.productfoodname);
            price=itemView.findViewById(R.id.productfoodprice);
            date=itemView.findViewById(R.id.productfoodate);
            time=itemView.findViewById(R.id.productfoodtime);
            total=itemView.findViewById(R.id.productfoodcount);
        }
    }
}
