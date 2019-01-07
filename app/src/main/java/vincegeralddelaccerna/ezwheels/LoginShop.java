package vincegeralddelaccerna.ezwheels;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginShop extends AppCompatActivity implements View.OnClickListener {

    TextView btnShopreg, btnShophome;
    EditText shopUsername, shopPassword;
    Button shopLogin;
    ProgressBar mProgress;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_shop);
        btnShopreg = findViewById(R.id.btnShopreg);
        btnShophome = findViewById(R.id.btnShophome);
        shopUsername = findViewById(R.id.shopUsername);
        shopPassword = findViewById(R.id.shopPassword);
        shopLogin = findViewById(R.id.shopLogin);
        shopLogin.setOnClickListener(this);
        btnShopreg.setOnClickListener(this);
        btnShophome.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(networkConnection()){
            Toast.makeText(this, "No Internet Connection. Please check internet connection", Toast.LENGTH_SHORT).show();
        }
        else{
            if(mAuth.getCurrentUser() != null){
                Intent mainIntent = new Intent(this, DashBoard.class);
                startActivity(mainIntent);
            }
        }

    }

    private boolean networkConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.btnShopreg){
            Intent shopReg = new Intent(this, RegisterShop.class);
            startActivity(shopReg);
        }

        if(view.getId() == R.id.btnShophome){
            Intent shopHome = new Intent(this, MainActivity.class);
            startActivity(shopHome);
        }
        if(view.getId() == R.id.shopLogin){
            final String user = shopUsername.getText().toString();
            final String pass = shopPassword.getText().toString();

            if(TextUtils.isEmpty(user)){
                Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(pass)){
                Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
                return;
            }

            mProgress.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(user, pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                successful();
                            }
                            else{
                                error(); 
                            }
                        }
                    });

        }


    }
    public void successful(){
        Intent successIntent = new Intent(this, ShopDashboard.class);
        startActivity(successIntent);
        Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
    }

    public void error(){
        if(!networkConnection()){
            Toast.makeText(this, "No Internet Connection. Please check internet connection", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }
}
