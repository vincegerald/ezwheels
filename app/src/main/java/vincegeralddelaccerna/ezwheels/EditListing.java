package vincegeralddelaccerna.ezwheels;

import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import co.gofynd.gravityview.GravityView;

import static android.app.Activity.RESULT_OK;

public class EditListing extends AppCompatActivity implements View.OnClickListener {

    private static final String [] brands = new String[]{
            "Toyota","Suzuki","Mitsubishi","Kia","Chevrolet"
    };
    private static final String [] model = new String[]{
            "Fortuner","Wigo","LandCruiser","MonteroSport","Sportage"
    };
    private static final String [] year = new String[]{
            "2011","2012","2013","2014","2015","2016","2017","2018"
    };
    private static final String [] transmission = new String[]{
            "Manual","Automatic"
    };
    private static final String [] color = new String[]{
            "White","Black","Red","Blue","Yellow"
    };
    private static final String [] priceCondition = new String[]{
            "Negotiable","Fixed"
    };

    private static final String [] fuels = new String[]{
            "Diesel","Gasoline"
    };

    private static String finalCategory , finalDoors;

    private List<String> Shop = new ArrayList<>();

    private Uri videoUri, imageUri1, imageUri2, imageUri3, imageUri4, videoPF;
    private static final int SINGLE_VIDEO = 1;
    private static final int PANORAMA_IMAGE = 2 ;
    private static final int IMAGE_REQUEST_1 = 3;
    private static final int IMAGE_REQUEST_2 = 4;
    private static final int IMAGE_REQUEST_3 = 5;
    private static final int IMAGE_REQUEST_4 = 6;
    ImageButton image;
    AutoCompleteTextView brandText, modelText, yearText, transmissionText, colorText, priceConditionText, fuel;
    Button btn3, btn4, btnBack, btnStep2, btn6, btn5, btn7, btn8, btn9, btn10, addImage, btnVideo, addPanorama, btnFront, btnfSide, btnBackImage, addFside, buttonBack, buttonSside, buttonVideo,
            buttonFside, addImageSside;
    ScrollView addList1, addList2;
    LinearLayout addList3, addList4, addList5, addListImage1, addListFside, addListSside;
    ImageView image1, image2, image3, image4, imagePanorama;
    VideoView videoView;
    MediaController mediaController;
    GravityView gravityView;
    EditText series, edition, mileage, price;
    ListView listViewshop;
    RadioButton car, motor;
    RadioGroup vehicleType;
    EditText editText5;
    ProgressBar progressBar;
    Toolbar toolbar;
    ProgressBar p, p1, p2, p3,p4;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mDatabaseRef1;
    private DatabaseReference forCar;
    private DatabaseReference forMotor;
    private FirebaseAuth mAuth;
    private StorageTask mUploadTask;

    private static String imagePath1 = "";
    private static  String imagePath2 = "";
    private static  String imagePath3 = "";
    private static  String imagePath4 = "";
    private static String videoPath = "";
    private static String type = "car";
    private static String formattedDate;
    private static  String listingid;


