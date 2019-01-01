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
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView register, shopView;
    Button cancelRegister, loginButton;
    EditText loginUsername, loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.register  = this.findViewById(R.id.textView);
        this.loginButton = this.findViewById(R.id.button);
        shopView = findViewById(R.id.shopView);
        this.loginUsername = this.findViewById(R.id.editText);
        this.loginPassword = this.findViewById(R.id.editText2);
        shopView.setOnClickListener(this);
        //this.register.setOnClickListener(this);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        if(!networkConnection()){
//            Toast.makeText(this, "No Internet Connection. Please check internet connection", Toast.LENGTH_SHORT).show();
//        }
//        else{
//            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//            if(currentUser != null){
//                Intent dashhboardintent = new Intent(this, DashBoard.class);
//                startActivity(dashhboardintent);
//            }
//        }
//    }


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



    }
}
