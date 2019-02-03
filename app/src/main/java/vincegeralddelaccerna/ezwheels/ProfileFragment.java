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
import com.squareup.picasso.Picasso;


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

    TextView name, shopname, contacts, email, location, l;
    EditText nametext, shopnametext, contacttext, emailtext, locationtext, lnametext, descriptiontext;
    Button logoutBtn;
    ImageView editProfile, saveProfile, profImg;
    RatingBar ratingBar;

    //strings

    private String firstname="", lastname="", contact="", address;
    private String contactnumber;
    private String locations;
    private String shopName;
    private float rating;
    private String emaill;
    private String description;
    private String purl;

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
        location = v.findViewById(R.id.location);
        nametext = v.findViewById(R.id.nametext);
        shopnametext = v.findViewById(R.id.shopnametext);
        contacttext = v.findViewById(R.id.contacttext);
        locationtext = v.findViewById(R.id.locationtext);
        ratingBar = v.findViewById(R.id.ratingBar);
        editProfile = v.findViewById(R.id.editProfile);
        saveProfile = v.findViewById(R.id.saveProfile);
        lnametext = v.findViewById(R.id.lnametext);
        descriptiontext = v.findViewById(R.id.descriptiontext);
        profImg.setOnClickListener(this);
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


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    firstname = dataSnapshot.child("firstname").getValue().toString();
                    lastname = dataSnapshot.child("lastname").getValue().toString();
                    contactnumber = dataSnapshot.child("contactnumber").getValue().toString();
                    emaill = dataSnapshot.child("email").getValue().toString();
                    purl = dataSnapshot.child("purl").getValue().toString();
                    nametext.setText(firstname);
                    lnametext.setText(lastname);
                    contacttext.setText(contactnumber);
                    shopname.setVisibility(View.GONE);
                    location.setVisibility(View.GONE);
                    shopnametext.setVisibility(View.GONE);
                    locationtext.setVisibility(View.GONE);
                    descriptiontext.setVisibility(View.GONE);
                    ratingBar.setVisibility(View.GONE);
                    Picasso.get().load(purl).fit().centerCrop().into(profImg);

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
                                description = dataSnapshot.child("description").getValue().toString();
                                purl = dataSnapshot.child("purl").getValue().toString();
                                    //rating = (float) dataSnapshot.child("rating").getValue();
                                ratingBar.setRating(5);
                                nametext.setText(firstname);
                                lnametext.setText(lastname);
                                contacttext.setText(contactnumber);
                                shopnametext.setText(shopName);
                                locationtext.setText(locations);
                                descriptiontext.setText(description);
                                Picasso.get().load(purl).fit().centerCrop().into(profImg);
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
            locationtext.setFocusableInTouchMode(true);
            locationtext.setTextColor(Color.parseColor("#000000"));
            locationtext.setFocusable(true);
            descriptiontext.setFocusableInTouchMode(true);
            descriptiontext.setTextColor(Color.parseColor("#000000"));
            descriptiontext.setFocusable(true);
            profImg.setFocusable(true);
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
            final String descText = descriptiontext.getText().toString();


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
                                        buyerProf.child("contactnumber").setValue(contact);
                                        buyerProf.child("firstname").setValue(name);
                                        buyerProf.child("lastname").setValue(lnameText);
                                        buyerProf.child("email").setValue(emailText);
                                        Toast.makeText(getActivity(), "Profile Updated", Toast.LENGTH_SHORT).show();
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
                                        locationtext.setFocusableInTouchMode(false);
                                        locationtext.setTextColor(Color.parseColor("#606060"));
                                        locationtext.setFocusable(false);
                                        descriptiontext.setFocusableInTouchMode(false);
                                        descriptiontext.setTextColor(Color.parseColor("#606060"));
                                        descriptiontext.setFocusable(false);
                                        profImg.setFocusable(false);
                                        editProfile.setVisibility(View.VISIBLE);
                                        saveProfile.setVisibility(View.GONE);

                                    }

                                    else{
                                        shopProf = FirebaseDatabase.getInstance().getReference("Shop").child(uid);
                                        shopProf.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.exists()){
                                                    shopProf.child("contactnumber").setValue(contact);
                                                    shopProf.child("firstname").setValue(name);
                                                    shopProf.child("lastname").setValue(lnameText);
                                                    shopProf.child("email").setValue(emailText);
                                                    shopProf.child("description").setValue(descText);
                                                    shopProf.child("name").setValue(shopnameText);
                                                    shopProf.child("location").setValue(locationText);
                                                    Toast.makeText(getActivity(), "Profile Updated", Toast.LENGTH_SHORT).show();
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
                                                    locationtext.setFocusableInTouchMode(false);
                                                    locationtext.setTextColor(Color.parseColor("#606060"));
                                                    locationtext.setFocusable(false);
                                                    descriptiontext.setFocusableInTouchMode(false);
                                                    descriptiontext.setTextColor(Color.parseColor("#606060"));
                                                    descriptiontext.setFocusable(false);
                                                    editProfile.setVisibility(View.VISIBLE);
                                                    saveProfile.setVisibility(View.GONE);
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
                    locationtext.setFocusableInTouchMode(false);
                    locationtext.setTextColor(Color.parseColor("#606060"));
                    locationtext.setFocusable(false);
                    descriptiontext.setFocusableInTouchMode(false);
                    descriptiontext.setTextColor(Color.parseColor("#606060"));
                    descriptiontext.setFocusable(false);
                    profImg.setFocusable(false);
                    editProfile.setVisibility(View.VISIBLE);
                    saveProfile.setVisibility(View.GONE);

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
