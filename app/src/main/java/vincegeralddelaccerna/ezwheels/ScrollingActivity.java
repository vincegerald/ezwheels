package vincegeralddelaccerna.ezwheels;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ScrollingActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    ImageView scrollImage;
    TextView shopName, vehicleName, priceView, priceCondition, date, transmissionView, mileageView, yearView,sellerName, sellerAddress, sellerContact, fuelType, seriesView, editionView, infoView,
            textView13, textView14;
    Button call, message, reserve,trade, approve;
    FloatingActionButton fab;
    VideoView video;
    CardView cardSeller, cardTrade, cardReservation;
    RatingBar ratingbar;
    TextView reportUser;
    Float rating;
    TextView rateUser;
    Button apply;
    private static double lon, lat;
    MapView mapView;
    GoogleMap map;
    private static final String MAP_VIEW_BUNDLE_KEY = "AIzaSyCn5Caz2H3SqFIIrOSLMJCWYm7n21Oy3VI";
    //imageview
    ImageView imageView1, imageView2, imageView3, imageView4, edit;



    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mDatabaseRef1, checkifShop, checkRes;

    private  String firstname, lastname, contact, description, location, name, uid, status;
    private String brand, model;
    private String listingid;
    private String image1;
    private String price, year, color;
    private String shopuid;
    private Float newRating;


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if(mapViewBundle == null){
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#000000"));
        setSupportActionBar(toolbar);


        mapView = findViewById(R.id.map);
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
        approve = findViewById(R.id.approve);
        reportUser = findViewById(R.id.reportUser);
        ratingbar = findViewById(R.id.ratingBar);
        rateUser = findViewById(R.id.rateUser);
        apply = findViewById(R.id.apply);
        reportUser.setOnClickListener(this);
        apply.setOnClickListener(this);

        //card
        cardSeller = findViewById(R.id.cardSeller);
        cardReservation = findViewById(R.id.cardReservation);
        cardTrade = findViewById(R.id.cardTrade);

        Bundle mapViewBundle = null;

        if(savedInstanceState != null){
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);



        lat = getIntent().getDoubleExtra("lat", 0.2f);
        lon = getIntent().getDoubleExtra("lon", 0.2f);




        //buttons
        call = findViewById(R.id.call);
        message = findViewById(R.id.message);
        reserve = findViewById(R.id.reserveButton);
        trade = findViewById(R.id.tradeButton);
        fab = findViewById(R.id.fab);
        rateUser.setOnClickListener(this);

        //imageview
        imageView1 = findViewById(R.id.image1);
        imageView2 = findViewById(R.id.image2);
        imageView3 = findViewById(R.id.image3);
        imageView4 = findViewById(R.id.image4);
        edit = findViewById(R.id.edit);

        //listeners

        call.setOnClickListener(this);
        message.setOnClickListener(this);
        reserve.setOnClickListener(this);
        trade.setOnClickListener(this);
        fab.setOnClickListener(this);
        edit.setOnClickListener(this);
        approve.setOnClickListener(this);

        //firebase

        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mDatabaseRef1 = FirebaseDatabase.getInstance().getReference();
        checkifShop = FirebaseDatabase.getInstance().getReference("Shop");
        checkRes = FirebaseDatabase.getInstance().getReference("Reservation");


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        image1 = getIntent().getStringExtra("image_url1");
        String image2 = getIntent().getStringExtra("image_url2");
        String image3 = getIntent().getStringExtra("image_url3");
        String image4 = getIntent().getStringExtra("image_url4");
        String videoUrl = getIntent().getStringExtra("videoUrl");
        Uri uriVideo = Uri.parse(videoUrl);
        brand = getIntent().getStringExtra("brand");
        model = getIntent().getStringExtra("model");
        year = getIntent().getStringExtra("year");
        color = getIntent().getStringExtra("color");
        String transmission = getIntent().getStringExtra("transmission");
        String pricecondition = getIntent().getStringExtra("pricecondition");
        String mileage = getIntent().getStringExtra("mileage");
        price = getIntent().getStringExtra("price");
        uid = getIntent().getStringExtra("uid");
        String fuel = getIntent().getStringExtra("fuel");
        String dateData = getIntent().getStringExtra("date");
        String seriesData = getIntent().getStringExtra("series");
        String editionData = getIntent().getStringExtra("edition");
        String infoData = getIntent().getStringExtra("info");
        listingid = getIntent().getStringExtra("listingid");
        status = getIntent().getStringExtra("status");


        getSupportActionBar().setTitle(brand + " " + model);


        //check if the listing is posted by current user
        String id = mAuth.getCurrentUser().getUid();
        if(id.equals(uid)){
            cardSeller.setVisibility(View.GONE);
            cardReservation.setVisibility(View.GONE);
            cardTrade.setVisibility(View.GONE);
            textView13.setVisibility(View.GONE);
            textView14.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
            edit.setVisibility(View.VISIBLE);
            approve.setVisibility(View.VISIBLE);
            ratingbar.setVisibility(View.GONE);
            reportUser.setVisibility(View.GONE);
            apply.setVisibility(View.GONE   );
        }

        if(status.equals("SOLD")){
            approve.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);
        }
        //Toast.makeText(this, listingid, Toast.LENGTH_SHORT).show();
        checkifShop = FirebaseDatabase.getInstance().getReference("Reservation");

        checkifShop.orderByChild("listid").equalTo(listingid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    String status = dataSnapshot.child("status").getValue().toString();
                    if(status.equals("APPROVED")){
                        reserve.setText("Reservations for this vehicle is currently unavailable");
                        reserve.setBackgroundColor(Color.parseColor("#808080"));
                        reserve.setClickable(false);
                        reserve.setFocusable(false);
                    }

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
                Toast.makeText(ScrollingActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Shop").child(uid);

        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    firstname = dataSnapshot.child("firstname").getValue().toString();
                    lastname =  dataSnapshot.child("lastname").getValue().toString();
                    contact = dataSnapshot.child("contact").getValue().toString();
                    description  = dataSnapshot.child("description").getValue().toString();
                    location = dataSnapshot.child("location").getValue().toString();
                    name = dataSnapshot.child("name").getValue().toString();
//                    lon = Double.parseDouble(dataSnapshot.child("lon").getValue().toString());
//                    lat = Double.parseDouble(dataSnapshot.child("lat").getValue().toString());
                    sellerName.setText(firstname + " " + lastname);
                    sellerAddress.setText(location);
                    sellerContact.setText(contact);
                    rating = Float.parseFloat(dataSnapshot.child("rating").getValue().toString());
                    ratingbar.setVisibility(View.GONE);
                    reportUser.setVisibility(View.GONE);
                    rateUser.setVisibility(View.GONE);
                    shopuid = dataSnapshot.child("uid").getValue().toString();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ScrollingActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        checkifShop = FirebaseDatabase.getInstance().getReference("Shop").child(mAuth.getCurrentUser().getUid());
        checkifShop.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    ratingbar.setVisibility(View.GONE);
                    reportUser.setVisibility(View.GONE);
                    rateUser.setVisibility(View.GONE);
                }
                else{
                    ratingbar.setVisibility(View.VISIBLE);
                    reportUser.setVisibility(View.VISIBLE);
                    rateUser.setVisibility(View.VISIBLE);
                    ratingbar.setRating(rating);
                    ratingbar.setClickable(false);
                    ratingbar.setFocusable(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ScrollingActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //set the datas
        video.setVideoURI(uriVideo);
        video.start();
        Picasso.get().load(image1).fit().centerCrop().into(scrollImage);
        Picasso.get().load(image1).fit().centerCrop().into(imageView1);
        Picasso.get().load(image2).fit().centerCrop().into(imageView2);
        Picasso.get().load(image3).fit().centerCrop().into(imageView3);
        Picasso.get().load(image4).fit().centerCrop().into(imageView4);
        vehicleName.setText(brand + " " + model);
        priceView.setText(price);
        priceCondition.setText(pricecondition);
        date.setText(dateData);
        mileageView.setText(mileage);
        transmissionView.setText(transmission);
        yearView.setText(year);
        fuelType.setText(fuel);
        seriesView.setText(seriesData);
        editionView.setText(editionData);
        infoView.setText(infoData);



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(final View view) {
        int id = view.getId();

        if(id == R.id.apply){
            Intent reqIntent = new Intent(this, AddLoanReq.class);
            reqIntent.putExtra("shopUid", uid);
            reqIntent.putExtra("listid", listingid);
            startActivity(reqIntent);
        }

        if(id == R.id.message){

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_VIEW);
            sendIntent.setData(Uri.parse("smsto:" + contact));  // This ensures only SMS apps respond
            sendIntent.putExtra("sms_body", "Hi im interested in your listing " + model +" " + brand + " in ezwheels!");
            if (sendIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(sendIntent);
            }

        }

        if(id == R.id.call){

            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + contact));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

        }

        if(id == R.id.reserveButton){
            Intent intent = new Intent(ScrollingActivity.this, SetReservationFragment.class);
            intent.putExtra("shopuid", uid);
            intent.putExtra("listingid", listingid);
            intent.putExtra("image1", image1);
            intent.putExtra("name", name);
            intent.putExtra("model", model);
            intent.putExtra("brand", brand);
            intent.putExtra("price", price);
            intent.putExtra("year", year);
            startActivity(intent);
        }

        if(id == R.id.tradeButton){
            Intent intent = new Intent(ScrollingActivity.this, SetTradeinFragment.class);
            intent.putExtra("shopuid", uid);
            intent.putExtra("listingid", listingid);
            intent.putExtra("image1", image1);
            intent.putExtra("name", name);
            intent.putExtra("model", model);
            intent.putExtra("brand", brand);
            intent.putExtra("price", price);
            intent.putExtra("year", year);
            startActivity(intent);
        }

        if(id == R.id.fab){

            String currentUid = mAuth.getCurrentUser().getUid();
            String fid = mDatabaseRef1.push().getKey();
            Favorites favorites = new Favorites(fid, image1, brand, model, price, color, year, currentUid, uid, listingid, name);
            mDatabaseRef1.child("Favorites").child(fid).setValue(favorites).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Snackbar.make(view, "Vehicle Added to favorites", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else{
                        Toast.makeText(ScrollingActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }

        if(id == R.id.edit){
            Intent intent = new Intent(ScrollingActivity.this, EditListing.class);
            intent.putExtra("listingid", listingid);
            startActivity(intent);
        }

        if(id == R.id.reportUser){
            final AlertDialog.Builder builder = new AlertDialog.Builder(ScrollingActivity.this);

            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            input.setLines(5);
            input.setPadding(50,50,10,10);
            builder.setView(input);
            builder.setMessage("Report User")
                    .setCancelable(false)
                    .setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialogInterface, int i) {
                            final String reportText = input.getText().toString();

                            final DatabaseReference reportUser = FirebaseDatabase.getInstance().getReference("Reports");
                            String id = reportUser.push().getKey();
                            Report report = new Report(uid, reportText, mAuth.getCurrentUser().getUid());
                            reportUser.child(id).setValue(report).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Snackbar.make(view, "User Reported. The admin will review your report", Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                        dialogInterface.dismiss();
                                    }
                                    else{
                                        Toast.makeText(ScrollingActivity.this, "Error Adding Report", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            dialogInterface.dismiss();

                        }
                    }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.setTitle(name);
            alertDialog.show();
        }

        if(id == R.id.rateUser){
            final RatingBar ratingBar = new RatingBar(this);
            final AlertDialog.Builder builder = new AlertDialog.Builder(ScrollingActivity.this);
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            ratingBar.setLayoutParams(lp);
            ratingBar.setNumStars(5);
            ratingBar.setStepSize((float) .5);
            ratingBar.setRating(rating);
            linearLayout.addView(ratingBar);
            builder.setView(linearLayout);

            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    newRating = v;
                }
            });
            builder.setMessage("Rate Shop")
                    .setCancelable(false)
                    .setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialogInterface, int i) {

                            final DatabaseReference rateShop = FirebaseDatabase.getInstance().getReference("Shop").child(shopuid);

                            rateShop.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        float finalRating = (rating + newRating) / 2;
                                        rateShop.child("rating").setValue(finalRating);
                                        Snackbar.make(view, "Rating added. Enjoy browsing", Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                        dialogInterface.dismiss();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(ScrollingActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });



                            dialogInterface.dismiss();

                        }
                    }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.setTitle(name);
            alertDialog.show();



        }

        if(id == R.id.approve){
            final AlertDialog.Builder builder = new AlertDialog.Builder(ScrollingActivity.this);
            builder.setMessage("Mark as sold?").setCancelable(false)
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialogInterface, int i) {
                            final DatabaseReference carSold = FirebaseDatabase.getInstance().getReference("Car").child(listingid);
                            carSold.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        carSold.child("status").setValue("SOLD");
                                        Snackbar.make(view, "Car mark as sold", Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                        dialogInterface.dismiss();
                                    }
                                    else{
                                        final DatabaseReference motorSold = FirebaseDatabase.getInstance().getReference("Motor").child(listingid);
                                        motorSold.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                motorSold.child("status").setValue("sold");
                                                Snackbar.make(view, "Motorcycle mark as sold", Snackbar.LENGTH_LONG)
                                                        .setAction("Action", null).show();
                                                dialogInterface.dismiss();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                Toast.makeText(ScrollingActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(ScrollingActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                            dialogInterface.dismiss();

                        }
                    }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.setTitle("Mark " + brand + " " + model + " as sold?");
            alertDialog.show();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
//        map = googleMap;
//

//        LatLng ny = new LatLng(40.7143528, -74.0059731);
//        map.moveCamera(CameraUpdateFactory.newLatLng(ny));
        map = googleMap;
        map.setMinZoomPreference(12);
        double finalLat = lat;
        double finatLong = lon;
        Toast.makeText(ScrollingActivity.this, String.valueOf(lat), Toast.LENGTH_SHORT).show();
        LatLng ny = new LatLng(lat, lon);

        map.addMarker(new MarkerOptions().position(ny).title("Vehicle Shop"));



        UiSettings uiSettings = map.getUiSettings();
        uiSettings.setIndoorLevelPickerEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setZoomControlsEnabled(true);

        CameraPosition.Builder camBuilder = CameraPosition.builder();
        camBuilder.bearing(45);
        camBuilder.tilt(30);
        camBuilder.target(ny);
        camBuilder.zoom(15);

        CameraPosition cp = camBuilder.build();

        map.moveCamera(CameraUpdateFactory.newCameraPosition(cp));
//        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                if(ScrollingActivity.this.cameraPositionUpdate){
//                    ScrollingActivity.this.cameraPositionUpdate = false;
//                    map.moveCamera(CameraUpdateFactory.zoomTo(18));
//                }else{
//                    ScrollingActivity.this.cameraPositionUpdate = true;
//                    map.moveCamera(CameraUpdateFactory.zoomTo(15));
//                }
//            }
//        });
//
    }
}
