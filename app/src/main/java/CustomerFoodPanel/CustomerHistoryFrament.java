package CustomerFoodPanel;

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

public class CustomerHistoryFrament extends Fragment {
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference,data;
    FirebaseDatabase firebaseDatabase;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_cus_history,null);
        getActivity().setTitle("History Order");
        recyclerView=v.findViewById(R.id.recyclecustomOldorderCus);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        String Userid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference=FirebaseDatabase.getInstance().getReference("HistoryCart").child(Userid);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
 FirebaseRecyclerAdapter<MyCartModel,CartViewHolder>adapter2=new FirebaseRecyclerAdapter<MyCartModel, CartViewHolder>(
         MyCartModel.class,
         R.layout.dataitemorderincustomer,
         CartViewHolder.class,
         databaseReference
 ) {
     @Override
     protected void populateViewHolder(CartViewHolder cartViewHolder, MyCartModel myCartModel, int i) {
         cartViewHolder.setNamecart("Food:"+myCartModel.getNameFood());
         cartViewHolder.setPricecart("Price:"+myCartModel.getPrice());
         cartViewHolder.setTotalcart("Total"+myCartModel.getTotalCount());
         cartViewHolder.setDeletecart(myCartModel.getRamdomFoodOderID());

     }
 };
 recyclerView.setAdapter(adapter2);
    }
    public static class CartViewHolder extends RecyclerView.ViewHolder{
        View myview;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            myview=itemView;
        }
        public void setNamecart(String name){
            TextView mName=myview.findViewById(R.id.productfoodnameoldeorder);
            mName.setText(name);
        }
        public void setPricecart(String price){
            TextView mPrice=myview.findViewById(R.id.productfoodpriceoldeorder);
            mPrice.setText(price);
        }

        public void setTotalcart(String total){
            TextView mTotal=myview.findViewById(R.id.productfoodcountoldeorder);
            mTotal.setText(total);
        }
        public void setDeletecart(String randomID){
            DatabaseReference databaseReference1;
            String Userid= FirebaseAuth.getInstance().getCurrentUser().getUid();
            databaseReference1=FirebaseDatabase.getInstance().getReference("HistoryCart").child(Userid);
            ImageView mDelete=myview.findViewById(R.id.deletecardoldeorder);
            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databaseReference1.child(randomID).removeValue();
                }
            });

        }

    }
}
