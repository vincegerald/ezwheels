package vincegeralddelaccerna.ezwheels;

import android.app.Activity;
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
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import co.gofynd.gravityview.GravityView;

import static android.app.Activity.RESULT_OK;

public class ShopAddListing extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

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
    private static String finalPcondition, finalTransmission, finalFuel, finalCategory, finalDoors;



    private List<String> Shop = new ArrayList<>();

    private Uri videoUri, imageUri1, imageUri2, imageUri3, imageUri4, proofUri;
    private static final int SINGLE_VIDEO = 1;
    private static final int PANORAMA_IMAGE = 2 ;
    private static final int IMAGE_REQUEST_1 = 3;
    private static final int IMAGE_REQUEST_2 = 4;
    private static final int IMAGE_REQUEST_3 = 5;
    private static final int IMAGE_REQUEST_4 = 6;
    private static final int PROOF_IMAGE = 7;
    ImageButton image;
    AutoCompleteTextView brandText, modelText, yearText, transmissionText, colorText, priceConditionText, fuel;
    Button btn3, btn4, btnBack, btnStep2, btn6, btn5, btn7, btn8, btn9, btn10, addImage, btnVideo, addPanorama, btnFront, btnfSide, btnBackImage, addFside, buttonBack, buttonSside, buttonVideo,
            buttonFside, addImageSside, payment, videoback, got, cancel;
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
    Spinner transSpinner, fuelSpinner, priceConSpinner;
    Toolbar toolbar;
    ScrollView instructions, paymentt;
    ImageView proof;
    EditText sender, code;
    ProgressBar progress;
    TextView reminder, stepp2;
    Spinner doors, category;


    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mDatabaseRef1;
    private DatabaseReference addPayment;
    private FirebaseAuth mAuth;
    private StorageTask mUploadTask;

    private static String imagePath1 = "";
    private static  String imagePath2 = "";
    private static  String imagePath3 = "";
    private static  String imagePath4 = "";
    private static  String imageProof = "";
    private static String videoPath = "";
    private static String type = "car";
    private static String formattedDate;
    ProgressBar p, p1, p2, p3,p4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.shopaddlisting);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#fefefe"));
        toolbar.setTitle("Shop Add Listing");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //payment and listeners

        //text view

        reminder = findViewById(R.id.reminder);
        stepp2 = findViewById(R.id.stepp2);

        //edittext
        sender = findViewById(R.id.sender);
        code = findViewById(R.id.code);

        progress = findViewById(R.id.progress);
        //imageview

        proof = findViewById(R.id.proof);
        proof.setOnClickListener(this);

        //end of payment

        //linear layouts
        instructions = findViewById(R.id.instructions);
        paymentt = findViewById(R.id.paymentt);

        //button and listeners
        got = findViewById(R.id.got);
        cancel = findViewById(R.id.cancel);
        videoback = findViewById(R.id.videoback);

        got.setOnClickListener(this);
        cancel.setOnClickListener(this);
        videoback.setOnClickListener(this);

        //end of button and listeners


        payment = findViewById(R.id.payment);
        videoback = findViewById(R.id.videoback);
        payment.setOnClickListener(this);
        videoback.setOnClickListener(this);
        //spinner

        category = findViewById(R.id.category);
        doors = findViewById(R.id.doors);
        transSpinner = findViewById(R.id.transmission);
        fuelSpinner = findViewById(R.id.fuel);
        priceConSpinner = findViewById(R.id.pricecondition);

        progressBar = findViewById(R.id.progressBar5);
        editText5 = findViewById(R.id.editText5);
        vehicleType = findViewById(R.id.type);
        listViewshop = findViewById(R.id.listviewshop);
        mileage = findViewById(R.id.mileage);
        price = findViewById(R.id.price);
        series = findViewById(R.id.editText3);
        edition = findViewById(R.id.editText4);
        //fuel = findViewById(R.id.fuelText);
        brandText = findViewById(R.id.brandText);
        modelText = findViewById(R.id.modelText);
        yearText = findViewById(R.id.yearText);
        colorText = findViewById(R.id.colorText);
        //transmissionText = findViewById(R.id.transmissionText);
        //priceConditionText = findViewById(R.id.PriceCondition);
        videoView = findViewById(R.id.videoView);
        mediaController = new MediaController(this);
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
        ArrayAdapter<String> brandAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, brands);
        brandText.setAdapter(brandAdapter);
        ArrayAdapter<String> modelAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, model);
        modelText.setAdapter(modelAdapter);
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, year);
        yearText.setAdapter(yearAdapter);