    private static String date, editionn, finalBrand, finalColor, finalMileage, finalModel, finalPcondition, finalPrice, finalTransmission, finalYear, fuell, imageP1, imageP2, imageP3, imageP4, info,
            listid,seriess,status,uid,videoP, originalType, typee;
    private String origType;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editshoplisting);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#fefefe"));
        toolbar.setTitle("Edit Listing");
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get the intent listing id

        listingid = getIntent().getStringExtra("listingid");
        Toast.makeText(this, listingid, Toast.LENGTH_SHORT).show();

        progressBar = findViewById(R.id.progressBar5);
        editText5 = findViewById(R.id.editText5);
        vehicleType = findViewById(R.id.type);
        listViewshop = findViewById(R.id.listviewshop);
        mileage = findViewById(R.id.mileage);
        price = findViewById(R.id.price);
        series = findViewById(R.id.editText3);
        edition = findViewById(R.id.editText4);
        fuel = findViewById(R.id.fuelText);
        brandText = findViewById(R.id.brandText);
        modelText = findViewById(R.id.modelText);
        yearText = findViewById(R.id.yearText);
        colorText = findViewById(R.id.colorText);
        transmissionText = findViewById(R.id.transmissionText);
        priceConditionText = findViewById(R.id.PriceCondition);
        videoView = findViewById(R.id.videoView);
        mediaController = new MediaController(getApplicationContext());
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);
        btn5 = findViewById(R.id.button5);
        btnFront = findViewById(R.id.buttonFront);
        btn6 = findViewById(R.id.button6);
        btn7 = findViewById(R.id.button7);
        btn8 = findViewById(R.id.button8);
        btnfSide = findViewById(R.id.buttonfSide);
        addFside = findViewById(R.id.addFside);
        buttonBack = findViewById(R.id.buttonBack);
        buttonSside = findViewById(R.id.buttonSside);
        buttonVideo = findViewById(R.id.buttonVideo);
        addImageSside = findViewById(R.id.addImageSside);
        buttonFside = findViewById(R.id.buttonFside);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        addImage = findViewById(R.id.addImage);
        btnBack = findViewById(R.id.btnBack);
        btnBackImage = findViewById(R.id.btnBackImage);
        btnStep2 = findViewById(R.id.btnStep2);
        addList1 = findViewById(R.id.addList1);
        addList2 = findViewById(R.id.addList2);
        addList3 = findViewById(R.id.addList3);
        addList4 = findViewById(R.id.addList4);
        addList5 = findViewById(R.id.addList5);
        addListImage1 = findViewById(R.id.addListImage1);
        addListFside = findViewById(R.id.addListFside);
        addListSside = findViewById(R.id.addListSside);
        btnVideo = findViewById(R.id.btnVideo);
//        addPanorama = v.findViewById(R.id.addPanorama);
//        imagePanorama = v.findViewById(R.id.imageView15);
        car = findViewById(R.id.radioButton);
        motor = findViewById(R.id.radioButton2);
        ArrayAdapter<String> brandAdapter = new ArrayAdapter<String>(EditListing.this, android.R.layout.simple_list_item_1, brands);
        brandText.setAdapter(brandAdapter);
        ArrayAdapter<String> modelAdapter = new ArrayAdapter<String>(EditListing.this, android.R.layout.simple_list_item_1, model);
        modelText.setAdapter(modelAdapter);
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(EditListing.this, android.R.layout.simple_list_item_1, year);
        yearText.setAdapter(yearAdapter);
        ArrayAdapter<String> transmissionAdapter = new ArrayAdapter<String>(EditListing.this, android.R.layout.simple_list_item_1, transmission);
        transmissionText.setAdapter(transmissionAdapter);
        ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(EditListing.this, android.R.layout.simple_list_item_1, color);
        colorText.setAdapter(colorAdapter);
        ArrayAdapter<String> condtionAdapter = new ArrayAdapter<String>(EditListing.this, android.R.layout.simple_list_item_1, priceCondition);
        priceConditionText.setAdapter(condtionAdapter);
        ArrayAdapter<String> fuelAdapter = new ArrayAdapter<String>(EditListing.this, android.R.layout.simple_list_item_1, fuels);
        fuel.setAdapter(fuelAdapter);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btnBackImage.setOnClickListener(this);
        btnFront.setOnClickListener(this);
        btnfSide.setOnClickListener(this);
        addFside.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
        buttonSside.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnStep2.setOnClickListener(this);
        addImage.setOnClickListener(this);
        btnVideo.setOnClickListener(this);
