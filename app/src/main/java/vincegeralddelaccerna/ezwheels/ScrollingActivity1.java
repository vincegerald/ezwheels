package vincegeralddelaccerna.ezwheels;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import android.widget.RatingBar;
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

public class ScrollingActivity1 extends AppCompatActivity implements View.OnClickListener {

    ImageView scrollImage;
    TextView shopName, vehicleName, priceView, priceCondition, date, transmissionView, mileageView, yearView,sellerName, sellerAddress, sellerContact, fuelType, seriesView, editionView, infoView,
            textView13, textView14;
    Button call, message, reserve,trade;
    FloatingActionButton fab;
    VideoView video;
    CardView cardSeller, cardTrade, cardReservation;
    RatingBar ratingbar;
    TextView reportUser;

    //imageview
    ImageView imageView1, imageView2, imageView3, imageView4;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mDatabaseRef1;
    private DatabaseReference mDatabaseRef2;
    private FirebaseDatabase mDatabase;


    private  String firstname, lastname, contact, description, location, name, uid;
    private String brand, model;
    private String listingid;
    private String image1, image2, image3, image4;
    private String price, year, color;
    private String videoUrl;
    private String transmission;
    private String infoData, pricecondition, mileage, fuel, dateData, seriesData, editionData ;
    private Uri uriVideo;
    private String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling1);
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
        textView13 = findViewById(R.id.textView13);
        textView14 = findViewById(R.id.textView14);
        reportUser = findViewById(R.id.reportUser);
        ratingbar = findViewById(R.id.ratingBar);
        reportUser.setOnClickListener(this);

        //card
        cardSeller = findViewById(R.id.cardSeller);
        cardReservation = findViewById(R.id.cardReservation);
        cardTrade = findViewById(R.id.cardTrade);


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
        mDatabaseRef2 = FirebaseDatabase.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance();


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        listingid = getIntent().getStringExtra("listId");


        getSupportActionBar().setTitle(getIntent().getStringExtra("brand") + " " + getIntent().getStringExtra("model"));


        //check if the listing is posted by current user
        String id = mAuth.getCurrentUser().getUid();
        if(id.equals(uid)){
            cardSeller.setVisibility(View.GONE);
            cardReservation.setVisibility(View.GONE);
            cardTrade.setVisibility(View.GONE);
            textView13.setVisibility(View.GONE);
            textView14.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
        }



        mDatabase.getReference("Car").child(listingid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    mDatabaseRef2 = FirebaseDatabase.getInstance().getReference("Car").child(listingid);
                    mDatabaseRef2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.exists()){
                                image1 = dataSnapshot.child("image").getValue().toString();
                                image2 = dataSnapshot.child("imagePath1").getValue().toString();
                                image3 = dataSnapshot.child("imagePath2").getValue().toString();
                                image4 = dataSnapshot.child("imagePath3").getValue().toString();
                                videoUrl = dataSnapshot.child("videoPath").getValue().toString();
                                uriVideo = Uri.parse(videoUrl);
                                brand = dataSnapshot.child("finalBrand").getValue().toString();
                                model = dataSnapshot.child("finalModel").getValue().toString();
                                year = dataSnapshot.child("finalYear").getValue().toString();
                                color = dataSnapshot.child("finalColor").getValue().toString();
                                transmission = dataSnapshot.child("finalTransmission").getValue().toString();
                                pricecondition = dataSnapshot.child("finalPcondition").getValue().toString();
                                mileage = dataSnapshot.child("finalMileage").getValue().toString();
                                price = dataSnapshot.child("finalPrice").getValue().toString();
                                uid = dataSnapshot.child("uid").getValue().toString();
                                fuel = dataSnapshot.child("fuel").getValue().toString();
                                dateData =dataSnapshot.child("date").getValue().toString();
                                seriesData = dataSnapshot.child("series").getValue().toString();
                                editionData = dataSnapshot.child("edition").getValue().toString();
                                infoData = dataSnapshot.child("info").getValue().toString();

                                type = "car";

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


                            mDatabaseRef = FirebaseDatabase.getInstance().getReference("Shop").child(uid);

                            mDatabaseRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                   if(dataSnapshot.exists()){
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

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(ScrollingActivity1.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(ScrollingActivity1.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    Toast.makeText(ScrollingActivity1.this, "false", Toast.LENGTH_SHORT).show();

                    mDatabaseRef2 = FirebaseDatabase.getInstance().getReference("Motor").child(listingid);

                    mDatabaseRef2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.exists()){
                                image1 = dataSnapshot.child("image").getValue().toString();
                                image2 = dataSnapshot.child("imagePath1").getValue().toString();
                                image3 = dataSnapshot.child("imagePath2").getValue().toString();
                                image4 = dataSnapshot.child("imagePath3").getValue().toString();
                                videoUrl = dataSnapshot.child("videoPath").getValue().toString();
                                uriVideo = Uri.parse(videoUrl);
                                brand = dataSnapshot.child("finalBrand").getValue().toString();
                                model = dataSnapshot.child("finalModel").getValue().toString();
                                year = dataSnapshot.child("finalYear").getValue().toString();
                                color = dataSnapshot.child("finalColor").getValue().toString();
                                transmission = dataSnapshot.child("finalTransmission").getValue().toString();
                                pricecondition = dataSnapshot.child("finalPcondition").getValue().toString();
                                mileage = dataSnapshot.child("finalMileage").getValue().toString();
                                price = dataSnapshot.child("finalPrice").getValue().toString();
                                uid = dataSnapshot.child("uid").getValue().toString();
                                fuel = dataSnapshot.child("fuel").getValue().toString();
                                dateData =dataSnapshot.child("date").getValue().toString();
                                seriesData = dataSnapshot.child("series").getValue().toString();
                                editionData = dataSnapshot.child("edition").getValue().toString();
                                infoData = dataSnapshot.child("info").getValue().toString();
                                type = "motor";

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

                            mDatabaseRef = FirebaseDatabase.getInstance().getReference("Shop").child(uid);

                            mDatabaseRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
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

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(ScrollingActivity1.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(ScrollingActivity1.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ScrollingActivity1.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



        //set the datas




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
            Intent intent = new Intent(ScrollingActivity1.this, SetReservationFragment.class);
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
            Intent intent = new Intent(ScrollingActivity1.this, SetTradeinFragment.class);
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


            final AlertDialog.Builder builder = new AlertDialog.Builder(ScrollingActivity1.this);
            builder.setMessage("Remove from Favorites?").setCancelable(false)
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialogInterface, int i) {
                            final DatabaseReference favDelete = FirebaseDatabase.getInstance().getReference("Favorites").child(getIntent().getStringExtra("fid"));
                            favDelete.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        favDelete.removeValue();
                                        dialogInterface.dismiss();
                                    }
                                    else{
                                        final DatabaseReference motorSold = FirebaseDatabase.getInstance().getReference("Motor").child(listingid);
                                        motorSold.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                motorSold.child("status").setValue("sold");
                                                dialogInterface.dismiss();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                Toast.makeText(ScrollingActivity1.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

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
            alertDialog.setTitle(brand + " " + model);
            alertDialog.show();

//

        }

        if(id == R.id.reportUser){
            final AlertDialog.Builder builder = new AlertDialog.Builder(ScrollingActivity1.this);

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
                                        Toast.makeText(ScrollingActivity1.this, "Report Addded. Thank you for making ezwheels improve", Toast.LENGTH_SHORT).show();
                                        dialogInterface.dismiss();
                                    }
                                    else{
                                        Toast.makeText(ScrollingActivity1.this, "Error Adding Report", Toast.LENGTH_SHORT).show();
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
    }




}
