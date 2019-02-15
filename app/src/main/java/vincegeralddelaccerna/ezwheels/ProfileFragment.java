package vincegeralddelaccerna.ezwheels;


import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 *
 *
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    DatabaseReference databaseReference, databaseReference1, buyerProf, shopProf, checkIfAlreadyPaid, checkIfAlreadyAdded;
    private static final int IMAGE_REQUEST_1 = 1;
    private FirebaseAuth mAuth;

    //buttons
    Button btnLogout;

    //Textviews

    TextView name, shopname, contacts, email, location, descriptionn, click, account;
    EditText nametext, shopnametext, contacttext, emailtext, locationtext, lnametext, descriptiontext;
    Button logoutBtn;
    ImageView editProfile, saveProfile, profImg, addComp;
    TextView addCompany;
    RatingBar ratingBar;
    ProgressBar bar;
    private StorageReference mStorageRef;

    //strings

    private String firstname="", lastname="", contact="", address;
    private String contactnumber;
    private String locations;
    private String shopName;
    private float rating;
    private String emaill;
    private String description;
    private String purl;
    private Uri imageUri;
    private String profUrl;
    private String status;
    private static String uid;

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
        profImg = v.findViewById(R.id.profImg);
        editProfile = v.findViewById(R.id.editProfile);
        saveProfile = v.findViewById(R.id.saveProfile);
        lnametext = v.findViewById(R.id.lnametext);
        descriptiontext = v.findViewById(R.id.descriptiontext);
        descriptionn = v.findViewById(R.id.description);
        click = v.findViewById(R.id.click);
        account = v.findViewById(R.id.account);
        addComp = v.findViewById(R.id.addComp);
        addCompany = v.findViewById(R.id.addCompany);
        profImg.setOnClickListener(this);
        bar = v.findViewById(R.id.bar);
        click.setOnClickListener(this);
        saveProfile.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
        editProfile.setOnClickListener(this);
        profImg.setClickable(false);
        profImg.setFocusable(false);
        ratingBar.setFocusable(false);
        addComp.setOnClickListener(this);
        addCompany.setOnClickListener(this);

        //firebase

        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        final String uid = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Buyers").child(uid);
        databaseReference1 = FirebaseDatabase.getInstance().getReference();
        buyerProf = FirebaseDatabase.getInstance().getReference();
        shopProf = FirebaseDatabase.getInstance().getReference();
        checkIfAlreadyPaid = FirebaseDatabase.getInstance().getReference();
        checkIfAlreadyAdded = FirebaseDatabase.getInstance().getReference();

        checkIfAlreadyPaid = FirebaseDatabase.getInstance().getReference("Payments");

        checkIfAlreadyPaid.orderByChild("type").equalTo("Subscription Fee").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String id = dataSnapshot.child("uid").getValue().toString();
                if(id.equals(mAuth.getCurrentUser().getUid())){
                    click.setText("Wait for the confirmation email from the representative from ezwheels");
                    click.setClickable(false);
                    click.setFocusable(false);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        checkIfAlreadyAdded = FirebaseDatabase.getInstance().getReference("Finance Company");

        checkIfAlreadyAdded.orderByChild("shopUid").equalTo(mAuth.getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String id = dataSnapshot.child("shopUid").getValue().toString();
                if(id.equals(mAuth.getCurrentUser().getUid())){
                    addCompany.setText("VIEW FINANCE COMPANIES");
                    addCompany.setVisibility(View.VISIBLE);
                    addComp.setVisibility(View.GONE);
                    addComp.setClickable(false);
                    addComp.setFocusable(false);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    bar.setVisibility(View.VISIBLE);
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
                    bar.setVisibility(View.GONE);
                    descriptionn.setVisibility(View.GONE);
                    click.setVisibility(View.GONE);
                    account.setVisibility(View.GONE);

                }
                else{
                    bar.setVisibility(View.VISIBLE);
                    databaseReference1 = FirebaseDatabase.getInstance().getReference("Shop").child(uid);
                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                //uid = dataSnapshot.child("uid").getValue().toString();
                                firstname = dataSnapshot.child("firstname").getValue().toString();
                                lastname = dataSnapshot.child("lastname").getValue().toString();
                                contactnumber = dataSnapshot.child("contact").getValue().toString();
                                locations = dataSnapshot.child("location").getValue().toString();
                                shopName = dataSnapshot.child("name").getValue().toString();
                                emaill = dataSnapshot.child("email").getValue().toString();
                                description = dataSnapshot.child("description").getValue().toString();
                                purl = dataSnapshot.child("purl").getValue().toString();
                                rating = Float.parseFloat(dataSnapshot.child("rating").getValue().toString());
                                status = dataSnapshot.child("status").getValue().toString();
                                ratingBar.setRating(rating);
                                ratingBar.setRating(rating);
//                                Toast.makeText(getActivity(), purl, Toast.LENGTH_SHORT).show();
                                nametext.setText(firstname);
                                lnametext.setText(lastname);
                                contacttext.setText(contactnumber);
                                shopnametext.setText(shopName);
                                locationtext.setText(locations);
                                descriptiontext.setText(description);
                                Picasso.get().load(purl).fit().centerCrop().into(profImg);
                                bar.setVisibility(View.GONE);
                                if(status.equals("ACTIVATED")){
                                    account.setText("ACCOUNT IS " + status);
                                    account.setTextColor(Color.parseColor("#007f00"));
                                    click.setVisibility(View.GONE);
                                    addComp.setVisibility(View.VISIBLE);
                                    addCompany.setVisibility(View.VISIBLE);
                                }
                                else{
                                    account.setText("ACCOUNT IS " + status);
                                    account.setTextColor(Color.parseColor("#FF0000"));
                                    addComp.setVisibility(View.GONE);
                                    addCompany.setVisibility(View.GONE);
                                }
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

        if(id == R.id.addCompany){
            Intent intent = new Intent(getContext(), ViewAddFinanceComp.class);
            intent.putExtra("uid", mAuth.getCurrentUser().getUid());
            startActivity(intent);
        }

        if(id == R.id.addComp){
            Intent intent = new Intent(getContext(), AddFinanceComp.class);
            startActivity(intent);
        }

        if(id == R.id.click){
            //Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), ActivateAccount.class);
            intent.putExtra("uid", mAuth.getCurrentUser().getUid());
            startActivity(intent);
        }

        if(id == R.id.profImg){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, IMAGE_REQUEST_1);
        }

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
            profImg.setClickable(true);
            editProfile.setVisibility(View.GONE);
            saveProfile.setVisibility(View.VISIBLE);
            logoutBtn.setVisibility(View.GONE);
        }

        if(id == R.id.saveProfile){
            final String name = nametext.getText().toString();
            final String contact = contacttext.getText().toString();
            final String shopnameText = shopnametext.getText().toString();
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
                            buyerProf.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        buyerProf.child("contactnumber").setValue(contact);
                                        buyerProf.child("firstname").setValue(name);
                                        buyerProf.child("lastname").setValue(lnameText);
                                        buyerProf.child("purl").setValue(purl);
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
                                        profImg.setClickable(false);
                                        editProfile.setVisibility(View.VISIBLE);
                                        saveProfile.setVisibility(View.GONE);
                                        logoutBtn.setVisibility(View.VISIBLE);

                                    }

                                    else{
                                        shopProf = FirebaseDatabase.getInstance().getReference("Shop").child(uid);
                                        shopProf.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.exists()){
                                                    shopProf.child("contactnumber").setValue(contact);
                                                    shopProf.child("firstname").setValue(name);
                                                    shopProf.child("lastname").setValue(lnameText);
                                                    shopProf.child("description").setValue(descText);
                                                    shopProf.child("name").setValue(shopnameText);
                                                    shopProf.child("location").setValue(locationText);
                                                    shopProf.child("purl").setValue(purl);
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
                                                    profImg.setClickable(false);
                                                    profImg.setFocusable(false);
                                                    logoutBtn.setVisibility(View.VISIBLE);
//                                                    Intent intent = new Intent(getActivity(), ShopDashboard.class);
//                                                    startActivity(intent);


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
                    profImg.setClickable(false);
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



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_REQUEST_1 && resultCode == RESULT_OK){
            imageUri = data.getData();
            Picasso.get().load(imageUri).fit().centerCrop().into(profImg);

            final String path = System.currentTimeMillis() + "." + getFileExtension(imageUri);
            bar.setVisibility(View.VISIBLE);
            StorageReference storageReference = mStorageRef.child("Images").child(path);
            storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    mStorageRef.child("Images/"+path).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            purl = uri.toString();
                            bar.setVisibility(View.GONE);
                        }
                    });
                }
            });

        }
    }


    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
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
