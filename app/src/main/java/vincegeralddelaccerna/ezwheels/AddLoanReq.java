package vincegeralddelaccerna.ezwheels;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddLoanReq extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {


    DatabaseReference getCompanies;
    CheckBox comp1, comp2, comp3, comp4, comp5;
    EditText downpayment;
    Spinner months;
    Button cancel, submit;
    DatabaseReference addLoanreq;
    FirebaseAuth mAuth;
    ProgressBar p2;
    private static String company1, company2, company3, company4, company5, month, compa1, compa2, compa3, compa4, compa5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_loan_req);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#fefefe"));
        toolbar.setTitle("Loan Request");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        comp1  = findViewById(R.id.comp1);
        comp2  = findViewById(R.id.comp2);
        comp3  = findViewById(R.id.comp3);
        comp4  = findViewById(R.id.comp4);
        comp5  = findViewById(R.id.comp5);
        downpayment = findViewById(R.id.downpayment);
        months = findViewById(R.id.months);
        cancel = findViewById(R.id.cancel);
        submit = findViewById(R.id.submit);
        p2 = findViewById(R.id.p2);
        cancel.setOnClickListener(this);
        submit.setOnClickListener(this);
        addLoanreq = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.months, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        months.setAdapter(adapter);
        months.setOnItemSelectedListener(this);


        Toast.makeText(this, getIntent().getStringExtra("shopUid"), Toast.LENGTH_SHORT).show();

        getCompanies = FirebaseDatabase.getInstance().getReference("Finance Company");

        getCompanies.orderByChild("shopUid").equalTo(getIntent().getStringExtra("shopUid")).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                compa1 = dataSnapshot.child("loanReq1").getValue().toString();
                compa2 = dataSnapshot.child("loanReq2").getValue().toString();
                compa3 = dataSnapshot.child("loanReq3").getValue().toString();
                compa4 = dataSnapshot.child("loanReq4").getValue().toString();
                compa5 = dataSnapshot.child("loanReq5").getValue().toString();
                Toast.makeText(AddLoanReq.this, compa1, Toast.LENGTH_SHORT).show();

                if(compa1.equals("")){
                    comp1.setVisibility(View.GONE);
                }
                else{
                    comp1.setVisibility(View.VISIBLE);
                    comp1.setText(compa1);
                }
                if(compa2.equals("")){
                    comp2.setVisibility(View.GONE);
                }
                else{
                    comp2.setVisibility(View.VISIBLE);
                    comp2.setText(compa2);
                }
                if(compa3.equals("")){
                    comp3.setVisibility(View.GONE);
                }
                else{
                    comp3.setVisibility(View.VISIBLE);
                    comp3.setText(compa3);
                }
                if(compa4.equals("")){
                    comp4.setVisibility(View.GONE);
                }
                else{
                    comp4.setVisibility(View.VISIBLE);
                    comp4.setText(compa4);
                }
                if(compa5.equals("")){
                    comp5.setVisibility(View.GONE);
                }
                else{
                    comp5.setVisibility(View.VISIBLE);
                    comp5.setText(compa1);
                }
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
                Toast.makeText(AddLoanReq.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        comp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(comp1.isChecked()){

                    Toast.makeText(AddLoanReq.this, compa1, Toast.LENGTH_SHORT).show();
                }
            }
        });

        comp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(comp1.isChecked()){

                    Toast.makeText(AddLoanReq.this, compa1, Toast.LENGTH_SHORT).show();
                }
            }
        });

        comp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(comp2.isChecked()){

                    Toast.makeText(AddLoanReq.this, compa2, Toast.LENGTH_SHORT).show();
                }
            }
        });

        comp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(comp3.isChecked()){

                    Toast.makeText(AddLoanReq.this, compa3, Toast.LENGTH_SHORT).show();
                }
            }
        });

        comp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(comp4.isChecked()){

                    Toast.makeText(AddLoanReq.this, compa4, Toast.LENGTH_SHORT).show();
                }
            }
        });

        comp5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(comp5.isChecked()){

                    Toast.makeText(AddLoanReq.this, compa5, Toast.LENGTH_SHORT).show();
                }
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            month = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.submit){

            p2.setVisibility(View.VISIBLE);
            addLoanreq = FirebaseDatabase.getInstance().getReference("Loan Requests");

            String aid = addLoanreq.push().getKey();
            final String dp = downpayment.getText().toString();
            LoanReq loanReq = new LoanReq(getIntent().getStringExtra("shopUid"), compa1, compa2, compa3, compa4, compa5, mAuth.getCurrentUser().getUid(), "false", "false", aid, dp, month, getIntent().getStringExtra("listid"), "PENDING");
            addLoanreq.child(aid).setValue(loanReq).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(AddLoanReq.this, "Submitted Loan Requests... Wait for the shop to contact you", Toast.LENGTH_SHORT).show();
                        p2.setVisibility(View.GONE);
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