//        addPanorama.setOnClickListener(this);
        addImageSside.setOnClickListener(this);
        buttonFside.setOnClickListener(this);
        buttonVideo.setOnClickListener(this);
        vehicleType.setVisibility(View.GONE);
        vehicleType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioButton:
                        type = "car";
                        Toast.makeText(EditListing.this, type, Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.radioButton2:
                        type = "motor";
                        Toast.makeText(EditListing.this, type, Toast.LENGTH_SHORT).show();
                }
            }
        });

        p = findViewById(R.id.barimage);
        p1 = findViewById(R.id.barimage1);
        p2 = findViewById(R.id.barimage2);
        p3 = findViewById(R.id.barimage3);
        p4 = findViewById(R.id.barimage4);


        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mDatabaseRef1 = FirebaseDatabase.getInstance().getReference();
        forCar = FirebaseDatabase.getInstance().getReference();
        forMotor = FirebaseDatabase.getInstance().getReference();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Car").child(listingid);

        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                        date = dataSnapshot.child("date").getValue().toString();
                        editionn = dataSnapshot.child("edition").getValue().toString();
                        finalBrand = dataSnapshot.child("finalBrand").getValue().toString();
                        finalColor = dataSnapshot.child("finalColor").getValue().toString();
                        finalMileage = dataSnapshot.child("finalMileage").getValue().toString();
                        finalModel = dataSnapshot.child("finalModel").getValue().toString();
                        finalPcondition = dataSnapshot.child("finalPcondition").getValue().toString();
                        finalPrice = dataSnapshot.child("finalPrice").getValue().toString();
                        finalTransmission = dataSnapshot.child("finalTransmission").getValue().toString();
                        finalYear = dataSnapshot.child("finalYear").getValue().toString();
                        fuell = dataSnapshot.child("fuel").getValue().toString();
                        imagePath4 = dataSnapshot.child("image").getValue().toString();
                        imagePath1 = dataSnapshot.child("imagePath1").getValue().toString();
                        imagePath2 = dataSnapshot.child("imagePath2").getValue().toString();
                        imagePath3 = dataSnapshot.child("imagePath3").getValue().toString();
                        info = dataSnapshot.child("info").getValue().toString();
                        listid = dataSnapshot.child("listid").getValue().toString();
                        seriess = dataSnapshot.child("series").getValue().toString();
                        date = dataSnapshot.child("date").getValue().toString();
                        status = dataSnapshot.child("status").getValue().toString();
                        uid = dataSnapshot.child("uid").getValue().toString();
                        videoP = dataSnapshot.child("videoPath").getValue().toString();
                        finalCategory = dataSnapshot.child("category").getValue().toString();
                        finalDoors = dataSnapshot.child("doors").getValue().toString();
                        brandText.setText(finalBrand);
                        modelText.setText(finalModel);
                        yearText.setText(finalYear);
                        colorText.setText(finalColor);
                        transmissionText.setText(finalTransmission);
                        priceConditionText.setText(finalPcondition);
                        mileage.setText(finalMileage);
                        price.setText(finalPrice);
                        fuel.setText(fuell);
                        edition.setText(editionn);
                        series.setText(seriess);
                        editText5.setText(info);
                        Picasso.get().load(imagePath4).fit().centerCrop().into(image1);
                        Picasso.get().load(imagePath1).fit().centerCrop().into(image2);
                        Picasso.get().load(imagePath2).fit().centerCrop().into(image3);
                        Picasso.get().load(imagePath3).fit().centerCrop().into(image4);
                        Uri uri = Uri.parse(videoP);
                        videoView.setVideoURI(uri);
                        car.setChecked(true);
                        motor.setChecked(false);
                    Toast.makeText(EditListing.this, imagePath1, Toast.LENGTH_SHORT).show();

                }
                else{
                    mDatabaseRef1 = FirebaseDatabase.getInstance().getReference("Motor").child(listingid);
                    mDatabaseRef1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot .exists()){
                                date = dataSnapshot.child("date").getValue().toString();
                                editionn = dataSnapshot.child("edition").getValue().toString();
                                finalBrand = dataSnapshot.child("finalBrand").getValue().toString();
                                finalColor = dataSnapshot.child("finalColor").getValue().toString();
                                finalMileage = dataSnapshot.child("finalMileage").getValue().toString();
                                finalModel = dataSnapshot.child("finalModel").getValue().toString();
                                finalPcondition = dataSnapshot.child("finalPcondition").getValue().toString();
                                finalPrice = dataSnapshot.child("finalPrice").getValue().toString();
                                finalTransmission = dataSnapshot.child("finalTransmission").getValue().toString();
                                finalYear = dataSnapshot.child("finalYear").getValue().toString();
                                fuell = dataSnapshot.child("fuel").getValue().toString();
                                imagePath4 = dataSnapshot.child("image").getValue().toString();
                                imagePath1 = dataSnapshot.child("imagePath1").getValue().toString();
                                imagePath2 = dataSnapshot.child("imagePath2").getValue().toString();
                                imagePath3 = dataSnapshot.child("imagePath3").getValue().toString();
                                info = dataSnapshot.child("info").getValue().toString();
                                listid = dataSnapshot.child("listid").getValue().toString();
                                seriess = dataSnapshot.child("series").getValue().toString();
                                date = dataSnapshot.child("date").getValue().toString();
                                finalCategory = dataSnapshot.child("category").getValue().toString();
                                finalDoors = dataSnapshot.child("doors").getValue().toString();
                                status = dataSnapshot.child("status").getValue().toString();
                                uid = dataSnapshot.child("uid").getValue().toString();
                                videoP = dataSnapshot.child("videoPath").getValue().toString();
                                brandText.setText(finalBrand);
                                modelText.setText(finalModel);
                                yearText.setText(finalYear);
                                colorText.setText(finalColor);
                                transmissionText.setText(finalTransmission);
                                priceConditionText.setText(finalPcondition);
                                mileage.setText(finalMileage);
                                price.setText(finalPrice);
                                fuel.setText(fuell);
                                edition.setText(editionn);
                                series.setText(seriess);
                                editText5.setText(info);
                                Picasso.get().load(imagePath4).fit().centerCrop().into(image1);
                                Picasso.get().load(imagePath1).fit().centerCrop().into(image2);
                                Picasso.get().load(imagePath2).fit().centerCrop().into(image3);
                                Picasso.get().load(imagePath3).fit().centerCrop().into(image4);
                                Uri uri = Uri.parse(videoP);
                                videoView.setVideoURI(uri);
                                motor.setChecked(true);
                                car.setChecked(false);
//                                Toast.makeText(EditListing.this, typee, Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(EditListing.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }




            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EditListing.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });


        Toast.makeText(this, typee, Toast.LENGTH_SHORT).show();
