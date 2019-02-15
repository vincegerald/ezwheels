package vincegeralddelaccerna.ezwheels;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PaymentScrolling extends AppCompatActivity implements OnMapReadyCallback {


    TextView payment, price, date, codee, senderName, sender, senderContact, senderShop, senderLocation;
    private static String pid, uid, shopuid;
    DatabaseReference getPay, getShop, getBuyer;
    private static String amount, type, code, datee,shop, id;
    private static String firstname, contact, shopname, lastname, contactnumber, location, image;
    FirebaseAuth mAuth;
    ImageView codeImage;
    private static double lon, lat;
    MapView mapView;
    GoogleMap map;
    private static final String MAP_VIEW_BUNDLE_KEY = "AIzaSyCn5Caz2H3SqFIIrOSLMJCWYm7n21Oy3VI";



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

        setContentView(R.layout.activity_payment_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mapView = findViewById(R.id.map);
        payment = findViewById(R.id.payment);
        price = findViewById(R.id.price);
        date = findViewById(R.id.date);
        codee = findViewById(R.id.code);
        sender = findViewById(R.id.sender);
        senderName = findViewById(R.id.senderName);
        senderContact = findViewById(R.id.senderContact);
        senderShop = findViewById(R.id.senderShop);
        senderLocation = findViewById(R.id.senderLocation);
        codeImage = findViewById(R.id.codeImage);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        pid = getIntent().getStringExtra("pid");
        uid = getIntent().getStringExtra("uid");
        shopuid = getIntent().getStringExtra("shopuid");

        getPay = FirebaseDatabase.getInstance().getReference("Payments");
        getShop = FirebaseDatabase.getInstance().getReference("Shop");
        getBuyer = FirebaseDatabase.getInstance().getReference("Buyer");


        mAuth = FirebaseAuth.getInstance();

        getSupportActionBar().setTitle("Payments");


        id = mAuth.getCurrentUser().getUid();

        Bundle mapViewBundle = null;

        if(savedInstanceState != null){
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);



        lat = getIntent().getDoubleExtra("lat", 0.2f);
        lon = getIntent().getDoubleExtra("lon", 0.2f);



        getPay.orderByChild("id").equalTo(pid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    amount  =  dataSnapshot.child("amount").getValue().toString();
                    type = dataSnapshot.child("type").getValue().toString();
                    code = dataSnapshot.child("code").getValue().toString();
                    datee = dataSnapshot.child("date").getValue().toString();
                    shop = dataSnapshot.child("shopuid").getValue().toString();
                    id = dataSnapshot.child("uid").getValue().toString();
                    image = dataSnapshot.child("image").getValue().toString();

                    codee.setText("Code: " + code);
                    payment.setText(type);
                    date.setText("Date: " + datee);
                    price.setText(amount);
                    Picasso.get().load(image).fit().centerCrop().into(codeImage);
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
                Toast.makeText(PaymentScrolling.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        if(id.equals(uid)){
            getShop.child(shopuid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        firstname = dataSnapshot.child("firstname").getValue().toString();
                        lastname = dataSnapshot.child("lastname").getValue().toString();
                        contactnumber = dataSnapshot.child("contact").getValue().toString();
                        shopname = dataSnapshot.child("name").getValue().toString();
                        location = dataSnapshot.child("location").getValue().toString();

                        sender.setText("Sendee Details");
                        senderName.setText("Name: " + firstname + " " + lastname);
                        senderContact.setText("Contact number: " + contactnumber);
                        senderLocation.setText("Location: " + location);
                        senderShop.setText("Shopname: " + shopname);
                    }
                    else{
                        getBuyer.child(shopuid).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(PaymentScrolling.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(PaymentScrolling.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        if(id.equals(shopuid)){
            getBuyer.child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){

                        firstname = dataSnapshot.child("firstname").getValue().toString();
                        lastname = dataSnapshot.child("lastname").getValue().toString();
                        contactnumber = dataSnapshot.child("contactnumber").getValue().toString();
                        senderName.setText("Name: " +firstname + " " + lastname);
                        senderContact.setText("Contact Number: " + contactnumber);
                        senderLocation.setVisibility(View.GONE);
                        senderShop.setVisibility(View.GONE);
                    }
                    else{
                        getShop.child(uid).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){

                                    firstname = dataSnapshot.child("firstname").getValue().toString();
                                    lastname = dataSnapshot.child("lastname").getValue().toString();
                                    contactnumber = dataSnapshot.child("contact").getValue().toString();
                                    shopname = dataSnapshot.child("name").getValue().toString();
                                    location = dataSnapshot.child("location").getValue().toString();

                                    senderName.setText("Name: " + firstname + " " + lastname);
                                    senderContact.setText("Contact number: " + contactnumber);
                                    senderLocation.setText("Location: " + location);
                                    senderShop.setText("Shopname: " + shopname);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(PaymentScrolling.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(PaymentScrolling.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
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
        //Toast.makeText(PaymentScrolling.this, String.valueOf(lat), Toast.LENGTH_SHORT).show();
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
