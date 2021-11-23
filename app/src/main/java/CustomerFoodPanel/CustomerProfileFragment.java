package CustomerFoodPanel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cookingrecipegood.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerProfileFragment extends Fragment {
    private TextView firstnameprofile,lastnameprofile,emailprofile,phoneprofile,addressproflie;
    FirebaseDatabase firebaseDatabase;
    private DatabaseReference data;
    String cityCus,districtCus,addressCus,phoneCus,firstname,lastname,emailcus;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_cus_profile,null);
        getActivity().setTitle("Profiles");
        firstnameprofile=v.findViewById(R.id.Firstnameprofile);
        lastnameprofile=v.findViewById(R.id.Lastnameprofile);
        emailprofile=v.findViewById(R.id.Emailprofile);
        phoneprofile=v.findViewById(R.id.Phoneprofile);
        addressproflie=v.findViewById(R.id.Addressprofile);
        String useid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        data=firebaseDatabase.getInstance().getReference("Customer").child(useid);
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Customer customer=snapshot.getValue(Customer.class);
                phoneCus=customer.getMbnumber();
                cityCus=customer.getCity();
                districtCus=customer.getDistrict();
                addressCus=customer.getAddress();
                firstname=customer.getFirstname();
                lastname=customer.getLastname();
                emailcus=customer.getEmail();
                String AddressCustomer=addressCus+","+districtCus+","+cityCus;
                firstnameprofile.setText("First Name:"+firstname);
                lastnameprofile.setText("Last Name:"+lastname);
                emailprofile.setText("My Email:"+emailcus);
                phoneprofile.setText("My Phone:"+phoneCus);
                addressproflie.setText("Address:"+AddressCustomer);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return v;
    }
}
