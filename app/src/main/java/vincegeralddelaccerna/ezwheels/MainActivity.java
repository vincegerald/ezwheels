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


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView register, shopView;
    Button cancelRegister, loginButton;
    EditText loginUsername, loginPassword;
    ProgressBar mProgress;

    private FirebaseAuth mAuth;

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
        //this.register.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(!networkConnection()){
            Toast.makeText(this, "No Internet Connection. Please check internet connection", Toast.LENGTH_SHORT).show();
        }
        else{
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if(currentUser != null){
                Intent dashhboardintent = new Intent(this, DashBoard.class);
                startActivity(dashhboardintent);
            }
        }
    }

    private boolean networkConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch(id){
            case R.id.textView:
                Intent intent = new Intent(this, Register.class);
                startActivity(intent);
                break;
            case R.id.shopView:
                Intent shopReg = new Intent(this, LoginShop.class);
                startActivity(shopReg);
        }
    }

    public void login(){
        String logUsername = this.loginUsername.getText().toString();
        String logPassword = this.loginPassword.getText().toString();

        if(TextUtils.isEmpty(logUsername)){
            Toast.makeText(this, "Missing username", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(logPassword)){
            Toast.makeText(this, "Missing password", Toast.LENGTH_SHORT).show();
            return;
        }

        mProgress.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(logUsername, logPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser currentUser =mAuth.getCurrentUser();
                            successful();
                        }
                        else{

                        }
                    }
                });

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
