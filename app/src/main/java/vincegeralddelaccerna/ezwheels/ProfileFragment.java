package vincegeralddelaccerna.ezwheels;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
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

    DatabaseReference databaseReference, databaseReference1, buyerProf, shopProf;
    private FirebaseAuth mAuth;

    //buttons
    Button btnLogout;

    //Textviews

    TextView name, shopname, contacts, email, location;
    EditText nametext, shopnametext, contacttext, emailtext, locationtext, lnametext;
    Button logoutBtn;
    ImageView editProfile, saveProfile;
    RatingBar ratingBar;

    //strings

    private String firstname="", lastname="", contact="", address;
    private String contactnumber;
    private String locations;
    private String shopName;
    private float rating;
    private String emaill;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_profile, container, false);

        logoutBtn = v.findViewById(R.id.logoutBtn);
        name = v.findViewById(R.id.name);
        shopname = v.findViewById(R.id.shopname);
        contacts = v.findViewById(R.id.contact);
        email = v.findViewById(R.id.email);
        location = v.findViewById(R.id.location);
        nametext = v.findViewById(R.id.nametext);
        shopnametext = v.findViewById(R.id.shopnametext);
        contacttext = v.findViewById(R.id.contacttext);
        emailtext = v.findViewById(R.id.emailtext);
        locationtext = v.findViewById(R.id.locationtext);
        ratingBar = v.findViewById(R.id.ratingBar);
        editProfile = v.findViewById(R.id.editProfile);
        saveProfile = v.findViewById(R.id.saveProfile);
        lnametext = v.findViewById(R.id.lnametext);
        saveProfile.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
        editProfile.setOnClickListener(this);




        //textviews and buttons



        //firebase

        mAuth = FirebaseAuth.getInstance();
        final String uid = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Buyers").child(uid);
        databaseReference1 = FirebaseDatabase.getInstance().getReference();
        buyerProf = FirebaseDatabase.getInstance().getReference();
        shopProf = FirebaseDatabase.getInstance().getReference();

        //listeners
////        btnLogout.setOnClickListener(this);
//        this.firstname = firstname;
//        this.lastname = lastname;
//        this.username = username;
//        this.contactnumber = contactnumber;
//        this.purl = purl;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    firstname = dataSnapshot.child("firstname").getValue().toString();
                    lastname = dataSnapshot.child("lastname").getValue().toString();
                    contactnumber = dataSnapshot.child("contactnumber").getValue().toString();
                    emaill = dataSnapshot.child("email").getValue().toString();
                    nametext.setText(firstname);
                    lnametext.setText(lastname);
                    contacttext.setText(contactnumber);
                    emailtext.setText(emaill);
                    shopname.setVisibility(View.GONE);
                    location.setVisibility(View.GONE);
                    shopnametext.setVisibility(View.GONE);
                    locationtext.setVisibility(View.GONE);
                    ratingBar.setVisibility(View.GONE);

                }
                else{
                    databaseReference1 = FirebaseDatabase.getInstance().getReference("Shop").child(uid);
                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                firstname = dataSnapshot.child("firstname").getValue().toString();
                                lastname = dataSnapshot.child("lastname").getValue().toString();
                                contactnumber = dataSnapshot.child("contact").getValue().toString();
                                locations = dataSnapshot.child("location").getValue().toString();
                                shopName = dataSnapshot.child("name").getValue().toString();
                                emaill = dataSnapshot.child("email").getValue().toString();
                                    //rating = (float) dataSnapshot.child("rating").getValue();
                                ratingBar.setRating(5);
                                nametext.setText(firstname);
                                lnametext.setText(lastname);
                                contacttext.setText(contactnumber);
                                shopnametext.setText(shopName);
                                emailtext.setText(emaill);
                                locationtext.setText(locations);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
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

        if(id == R.id.logoutBtn){
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(getActivity(), "Thank you for using the app", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }

        if(id == R.id.editProfile){
            Toast.makeText(getActivity(), "edit", Toast.LENGTH_SHORT).show();
            nametext.setFocusableInTouchMode(true);
            nametext.setTextColor(Color.parseColor("#000000"));
            nametext.setFocusable(true);
            lnametext.setFocusableInTouchMode(true);
            lnametext.setTextColor(Color.parseColor("#000000"));
            lnametext.setFocusable(true);
            contacttext.setFocusableInTouchMode(true);
            contacttext.setTextColor(Color.parseColor("#000000"));
            contacttext.setFocusable(true);
            shopnametext.setFocusableInTouchMode(true);
            shopnametext.setTextColor(Color.parseColor("#000000"));
            shopnametext.setFocusable(true);
            emailtext.setFocusableInTouchMode(true);
            emailtext.setTextColor(Color.parseColor("#000000"));
            emailtext.setFocusable(true);
            locationtext.setFocusableInTouchMode(true);
            locationtext.setTextColor(Color.parseColor("#000000"));
            locationtext.setFocusable(true);
            editProfile.setVisibility(View.GONE);
            saveProfile.setVisibility(View.VISIBLE);
        }

        if(id == R.id.saveProfile){
            final String name = nametext.getText().toString();
            final String contact = contacttext.getText().toString();
            final String shopnameText = shopnametext.getText().toString();
            final String emailText = emailtext.getText().toString();
            final String locationText = locationtext.getText().toString();
            final String lnameText = lnametext.getText().toString();


            final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Update Profile Account?").setCancelable(false)
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            buyerProf = FirebaseDatabase.getInstance().getReference("Buyers").child(uid);
                            buyerProf.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        Buyer buyer = new Buyer();
                                    }
                                    else{
                                        shopProf = FirebaseDatabase.getInstance().getReference("Shop").child(uid);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    nametext.setFocusableInTouchMode(false);
                    nametext.setTextColor(Color.parseColor("#606060"));
                    nametext.setFocusable(false);
                    lnametext.setFocusableInTouchMode(false);
                    lnametext.setTextColor(Color.parseColor("#606060"));
                    lnametext.setFocusable(false);
                    contacttext.setFocusableInTouchMode(false);
                    contacttext.setTextColor(Color.parseColor("#606060"));
                    contacttext.setFocusable(false);
                    shopnametext.setFocusableInTouchMode(false);
                    shopnametext.setTextColor(Color.parseColor("#606060"));
                    shopnametext.setFocusable(false);
                    emailtext.setFocusableInTouchMode(false);
                    emailtext.setTextColor(Color.parseColor("#606060"));
                    emailtext.setFocusable(false);
                    locationtext.setFocusableInTouchMode(false);
                    locationtext.setTextColor(Color.parseColor("#606060"));
                    locationtext.setFocusable(false);
                    editProfile.setVisibility(View.GONE);
                    saveProfile.setVisibility(View.VISIBLE);
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.setTitle(firstname + " " + lastname);
            alertDialog.show();
        }
    }

//    @Override
//    public void onClick(View view) {
//
//        int id = view.getId();
//
//        if(id == R.id.btnLogout){
//            mAuth.signOut();
//        }
//
//    }
}
