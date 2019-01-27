package vincegeralddelaccerna.ezwheels;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class TrademyVehicle extends Fragment {

    ImageView scrollImage;
    TextView shopName, vehicleName, priceView, priceCondition, date, transmissionView, mileageView, yearView,sellerName, sellerAddress, sellerContact, fuelType, seriesView, editionView, infoView,
            textView13, textView14;
    Button call, message, reserve,trade;
    FloatingActionButton fab;
    VideoView video;
    CardView cardSeller, cardTrade, cardReservation;

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

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter adapter;
    Toolbar toolbar;


    public TrademyVehicle() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  v =  inflater.inflate(R.layout.fragment_trademy_vehicle, container, false);

        Bundle b = getArguments();
        String id = b.getString("listingid");
        Toast.makeText(getActivity(), b.getString("listingid"), Toast.LENGTH_SHORT).show();


        scrollImage = v.findViewById(R.id.scrollImage);
        vehicleName = v.findViewById(R.id.textView22);
        priceView = v.findViewById(R.id.textView23);
        priceCondition = v.findViewById(R.id.textView24);
        date = v.findViewById(R.id.textView25);
        transmissionView = v.findViewById(R.id.textView26);
        mileageView = v.findViewById(R.id.textView27);
        yearView = v.findViewById(R.id.textView28);
        sellerName = v.findViewById(R.id.sellerName);
        fuelType = v.findViewById(R.id.textView29);
        sellerAddress = v.findViewById(R.id.sellerAddress);
        sellerContact = v.findViewById(R.id.sellerContact);
        editionView = v.findViewById(R.id.edition);
        seriesView = v.findViewById(R.id.series);
        infoView = v.findViewById(R.id.info);
        video = v.findViewById(R.id.video);
        textView13 = v.findViewById(R.id.textView13);
        textView14 = v.findViewById(R.id.textView14);
        Toast.makeText(getActivity(), listingid, Toast.LENGTH_SHORT).show();
        //card
        cardSeller = v.findViewById(R.id.cardSeller);
        cardReservation = v.findViewById(R.id.cardReservation);
        cardTrade = v.findViewById(R.id.cardTrade);


        //buttons
        call = v.findViewById(R.id.call);
        message = v.findViewById(R.id.message);
        reserve = v.findViewById(R.id.reserveButton);
        trade = v.findViewById(R.id.tradeButton);
        fab = v.findViewById(R.id.fab);

        //imageview
        imageView1 = v.findViewById(R.id.image1);
        imageView2 = v.findViewById(R.id.image2);
        imageView3 = v.findViewById(R.id.image3);
        imageView4 = v.findViewById(R.id.image4);
        //listeners

//        call.setOnClickListener(this);
//        message.setOnClickListener(this);
//        reserve.setOnClickListener(this);
//        trade.setOnClickListener(this);
//        fab.setOnClickListener(this);

//        mDatabase.getReference("Car").child(listingid).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                if(dataSnapshot.exists()){
//
//                    mDatabaseRef2 = FirebaseDatabase.getInstance().getReference("Car").child(listingid);
//                    mDatabaseRef2.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                            image1 = dataSnapshot.child("image").getValue().toString();
//                            image2 = dataSnapshot.child("imagePath1").getValue().toString();
//                            image3 = dataSnapshot.child("imagePath2").getValue().toString();
//                            image4 = dataSnapshot.child("imagePath3").getValue().toString();
//                            videoUrl = dataSnapshot.child("videoPath").getValue().toString();
//                            uriVideo = Uri.parse(videoUrl);
//                            brand = dataSnapshot.child("finalBrand").getValue().toString();
//                            model = dataSnapshot.child("finalModel").getValue().toString();
//                            year = dataSnapshot.child("finalYear").getValue().toString();
//                            color = dataSnapshot.child("finalColor").getValue().toString();
//                            transmission = dataSnapshot.child("finalTransmission").getValue().toString();
//                            pricecondition = dataSnapshot.child("finalPcondition").getValue().toString();
//                            mileage = dataSnapshot.child("finalMileage").getValue().toString();
//                            price = dataSnapshot.child("finalPrice").getValue().toString();
//                            uid = dataSnapshot.child("uid").getValue().toString();
//                            fuel = dataSnapshot.child("fuel").getValue().toString();
//                            dateData =dataSnapshot.child("date").getValue().toString();
//                            seriesData = dataSnapshot.child("series").getValue().toString();
//                            editionData = dataSnapshot.child("edition").getValue().toString();
//                            infoData = dataSnapshot.child("info").getValue().toString();
//
//                            type = "car";
//
////                            video.setVideoURI(uriVideo);
////                            video.start();
//                            Picasso.get().load(image1).fit().centerCrop().into(scrollImage);
////                            Picasso.get().load(image1).fit().centerCrop().into(imageView1);
////                            Picasso.get().load(image2).fit().centerCrop().into(imageView2);
////                            Picasso.get().load(image3).fit().centerCrop().into(imageView3);
////                            Picasso.get().load(image4).fit().centerCrop().into(imageView4);
////                            vehicleName.setText(brand + " " + model);
////                            priceView.setText(price);
////                            priceCondition.setText(pricecondition);
////                            date.setText(dateData);
////                            mileageView.setText(mileage);
////                            transmissionView.setText(transmission);
////                            yearView.setText(year);
////                            fuelType.setText(fuel);
////                            seriesView.setText(seriesData);
////                            editionView.setText(editionData);
////                            infoView.setText(infoData);
//
//
//                            //                            mDatabaseRef = FirebaseDatabase.getInstance().getReference("Shop").child(uid);
//                            //
//                            //                            mDatabaseRef.addValueEventListener(new ValueEventListener() {
//                            //                                @Override
//                            //                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            //                                    firstname = dataSnapshot.child("firstname").getValue().toString();
//                            //                                    lastname =  dataSnapshot.child("lastname").getValue().toString();
//                            //                                    contact = dataSnapshot.child("contact").getValue().toString();
//                            //                                    description  = dataSnapshot.child("description").getValue().toString();
//                            //                                    location = dataSnapshot.child("location").getValue().toString();
//                            //                                    name = dataSnapshot.child("name").getValue().toString();
//                            //                                    sellerName.setText(firstname + " " + lastname);
//                            //                                    sellerAddress.setText(location);
//                            //                                    sellerContact.setText(contact);
//                            //
//                            //                                }
//                            //
//                            //                                @Override
//                            //                                public void onCancelled(@NonNull DatabaseError databaseError) {
//                            //                                    Toast.makeText(TradeScrolling.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//                            //                                }
//                            //                            });
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//                            Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//                else{
//                    Toast.makeText(getActivity(), "false", Toast.LENGTH_SHORT).show();
//
//                    mDatabaseRef2 = FirebaseDatabase.getInstance().getReference("Motor").child(listingid);
//
//                    mDatabaseRef2.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                            image1 = dataSnapshot.child("image").getValue().toString();
//                            image2 = dataSnapshot.child("imagePath1").getValue().toString();
//                            image3 = dataSnapshot.child("imagePath2").getValue().toString();
//                            image4 = dataSnapshot.child("imagePath3").getValue().toString();
//                            videoUrl = dataSnapshot.child("videoPath").getValue().toString();
//                            uriVideo = Uri.parse(videoUrl);
//                            brand = dataSnapshot.child("finalBrand").getValue().toString();
//                            model = dataSnapshot.child("finalModel").getValue().toString();
//                            year = dataSnapshot.child("finalYear").getValue().toString();
//                            color = dataSnapshot.child("finalColor").getValue().toString();
//                            transmission = dataSnapshot.child("finalTransmission").getValue().toString();
//                            pricecondition = dataSnapshot.child("finalPcondition").getValue().toString();
//                            mileage = dataSnapshot.child("finalMileage").getValue().toString();
//                            price = dataSnapshot.child("finalPrice").getValue().toString();
//                            uid = dataSnapshot.child("uid").getValue().toString();
//                            fuel = dataSnapshot.child("fuel").getValue().toString();
//                            dateData =dataSnapshot.child("date").getValue().toString();
//                            seriesData = dataSnapshot.child("series").getValue().toString();
//                            editionData = dataSnapshot.child("edition").getValue().toString();
//                            infoData = dataSnapshot.child("info").getValue().toString();
//                            type = "motor";
//
////                            video.setVideoURI(uriVideo);
////                            video.start();
//                            Picasso.get().load(image1).fit().centerCrop().into(scrollImage);
////                            Picasso.get().load(image1).fit().centerCrop().into(imageView1);
////                            Picasso.get().load(image2).fit().centerCrop().into(imageView2);
////                            Picasso.get().load(image3).fit().centerCrop().into(imageView3);
////                            Picasso.get().load(image4).fit().centerCrop().into(imageView4);
////                            vehicleName.setText(brand + " " + model);
////                            priceView.setText(price);
////                            priceCondition.setText(pricecondition);
////                            date.setText(dateData);
////                            mileageView.setText(mileage);
////                            transmissionView.setText(transmission);
////                            yearView.setText(year);
////                            fuelType.setText(fuel);
////                            seriesView.setText(seriesData);
////                            editionView.setText(editionData);
////                            infoView.setText(infoData);
//
////                            mDatabaseRef = FirebaseDatabase.getInstance().getReference("Shop").child(uid);
////
////                            mDatabaseRef.addValueEventListener(new ValueEventListener() {
////                                @Override
////                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                                    firstname = dataSnapshot.child("firstname").getValue().toString();
////                                    lastname =  dataSnapshot.child("lastname").getValue().toString();
////                                    contact = dataSnapshot.child("contact").getValue().toString();
////                                    description  = dataSnapshot.child("description").getValue().toString();
////                                    location = dataSnapshot.child("location").getValue().toString();
////                                    name = dataSnapshot.child("name").getValue().toString();
////                                    sellerName.setText(firstname + " " + lastname);
////                                    sellerAddress.setText(location);
////                                    sellerContact.setText(contact);
////
////                                }
////
////                                @Override
////                                public void onCancelled(@NonNull DatabaseError databaseError) {
////                                    Toast.makeText(TradeScrolling.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
////                                }
////                            });
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//                            Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });



        return v;
    }

