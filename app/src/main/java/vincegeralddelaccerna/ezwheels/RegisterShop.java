package vincegeralddelaccerna.ezwheels;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterShop extends AppCompatActivity implements View.OnClickListener{

    Button btnNext, btnBack, btnFinish, btnHome;
    LinearLayout step1, step2;
    EditText shopFirstname, shopLastname, shopUsername, shopEmail, shopContact, shopPassword, shopName, shopLocation, shopDescription;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String mobilePattern = "^(09|\\+639)\\d{9}$";
    ProgressBar mProgress;

    FirebaseAuth mAuth;

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






        shopFirstname = findViewById(R.id.regshopFirstname);
        shopLastname = findViewById(R.id.regshopLastname);
        shopEmail = findViewById(R.id.regshopEmail);
        shopContact = findViewById(R.id.regshopContact);
        shopPassword = findViewById(R.id.regshopPassword);
        shopName = findViewById(R.id.regShopname);
        shopLocation = findViewById(R.id.regShopLocation);
        shopDescription = findViewById(R.id.regShopdescription);
        btnNext.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnFinish.setOnClickListener(this);
        btnHome.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() != null){
            Intent mainIntent = new Intent(this, DashBoard.class);
            startActivity(mainIntent);
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnNext) {
            step1.setVisibility(View.GONE);
            step2.setVisibility(View.VISIBLE);
        }

        if(view.getId() == R.id.btnBack){
            step1.setVisibility(View.VISIBLE);
            step2.setVisibility(View.GONE);
        }

        if(view.getId() == R.id.btnHome){
            Intent loginShop = new Intent(this, LoginShop.class);
            startActivity(loginShop);
        }

        if(view.getId() == R.id.btnFinish){
            final String sFirstname = shopFirstname.getText().toString();
            final String sLastname = shopLastname.getText().toString();
            final String sEmail = shopEmail.getText().toString();
            final String sContact = shopContact.getText().toString();
            final String sPassword = shopPassword.getText().toString();
            final String sName = shopName.getText().toString();
            final String sLocation = shopLocation.getText().toString();
            final String sDescription = shopDescription.getText().toString();

            if(TextUtils.isEmpty(sFirstname)){
                Toast.makeText(getApplicationContext(), "Enter firstname", Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(sLastname)){
                Toast.makeText(getApplicationContext(), "Enter lastname", Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(sEmail)){
                Toast.makeText(getApplicationContext(), "Enter email", Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(sContact)){
                Toast.makeText(getApplicationContext(), "Enter contactnumber", Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(sPassword)){
                Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(sName)){
                Toast.makeText(getApplicationContext(), "Enter shopname", Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(sLocation)){
                Toast.makeText(getApplicationContext(), "Enter shop location", Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(sDescription)){
                Toast.makeText(getApplicationContext(), "Enter shop description", Toast.LENGTH_SHORT).show();
                return;
            }
            if(sPassword.length() < 6){
                Toast.makeText(getApplicationContext(), "Enter at least 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!sEmail.matches(emailPattern)){
                Toast.makeText(getApplicationContext(), "Invalid email", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!sContact.matches(mobilePattern)){
                Toast.makeText(getApplicationContext(), "Invalid number", Toast.LENGTH_SHORT).show();
                return;
            }

            mProgress.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(sEmail, sPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            mProgress.setVisibility(View.GONE);
                            if(task.isSuccessful()){
                                Shop shop = new Shop(
                                        sFirstname,
                                        sLastname,
                                        sContact,
                                        sName,
                                        sLocation,
                                        sDescription
                                );
                                FirebaseDatabase.getInstance().getReference("Shop")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(shop).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(RegisterShop.this, "Registration Successful", Toast.LENGTH_SHORT).show();
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
}

