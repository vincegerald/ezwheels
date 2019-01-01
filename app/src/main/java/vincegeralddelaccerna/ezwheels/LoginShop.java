package vincegeralddelaccerna.ezwheels;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginShop extends AppCompatActivity implements View.OnClickListener {

    Button btnShopreg, btnShophome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_shop);
        btnShopreg = findViewById(R.id.btnShopreg);
        btnShophome = findViewById(R.id.btnShophome);
        btnShopreg.setOnClickListener(this);
        btnShophome.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.btnShopreg){
            Intent shopReg = new Intent(this, RegisterShop.class);
            startActivity(shopReg);
        }

        if(view.getId() == R.id.btnShophome){
            Intent shopHome = new Intent(this, LoginShop.class);
            startActivity(shopHome);

        }

    }
}