//        adapter = new ListViewAdapter(Shop, getActivity());
//        listViewshop.setAdapter(adapter);

//        if(!gravityView.deviceSupported()){
//            Bitmap bitmapFactory = BitmapFactory.decodeResource(getResources(), R.drawable.panorama);
//            imagePanorama.setImageBitmap(bitmapFactory);
//        }
//        else{
//            gravityView.setImage(imagePanorama, R.drawable.panorama).center();
//        }

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
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
//        if(view.getId() == R.id.button3){
//            ShopAddListing listing = new ShopAddListing();
//            FragmentManager manager = getFragmentManager();
//            manager.beginTransaction()
//                    .replace(R.id.screen_area, listing, listing.getTag())
//                    .commit();
//        }
        if(view.getId() == R.id.button4){
            addList1.setVisibility(View.GONE);
            addList2.setVisibility(View.VISIBLE);
        }

        if(view.getId() == R.id.btnBack){
            addList1.setVisibility(View.VISIBLE);
            addList2.setVisibility(View.GONE);
        }

        if(view.getId() == R.id.btnStep2){
            addList1.setVisibility(View.GONE);
            addList2.setVisibility(View.GONE);
            addList3.setVisibility(View.VISIBLE);
        }

        if(view.getId() == R.id.button5){
            addList1.setVisibility(View.GONE);
            addList3.setVisibility(View.GONE);
            addListImage1.setVisibility(View.VISIBLE);
        }

        if(view.getId() == R.id.button6){
            addList1.setVisibility(View.GONE);
            addList2.setVisibility(View.VISIBLE);
            addList3.setVisibility(View.GONE);
        }

        if(view.getId() == R.id.buttonFront){
            addList1.setVisibility(View.GONE);
            addList2.setVisibility(View.GONE);
            addList3.setVisibility(View.VISIBLE);
        }

        if(view.getId() == R.id.buttonfSide){
            addList1.setVisibility(View.GONE);
            addListFside.setVisibility(View.VISIBLE);
            addListImage1.setVisibility(View.GONE);
        }

        if(view.getId() == R.id.buttonSside){
            addList1.setVisibility(View.GONE);
            addListFside.setVisibility(View.GONE);
            addListSside.setVisibility(View.VISIBLE);
        }

        if(view.getId() == R.id.buttonVideo){
            addList1.setVisibility(View.GONE);
            addListSside.setVisibility(View.GONE);
            addList4.setVisibility(View.VISIBLE);
        }

        if(view.getId() == R.id.buttonFside){
            addList1.setVisibility(View.GONE);
            addListSside.setVisibility(View.GONE);
            addListFside.setVisibility(View.VISIBLE);
        }

        if(view.getId() == R.id.button7){
            addList1.setVisibility(View.GONE);
            addListSside.setVisibility(View.VISIBLE);
            addList4.setVisibility(View.GONE);
        }

        if(view.getId() == R.id.buttonBack){
            addList1.setVisibility(View.GONE);
            addListImage1.setVisibility(View.VISIBLE);
            addListFside.setVisibility(View.GONE);
        }

        if(view.getId() == R.id.button8){
            final String finalBrand = brandText.getText().toString();
            final String finalModel = modelText.getText().toString();
            final String finalYear = yearText.getText().toString();
            final String finalColor = colorText.getText().toString();
            final String finalTransmission = transmissionText.getText().toString();
            final String finalPcondition = priceConditionText.getText().toString();
            final String finalMileage = mileage.getText().toString();
            final String finalPrice = price.getText().toString();
            final String finalFuel = fuel.getText().toString();
            final String finalEdition = edition.getText().toString();
            final String finalSeries = series.getText().toString();
            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);
                final String finalInfo = editText5.getText().toString();

            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            formattedDate = df.format(c);

            final String shop = "";
            final String status = "AVAILABLE";
            final String uid = mAuth.getCurrentUser().getUid();

            if(TextUtils.isEmpty(finalBrand) || TextUtils.isEmpty(finalModel) || TextUtils.isEmpty(finalYear) || TextUtils.isEmpty(finalColor) || TextUtils.isEmpty(finalTransmission) || TextUtils.isEmpty(finalPcondition) || TextUtils.isEmpty(finalMileage) || TextUtils.isEmpty(finalPrice)){
                Toast.makeText(EditListing.this, "Input all fields", Toast.LENGTH_SHORT).show();
            }

            else{
                uploadFile(finalEdition, finalSeries, finalInfo, finalFuel, formattedDate, uid, videoUri, imageUri1, imageUri2, imageUri3, imageUri4, finalBrand, finalModel, finalYear, finalColor, finalTransmission, finalPcondition, finalMileage, finalPrice, shop, status);

            }

        }

        if(view.getId() == R.id.addImage){

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, IMAGE_REQUEST_1);
        }

        if(view.getId() == R.id.btnBackImage){

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, IMAGE_REQUEST_2);
        }

        if(view.getId() == R.id.addFside){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, IMAGE_REQUEST_3);
        }

        if(view.getId() == R.id.addImageSside){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, IMAGE_REQUEST_4);
        }

        if(view.getId() == R.id.btnVideo){
            Intent gallery = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);