//    @Override
//    public void onClick(View view) {
//        int id = view.getId();
//
//        if(id == R.id.message){
//
//            Intent sendIntent = new Intent();
//            sendIntent.setAction(Intent.ACTION_VIEW);
//            sendIntent.setData(Uri.parse("smsto:" + contact));  // This ensures only SMS apps respond
//            sendIntent.putExtra("sms_body", "Hi im interested in your listing " + model +" " + brand + " in ezwheels!");
//            if (sendIntent.resolveActivity(getPackageManager()) != null) {
//                startActivity(sendIntent);
//            }
//
//        }
//
//        if(id == R.id.call){
//
//            Intent intent = new Intent(Intent.ACTION_DIAL);
//            intent.setData(Uri.parse("tel:" + contact));
//            if (intent.resolveActivity(getPackageManager()) != null) {
//                startActivity(intent);
//            }
//
//        }
//
//        if(id == R.id.reserveButton){
//            Intent intent = new Intent(TradeScrolling.this, SetReservationFragment.class);
//            intent.putExtra("shopuid", uid);
//            intent.putExtra("listingid", listingid);
//            intent.putExtra("image1", image1);
//            intent.putExtra("name", name);
//            intent.putExtra("model", model);
//            intent.putExtra("brand", brand);
//            intent.putExtra("price", price);
//            startActivity(intent);
//        }
//
//        if(id == R.id.tradeButton){
//            Intent intent = new Intent(TradeScrolling.this, SetTradeinFragment.class);
//            intent.putExtra("shopuid", uid);
//            intent.putExtra("listingid", listingid);
//            intent.putExtra("image1", image1);
//            intent.putExtra("name", name);
//            intent.putExtra("model", model);
//            intent.putExtra("brand", brand);
//            intent.putExtra("price", price);
//            startActivity(intent);
//        }
//
//        if(id == R.id.fab){
//
//            DatabaseReference favDelete = FirebaseDatabase.getInstance().getReference("Favorites").child(getIntent().getStringExtra("fid"));
//            favDelete.removeValue();
//            Toast.makeText(TradeScrolling.this, "Favorite Deleted", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(TradeScrolling.this, ShopDashboard.class);
//            startActivity(intent
//            );
//
//        }

}
