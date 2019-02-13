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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class SetTradeinFragment extends AppCompatActivity implements View.OnClickListener {


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
    private String status = "PENDING";
    private static String imagePath1;
    private static  String imagePath2;
    ProgressBar p1, p2;

    //uri
    private Uri imageUri1, imageUri2;

    //properties

    RadioGroup type;
    RadioButton type1, type2, type3;
    AutoCompleteTextView brand, model, year;
    TextView input, input1;
    EditText price, price1;
    ImageView image1, image2;
    Button tradeBtn;

//    public SetTradeinFragment() {
//        // Required empty public constructor
//    }

    //firebase

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mAuth;
    private StorageTask mUploadTask;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_set_tradein);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#fefefe"));
        toolbar.setTitle("Trade");
        setSupportActionBar(toolbar);

        price = findViewById(R.id.address);
        price1 = findViewById(R.id.address1);
        input = findViewById(R.id.input);
        input1 = findViewById(R.id.input1);
        brand = findViewById(R.id.brandText);
        model = findViewById(R.id.modelText);
        year = findViewById(R.id.yearText);
        type = findViewById(R.id.type);
        type1 = findViewById(R.id.radioButton);
        type2 = findViewById(R.id.radioButton2);
        type3 = findViewById(R.id.radioButton3);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        tradeBtn = findViewById(R.id.tradeBtn);
        p1 = findViewById(R.id.p1);
        p2 = findViewById(R.id.p2);

        //toolbar

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //listeners
        image1.setOnClickListener(this);
        image2.setOnClickListener(this);
        tradeBtn.setOnClickListener(this);

        ArrayAdapter<String> brandAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, brands);
        brand.setAdapter(brandAdapter);
        ArrayAdapter<String> modelAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, models);
        model.setAdapter(modelAdapter);
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, years);
        year.setAdapter(yearAdapter);


        type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i){
                    case R.id.radioButton:
                        price.setVisibility(View.GONE);
                        input.setVisibility(View.GONE);
                        price1.setVisibility(View.GONE);
                        input1.setVisibility(View.GONE);
                        typeText = "SWAP";
//                        typeText = "car1";
//                        Toast.makeText(SetTradeinFragment.this, typeText, Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.radioButton2:
                        typeText = "I WILL ADD";
                        price.setVisibility(View.VISIBLE);
                        input.setVisibility(View.VISIBLE);
                        price1.setVisibility(View.GONE);
                        input1.setVisibility(View.GONE);
//                        typeText = "motor";
//                        Toast.makeText(SetTradeinFragment.this, typeText, Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.radioButton3:
                        typeText = "SHOP WILL ADD";
                        price.setVisibility(View.GONE);
                        input.setVisibility(View.GONE);
                        price1.setVisibility(View.VISIBLE);
                        input1.setVisibility(View.VISIBLE);

                }

            }
        });


        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

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
            final String finalBrand = brand.getText().toString();
            final String finalModel = model.getText().toString();
            final String finalYear = year.getText().toString();
            final String model = getIntent().getStringExtra("model").toString();
            final String brand = getIntent().getStringExtra("brand").toString();
            final String name = getIntent().getStringExtra("name").toString();
            final String image1 = getIntent().getStringExtra("image1").toString();
            final String listing = getIntent().getStringExtra("listingid");
            final String seller = getIntent().getStringExtra("shopuid");
            final String price = getIntent().getStringExtra("price");
            final String yearr = getIntent().getStringExtra("year");
            //Toast.makeText(this, imagePath1, Toast.LENGTH_SHORT).show();
            addTrade(imagePath1, imagePath2, finalPrice, finalPrice1, finalBrand, finalModel, finalYear, typeText, model, brand, yearr, name, image1, listing, seller, price);
        }
    }

    private void addTrade(final String imagePath1, String imagePath2, String price, String price1, String finalbrand, String finalmodel, String finalYear, String type, String model, String brand, String yearr, String name, String image1, String listing, String seller, String priceList) {

        String uid = mAuth.getCurrentUser().getUid();
        String shopuid =  getIntent().getStringExtra("shopuid");
        String listid = getIntent().getStringExtra("listingid");
        String id = mDatabaseRef.push().getKey();
        String seen = "false";
        String fromSeen = "false";
        Trade trade = new Trade(listid, imagePath1, imagePath2, price, price1, finalbrand, finalmodel,finalYear, uid, shopuid, type, image1, model, brand, yearr, name, status, priceList, id, seen, fromSeen);
        mDatabaseRef.child("Trade").child(id).setValue(trade).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SetTradeinFragment.this, "Added Trade... Wait for the shop to contact you and confirm", Toast.LENGTH_SHORT).show();
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


            final String path = System.currentTimeMillis() + "." + getFileExtension(imageUri1);
            p1.setVisibility(View.VISIBLE);
            StorageReference storageReference = mStorageRef.child("Images").child(path);
            storageReference.putFile(imageUri1).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    mStorageRef.child("Images/"+path).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Toast.makeText(SetTradeinFragment.this, "Image 1 added", Toast.LENGTH_SHORT).show();
                            imagePath1 = uri.toString();
                            Picasso.get().load(imageUri1).fit().centerCrop().into(image1);
                            p1.setVisibility(View.GONE);

                        }
                    });
                }
            });
        }

        if(requestCode == IMAGE_REQUEST_2 && resultCode == RESULT_OK){
            imageUri2 = data.getData();


            final String path1 = System.currentTimeMillis() + "." + getFileExtension(imageUri2);
            p2.setVisibility(View.VISIBLE);
            StorageReference storageReference = mStorageRef.child("Images").child(path1);
            storageReference.putFile(imageUri2).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    mStorageRef.child("Images/"+path1).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Toast.makeText(SetTradeinFragment.this, "Image 2 added", Toast.LENGTH_SHORT).show();
                            imagePath2 = uri.toString();
                            Picasso.get().load(imageUri2).fit().centerCrop().into(image2);
                            p2.setVisibility(View.GONE);
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
