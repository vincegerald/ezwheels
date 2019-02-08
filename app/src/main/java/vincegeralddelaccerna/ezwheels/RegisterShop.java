package vincegeralddelaccerna.ezwheels;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegisterShop extends AppCompatActivity implements View.OnClickListener {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1 ;
    Button btnNext, btnBack, btnFinish, btnHome;
    private static double lon, lat;
    private static String purl;
    private float rating = 5.0f;
    ;
    LinearLayout step1, step2;
    EditText shopFirstname, shopLastname, shopUsername, shopEmail, shopContact, shopPassword, shopName, shopLocation, shopDescription;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String mobilePattern = "^(09|\\+639)\\d{9}$";
    ProgressBar mProgress;
    LocationManager locationManager;
    FirebaseAuth mAuth;
    ScrollView scroll1, scroll2;
    TextView textView;
    private FirebaseDatabase mDatabase;
    private StorageReference mStorageRef;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static Double longitude, latitude;
    private String status = "NOT ACTIVATED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_shop);
        btnNext = findViewById(R.id.btnNext);
        btnBack = findViewById(R.id.btnBack);
        btnFinish = findViewById(R.id.btnFinish);
        btnHome = findViewById(R.id.btnHome);
        mProgress = findViewById(R.id.progressBar3);
        step1 = findViewById(R.id.registerStep1);
        step2 = findViewById(R.id.registerStep2);
        textView = findViewById(R.id.textView3);


        shopFirstname = findViewById(R.id.regshopFirstname);
        shopLastname = findViewById(R.id.regshopLastname);
        shopEmail = findViewById(R.id.regshopEmail);
        shopContact = findViewById(R.id.regshopContact);
        shopPassword = findViewById(R.id.regshopPassword);
        shopName = findViewById(R.id.regShopname);
        shopLocation = findViewById(R.id.regShopLocation);
        shopDescription = findViewById(R.id.regShopdescription);
        scroll1 = findViewById(R.id.scroll1);
        scroll2 = findViewById(R.id.scroll2);
        btnNext.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnFinish.setOnClickListener(this);
        btnHome.setOnClickListener(this);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


    }


    @Override
    protected void onStart() {
        super.onStart();

        if (!networkConnection()) {
            Toast.makeText(this, "No Internet Connection. Please check internet connection", Toast.LENGTH_SHORT).show();
        } else {
            FirebaseUser currentUser = mAuth.getCurrentUser();

            if (currentUser != null) {
                mDatabase.getReference("Shop").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Intent intent = new Intent(RegisterShop.this, ShopDashboard.class);
                            startActivity(intent);
                            Toast.makeText(RegisterShop.this, "Shop", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(RegisterShop.this, DashBoard.class);
                            startActivity(intent);
                            Toast.makeText(RegisterShop.this, "Buyer", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
//            Toast.makeText(MainActivity.this, "Login First", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean networkConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnNext) {
            step1.setVisibility(View.GONE);
            step2.setVisibility(View.VISIBLE);
            scroll1.setVisibility(View.GONE);
            scroll2.setVisibility(View.VISIBLE);

        }

        if (view.getId() == R.id.btnBack) {
            step1.setVisibility(View.VISIBLE);
            step2.setVisibility(View.GONE);
            scroll1.setVisibility(View.VISIBLE);
            scroll2.setVisibility(View.GONE);
        }

        if (view.getId() == R.id.btnHome) {
            Intent loginShop = new Intent(this, LoginShop.class);
            startActivity(loginShop);
        }

        if (view.getId() == R.id.btnFinish) {
            final String sFirstname = shopFirstname.getText().toString();
            final String sLastname = shopLastname.getText().toString();
            final String sEmail = shopEmail.getText().toString();
            final String sContact = shopContact.getText().toString();
            final String sPassword = shopPassword.getText().toString();
            final String sName = shopName.getText().toString();
            final String sLocation = shopLocation.getText().toString();
            final String sDescription = shopDescription.getText().toString();

            if (TextUtils.isEmpty(sFirstname)) {
                Toast.makeText(getApplicationContext(), "Enter firstname", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(sLastname)) {
                Toast.makeText(getApplicationContext(), "Enter lastname", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(sEmail)) {
                Toast.makeText(getApplicationContext(), "Enter email", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(sContact)) {
                Toast.makeText(getApplicationContext(), "Enter contactnumber", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(sPassword)) {
                Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(sName)) {
                Toast.makeText(getApplicationContext(), "Enter shopname", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(sLocation)) {
                Toast.makeText(getApplicationContext(), "Enter shop location", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(sDescription)) {
                Toast.makeText(getApplicationContext(), "Enter shop description", Toast.LENGTH_SHORT).show();
                return;
            }
            if (sPassword.length() < 6) {
                Toast.makeText(getApplicationContext(), "Enter at least 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!sEmail.matches(emailPattern)) {
                Toast.makeText(getApplicationContext(), "Invalid email", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!sContact.matches(mobilePattern)) {
                Toast.makeText(getApplicationContext(), "Invalid number", Toast.LENGTH_SHORT).show();
                return;
            }

            mProgress.setVisibility(View.VISIBLE);

            getLocation();

            Toast.makeText(this, String.valueOf(longitude), Toast.LENGTH_SHORT).show();
            final String image = "https://firebasestorage.googleapis.com/v0/b/ezwheels-7396e.appspot.com/o/man.png?alt=media&token=ee7b4f1f-3212-4435-80b7-753ae164ebf2";
            Uri uri  = Uri.parse(image);
            StorageReference storageReference = mStorageRef.child("Images").child(image);
            storageReference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    mStorageRef.child("Images/"+image).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            purl = uri.toString();
                            Toast.makeText(RegisterShop.this, "Wait for a moment", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            mAuth.createUserWithEmailAndPassword(sEmail, sPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Shop shop = new Shop(
                                        sFirstname,
                                        sLastname,
                                        sContact,
                                        sName,
                                        sLocation,
                                        sDescription,
                                        image,
                                        longitude,
                                        latitude,
                                        rating,
                                        sEmail,
                                        FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                        status
                                );
                                FirebaseDatabase.getInstance().getReference("Shop")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(shop).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            mProgress.setVisibility(View.GONE);
                                            Toast.makeText(RegisterShop.this, "Registration Successful" + longitude, Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(RegisterShop.this, ShopDashboard.class);
                                            startActivity(intent);
                                        }
                                        else{
                                            Toast.makeText(RegisterShop.this, "Registration Fail", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    });

        }
    }

    private void getLocation() {

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(RegisterShop.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterShop.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Requires Permission")
                        .setMessage("Ezwheels requires you to give permission to get your shop location")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(RegisterShop.this,
                                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                            }
                        }).create().show();
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(RegisterShop.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location != null){
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
                    }
                }
            });
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getApplication().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == MY_PERMISSIONS_REQUEST_READ_CONTACTS){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }
            else{

            }
        }
    }
}

