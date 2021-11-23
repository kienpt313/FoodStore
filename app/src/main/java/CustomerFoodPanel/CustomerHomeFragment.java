package CustomerFoodPanel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.cookingrecipegood.AdminfoodPanel.FoodDetails;
import com.example.cookingrecipegood.MainStart;
import com.example.cookingrecipegood.R;
import com.example.cookingrecipegood.UpdateFoodModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CustomerHomeFragment extends Fragment {
    private RecyclerView foodadminRecyclerview;
    private TextView searchfoodtoorder;
    private DatabaseReference MdatabaseReference;
    ImageSlider imageSlider;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_cus_home,null);
        getActivity().setTitle("Home");
        setHasOptionsMenu(true);
        MdatabaseReference=FirebaseDatabase.getInstance().getReference().child("FoodDetails");
        foodadminRecyclerview=v.findViewById(R.id.food_admin_recyc);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        foodadminRecyclerview.setHasFixedSize(true);
        foodadminRecyclerview.setLayoutManager(layoutManager);
        imageSlider=v.findViewById(R.id.image_slider);
        final List<SlideModel>remoteimages=new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Advertisement").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren()){
                    remoteimages.add(new SlideModel(data.child("url").getValue().toString(),data.child("title").getValue().toString(), ScaleTypes.FIT));
                    imageSlider.setImageList(remoteimages,ScaleTypes.FIT);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        searchfoodtoorder=v.findViewById(R.id.searchFoodOrder);
        searchfoodtoorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getActivity(),SearchFoodActivity.class);
                startActivity(intent);
            }
        });

        return v;

    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<DataOderFood,foodorderViewHolder>adapterorder=new FirebaseRecyclerAdapter<DataOderFood, foodorderViewHolder>
                (
                        DataOderFood.class,
                        R.layout.itemoderfood_data,
                        foodorderViewHolder.class,
                        MdatabaseReference

                ) {
            @Override
            protected void populateViewHolder(foodorderViewHolder foodorderViewHolder, DataOderFood dataOderFood, int i) {

                foodorderViewHolder.setNameOrderFood("Name Food:"+dataOderFood.getFood());
                foodorderViewHolder.setPriceOrder("Price:"+dataOderFood.getPrice());
                foodorderViewHolder.setImageOrder(dataOderFood.getImageURL());
                foodorderViewHolder.myview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getActivity(),FoodOderInfo.class);
                        intent.putExtra("namefoodoreder",dataOderFood.getFood());
                        intent.putExtra("pricefoodorder",dataOderFood.getPrice());
                        intent.putExtra("imagefoodorder",dataOderFood.getImageURL());
                        intent.putExtra("descriptionfoodorder",dataOderFood.getDescription());
                        intent.putExtra("adminIDofFood",dataOderFood.getAdminID());
                        intent.putExtra("cityofadmin",dataOderFood.getCity());
                        intent.putExtra("districtofadmin",dataOderFood.getDistricts());
                        intent.putExtra("addressofadmin",dataOderFood.getAddress());
                        startActivity(intent);
                    }
                });
            }
        };
        foodadminRecyclerview.setAdapter(adapterorder);
    }
    public static class foodorderViewHolder extends RecyclerView.ViewHolder{

        View myview;
        public foodorderViewHolder(@NonNull View itemView) {
            super(itemView);
            myview=itemView;
        }

        public void setNameOrderFood(String name_Food){
            TextView mNamefood=myview.findViewById(R.id.nameorderfood);
            mNamefood.setText(name_Food);
        }
        public void setPriceOrder(String price_order){
                TextView mPricefood=myview.findViewById(R.id.priceorderfood);
                mPricefood.setText(price_order);
        }
        public void setImageOrder(String image_order){
            ImageView mimagefood=myview.findViewById(R.id.Imagevieworderfood);
            Picasso.get().load(image_order).networkPolicy(NetworkPolicy.OFFLINE).into(mimagefood, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    Picasso.get().load(image_order).into(mimagefood);

                }
            });
        }
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
