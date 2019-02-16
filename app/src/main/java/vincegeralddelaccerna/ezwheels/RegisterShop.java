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
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

public class RegisterShop extends AppCompatActivity implements View.OnClickListener, LocationListener {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    Button btnNext, btnBack, btnFinish, btnHome;
    private static double lon, lat;
    private static String purl;
    private float rating = 5.0f;
    ;
    LinearLayout step1, step2;
    EditText shopFirstname, shopLastname, shopUsername, shopEmail, shopContact, shopPassword, shopName, shopLocation, shopDescription;
    String emailPattern = "/^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$/;";
    String mobilePattern = "^(09|\\+639)\\d{9}$";
    ProgressBar mProgress;
    LocationManager locationManager;
    FirebaseAuth mAuth;
    ScrollView scroll1, scroll2;
    TextView textView;
    ImageView permit;
    ProgressBar progress;
    private FirebaseDatabase mDatabase;
    private StorageReference mStorageRef;
    private static double longitude , latitude;
    private String status = "NOT ACTIVATED";
    private Uri uriImage;
    private static String imagePath = "";
    private static final int REQUEST_LOCATION = 1;
    private static double latti, longi;

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
        progress = findViewById(R.id.progressPermit);
        permit = findViewById(R.id.permit);
        permit.setOnClickListener(this);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();








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
                        Toast.makeText(RegisterShop.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }
    }

    private boolean networkConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onClick(final View view) {

        if(view.getId() == R.id.permit){

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 1);
        }

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

            //Toast.makeText(this, "finished", Toast.LENGTH_SHORT).show();

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);

            onLocationChanged(location);


            final String sFirstname = shopFirstname.getText().toString().trim();
            final String sLastname = shopLastname.getText().toString().trim();
            final String sEmail = shopEmail.getText().toString().trim();
            final String sContact = shopContact.getText().toString().trim();
            final String sPassword = shopPassword.getText().toString().trim();
            final String sName = shopName.getText().toString().trim();
            final String sLocation = shopLocation.getText().toString().trim();
            final String sDescription = shopDescription.getText().toString().trim();


            if (TextUtils.isEmpty(sFirstname)) {
                Snackbar.make(view, "Enter firstname...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;
            }
            if (TextUtils.isEmpty(sLastname)) {
                Snackbar.make(view, "Enter lastname...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;
            }
            if (TextUtils.isEmpty(sEmail)) {
                Snackbar.make(view, "Enter email...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;
            }
            if (TextUtils.isEmpty(sContact)) {
                Snackbar.make(view, "Enter contactnumber...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;
            }
            if (TextUtils.isEmpty(sPassword)) {
                Snackbar.make(view, "Enter password...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;
            }
            if (TextUtils.isEmpty(sName)) {
                Snackbar.make(view, "Enter shopname...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;
            }
            if (TextUtils.isEmpty(sLocation)) {
                Snackbar.make(view, "Enter location...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;
            }
            if (TextUtils.isEmpty(sDescription)) {
                Toast.makeText(getApplicationContext(), "Enter shop description", Toast.LENGTH_SHORT).show();
                return;
            }
            if (sPassword.length() < 6) {
                Snackbar.make(view, "Enter at least 6 characters...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;
            }

            if (!sContact.matches(mobilePattern)) {
                Snackbar.make(view, "Invalid number...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;
            }

            if(imagePath.equals("")){
                Snackbar.make(view, "Enter business permit...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;
            }

            mProgress.setVisibility(View.VISIBLE);



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
                                        imagePath,
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
                                            Toast.makeText(RegisterShop.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(RegisterShop.this, ShopDashboard.class);
                                            startActivity(intent);
                                        }
                                        else{
                                            Snackbar.make(view, "Registration Unsuccessful...", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                        }
                                    }
                                });
                            }
                            else{
                                Snackbar.make(view, "Registration Unsuccessful...", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        }
                    });

        }
    }



    private void showGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please turn on your gps connection")
                .setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK){
            uriImage = data.getData();
            final String path4 = System.currentTimeMillis() + "." + getFileExtension(uriImage);
            progress.setVisibility(View.VISIBLE);
            StorageReference storageReference = mStorageRef.child("Images").child(path4);
            storageReference.putFile(uriImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    mStorageRef.child("Images/"+path4).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imagePath = uri.toString();
                            progress.setVisibility(View.GONE);
                            Picasso.get().load(uriImage).fit().centerCrop().into(permit);
                            Toast.makeText(RegisterShop.this, "Added Business Permit", Toast.LENGTH_SHORT).show();
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


    @Override
    public void onLocationChanged(Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}

