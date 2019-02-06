package vincegeralddelaccerna.ezwheels;


import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class EditTrade extends AppCompatActivity implements View.OnClickListener {


    //requests

    private static final int IMAGE_REQUEST_1 = 1;
    private static final int IMAGE_REQUEST_2 = 2;



    //strings
    private static final String [] brands = new String[]{
            "Toyota","Suzuki","Mitsubishi","Kia","Chevrolet"
    };
    private static final String [] models = new String[]{
            "Fortuner","Wigo","LandCruiser","MonteroSport","Sportage"
    };

    private static final String [] years = new String[]{
            "2010","2011","2012","2013","2014","2015","2016","2017","2018"
    };

    private String typeText = "SWAP";
    private static String typeTrade;
    private String status = "PENDING";
    private static String imagePath1;
    private static  String imagePath2;

    //uri
    private Uri imageUri1, imageUri2;

    //properties

    RadioGroup type;
    RadioButton type1, type2, type3;
    AutoCompleteTextView brandText, modelText, yearText;
    TextView input, input1;
    EditText price, price1;
    ImageView imageCont1, imageCont2;


    Button tradeBtn;
    private static String tid, ty;
    private static String seen, brand, image1, listingid, model, name, priceList,shopuid, statusFinal, tidFinal, uid, year;

//    public SetTradeinFragment() {
//        // Required empty public constructor
//    }

    //firebase

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mDatabaseRef1;
    private FirebaseAuth mAuth;
    private StorageTask mUploadTask;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_set_tradein);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#fefefe"));
        toolbar.setTitle("Edit Trade");
        setSupportActionBar(toolbar);

        price = findViewById(R.id.address);
        price1 = findViewById(R.id.address1);
        input = findViewById(R.id.input);
        input1 = findViewById(R.id.input1);
        brandText = findViewById(R.id.brandText);
        modelText = findViewById(R.id.modelText);
        yearText = findViewById(R.id.yearText);
        type = findViewById(R.id.type);
        type1 = findViewById(R.id.radioButton);
        type2 = findViewById(R.id.radioButton2);
        type3 = findViewById(R.id.radioButton3);
        imageCont1 = findViewById(R.id.image1);
        imageCont2 = findViewById(R.id.image2);
        tradeBtn = findViewById(R.id.tradeBtn);

        //toolbar

        tid = getIntent().getStringExtra("tid");


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //listeners
        imageCont1.setOnClickListener(this);
        imageCont2.setOnClickListener(this);
        tradeBtn.setOnClickListener(this);

        ArrayAdapter<String> brandAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, brands);
        brandText.setAdapter(brandAdapter);
        ArrayAdapter<String> modelAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, models);
        modelText.setAdapter(modelAdapter);
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, years);
        yearText.setAdapter(yearAdapter);

        type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i){
                    case R.id.radioButton:
                        price.setVisibility(View.GONE);
                        input.setVisibility(View.GONE);
                        price1.setVisibility(View.GONE);
                        input1.setVisibility(View.GONE);
                        price.setText("");
                        price1.setText("");
                        typeTrade = "SWAP";
//                        typeText = "car1";
//                        Toast.makeText(SetTradeinFragment.this, typeText, Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.radioButton2:
                        typeTrade = "I WILL ADD";
                        price.setVisibility(View.VISIBLE);
                        input.setVisibility(View.VISIBLE);
                        price1.setVisibility(View.GONE);
                        price1.setText("");
                        input1.setVisibility(View.GONE);
//                        typeText = "motor";
//                        Toast.makeText(SetTradeinFragment.this, typeText, Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.radioButton3:
                        typeTrade = "SHOP WILL ADD";
                        price.setVisibility(View.GONE);
                        price.setText("");
                        input.setVisibility(View.GONE);
                        price1.setVisibility(View.VISIBLE);
                        input1.setVisibility(View.VISIBLE);

                }

            }
        });


        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mDatabaseRef1 = FirebaseDatabase.getInstance().getReference();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Trade").child(tid);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    final String addPrice = dataSnapshot.child("addPrice").getValue().toString();
                    brand = dataSnapshot.child("brand").getValue().toString();
                    final String fbrand = dataSnapshot.child("fbrand").getValue().toString();
                    final String fmodel = dataSnapshot.child("fmodel").getValue().toString();
                    final String fyear = dataSnapshot.child("fyear").getValue().toString();
                    image1 = dataSnapshot.child("image1").getValue().toString();
                    imagePath1 = dataSnapshot.child("imagePath1").getValue().toString();
                    imagePath2 = dataSnapshot.child("imagePath2").getValue().toString();
                    listingid = dataSnapshot.child("listingid").getValue().toString();
                    model = dataSnapshot.child("model").getValue().toString();
                    name = dataSnapshot.child("name").getValue().toString();
                    priceList = dataSnapshot.child("priceList").getValue().toString();
                    final String shopAddPrice = dataSnapshot.child("shopAddPrice").getValue().toString();
                    shopuid = dataSnapshot.child("shopuid").getValue().toString();
                    statusFinal = dataSnapshot.child("status").getValue().toString();
                    tidFinal = dataSnapshot.child("tid").getValue().toString();
                    typeTrade = dataSnapshot.child("type").getValue().toString();
                    uid = dataSnapshot.child("uid").getValue().toString();
                    year = dataSnapshot.child("year").getValue().toString();
                    seen = dataSnapshot.child("seen").getValue().toString();
                    Picasso.get().load(imagePath1).fit().centerCrop().into(imageCont1);
                    Picasso.get().load(imagePath2).fit().centerCrop().into(imageCont2);
                    brandText.setText(fbrand);
                    modelText.setText(fmodel);
                    yearText.setText(fyear);

                    if(typeTrade.equals("SWAP")){
                        type1.setChecked(true);
                        Toast.makeText(EditTrade.this, typeTrade, Toast.LENGTH_SHORT).show();
                    }
                    else if(typeTrade.equals("I WILL ADD")){
                        type2.setChecked(true);
                        price.setText(addPrice);
                        Toast.makeText(EditTrade.this, typeTrade, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        type3.setChecked(true);
                        price1.setText(shopAddPrice);
                        Toast.makeText(EditTrade.this, typeTrade, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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

        if(id == R.id.image1){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, IMAGE_REQUEST_1);
        }

        if(id == R.id.image2){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, IMAGE_REQUEST_2);
        }

        if(id == R.id.tradeBtn){
            final String finalPrice = price.getText().toString();
            final String finalPrice1 = price1.getText().toString();
            final String finalBrand = brandText.getText().toString();
            final String finalModel = modelText.getText().toString();
            final String finalYear = yearText.getText().toString();

            Toast.makeText(this, finalBrand, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, tidFinal, Toast.LENGTH_SHORT).show();
            updateTrade(finalPrice, finalPrice1, finalBrand, finalModel, finalYear);
        }
    }

    private void updateTrade(final String finalPrice,final String finalPrice1,final String finalBrand,final String finalModel,final String finalYear) {


        Trade trade = new Trade(listingid, imagePath1, imagePath2, finalPrice, finalPrice1, finalBrand, finalModel,finalYear, uid, shopuid, typeTrade, image1, model, brand, year, name, statusFinal, priceList, tidFinal,seen);
        mDatabaseRef1.child("Trade").child(tid).setValue(trade).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(EditTrade.this, "Trade Edited", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_REQUEST_1 && resultCode == RESULT_OK){
            imageUri1 = data.getData();
            Picasso.get().load(imageUri1).fit().centerCrop().into(imageCont1);

            final String path = System.currentTimeMillis() + "." + getFileExtension(imageUri1);

            StorageReference storageReference = mStorageRef.child("Images").child(path);
            storageReference.putFile(imageUri1).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    mStorageRef.child("Images/"+path).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Toast.makeText(EditTrade.this, "Image 1 added", Toast.LENGTH_SHORT).show();
                            imagePath1 = uri.toString();
                        }
                    });
                }
            });
        }

        if(requestCode == IMAGE_REQUEST_2 && resultCode == RESULT_OK){
            imageUri2 = data.getData();
            Picasso.get().load(imageUri2).fit().centerCrop().into(imageCont2);

            final String path1 = System.currentTimeMillis() + "." + getFileExtension(imageUri2);

            StorageReference storageReference = mStorageRef.child("Images").child(path1);
            storageReference.putFile(imageUri2).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    mStorageRef.child("Images/"+path1).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Toast.makeText(EditTrade.this, "Image 2 added", Toast.LENGTH_SHORT).show();
                            imagePath2 = uri.toString();
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
}
