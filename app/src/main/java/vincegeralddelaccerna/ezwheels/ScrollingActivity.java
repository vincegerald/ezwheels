package vincegeralddelaccerna.ezwheels;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ScrollingActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView scrollImage;
    TextView shopName, vehicleName, priceView, priceCondition, date, transmissionView, mileageView, yearView,sellerName, sellerAddress, sellerContact;
    Button call, message;
    VideoView video;

    DatabaseReference databaseReference;

    private  String firstname, lastname, contact, description, location, name;
    private String brand, model;

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
        transmissionView = findViewById(R.id.textView29);
        mileageView = findViewById(R.id.textView27);
        yearView = findViewById(R.id.textView28);
        sellerName = findViewById(R.id.sellerName);
        sellerAddress = findViewById(R.id.sellerAddress);
        sellerContact = findViewById(R.id.sellerContact);
        video = findViewById(R.id.video);

        //buttons
        call = findViewById(R.id.call);
        message = findViewById(R.id.message);

        //listeners

        call.setOnClickListener(this);
        message.setOnClickListener(this);




        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String image1 = getIntent().getStringExtra("image_url1");
        String image2 = getIntent().getStringExtra("image_url2");
        String image3 = getIntent().getStringExtra("image_url3");
        String image4 = getIntent().getStringExtra("image_url4");
        String videoUrl = getIntent().getStringExtra("videoUrl");
        Uri uriVideo = Uri.parse(videoUrl);
        brand = getIntent().getStringExtra("brand");
        model = getIntent().getStringExtra("model");
        String year = getIntent().getStringExtra("year");
        String color = getIntent().getStringExtra("color");
        String transmission = getIntent().getStringExtra("transmission");
        String pricecondition = getIntent().getStringExtra("pricecondition");
        String mileage = getIntent().getStringExtra("mileage");
        String price = getIntent().getStringExtra("price");
        String uid = getIntent().getStringExtra("uid");
        getSupportActionBar().setTitle(brand + " " + model);




        databaseReference = FirebaseDatabase.getInstance().getReference("Shop").child(uid);

        databaseReference.addValueEventListener(new ValueEventListener() {
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
        vehicleName.setText(brand + " " + model);
        priceView.setText(price);
        priceCondition.setText(pricecondition);
        date.setText("Wala pay data");
        mileageView.setText(mileage);
        transmissionView.setText(transmission);
        yearView.setText(year);
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
    }
}
