package vincegeralddelaccerna.ezwheels;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddFinanceComp extends AppCompatActivity implements View.OnClickListener {

    AutoCompleteTextView comp1, comp2, comp3, comp4, comp5, comp6, comp7, comp8, comp9, comp10;
    Button save, cancel;
    Toolbar toolbar;
    DatabaseReference addComp;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    ImageView add1, add2, add3, add4, add5, add6, add7, add8, add9, add10;
    TextView co1, co2, co3, co4, co5, co6, co7, co8, co9, co10;
    LinearLayout r1, r2, r3, r4, r5, r6, r7, r8, r9, r10;
    TextView ti1, ti2, ti3, ti4, ti5, ti6, ti7, ti8, ti9, ti10;
    EditText ei1, ei2, ei3 ,ei4, ei5, ei6, ei7, ei8, ei9, ei10;

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
        comp6 = findViewById(R.id.company6);
        comp7 = findViewById(R.id.company7);
        comp8 = findViewById(R.id.company8);
        comp9 = findViewById(R.id.company9);
        comp10 = findViewById(R.id.company10);
        save = findViewById(R.id.save);
        cancel = findViewById(R.id.cancel);
        progressBar = findViewById(R.id.progress);

        //linear layout

        r1 = findViewById(R.id.r1);
        r2 = findViewById(R.id.r2);
        r3 = findViewById(R.id.r3);
        r4 = findViewById(R.id.r4);
        r5 = findViewById(R.id.r5);
        r6 = findViewById(R.id.r6);
        r7 = findViewById(R.id.r7);
        r8 = findViewById(R.id.r8);
        r9 = findViewById(R.id.r9);
        r10 = findViewById(R.id.r10);

        //edit text


        ei1 = findViewById(R.id.ei1);
        ei2 = findViewById(R.id.ei2);
        ei3 = findViewById(R.id.ei3);
        ei4 = findViewById(R.id.ei4);
        ei5 = findViewById(R.id.ei5);
        ei6 = findViewById(R.id.ei6);
        ei7 = findViewById(R.id.ei7);
        ei8 = findViewById(R.id.ei8);
        ei9 = findViewById(R.id.ei9);
        ei10 = findViewById(R.id.ei10);


        //textview

        co1 = findViewById(R.id.comp1);
        co2 = findViewById(R.id.comp2);
        co3 = findViewById(R.id.comp3);
        co4 = findViewById(R.id.comp4);
        co5 = findViewById(R.id.comp5);
        co6 = findViewById(R.id.comp6);
        co7 = findViewById(R.id.comp7);
        co8 = findViewById(R.id.comp8);
        co9 = findViewById(R.id.comp9);
        co10 = findViewById(R.id.comp10);

        //

        ti1 = findViewById(R.id.ti1);
        ti2 = findViewById(R.id.ti2);
        ti3 = findViewById(R.id.ti3);
        ti4 =  findViewById(R.id.ti4);
        ti5 = findViewById(R.id.ti5);
        ti6 = findViewById(R.id.ti6);
        ti7 = findViewById(R.id.ti7);
        ti8 = findViewById(R.id.ti8);
        ti9 = findViewById(R.id.ti9);
        ti10 = findViewById(R.id.ti10);

        //imageview
        add1 = findViewById(R.id.add1);
        add2 = findViewById(R.id.add2);
        add3 = findViewById(R.id.add3);
        add4 = findViewById(R.id.add4);
        add5 = findViewById(R.id.add5);
        add6 = findViewById(R.id.add6);
        add7 = findViewById(R.id.add7);
        add8 = findViewById(R.id.add8);
        add9 = findViewById(R.id.add9);
        add10 = findViewById(R.id.add10);

        //listeners
        add1.setOnClickListener(this);
        add2.setOnClickListener(this);
        add3.setOnClickListener(this);
        add4.setOnClickListener(this);
        add5.setOnClickListener(this);
        add6.setOnClickListener(this);
        add7.setOnClickListener(this);
        add8.setOnClickListener(this);
        add9.setOnClickListener(this);
        add10.setOnClickListener(this);

        

        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
        addComp = FirebaseDatabase.getInstance().getReference();
        mAuth =FirebaseAuth.getInstance();


        toolbar =  findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#fefefe"));
        toolbar.setTitle("Add Finance Company");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayAdapter<String>  company = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, companies);
        comp1.setAdapter(company);
        comp2.setAdapter(company);
        comp3.setAdapter(company);
        comp4.setAdapter(company);
        comp5.setAdapter(company);
        comp6.setAdapter(company);
        comp7.setAdapter(company);
        comp8.setAdapter(company);
        comp9.setAdapter(company);
        comp10.setAdapter(company);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
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
            final String company6 = comp6.getText().toString();
            final String company7 = comp7.getText().toString();
            final String company8 = comp8.getText().toString();
            final String company9 = comp9.getText().toString();
            final String company10 = comp10.getText().toString();
            final String interest1 = ei1.getText().toString();
            final String interest2 = ei2.getText().toString();
            final String interest3 = ei3.getText().toString();
            final String interest4 = ei4.getText().toString();
            final String interest5 = ei5.getText().toString();
            final String interest6 = ei6.getText().toString();
            final String interest7 = ei7.getText().toString();
            final String interest8 = ei8.getText().toString();
            final String interest9 = ei9.getText().toString();
            final String interest10 = ei10.getText().toString();
            String shopUid  = mAuth.getCurrentUser().getUid();
            addComp = FirebaseDatabase.getInstance().getReference("Finance Company");
            String fid = addComp.push().getKey();
            LoanComp loan = new LoanComp(shopUid, fid, company1, company2, company3, company4, company5, company6, company7, company8, company9, company10, interest1, interest2, interest3
            , interest4, interest5, interest6, interest7, interest8, interest9, interest10);
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

        if(id == R.id.add1){
            //Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
            co2.setVisibility(View.VISIBLE);
            r2.setVisibility(View.VISIBLE);
            ti2.setVisibility(View.VISIBLE);
            ei2.setVisibility(View.VISIBLE);
        }

        if(id == R.id.add2){
            co3.setVisibility(View.VISIBLE);
            r3.setVisibility(View.VISIBLE);
            ti3.setVisibility(View.VISIBLE);
            ei3.setVisibility(View.VISIBLE);
        }

        if(id == R.id.add3){
            co4.setVisibility(View.VISIBLE);
            r4.setVisibility(View.VISIBLE);
            ti4.setVisibility(View.VISIBLE);
            ei4.setVisibility(View.VISIBLE);
        }

        if(id == R.id.add4){
            co5.setVisibility(View.VISIBLE);
            r5.setVisibility(View.VISIBLE);
            ti5.setVisibility(View.VISIBLE);
            ei5.setVisibility(View.VISIBLE);
        }
        if(id == R.id.add5){
            co6.setVisibility(View.VISIBLE);
            r6.setVisibility(View.VISIBLE);
            ti6.setVisibility(View.VISIBLE);
            ei6.setVisibility(View.VISIBLE);
        }
        if(id == R.id.add6){
            co7.setVisibility(View.VISIBLE);
            r7.setVisibility(View.VISIBLE);
            ti7.setVisibility(View.VISIBLE);
            ei7.setVisibility(View.VISIBLE);
        }
        if(id == R.id.add7){
            co8.setVisibility(View.VISIBLE);
            r8.setVisibility(View.VISIBLE);
            ti8.setVisibility(View.VISIBLE);
            ei8.setVisibility(View.VISIBLE);
        }
        if(id == R.id.add8){
            co9.setVisibility(View.VISIBLE);
            r9.setVisibility(View.VISIBLE);
            ti9.setVisibility(View.VISIBLE);
            ei9.setVisibility(View.VISIBLE);
        }
        if(id == R.id.add9){
            co10.setVisibility(View.VISIBLE);
            r10.setVisibility(View.VISIBLE);
            ti10.setVisibility(View.VISIBLE);
            ei10.setVisibility(View.VISIBLE);
        }
    }
}
