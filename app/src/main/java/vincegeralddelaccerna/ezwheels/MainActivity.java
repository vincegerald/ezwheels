package vincegeralddelaccerna.ezwheels;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView register, shopView;
    Button cancelRegister, loginButton;
    EditText loginUsername, loginPassword;
    ProgressBar mProgress;

    private FirebaseAuth mAuth;
    private DatabaseReference getBuyer;
    private FirebaseDatabase mDatabase, userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.register  = this.findViewById(R.id.textView);
        this.loginButton = this.findViewById(R.id.button);
        shopView = findViewById(R.id.shopView);
        this.loginUsername = this.findViewById(R.id.editText);
        this.loginPassword = this.findViewById(R.id.editText2);
        mProgress = findViewById(R.id.progressBar2);
        shopView.setOnClickListener(this);
        register.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        //this.register.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        userDatabase = FirebaseDatabase.getInstance();
        getBuyer = FirebaseDatabase.getInstance().getReference("Buyer");
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
                            Intent intent = new Intent(MainActivity.this, ShopDashboard.class);
                            startActivity(intent);
                            Toast.makeText(MainActivity.this, "Welcome Shop Trader!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            userDatabase.getReference("Buyers").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        Intent intent = new Intent(MainActivity.this, DashBoard.class);
                                        startActivity(intent);
                                        Toast.makeText(MainActivity.this, "Welcome Buyer!", Toast.LENGTH_SHORT).show();
                                    }
                                    else{

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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
    public void onClick(final View view) {
        int id = view.getId();

        switch(id){
            case R.id.textView:
                Intent intent = new Intent(this, Register.class);
                startActivity(intent);
                break;
            case R.id.shopView:
                Intent shopReg = new Intent(this, LoginShop.class);
                startActivity(shopReg);
                break;

            case R.id.button:

                final String logUsername = this.loginUsername.getText().toString().trim();
                final String logPassword = this.loginPassword.getText().toString().trim();

                if(TextUtils.isEmpty(logUsername)){
                    Snackbar.make(view, "Enter email...", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
                    return;
                }

                if(TextUtils.isEmpty(logPassword)){
                    Snackbar.make(view, "Enter password...", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
                    return;
                }

                mProgress.setVisibility(View.VISIBLE);
                loginUsername.setVisibility(View.INVISIBLE);
                loginPassword.setVisibility(View.INVISIBLE);
                mAuth.signInWithEmailAndPassword(logUsername, logPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    successful();
                                }
                                else{
                                    mProgress.setVisibility(View.GONE);
                                    loginUsername.setVisibility(View.VISIBLE);
                                    loginPassword.setVisibility(View.VISIBLE);
                                    loginUsername.setText("");
                                    loginPassword.setText("");
                                    if(!networkConnection()){
                                        Snackbar.make(view, "No Internet Connection. Please check internet connection...", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                    }
                                    else{
                                        Snackbar.make(view, "Invalid credentials...", Snackbar.LENGTH_LONG)
                                         .setAction("Action", null).show();
                                    }
                                }
                            }
                        });
        }
    }


    public void successful(){
        Intent successIntent = new Intent(this, DashBoard.class);
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
