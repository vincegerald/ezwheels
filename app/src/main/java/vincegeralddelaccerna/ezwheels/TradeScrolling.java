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

public class TradeScrolling extends AppCompatActivity implements View.OnClickListener {

    ImageView scrollImage;
    TextView shopName, vehicleName, priceView, priceCondition, date, transmissionView, mileageView, yearView,sellerName, sellerAddress, sellerContact, fuelType, seriesView, editionView, infoView,
            textView13, textView14, typeView, statusView;
    Button call, message, approve, decline;
    FloatingActionButton fab;
    VideoView video;
    CardView cardSeller, cardTrade, cardReservation;

    //imageview
    ImageView imageView1, imageView2, imageView3, imageView4, imageView14, edit;

    //spinner
    Spinner action;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mDatabaseRef1, approveRef;


    private static String  contact, description, location, name, uid;
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
    private static String contactnumber, firstname, lastname, address, status, shopUid, fyear, yearr, oYear;
    private DatabaseReference shopRef;
    private static String UserUId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_scrolling);
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
        edit = findViewById(R.id.edit);
        statusView = findViewById(R.id.status);

        approveRef = FirebaseDatabase.getInstance().getReference();


        //spinner
        action = findViewById(R.id.action);

        //adapter
//        ArrayAdapter<String> actionAdapter = new ArrayAdapter<String>(TradeScrolling.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.action));
//        actionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        action.setAdapter(actionAdapter);
//        action.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if(adapterView.getItemAtPosition(i).equals("SELECT ACTION")){
//
//                }
//                else {
//                    String item = adapterView.getItemAtPosition(i).toString();
//
//                    switch (item){
//                        case "APPROVE":
//                            Toast.makeText(TradeScrolling.this, "1", Toast.LENGTH_SHORT).show();
//                            approveRef = FirebaseDatabase.getInstance().getReference("Trade").child(tid);
//                            approveRef.addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                    approveRef.child("status").setValue("APPROVED");
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                }
//                            });
//                            break;
//                        case "NEGOTIATE":
//                            approveRef = FirebaseDatabase.getInstance().getReference("Trade").child(tid);
//                            approveRef.addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                    approveRef.child("status").setValue("NEGOTIATE");
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                }
//                            });
//                            Toast.makeText(TradeScrolling.this, "2", Toast.LENGTH_SHORT).show();
//                            break;
//                    }
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });


        //card
