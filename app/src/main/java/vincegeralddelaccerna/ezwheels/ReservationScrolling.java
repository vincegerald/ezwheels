package vincegeralddelaccerna.ezwheels;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ReservationScrolling extends AppCompatActivity implements View.OnClickListener {

    ImageView scrollImage;
    TextView shopName, vehicleName, priceView, priceCondition, date, transmissionView, mileageView, yearView,sellerName, sellerAddress, sellerContact, fuelType, seriesView, editionView, infoView,
            textView13, textView14, typeView, statusView;
    Button call, message, approve, decline;
    FloatingActionButton fab;
    VideoView video;
    CardView cardSeller, cardTrade, cardReservation;

    //imageview
    ImageView imageView1, imageView2, imageView3, imageView4, imageView14;

    //spinner
    Spinner action;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mDatabaseRef1, approveRef;


    private static String  listingId, resId, shopId, name, uid;
    private String brand, model;
    private String listingid;
    private String image1;
    private String price, year, color;
    private String imageCar;
    private Uri finalImageCar;
    private String tid;
    private String type, addPrice, shopAddPrice, userId, imagePath1, imagePath2;
    private static String offeredBrand;
    private static String offeredModel;
    private static String contactnumber, firstname, lastname, address, status;
    private DatabaseReference shopRef;
    private static String UserUId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#000000"));
        setSupportActionBar(toolbar);

        typeView = findViewById(R.id.textView10);
        scrollImage = findViewById(R.id.scrollImage);
        vehicleName = findViewById(R.id.textView22);
        priceView = findViewById(R.id.textView23);
        priceCondition = findViewById(R.id.textView24);
        date = findViewById(R.id.textView25);
        transmissionView = findViewById(R.id.textView26);
        mileageView = findViewById(R.id.textView27);
        yearView = findViewById(R.id.textView28);
        sellerName = findViewById(R.id.sellerName);
        fuelType = findViewById(R.id.textView29);
        sellerAddress = findViewById(R.id.sellerAddress);
        sellerContact = findViewById(R.id.sellerContact);
        editionView = findViewById(R.id.edition);
        seriesView = findViewById(R.id.series);
        infoView = findViewById(R.id.info);
        video = findViewById(R.id.video);
        textView13 = findViewById(R.id.textView13);
        textView14 = findViewById(R.id.textView14);
        imageView14 = findViewById(R.id.imageView14);
        statusView = findViewById(R.id.status);

        approveRef = FirebaseDatabase.getInstance().getReference();


        //spinner
        action = findViewById(R.id.action);



        //card
        cardSeller = findViewById(R.id.cardSeller);
        cardReservation = findViewById(R.id.cardReservation);
        cardTrade = findViewById(R.id.cardTrade);


        //buttons
        call = findViewById(R.id.call);
        message = findViewById(R.id.message);
        fab = findViewById(R.id.fab);

        //imageview
        imageView1 = findViewById(R.id.image1);
        imageView2 = findViewById(R.id.image2);
        imageView3 = findViewById(R.id.image3);
        imageView4 = findViewById(R.id.image4);
        approve = findViewById(R.id.approve);
        decline = findViewById(R.id.decline);

        //listeners

        call.setOnClickListener(this);
        message.setOnClickListener(this);
        fab.setOnClickListener(this);
        approve.setOnClickListener(this);
        decline.setOnClickListener(this);
        //firebase

        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mDatabaseRef1 = FirebaseDatabase.getInstance().getReference();


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        resId = getIntent().getStringExtra("resId");
        listingId = getIntent().getStringExtra("listingId");
        shopId = getIntent().getStringExtra("shopId");
        uid = getIntent().getStringExtra("uid");





        mDatabaseRef1 = FirebaseDatabase.getInstance().getReference("Buyers").child(uid);

        mDatabaseRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    mDatabaseRef1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            firstname = dataSnapshot.child("firstname").getValue().toString();
                            lastname = dataSnapshot.child("lastname").getValue().toString();
                            contactnumber = dataSnapshot.child("contact").getValue().toString();
                            status = dataSnapshot.child("status").getValue().toString();
                            Toast.makeText(ReservationScrolling.this, firstname, Toast.LENGTH_SHORT).show();
                            Log.d("number" ,contactnumber);
                            Log.d("fname" ,firstname);
                            Log.d("lname" ,lastname);
                            fab.setVisibility(View.VISIBLE);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(ReservationScrolling.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    });
                }

                else{
                    shopRef = FirebaseDatabase.getInstance().getReference("Shop").child(uid);
                    shopRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            firstname = dataSnapshot.child("firstname").getValue().toString();
                            lastname = dataSnapshot.child("lastname").getValue().toString();
                            address = dataSnapshot.child("location").getValue().toString();
                            contactnumber = dataSnapshot.child("contact").getValue().toString();
                            sellerName.setText(firstname + " " +lastname);
                            sellerContact.setText(contactnumber);
                            sellerAddress.setText(address);
                            Toast.makeText(ReservationScrolling.this, firstname, Toast.LENGTH_SHORT).show();
                            fab.setVisibility(View.VISIBLE);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(ReservationScrolling.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ReservationScrolling.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.message){

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_VIEW);
            sendIntent.setData(Uri.parse("smsto:" + contactnumber));  // This ensures only SMS apps respond
            sendIntent.putExtra("sms_body", "Hi im interested in your listing " + model +" " + brand + " in ezwheels!");
            if (sendIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(sendIntent);
            }

        }

        if(id == R.id.call){

            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + contactnumber));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

        }

        if(id == R.id.approve){
            approveRef = FirebaseDatabase.getInstance().getReference("Trade").child(tid);
            approveRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    approveRef.child("status").setValue("APPROVED");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        if(id == R.id.decline){
            approveRef = FirebaseDatabase.getInstance().getReference("Trade").child(tid);
            approveRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    approveRef.child("status").setValue("DECLINED");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        if(id == R.id.fab){
            DatabaseReference tradedelete = FirebaseDatabase.getInstance().getReference("Trade").child(tid);
            tradedelete.removeValue();
            Toast.makeText(ReservationScrolling.this, "Trade Deleted", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ReservationScrolling.this, ShopDashboard.class);
            startActivity(intent
            );
        }




    }


}
