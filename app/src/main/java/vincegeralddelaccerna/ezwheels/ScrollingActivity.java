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
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class ScrollingActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView scrollImage;
    TextView shopName, vehicleName, priceView, priceCondition, date, transmissionView, mileageView, yearView,sellerName, sellerAddress, sellerContact, fuelType, seriesView, editionView, infoView;
    Button call, message, reserve,trade;
    FloatingActionButton fab;
    VideoView video;

    //imageview
    ImageView imageView1, imageView2, imageView3, imageView4;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mDatabaseRef1;

    private  String firstname, lastname, contact, description, location, name, uid;
    private String brand, model;
    private String listingid;
    private String image1;
    private String price, year, color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#000000"));
        setSupportActionBar(toolbar);

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

        //buttons
        call = findViewById(R.id.call);
        message = findViewById(R.id.message);
        reserve = findViewById(R.id.reserveButton);
        trade = findViewById(R.id.tradeButton);
        fab = findViewById(R.id.fab);

        //imageview
        imageView1 = findViewById(R.id.image1);
        imageView2 = findViewById(R.id.image2);
        imageView3 = findViewById(R.id.image3);
        imageView4 = findViewById(R.id.image4);

        //listeners

        call.setOnClickListener(this);
        message.setOnClickListener(this);
        reserve.setOnClickListener(this);
        trade.setOnClickListener(this);
        fab.setOnClickListener(this);

        //firebase

        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mDatabaseRef1 = FirebaseDatabase.getInstance().getReference();


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
        getSupportActionBar().setTitle(brand + " " + model);




        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Shop").child(uid);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 firstname = dataSnapshot.child("firstname").getValue().toString();
                 lastname =  dataSnapshot.child("lastname").getValue().toString();
                 contact = dataSnapshot.child("contact").getValue().toString();
                 description  = dataSnapshot.child("description").getValue().toString();
                 location = dataSnapshot.child("location").getValue().toString();
                 name = dataSnapshot.child("name").getValue().toString();
                 sellerName.setText(firstname + " " + lastname);
                 sellerAddress.setText(location);
                 sellerContact.setText(contact);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ScrollingActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //set the datas

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
        video.setVideoURI(uriVideo);
        video.start();


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
            startActivity(intent);
        }

        if(id == R.id.fab){

            String currentUid = mAuth.getCurrentUser().getUid();

            Favorites favorites = new Favorites(image1, brand, model, price, color, year, currentUid, uid, listingid, name);
            mDatabaseRef1.child("Favorites").push().setValue(favorites).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(ScrollingActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(ScrollingActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
    }


}
