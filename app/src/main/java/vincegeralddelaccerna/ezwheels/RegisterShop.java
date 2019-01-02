package vincegeralddelaccerna.ezwheels;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterShop extends AppCompatActivity implements View.OnClickListener{

    Button btnNext, btnBack, btnFinish, btnHome;
    LinearLayout step1, step2;
    EditText shopFirstname, shopLastname, shopUsername, shopEmail, shopContact, shopPassword, shopName, shopLocation, shopDescription;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_shop);
        btnNext = findViewById(R.id.btnNext);
        btnBack = findViewById(R.id.btnBack);
        btnFinish = findViewById(R.id.btnFinish);
        btnHome = findViewById(R.id.btnHome);
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

            if()
        }
    }
}
