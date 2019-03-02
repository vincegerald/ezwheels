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
import android.widget.LinearLayout;
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
    CheckBox comp1, comp2, comp3, comp4, comp5, comp6, comp7, comp8, comp9, comp10;
    EditText downpayment;
    Spinner months;
    Button cancel, submit;
    DatabaseReference addLoanreq;
    FirebaseAuth mAuth;
    ProgressBar p2;
    private static String company1="", company2="", company3="", company4="", company5="",
            company6="", company7="", company8="", company9="", company10="",
            compa6, compa7,compa8, compa9, compa10,
            i1, i2, i3, i4, i5, i6, i7, i8, i9, i10,month, compa1, compa2, compa3, compa4, compa5;
    LinearLayout l1, l2, l3, l4, l5, l6, l7, l8, l9, l10;
    LinearLayout h1, h2, h3, h4, h5, h6, h7, h8, h9, h10;
    ImageView view1, view2, view3, view4, view5, view6, view7, view8, view9, view10;
    ImageView hide1, hide2, hide3, hide4, hide5, hide6, hide7, hide8, hide9, hide10;
    TextView interest1, interest2, interest3, interest4, interest5, interest6, interest7, interest8, interest9, interest10;



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

        //text view for interest

        interest1 = findViewById(R.id.interest1);
        interest2 = findViewById(R.id.interest2);
        interest3 = findViewById(R.id.interest3);
        interest4 = findViewById(R.id.interest4);
        interest5 = findViewById(R.id.interest5);
        interest6 = findViewById(R.id.interest6);
        interest7 = findViewById(R.id.interest7);
        interest8 = findViewById(R.id.interest8);
        interest9 = findViewById(R.id.interest9);
        interest10 = findViewById(R.id.interest10);

        //end of text view for interest

        //linear layout for checkbox

        l1 = findViewById(R.id.l1);
        l2 = findViewById(R.id.l2);
        l3 = findViewById(R.id.l3);
        l4 = findViewById(R.id.l4);
        l5 = findViewById(R.id.l5);
        l6 = findViewById(R.id.l6);
        l7 = findViewById(R.id.l7);
        l8 = findViewById(R.id.l8);
        l9 = findViewById(R.id.l9);
        l10 = findViewById(R.id.l10);

        //linear layout for interest

        h1 = findViewById(R.id.h1);
        h2 = findViewById(R.id.h2);
        h3 = findViewById(R.id.h3);
        h4 = findViewById(R.id.h4);
        h5 = findViewById(R.id.h5);
        h6 = findViewById(R.id.h6);
        h7 = findViewById(R.id.h7);
        h8 = findViewById(R.id.h8);
        h9 = findViewById(R.id.h9);
        h10 = findViewById(R.id.h10);

        //button to show interest and listeners

        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        view3 = findViewById(R.id.view3);
        view4 = findViewById(R.id.view4);
        view5 = findViewById(R.id.view5);
        view6 = findViewById(R.id.view6);
        view7 = findViewById(R.id.view7);
        view8 = findViewById(R.id.view8);
        view9 = findViewById(R.id.view9);
        view10 = findViewById(R.id.view10);

        view1.setOnClickListener(this);
        view2.setOnClickListener(this);
        view3.setOnClickListener(this);
        view4.setOnClickListener(this);
        view5.setOnClickListener(this);
        view6.setOnClickListener(this);
        view7.setOnClickListener(this);
        view8.setOnClickListener(this);
        view9.setOnClickListener(this);
        view10.setOnClickListener(this);

        //end of button to show interest and listeners

        //button to hide interest and listeners

        hide1 = findViewById(R.id.hide1);
        hide2 = findViewById(R.id.hide2);
        hide3 = findViewById(R.id.hide3);
        hide4 = findViewById(R.id.hide4);
        hide5 = findViewById(R.id.hide5);
        hide6 = findViewById(R.id.hide6);
        hide7 = findViewById(R.id.hide7);
        hide8 = findViewById(R.id.hide8);
        hide9 = findViewById(R.id.hide9);
        hide10 = findViewById(R.id.hide10);

        hide1.setOnClickListener(this);
        hide2.setOnClickListener(this);
        hide3.setOnClickListener(this);
        hide4.setOnClickListener(this);
        hide5.setOnClickListener(this);
        hide6.setOnClickListener(this);
        hide7.setOnClickListener(this);
        hide8.setOnClickListener(this);
        hide9.setOnClickListener(this);
        hide10.setOnClickListener(this);

        //end of button to hide interest and listeners



        comp1  = findViewById(R.id.comp1);
        comp2  = findViewById(R.id.comp2);
        comp3  = findViewById(R.id.comp3);
        comp4  = findViewById(R.id.comp4);
        comp5  = findViewById(R.id.comp5);
        comp6  = findViewById(R.id.comp6);
        comp7  = findViewById(R.id.comp7);
        comp8  = findViewById(R.id.comp8);
        comp9  = findViewById(R.id.comp9);
        comp10  = findViewById(R.id.comp10);
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


        //Toast.makeText(this, getIntent().getStringExtra("shopUid"), Toast.LENGTH_SHORT).show();

        getCompanies = FirebaseDatabase.getInstance().getReference("Finance Company");

        getCompanies.orderByChild("shopUid").equalTo(getIntent().getStringExtra("shopUid")).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                compa1 = dataSnapshot.child("loanReq1").getValue().toString();
                compa2 = dataSnapshot.child("loanReq2").getValue().toString();
                compa3 = dataSnapshot.child("loanReq3").getValue().toString();
                compa4 = dataSnapshot.child("loanReq4").getValue().toString();
                compa5 = dataSnapshot.child("loanReq5").getValue().toString();
                compa6 = dataSnapshot.child("loanReq6").getValue().toString();
                compa7 = dataSnapshot.child("loanReq7").getValue().toString();
                compa8 = dataSnapshot.child("loanReq8").getValue().toString();
                compa9 = dataSnapshot.child("loanReq9").getValue().toString();
                compa10 = dataSnapshot.child("loanReq10").getValue().toString();

                i1 = dataSnapshot.child("loanInt1").getValue().toString();
                i2 = dataSnapshot.child("loanInt2").getValue().toString();
                i3 = dataSnapshot.child("loanInt2").getValue().toString();
                i4 = dataSnapshot.child("loanInt4").getValue().toString();
                i5 = dataSnapshot.child("loanInt5").getValue().toString();
                i6 = dataSnapshot.child("loanInt6").getValue().toString();
                i7 = dataSnapshot.child("loanInt7").getValue().toString();
                i8 = dataSnapshot.child("loanInt8").getValue().toString();
                i9 = dataSnapshot.child("loanInt9").getValue().toString();
                i10 = dataSnapshot.child("loanInt10").getValue().toString();

                //Toast.makeText(AddLoanReq.this, compa1, Toast.LENGTH_SHORT).show();

                if(compa1.equals("")){
                    l1.setVisibility(View.GONE);
                }
                else{
                    l1.setVisibility(View.VISIBLE);
                    comp1.setText(compa1);
                    interest1.setText("Interest rate: " + i1);
                }
                if(compa2.equals("")){
                    l2.setVisibility(View.GONE);
                }
                else{
                    l2.setVisibility(View.VISIBLE);
                    comp2.setText(compa2);
                    interest2.setText("Interest rate: " + i2);
                }
                if(compa3.equals("")){
                    l3.setVisibility(View.GONE);
                }
                else{
                    l3.setVisibility(View.VISIBLE);
                    comp3.setText(compa3);
                    interest3.setText("Interest rate: " + i3);
                }
                if(compa4.equals("")){
                    l4.setVisibility(View.GONE);
                }
                else{
                    l4.setVisibility(View.VISIBLE);
                    comp4.setText(compa4);
                    interest4.setText("Interest rate: " + i4);
                }
                if(compa5.equals("")){
                    l5.setVisibility(View.GONE);
                }
                else{
                    l5.setVisibility(View.VISIBLE);
                    comp5.setText(compa5);
                    interest5.setText("Interest rate: " + i5);
                }
                if(compa6.equals("")){
                    l6.setVisibility(View.GONE);
                }
                else{
                    l6.setVisibility(View.VISIBLE);
                    comp6.setText(compa6);
                    interest6.setText("Interest rate: " + i6);
                }
                if(compa7.equals("")){
                    l7.setVisibility(View.GONE);
                }
                else{
                    l7.setVisibility(View.VISIBLE);
                    comp7.setText(compa7);
                    interest7.setText("Interest rate: " + i7);
                }
                if(compa8.equals("")){
                    l8.setVisibility(View.GONE);
                }
                else{
                    l8.setVisibility(View.VISIBLE);
                    comp8.setText(compa8);
                    interest8.setText("Interest rate: " + i8);
                }
                if(compa9.equals("")){
                    l9.setVisibility(View.GONE);
                }
                else{
                    l9.setVisibility(View.VISIBLE);
                    comp9.setText(compa9);
                    interest9.setText("Interest rate: " + i9);
                }
                if(compa10.equals("")){
                    l10.setVisibility(View.GONE);
                }
                else{
                    l10.setVisibility(View.VISIBLE);
                    comp10.setText(compa10);
                    interest10.setText("Interest rate: " + i10);
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
                    company1 = compa1;
                    //Toast.makeText(AddLoanReq.this, company1, Toast.LENGTH_SHORT).show();
                }
                else{
                    company1 = " ";
                }
            }
        });

        comp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(comp2.isChecked()){
                    company2 = compa2;
                    //Toast.makeText(AddLoanReq.this, company2, Toast.LENGTH_SHORT).show();
                }
                else{
                    company2 = " ";
                }
            }
        });

        comp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(comp3.isChecked()){
                    company3 = compa3;
                    //Toast.makeText(AddLoanReq.this, company3, Toast.LENGTH_SHORT).show();
                }
                else{
                    company3 = " ";
                }
            }
        });

        comp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(comp4.isChecked()){
                    company4 = compa4;
                    //Toast.makeText(AddLoanReq.this, company4, Toast.LENGTH_SHORT).show();
                }
                else{
                    company4 = " ";
                }
            }
        });

        comp5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(comp5.isChecked()){
                    company5 = compa5;
                    //Toast.makeText(AddLoanReq.this, company5, Toast.LENGTH_SHORT).show();
                }
                else{
                    company5  = " ";
                }
            }
        });

        comp6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(comp6.isChecked()){
                    company6 = compa6;
                    //Toast.makeText(AddLoanReq.this, company5, Toast.LENGTH_SHORT).show();
                }
                else{
                    company6  = " ";
                }
            }
        });

        comp7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(comp7.isChecked()){
                    company7 = compa7;
                    //Toast.makeText(AddLoanReq.this, company5, Toast.LENGTH_SHORT).show();
                }
                else{
                    company7  = " ";
                }
            }
        });

        comp8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(comp8.isChecked()){
                    company8 = compa8;
                    //Toast.makeText(AddLoanReq.this, company5, Toast.LENGTH_SHORT).show();
                }
                else{
                    company8  = " ";
                }
            }
        });

        comp9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(comp9.isChecked()){
                    company9 = compa9;
                    //Toast.makeText(AddLoanReq.this, company5, Toast.LENGTH_SHORT).show();
                }
                else{
                    company9 = " ";
                }
            }
        });

        comp10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(comp10.isChecked()){
                    company10 = compa10;
                    //Toast.makeText(AddLoanReq.this, company5, Toast.LENGTH_SHORT).show();
                }
                else{
                    company10  = " ";
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
            //Toast.makeText(this, month, Toast.LENGTH_SHORT).show();
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
            LoanReq loanReq = new LoanReq(getIntent().getStringExtra("shopUid"), company1, company2, company3, company4, company5, mAuth.getCurrentUser().getUid(), "false", "false", aid, dp, month, getIntent().getStringExtra("listid"), "PENDING");
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

        //show interest rate

        if(id == R.id.view1){
            view1.setVisibility(View.GONE);
            h1.setVisibility(View.VISIBLE);
        }

        if(id == R.id.view2){
            view2.setVisibility(View.GONE);
            h2.setVisibility(View.VISIBLE);
        }

        if(id == R.id.view3){
            view3.setVisibility(View.GONE);
            h3.setVisibility(View.VISIBLE);
        }

        if(id == R.id.view4){
            view4.setVisibility(View.GONE);
            h4.setVisibility(View.VISIBLE);
        }

        if(id == R.id.view5){
            view5.setVisibility(View.GONE);
            h5.setVisibility(View.VISIBLE);
        }

        if(id == R.id.view6){
            view6.setVisibility(View.GONE);
            h6.setVisibility(View.VISIBLE);
        }

        if(id == R.id.view7){
            view7.setVisibility(View.GONE);
            h7.setVisibility(View.VISIBLE);
        }

        if(id == R.id.view8){
            view8.setVisibility(View.GONE);
            h8.setVisibility(View.VISIBLE);
        }

        if(id == R.id.view9){
            view9.setVisibility(View.GONE);
            h9.setVisibility(View.VISIBLE);
        }

        if(id == R.id.view10){
            view10.setVisibility(View.GONE);
            h10.setVisibility(View.VISIBLE);
        }


        //end of show interest rate

        //hide interest rate

        if(id == R.id.hide1){

            view1.setVisibility(View.VISIBLE);
            h1.setVisibility(View.GONE);

        }

        if(id == R.id.hide1){

            view1.setVisibility(View.VISIBLE);
            h1.setVisibility(View.GONE);

        }

        if(id == R.id.hide2){

            view2.setVisibility(View.VISIBLE);
            h2.setVisibility(View.GONE);

        }

        if(id == R.id.hide3){

            view3.setVisibility(View.VISIBLE);
            h3.setVisibility(View.GONE);

        }

        if(id == R.id.hide4){

            view4.setVisibility(View.VISIBLE);
            h4.setVisibility(View.GONE);

        }

        if(id == R.id.hide5){

            view5.setVisibility(View.VISIBLE);
            h5.setVisibility(View.GONE);

        }

        if(id == R.id.hide6){

            view6.setVisibility(View.VISIBLE);
            h6.setVisibility(View.GONE);

        }

        if(id == R.id.hide7){

            view7.setVisibility(View.VISIBLE);
            h7.setVisibility(View.GONE);

        }

        if(id == R.id.hide8){

            view8.setVisibility(View.VISIBLE);
            h8.setVisibility(View.GONE);

        }

        if(id == R.id.hide9){

            view9.setVisibility(View.VISIBLE);
            h9.setVisibility(View.GONE);

        }

        if(id == R.id.hide10){

            view10.setVisibility(View.VISIBLE);
            h10.setVisibility(View.GONE);

        }

        //end of hide interest rate
    }
}
