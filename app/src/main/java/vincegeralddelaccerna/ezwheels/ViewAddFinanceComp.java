package vincegeralddelaccerna.ezwheels;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ViewAddFinanceComp extends AppCompatActivity implements View.OnClickListener {

    AutoCompleteTextView comp1, comp2, comp3, comp4, comp5;
    Button save, cancel;
    Toolbar toolbar;
    DatabaseReference addComp;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    DatabaseReference getFinanceComp;
    private static String loanReq1, loanReq2, loanReq3, loanReq4, loanReq5, loanCompId;

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
        toolbar.setTitle("EDIT FINANCE COMPANY");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayAdapter<String>  company = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, companies);
        comp1.setAdapter(company);
        comp2.setAdapter(company);
        comp3.setAdapter(company);
        comp4.setAdapter(company);
        comp5.setAdapter(company);


        getFinanceComp = FirebaseDatabase.getInstance().getReference("Finance Company");

        getFinanceComp.orderByChild("shopUid").equalTo(mAuth.getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                loanReq1 = dataSnapshot.child("loanReq1").getValue().toString();
                loanReq2 = dataSnapshot.child("loanReq2").getValue().toString();
                loanReq3 = dataSnapshot.child("loanReq3").getValue().toString();
                loanReq4 = dataSnapshot.child("loanReq4").getValue().toString();
                loanReq5 = dataSnapshot.child("loanReq5").getValue().toString();
                loanCompId = dataSnapshot.child("loanCompId").getValue().toString();
                comp1.setText(loanReq1);
                comp2.setText(loanReq2);
                comp3.setText(loanReq3);
                comp4.setText(loanReq4);
                comp5.setText(loanReq5);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
            String shopUid  = mAuth.getCurrentUser().getUid();
            addComp = FirebaseDatabase.getInstance().getReference("Finance Company");
            LoanComp loan = new LoanComp(shopUid, loanCompId, company1, company2, company3, company4, company5);
            addComp.child(loanCompId).setValue(loan).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        progressBar.setVisibility(View.VISIBLE);
                        Toast.makeText(ViewAddFinanceComp.this, "Updated Finance Companies", Toast.LENGTH_SHORT).show();
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