//                    SINGLE_VIDEO);
            startActivityForResult(gallery, SINGLE_VIDEO);
        }

        if(view.getId() == R.id.addPanorama){
            startActivityForResult(Intent.createChooser(new Intent().
                            setAction(Intent.ACTION_GET_CONTENT).
                            setType("panorama/*"),
                    "Select panorama"),
                    PANORAMA_IMAGE);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SINGLE_VIDEO && resultCode == RESULT_OK){
            videoUri = data.getData();
            if(videoUri != null){
                videoView.setVideoURI(videoUri);
                videoView.start();

                final String videoPath1 = System.currentTimeMillis() + "." + getFileExtension(videoUri);
                p4.setVisibility(View.VISIBLE);
                StorageReference storageReference = mStorageRef.child("Videos").child(videoPath1);
                storageReference.putFile(videoUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        mStorageRef.child("Videos/"+videoPath1).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                videoP = uri.toString();
                                p4.setVisibility(View.GONE);
                                Toast.makeText(EditListing.this, "Video Added", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }
        }
//
//        else if(requestCode == PANORAMA_IMAGE && resultCode == RESULT_OK){
//            Uri imageUri = data.getData();
//
//        }

        else if(requestCode == IMAGE_REQUEST_1 && resultCode == RESULT_OK){

            imageUri1 = data.getData();
            Picasso.get().load(imageUri1).fit().centerCrop().into(image1);
            p.setVisibility(View.VISIBLE);
            final String path4 = System.currentTimeMillis() + "." + getFileExtension(imageUri1);
            StorageReference storageReference = mStorageRef.child("Images").child(path4);
            storageReference.putFile(imageUri1).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    mStorageRef.child("Images/"+path4).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imagePath4 = uri.toString();
                            Toast.makeText(EditListing.this, "Image 1 added", Toast.LENGTH_SHORT).show();
                            p.setVisibility(View.GONE);
                        }
                    });
                }
            });



        }

        else if(requestCode == IMAGE_REQUEST_2 && resultCode == RESULT_OK){

            imageUri2 = data.getData();
            Picasso.get().load(imageUri2).fit().centerCrop().into(image2);


            final String path = System.currentTimeMillis() + "." + getFileExtension(imageUri2);
            p1.setVisibility(View.VISIBLE);
            StorageReference storageReference = mStorageRef.child("Images").child(path);
            storageReference.putFile(imageUri2).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    mStorageRef.child("Images/"+path).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imagePath1 = uri.toString();
                            Toast.makeText(EditListing.this, "Image 2 added", Toast.LENGTH_SHORT).show();
                            p1.setVisibility(View.GONE);
                        }
                    });
                }
            });
        }
        else if(requestCode == IMAGE_REQUEST_3 && resultCode == RESULT_OK){
            imageUri3 = data.getData();
            Picasso.get().load(imageUri3).fit().centerCrop().into(image3);
            p2.setVisibility(View.VISIBLE);
            final String path1 = System.currentTimeMillis() + "." + getFileExtension(imageUri3);
            StorageReference storageReference = mStorageRef.child("Images").child(path1);
            storageReference.putFile(imageUri3).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    mStorageRef.child("Images/"+path1).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imagePath2 = uri.toString();
                            Toast.makeText(EditListing.this, "Image 3 added", Toast.LENGTH_SHORT).show();
                            p2.setVisibility(View.GONE);
                        }
                    });
                }
            });
        }
        else if(requestCode == IMAGE_REQUEST_4 && resultCode == RESULT_OK){

            imageUri4 = data.getData();
            Picasso.get().load(imageUri4).fit().centerCrop().into(image4);
            p3.setVisibility(View.VISIBLE);
            final String path2 = System.currentTimeMillis() + "." + getFileExtension(imageUri4);
            StorageReference storageReference = mStorageRef.child("Images").child(path2);
            storageReference.putFile(imageUri4).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    mStorageRef.child("Images/"+path2).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imagePath3 = uri.toString();
                            Toast.makeText(EditListing.this, "Image 4 added", Toast.LENGTH_SHORT).show();
                            p3.setVisibility(View.GONE);
                        }
                    });
                }
            });
        }


    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getApplication().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile(final String edition, final String series, final String info, final String fuel, final String formattedData, final String uid, Uri videoUri, final Uri uriImage, final Uri uriImage1, final Uri uriImage2, final Uri uriImage3, final String finalBrand, final String finalModel, final String finalYear, final String finalColor, final String finalTransmission, final String finalPcondition, final String finalMileage, final String finalPrice, final String shop, final String status){


                                progressBar.setVisibility(View.VISIBLE);

                                forCar = FirebaseDatabase.getInstance().getReference("Car").child(listingid);
                                final Upload upload = new Upload(listingid, edition, series, info, uid, formattedDate, fuel, imagePath4, imagePath1, imagePath2, imagePath3, videoP, finalBrand, finalModel, finalYear, finalColor, finalTransmission, finalPcondition, finalMileage, finalPrice, shop, status, finalCategory, finalDoors);
                                forCar.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.exists()){
                                            forCar.setValue(upload).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(EditListing.this, "Updated Car Listing", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(EditListing.this, ShopDashboard.class);
                                                        startActivity(intent);
                                                    }
                                                }
                                            });
                                        }
                                        else{
                                            forMotor = FirebaseDatabase.getInstance().getReference("Motor").child(listingid);
                                            forMotor.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    if(dataSnapshot.exists()){
                                                        forMotor.setValue(upload).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    Toast.makeText(EditListing.this, "Updated Motor Listing", Toast.LENGTH_SHORT).show();
                                                                    Intent intent = new Intent(EditListing.this, ShopDashboard.class);
                                                                    startActivity(intent);
                                                                }
                                                            }
                                                        });
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                    Toast.makeText(EditListing.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

    }


}
