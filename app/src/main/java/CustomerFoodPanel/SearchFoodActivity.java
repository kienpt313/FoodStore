package CustomerFoodPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookingrecipegood.AdminfoodPanel.FoodDetails;
import com.example.cookingrecipegood.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class SearchFoodActivity extends AppCompatActivity {
    private Button searchBTN;
    private EditText inputText;
    private RecyclerView recyclerSearch;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_food);
        inputText=findViewById(R.id.searchfoodnameatcus);
        searchBTN=findViewById(R.id.buttonsearchfoodnameatcus);
        databaseReference=FirebaseDatabase.getInstance().getReference().child("FoodDetails");
        recyclerSearch=findViewById(R.id.recycsearch);
        recyclerSearch.setLayoutManager(new LinearLayoutManager(this));
        recyclerSearch.setHasFixedSize(true);

        searchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTextiput=inputText.getText().toString();
                firebaseuserSearch(searchTextiput);

            }
        });
    }

    private void firebaseuserSearch(String searchTextiput) {

        Toast.makeText(SearchFoodActivity.this,"Started Search",Toast.LENGTH_LONG).show();
        Query firebasesearchQuery=databaseReference.orderByChild("Food").startAt(searchTextiput).endAt(searchTextiput + "\uf8ff");

        FirebaseRecyclerAdapter<DataOderFood,CustomerViewHolder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<DataOderFood, CustomerViewHolder>(
                DataOderFood.class,
                R.layout.dataitemfoodtosearch,
                CustomerViewHolder.class,
                firebasesearchQuery
        ) {
            @Override
            protected void populateViewHolder(CustomerViewHolder customerViewHolder, DataOderFood dataOderFood, int i) {
                           customerViewHolder.setNameOrderFood("Name Food:"+dataOderFood.getFood());
                           customerViewHolder.setPriceOrder("Price:"+dataOderFood.getPrice());
                           customerViewHolder.setImageOrder(dataOderFood.getImageURL());
                           customerViewHolder.myView.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   Intent intent=new Intent(SearchFoodActivity.this,FoodOderInfo.class);
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
        recyclerSearch.setAdapter(firebaseRecyclerAdapter);
    }

    public static class CustomerViewHolder extends RecyclerView.ViewHolder{
        View myView;
        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            myView=itemView;
        }
        public void setNameOrderFood(String name_Food){
            TextView mNamefood=myView.findViewById(R.id.nameorderfoodsearch);
            mNamefood.setText(name_Food);
        }
        public void setPriceOrder(String price_order){
            TextView mPricefood=myView.findViewById(R.id.priceorderfoodsearch);
            mPricefood.setText(price_order);
        }
        public void setImageOrder(String image_order){
            ImageView mimagefood=myView.findViewById(R.id.Imagevieworderfoodsearch);
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



}