package vincegeralddelaccerna.ezwheels;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddFinanceComp extends AppCompatActivity implements View.OnClickListener {

    AutoCompleteTextView comp1, comp2, comp3, comp4, comp5;
    Button save, cancel;
    Toolbar toolbar;
    DatabaseReference addComp;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    private static final String [] companies = new String[]{
            "BDO","Security Bank","BPI","Metrobank","Eastwest", "RCBC", "PNB", "ChinaBank"
    };
//    ArrayAdapter<String> brandAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, brands);
//        brandText.setAdapter(brandAdapter);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_finance_comp);

        comp1 = findViewById(R.id.company1);
        comp2 = findViewById(R.id.company2);
        comp3 = findViewById(R.id.company3);
        comp4 = findViewById(R.id.company4);
        comp5 = findViewById(R.id.company5);
        save = findViewById(R.id.save);
        cancel = findViewById(R.id.cancel);
        progressBar = findViewById(R.id.progress);

        

        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
        addComp = FirebaseDatabase.getInstance().getReference();
        mAuth =FirebaseAuth.getInstance();


        toolbar =  findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#fefefe"));
        toolbar.setTitle("ADD FINANCE COMPANY");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayAdapter<String>  company = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, companies);
        comp1.setAdapter(company);
        comp2.setAdapter(company);
        comp3.setAdapter(company);
        comp4.setAdapter(company);
        comp5.setAdapter(company);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if(id == R.id.save){
            progressBar.setVisibility(View.VISIBLE);
            final String company1 = comp1.getText().toString();
            final String company2 = comp2.getText().toString();
            final String company3 = comp3.getText().toString();
            final String company4 = comp4.getText().toString();
            final String company5 = comp5.getText().toString();
            String shopUid  = mAuth.getCurrentUser().getUid();
            addComp = FirebaseDatabase.getInstance().getReference("Finance Company");
            String fid = addComp.push().getKey();
            LoanComp loan = new LoanComp(shopUid, fid, company1, company2, company3, company4, company5);
            addComp.child(fid).setValue(loan).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        progressBar.setVisibility(View.VISIBLE);
                        Toast.makeText(AddFinanceComp.this, "Added Finance Companies", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });
        }

        if(id == R.id.cancel){
            finish();
        }
    }
}