//        cardSeller = findViewById(R.id.cardSeller);
//        cardReservation = findViewById(R.id.cardReservation);
//        cardTrade = findViewById(R.id.cardTrade);


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
        edit.setOnClickListener(this);
        //firebase

        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mDatabaseRef1 = FirebaseDatabase.getInstance().getReference();


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        brand = getIntent().getStringExtra("brand");
        model = getIntent().getStringExtra("model");
        imageCar = getIntent().getStringExtra("image1");
        price = getIntent().getStringExtra("price");
        tid = getIntent().getStringExtra("tid");
        userId = getIntent().getStringExtra("uid");
        oYear = getIntent().getStringExtra("year");
        finalImageCar = Uri.parse(imageCar);


        getSupportActionBar().setTitle(brand + " " + model);


        //check if the listing is posted by current user


        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Trade").child(tid);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                type = dataSnapshot.child("type").getValue().toString();
                addPrice =  dataSnapshot.child("addPrice").getValue().toString();
                shopAddPrice = dataSnapshot.child("shopAddPrice").getValue().toString();
                offeredBrand = dataSnapshot.child("fbrand").getValue().toString();
                offeredModel = dataSnapshot.child("fmodel").getValue().toString();
                UserUId = dataSnapshot.child("uid").getValue().toString();
                shopUid = dataSnapshot.child("shopuid").getValue().toString();
                fyear = dataSnapshot.child("fyear").getValue().toString();
                yearr = dataSnapshot.child("year").getValue().toString();
                imagePath1  = dataSnapshot.child("imagePath1").getValue().toString();
                imagePath2 = dataSnapshot.child("imagePath2").getValue().toString();
                status = dataSnapshot.child("status").getValue().toString();

                String id = mAuth.getCurrentUser().getUid();

                if(id.equals(UserUId)){
                    approve.setVisibility(View.GONE);
                    decline.setVisibility(View.GONE);
                    fab.setVisibility(View.VISIBLE);

                    mDatabaseRef1 = FirebaseDatabase.getInstance().getReference("Buyers").child(shopUid);

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
                                        Toast.makeText(TradeScrolling.this, firstname, Toast.LENGTH_SHORT).show();
                                        Log.d("number" ,contactnumber);
                                        Log.d("fname" ,firstname);
                                        Log.d("lname" ,lastname);
                                        sellerAddress.setVisibility(View.GONE);
                                        sellerName.setText(firstname + " " +lastname);
                                        sellerContact.setText(contactnumber);




                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(TradeScrolling.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                    }


                                });
                            }

                            else{
                                shopRef = FirebaseDatabase.getInstance().getReference("Shop").child(shopUid);
                                shopRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        firstname = dataSnapshot.child("firstname").getValue().toString();
                                        lastname = dataSnapshot.child("lastname").getValue().toString();
                                        address = dataSnapshot.child("location").getValue().toString();
                                        contactnumber = dataSnapshot.child("contact").getValue().toString();
                                        textView13.setText("Seller Details");
                                         sellerName.setText(firstname + " " +lastname);
                                         sellerContact.setText(contactnumber);
                                         sellerAddress.setText(address);
                                        if(status.equals("DECLINED") || status.equals("APPROVED")){
                                            decline.setVisibility(View.GONE);
                                            approve.setVisibility(View.GONE);
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(TradeScrolling.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            if(type.equals("SWAP")){
                                typeView.setTextColor(Color.parseColor("#FFA500"));
                                typeView.setText(type);
                                fuelType.setVisibility(View.GONE);
                                imageView14.setVisibility(View.GONE);

                            }
                            else if(type.equals("SHOP WILL ADD")){
                                typeView.setTextColor(Color.parseColor("#004c00"));
                                typeView.setText(type  + " (" + shopAddPrice +")");
                                fuelType.setTextColor(Color.parseColor("#004c00"));
                                fuelType.setVisibility(View.VISIBLE);
                                fuelType.setText(type + " (" + shopAddPrice +")");
                            }
                            else{
                                typeView.setTextColor(Color.parseColor("#FF0000"));
                                typeView.setText(type + " (" + addPrice +")");
                                fuelType.setTextColor(Color.parseColor("#FF0000"));
                                fuelType.setVisibility(View.VISIBLE);
                                fuelType.setText(type + " (" + addPrice +")");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(TradeScrolling.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }


                if(shopUid.equals(id)){
                    fab.setVisibility(View.GONE);
                    {
                        mDatabaseRef1 = FirebaseDatabase.getInstance().getReference("Buyers").child(shopUid);

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
                                            Toast.makeText(TradeScrolling.this, firstname, Toast.LENGTH_SHORT).show();
                                            Log.d("number" ,contactnumber);
                                            Log.d("fname" ,firstname);
                                            Log.d("lname" ,lastname);
                                            sellerAddress.setVisibility(View.GONE);
                                            textView13.setText("Buyer Details");
                                            sellerName.setText(firstname + " " +lastname);
                                            sellerContact.setText(contactnumber);
                                            if(status.equals("DECLINED") || status.equals("APPROVED")){
                                                decline.setVisibility(View.GONE);
                                                approve.setVisibility(View.GONE);
                                            }



                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            Toast.makeText(TradeScrolling.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                        }


                                    });
                                }

                                else{
                                    shopRef = FirebaseDatabase.getInstance().getReference("Shop").child(shopUid);
                                    shopRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            firstname = dataSnapshot.child("firstname").getValue().toString();
                                            lastname = dataSnapshot.child("lastname").getValue().toString();
                                            address = dataSnapshot.child("location").getValue().toString();
                                            contactnumber = dataSnapshot.child("contact").getValue().toString();
                                            textView13.setText("Buyer Details");
                                            sellerName.setText(firstname + " " +lastname);
                                            sellerContact.setText(contactnumber);
                                            sellerAddress.setText(address);

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            Toast.makeText(TradeScrolling.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    if(type.equals("SWAP")){
                                        typeView.setTextColor(Color.parseColor("#FFA500"));
                                        typeView.setText(type);
                                        fuelType.setVisibility(View.GONE);
                                        imageView14.setVisibility(View.GONE);

                                    }
                                    else if(type.equals("SHOP WILL ADD")){
                                        typeView.setTextColor(Color.parseColor("#FF0000"));
                                        typeView.setText("YOU WILL ADD"  + " (" + shopAddPrice +")");
                                        fuelType.setTextColor(Color.parseColor("#FF0000"));
                                        fuelType.setVisibility(View.VISIBLE);
                                        fuelType.setText("YOU WILL ADD" + " (" + shopAddPrice +")");
                                    }
                                    else{
                                        typeView.setTextColor(Color.parseColor("#004c00"));
                                        typeView.setText("BUYER WILL ADD" + " (" + addPrice +")");
                                        fuelType.setTextColor(Color.parseColor("#004c00"));
                                        fuelType.setVisibility(View.VISIBLE);
                                        fuelType.setText("BUYER WILL ADD" + " (" + addPrice +")");
                                    }

                                    edit.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(TradeScrolling.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    if(type.equals("SWAP")){
                        typeView.setTextColor(Color.parseColor("#FFA500"));
                        typeView.setText(type);
                        fuelType.setVisibility(View.GONE);
                        imageView14.setVisibility(View.GONE);

                    }
                    else if(type.equals("SHOP WILL ADD")){
                        typeView.setTextColor(Color.parseColor("#FF0000"));
                        typeView.setText(type);
                    }
                    else{
                        typeView.setTextColor(Color.parseColor("#004c00"));
                        typeView.setText(type);
                    }

                }




                if(status.equals("PENDING")){
                    statusView.setTextColor(Color.parseColor("#FFA500"));
                    statusView.setText("("+status+")");
                }

                else if(status.equals("APPROVED")){
                    decline.setVisibility(View.GONE);
                    approve.setVisibility(View.GONE);
                    statusView.setTextColor(Color.parseColor("#004c00"));
                    statusView.setText("("+status+")");
                }
                else{
                    statusView.setTextColor(Color.parseColor("#FF0000"));
                    decline.setVisibility(View.GONE);
                    approve.setVisibility(View.GONE);
                    statusView.setText("("+status+")");
                }

                transmissionView.setText(offeredBrand + " " +offeredModel + "(" + fyear + ")");

                if(addPrice.equals("") || shopAddPrice.equals("")){
                    fuelType.setVisibility(View.GONE);
                    imageView14.setVisibility(View.GONE);

                }
                else if(!addPrice.equals("")){
                    fuelType.setTextColor(Color.parseColor("#004c00"));
                    fuelType.setText(addPrice);
                }
                else if(!shopAddPrice.equals("")){
                    fuelType.setTextColor(Color.parseColor("#FFA500"));
                    fuelType.setText(shopAddPrice);
                }

                Picasso.get().load(imagePath1).fit().centerCrop().into(imageView2);
                Picasso.get().load(imagePath2).fit().centerCrop().into(imageView3);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(TradeScrolling.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });





        Picasso.get().load(imageCar).fit().centerCrop().into(scrollImage);
        priceView.setText(price);
        vehicleName.setText(brand + " " + model + "(" + oYear + ")");
      //  Toast.makeText(this, firstname, Toast.LENGTH_SHORT).show();
//        typeView.setText("bogo");



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
            Toast.makeText(TradeScrolling.this, "Trade Deleted", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(TradeScrolling.this, ShopDashboard.class);
//            startActivity(intent);
        }

        if(id == R.id.edit){
            Intent editIntent = new Intent(TradeScrolling.this, EditTrade.class);
            editIntent.putExtra("tid", tid);
        }




    }


}
