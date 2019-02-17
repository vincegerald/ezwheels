package vincegeralddelaccerna.ezwheels;



import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Register extends AppCompatActivity implements View.OnClickListener {

    LocationManager locationManager;
    private FirebaseDatabase mDatabase, userDatabase;

    EditText name;
    EditText username;
    EditText email;
    EditText contactnumber;
    EditText password;
    EditText firstname;
    EditText lastname;
    Button btnRegister, btnRegisterCancel;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String mobilePattern = "^(09|\\+639)\\d{9}$";
    ProgressBar mProgress;

    private FirebaseAuth mAuth;
    private static String purl;
//    private GoogleApiClient googleApiClient;
//    private FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstname = findViewById(R.id.registerFirstname);
        lastname = findViewById(R.id.registerLastname);
        //username = findViewById(R.id.registerUsername);
        email = findViewById(R.id.registerEmail);
        contactnumber = findViewById(R.id.registerContact);
        password = findViewById(R.id.registerPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegisterCancel = findViewById(R.id.btnRegisterCancel);
        mProgress = findViewById(R.id.progressBar);
        btnRegister.setOnClickListener(this);
        btnRegisterCancel.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();



    }



    private boolean networkConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(!networkConnection()){
            Toast.makeText(this, "No Internet Connection. Please check internet connection", Toast.LENGTH_SHORT).show();
        }
        else{

            final FirebaseUser currentUser = mAuth.getCurrentUser();

            if(currentUser != null){
                mDatabase.getReference("Shop").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            Intent intent = new Intent(Register.this, ShopDashboard.class);
                            startActivity(intent);
                            Toast.makeText(Register.this, "Welcome Shop Trader!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            userDatabase.getReference("Buyers").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        Intent intent = new Intent(Register.this, DashBoard.class);
                                        startActivity(intent);
                                        Toast.makeText(Register.this, "Welcome Buyer!", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(Register.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(Register.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
//            Toast.makeText(MainActivity.this, "Login First", Toast.LENGTH_SHORT).show();
        }
    }




    @Override
    public void onClick(final View view) {

        if(view.getId() == R.id.btnRegisterCancel){
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
        }

        if(view.getId() == R.id.btnRegister){

            final String finalFirstname = firstname.getText().toString().trim();
            final String finalLastname = lastname.getText().toString().trim();
            final String finalEmail = email.getText().toString().trim();
            final String finalContact = contactnumber.getText().toString().trim();
            final String finalPassword = password.getText().toString().trim();

                if(TextUtils.isEmpty(finalFirstname)){
                    Snackbar.make(view, "Enter firstname...", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
                    return;
                }

                if(TextUtils.isEmpty(finalLastname)){
                    Snackbar.make(view, "Enter lastname...", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
                    return;
                }

                if(TextUtils.isEmpty(finalEmail)){
                    Snackbar.make(view, "Enter email...", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
                    return;
                }
                if(TextUtils.isEmpty(finalContact)){
                    Snackbar.make(view, "Enter contact number...", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
                    return;
                }
                if(TextUtils.isEmpty(finalPassword)){
                    Snackbar.make(view, "Enter password...", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
                    return;
                }
                if(finalPassword.length() < 6){
                    Snackbar.make(view, "Password too short. Minimum of 6 characters...", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
                    return;
                }
                if(!finalContact.matches(mobilePattern)){
                    Snackbar.make(view, "Please input a valid number...", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
                    return;
                }
                if(!finalEmail.matches(emailPattern)){
                    Snackbar.make(view, "Please input a valid email...", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
                    return;
                }
                final String image = "https://firebasestorage.googleapis.com/v0/b/ezwheels1-a8d36.appspot.com/o/man.png?alt=media&token=5f8996b7-e995-48b2-9026-b55554f2f701";
                firstname.setVisibility(View.INVISIBLE);
                lastname.setVisibility(View.INVISIBLE);
                //username.setVisibility(View.INVISIBLE);
                email.setVisibility(View.INVISIBLE);
                contactnumber.setVisibility(View.INVISIBLE);
                password.setVisibility(View.INVISIBLE);
                mProgress.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(finalEmail, finalPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                mProgress.setVisibility(View.GONE);
                                firstname.setVisibility(View.VISIBLE);
                                lastname.setVisibility(View.VISIBLE);
                                //username.setVisibility(View.VISIBLE);
                                email.setVisibility(View.VISIBLE);
                                contactnumber.setVisibility(View.VISIBLE);
                                password.setVisibility(View.VISIBLE);
                                if(task.isSuccessful()){
                                    Buyer buyer = new Buyer(
                                            finalFirstname,
                                            finalLastname,
                                            finalContact,
                                            image,
                                            finalEmail
                                    );
                                    FirebaseDatabase.getInstance().getReference("Buyers")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(buyer).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(Register.this, "Succesful Registration", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(Register.this, DashBoard.class);
                                                startActivity(intent);
                                            }
                                            else{
                                                Snackbar.make(view, "Registration Unsucessful...", Snackbar.LENGTH_LONG)
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



}


