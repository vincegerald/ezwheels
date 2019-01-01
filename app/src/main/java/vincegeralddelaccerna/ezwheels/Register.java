package vincegeralddelaccerna.ezwheels;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import org.w3c.dom.Text;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    EditText name;
    EditText username;
    EditText email;
    EditText contactnumber;
    EditText password;
    Button btnRegister, btnRegisterCancel;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String mobilePattern = "^(09|\\+639)\\d{9}$";
    ProgressBar mProgress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.registerName);
        username = findViewById(R.id.registerUsername);
        email = findViewById(R.id.registerEmail);
        contactnumber = findViewById(R.id.registerContact);
        password = findViewById(R.id.registerPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegisterCancel = findViewById(R.id.btnRegisterCancel);
        mProgress = findViewById(R.id.progressBar)
        btnRegister.setOnClickListener(this);
        btnRegisterCancel.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            Intent mainIntent = new Intent(Register.this, DashBoard.class);
            startActivity(mainIntent);
        }
    }


    @Override
    public void onClick(View view) {
        final String finalName = name.getText().toString();
        final String finalUsername = username.getText().toString();
        final String finalEmail = email.getText().toString();
        final String finalContact = contactnumber.getText().toString();
        final String finalPassword = password.getText().toString();

        if(view.getId() == R.id.btnRegister){
            if(TextUtils.isEmpty(finalName)){
                Toast.makeText(getApplicationContext(), "Enter name", Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(finalUsername)){
                Toast.makeText(getApplicationContext(), "Enter username", Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(finalEmail)){
                Toast.makeText(getApplicationContext(), "Enter email", Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(finalContact)){
                Toast.makeText(getApplicationContext(), "Enter contact number", Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(finalPassword)){
                Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_SHORT).show();
                return;
            }
            if(finalPassword.length() < 6){
                Toast.makeText(getApplicationContext(), "Password too short... Minimum of 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!finalContact.matches(mobilePattern)){
                Toast.makeText(getApplicationContext(), "Please input a valid number", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!finalEmail.matches(emailPattern)){
                Toast.makeText(getApplicationContext(), "Please input a valid email", Toast.LENGTH_SHORT).show();
                return;
            }

            mDialog.setMessage("Registering User... Wait for a moment");
            mDialog.show();
            mAuth = FirebaseAuth.getInstance();
            mAuth.createUserWithEmailAndPassword(finalEmail, finalPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                //Toast.makeText(Register.this, "Success", Toast.LENGTH_SHORT).show();
                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                String uid = currentUser.getUid();

                                FirebaseDatabase database = FirebaseDatabase.getInstance();

                                DatabaseReference buyersRef = database.getReference("buyers");
                                mDialog.dismiss();


                            }
                        }
                    });

        }
        else{
            Intent mainIntent = new Intent(Register.this, MainActivity.class);
            startActivity(mainIntent);
        }




    }

}
