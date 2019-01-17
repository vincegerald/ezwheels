package vincegeralddelaccerna.ezwheels;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 *
 *
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    //buttons
    Button btnLogout;

    //Textviews

    TextView userName, userContact, userAddress;

    //strings

    private String firstname="", lastname="", contact="", address;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_profile, container, false);

        //textviews and buttons

        btnLogout = v.findViewById(R.id.btnLogout);
        userName = v.findViewById(R.id.userName);
        userContact = v.findViewById(R.id.userContact);

        //firebase

        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Buyers").child(uid);


        //listeners
        btnLogout.setOnClickListener(this);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                firstname = dataSnapshot.child("firstname").getValue().toString();
                lastname = dataSnapshot.child("lastname").getValue().toString();
                contact = dataSnapshot.child("contactnumber").getValue().toString();
                userName.setText(firstname + " " + lastname);
                userContact.setText(contact);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



        return v;
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if(id == R.id.btnLogout){
            mAuth.signOut();
        }

    }
}