//        ArrayAdapter<String> transmissionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, transmission);
//        transmissionText.setAdapter(transmissionAdapter);
        ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, color);
        colorText.setAdapter(colorAdapter);
//        ArrayAdapter<String> condtionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, priceCondition);
//        priceConditionText.setAdapter(condtionAdapter);
//        ArrayAdapter<String> fuelAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fuels);
//        fuel.setAdapter(fuelAdapter);
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

        //progressbars

        p = findViewById(R.id.barimage);
        p1 = findViewById(R.id.barimage1);
        p2 = findViewById(R.id.barimage2);
        p3 = findViewById(R.id.barimage3);
        p4 = findViewById(R.id.barimage4);

        vehicleType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioButton:
                        type = "car";
                        //Toast.makeText(getActivity(), type, Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.radioButton2:
                        type = "motor";
                        //Toast.makeText(this, type, Toast.LENGTH_SHORT).show();
                }
            }
        });

        //spinner adapters

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.transmission, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transSpinner.setAdapter(adapter);
        transSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.fuel, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fuelSpinner.setAdapter(adapter1);
        fuelSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.pricecondition, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        priceConSpinner.setAdapter(adapter2);
        priceConSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter3);
        category.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this, R.array.doors, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doors.setAdapter(adapter4);
        doors.setOnItemSelectedListener(this);


        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mDatabaseRef1 = FirebaseDatabase.getInstance().getReference();
        addPayment = FirebaseDatabase.getInstance().getReference();
        gravityView = GravityView.getInstance(this);

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
    public void onClick(View view) {
//        if(view.getId() == R.id.button3){
//            ShopAddListing listing = new ShopAddListing();
//            FragmentManager manager = getFragmentManager();
//            manager.beginTransaction()
//                    .replace(R.id.screen_area, listing, listing.getTag())
//                    .commit();
//        }

        if(view.getId() == R.id.button3){
            finish();
        }
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
            paymentt.setVisibility(View.GONE);
            instructions.setVisibility(View.VISIBLE);
        }

        if(view.getId() == R.id.videoback){
            addList1.setVisibility(View.GONE);
            addListSside.setVisibility(View.VISIBLE);
            addList4.setVisibility(View.GONE);
        }

        if(view.getId() == R.id.payment){
            final String pricee = price.getText().toString();
            NumberFormat f = NumberFormat.getInstance();
            double payPrice = 0.00;
            try {
                payPrice = f.parse(pricee).doubleValue() * 0.005;
            } catch (ParseException e) {
                e.printStackTrace();
            }
//            int priceTemp = Integer.parseInt(pricee);
//            Float payPrice = Float.parseFloat(pricee);
            reminder.setText("To post this listing, you should pay .5% of your listing price Php(" + String.valueOf(payPrice) + ")");
            stepp2.setText("Send your payment Php(" + String.valueOf(payPrice) + ") to our official representative");
            instructions.setVisibility(View.VISIBLE);
            addList4.setVisibility(View.GONE);
            addList1.setVisibility(View.GONE);
        }

        if(view.getId() == R.id.got){
            instructions.setVisibility(View.GONE);
            addList1.setVisibility(View.GONE);
            paymentt.setVisibility(View.VISIBLE);
        }

        if(view.getId() == R.id.cancel){
            instructions.setVisibility(View.GONE);
            addList4.setVisibility(View.VISIBLE);
            addList1.setVisibility(View.GONE);
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
            //final String finalTransmission = transmissionText.getText().toString();
            //final String finalPcondition = priceConditionText.getText().toString();
            final String finalMileage = mileage.getText().toString();
            final String finalPrice = price.getText().toString();
            //final String finalFuel = fuel.getText().toString();
            final String finalEdition = edition.getText().toString();
            final String finalSeries = series.getText().toString();
            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);
            final String finalInfo = editText5.getText().toString();

            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            formattedDate = df.format(c);

            final String shop = "";
            final String status = "PENDING";
            final String uid = mAuth.getCurrentUser().getUid();

            if(TextUtils.isEmpty(finalBrand) || TextUtils.isEmpty(finalModel) || TextUtils.isEmpty(finalYear) || TextUtils.isEmpty(finalColor)   || TextUtils.isEmpty(finalMileage) || TextUtils.isEmpty(finalPrice)){
                Toast.makeText(this, "Input all fields", Toast.LENGTH_SHORT).show();
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

        if(view.getId() == R.id.proof){

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PROOF_IMAGE);

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
                                videoPath = uri.toString();
                                p4.setVisibility(View.GONE);
                                Toast.makeText(ShopAddListing.this, "Added Video", Toast.LENGTH_SHORT).show();


                            }
                        });
                    }
                });

            }
        }

        else if(requestCode == PROOF_IMAGE && resultCode == RESULT_OK){

            proofUri = data.getData();
            Picasso.get().load(proofUri).fit().centerCrop().into(proof);
            final String path5 = System.currentTimeMillis() + "." + getFileExtension(proofUri);
            progress.setVisibility(View.VISIBLE);
            StorageReference storageReference = mStorageRef.child("Images").child(path5);
            storageReference.putFile(proofUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    mStorageRef.child("Images/"+path5).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageProof = uri.toString();
                            progress.setVisibility(View.INVISIBLE);
                            Toast.makeText(ShopAddListing.this, "Added Proof Image", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

        else if(requestCode == IMAGE_REQUEST_1 && resultCode == RESULT_OK){

            imageUri1 = data.getData();
            Picasso.get().load(imageUri1).fit().centerCrop().into(image1);
            final String path4 = System.currentTimeMillis() + "." + getFileExtension(imageUri1);
            p.setVisibility(View.VISIBLE);
            StorageReference storageReference = mStorageRef.child("Images").child(path4);
            storageReference.putFile(imageUri1).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    mStorageRef.child("Images/"+path4).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imagePath4 = uri.toString();
                            p.setVisibility(View.GONE);
                            Toast.makeText(ShopAddListing.this, "Added FrontView Image", Toast.LENGTH_SHORT).show();
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
                            p1.setVisibility(View.GONE);
                            Toast.makeText(ShopAddListing.this, "Added BackView Image", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
        else if(requestCode == IMAGE_REQUEST_3 && resultCode == RESULT_OK){
            imageUri3 = data.getData();
            Picasso.get().load(imageUri3).fit().centerCrop().into(image3);

            final String path1 = System.currentTimeMillis() + "." + getFileExtension(imageUri3);
            p2.setVisibility(View.VISIBLE);
            StorageReference storageReference = mStorageRef.child("Images").child(path1);
            storageReference.putFile(imageUri3).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    mStorageRef.child("Images/"+path1).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imagePath2 = uri.toString();
                            p2.setVisibility(View.GONE);
                            Toast.makeText(ShopAddListing.this, "Added Side View Image", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
        else if(requestCode == IMAGE_REQUEST_4 && resultCode == RESULT_OK){

            imageUri4 = data.getData();
            Picasso.get().load(imageUri4).fit().centerCrop().into(image4);

            final String path2 = System.currentTimeMillis() + "." + getFileExtension(imageUri4);
            p3.setVisibility(View.VISIBLE);
            StorageReference storageReference = mStorageRef.child("Images").child(path2);
            storageReference.putFile(imageUri4).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    mStorageRef.child("Images/"+path2).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imagePath3 = uri.toString();
                            p3.setVisibility(View.GONE);
                            Toast.makeText(ShopAddListing.this, "Added Side/Inside View Image", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }


    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = ShopAddListing.this.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile(final String edition, final String series, final String info, final String fuel, final String formattedData, final String uid, Uri videoUri, final Uri uriImage, final Uri uriImage1, final Uri uriImage2, final Uri uriImage3, final String finalBrand, final String finalModel, final String finalYear, final String finalColor, final String finalTransmission, final String finalPcondition, final String finalMileage, final String finalPrice, final String shop, final String status){

        if(uriImage != null){

            final String codeText = code.getText().toString().trim();
            final String senderText = sender.getText().toString().trim();


            NumberFormat f = NumberFormat.getInstance();
            double payPrice = 0.00;
            try {
                payPrice = f.parse(finalPrice).doubleValue() * 0.005;

            } catch (ParseException e) {
                e.printStackTrace();
            }


            final String fpayPrice = String.valueOf(payPrice);

            final String imageUrl = uriImage.toString();
            //Toast.makeText(getActivity(), imageUrl, Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.VISIBLE);
            mDatabaseRef = mDatabaseRef.child("Car");
            final String listid = mDatabaseRef.push().getKey();
            Upload upload = new Upload(listid, edition, series, info, uid, formattedDate, fuel, imagePath4, imagePath1, imagePath2, imagePath3, videoPath, finalBrand, finalModel, finalYear, finalColor, finalTransmission, finalPcondition, finalMileage, finalPrice, shop, status, finalCategory, finalDoors);
            if(type.equals("car")){
                Toast.makeText(ShopAddListing.this, type, Toast.LENGTH_SHORT).show();
                mDatabaseRef.child(listid).setValue(upload).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            addPayment = addPayment.child("Payments");
                            String pid = addPayment.push().getKey();
                            Calendar calendar = Calendar.getInstance();
                            String date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                            //(String image, String name, String code, String uid, String id, String amount, String type, String shopuid, String resid, String listingid, String date, String seen)
                            Payments payments = new Payments(imageProof, senderText, codeText, mAuth.getCurrentUser().getUid(), pid, fpayPrice, "Listing Payment", "A3vBEHzSZlUcwuWTWlBoj8FHRkD2", "", listid, date, "false");
                            addPayment.child(pid).setValue(payments).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(ShopAddListing.this, "Saved", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(ShopAddListing.this, "Error Occured", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(ShopAddListing.this, "error", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
            }
            else{
                mDatabaseRef1 = mDatabaseRef1.child("Motor");
                String listid1 = mDatabaseRef1.push().getKey();

                Upload upload1 = new Upload(listid1, edition, series, info, uid, formattedDate, fuel, imagePath4, imagePath1, imagePath2, imagePath3, videoPath, finalBrand, finalModel, finalYear, finalColor, finalTransmission, finalPcondition, finalMileage, finalPrice, shop, status, finalCategory, finalDoors);
                mDatabaseRef1.child(listid1).setValue(upload1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            addPayment = addPayment.child("Payments");
                            String pid = addPayment.push().getKey();
                            Calendar calendar = Calendar.getInstance();
                            String date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                            //(String image, String name, String code, String uid, String id, String amount, String type, String shopuid, String resid, String listingid, String date, String seen)
                            Payments payments = new Payments(imageProof, senderText, codeText, mAuth.getCurrentUser().getUid(), pid, fpayPrice, "Listing Payment", "A3vBEHzSZlUcwuWTWlBoj8FHRkD2", "", listid, date, "false");
                            addPayment.child(pid).setValue(payments).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(ShopAddListing.this, "Saved", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(ShopAddListing.this, "Error Occured", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(ShopAddListing.this, "error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        int id = adapterView.getId();

        if(id == R.id.transmission){
            finalTransmission = adapterView.getItemAtPosition(i).toString();
            //Toast.makeText(this, finalTransmission, Toast.LENGTH_SHORT).show();
        }

        if(id == R.id.fuel){
            finalFuel = adapterView.getItemAtPosition(i).toString();
        }

        if(id == R.id.pricecondition){
            finalPcondition = adapterView.getItemAtPosition(i).toString();
        }

        if(id == R.id.category){
            finalCategory = adapterView.getItemAtPosition(i).toString();
        }

        if(id == R.id.doors){
            finalDoors = adapterView.getItemAtPosition(i).toString();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
