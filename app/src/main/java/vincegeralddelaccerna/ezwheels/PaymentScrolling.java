package vincegeralddelaccerna.ezwheels;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PaymentScrolling extends AppCompatActivity {


    TextView payment, price, date, codee, senderName, sender, senderContact, senderShop, senderLocation;
    private static String pid, uid, shopuid;
    DatabaseReference getPay, getShop, getBuyer;
    private static String amount, type, code, datee,shop, id;
    private static String firstname, contact, shopname, lastname, contactnumber, location;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        payment = findViewById(R.id.payment);
        price = findViewById(R.id.price);
        date = findViewById(R.id.date);
        codee = findViewById(R.id.code);
        sender = findViewById(R.id.sender);
        senderName = findViewById(R.id.senderName);
        senderContact = findViewById(R.id.senderContact);
        senderShop = findViewById(R.id.senderShop);
        senderLocation = findViewById(R.id.senderLocation);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        pid = getIntent().getStringExtra("pid");
        uid = getIntent().getStringExtra("uid");
        shopuid = getIntent().getStringExtra("shopuid");

        getPay = FirebaseDatabase.getInstance().getReference("Payments");
        getShop = FirebaseDatabase.getInstance().getReference("Shop");
        getBuyer = FirebaseDatabase.getInstance().getReference("Buyer");


        mAuth = FirebaseAuth.getInstance();

        getSupportActionBar().setTitle("Payments");


        id = mAuth.getCurrentUser().getUid();



        getPay.orderByChild("id").equalTo(pid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    amount  =  dataSnapshot.child("amount").getValue().toString();
                    type = dataSnapshot.child("type").getValue().toString();
                    code = dataSnapshot.child("code").getValue().toString();
                    datee = dataSnapshot.child("date").getValue().toString();
                    shop = dataSnapshot.child("shopuid").getValue().toString();
                    id = dataSnapshot.child("uid").getValue().toString();

                    codee.setText("Code: " + code);
                    payment.setText(type);
                    date.setText("Date: " + datee);
                    price.setText(amount);
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
                Toast.makeText(PaymentScrolling.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        if(id.equals(uid)){
            getShop.child(shopuid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        firstname = dataSnapshot.child("firstname").getValue().toString();
                        lastname = dataSnapshot.child("lastname").getValue().toString();
                        contactnumber = dataSnapshot.child("contact").getValue().toString();
                        shopname = dataSnapshot.child("name").getValue().toString();
                        location = dataSnapshot.child("location").getValue().toString();

                        sender.setText("Sendee Details");
                        senderName.setText("Name: " + firstname + " " + lastname);
                        senderContact.setText("Contact number: " + contactnumber);
                        senderLocation.setText("Location: " + location);
                        senderShop.setText("Shopname: " + shopname);
                    }
                    else{
                        getBuyer.child(shopuid).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(PaymentScrolling.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(PaymentScrolling.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        if(id.equals(shopuid)){
            getBuyer.child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){

                        firstname = dataSnapshot.child("firstname").getValue().toString();
                        lastname = dataSnapshot.child("lastname").getValue().toString();
                        contactnumber = dataSnapshot.child("contactnumber").getValue().toString();
                        senderName.setText("Name: " +firstname + " " + lastname);
                        senderContact.setText("Contact Number: " + contactnumber);
                        senderLocation.setVisibility(View.GONE);
                        senderShop.setVisibility(View.GONE);
                    }
                    else{
                        getShop.child(uid).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){

                                    firstname = dataSnapshot.child("firstname").getValue().toString();
                                    lastname = dataSnapshot.child("lastname").getValue().toString();
                                    contactnumber = dataSnapshot.child("contact").getValue().toString();
                                    shopname = dataSnapshot.child("name").getValue().toString();
                                    location = dataSnapshot.child("location").getValue().toString();

                                    senderName.setText("Name: " + firstname + " " + lastname);
                                    senderContact.setText("Contact number: " + contactnumber);
                                    senderLocation.setText("Location: " + location);
                                    senderShop.setText("Shopname: " + shopname);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(PaymentScrolling.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(PaymentScrolling.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }






//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
