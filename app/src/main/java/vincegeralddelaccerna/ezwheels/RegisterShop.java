package vincegeralddelaccerna.ezwheels;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class RegisterShop extends AppCompatActivity implements View.OnClickListener{

    Button btnNext, btnBack, btnFinish;
    LinearLayout step1, step2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_shop);
        btnNext = findViewById(R.id.btnNext);
        btnBack = findViewById(R.id.btnBack);
        btnFinish = findViewById(R.id.btnFinish);
        step1 = findViewById(R.id.registerStep1);
        step2 = findViewById(R.id.registerStep2);
        btnNext.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnFinish.setOnClickListener(this);
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
    }
}
