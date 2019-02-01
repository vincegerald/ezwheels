package vincegeralddelaccerna.ezwheels;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;

public class EditReservation extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private static String resId;

    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mAuth;

    RadioGroup type;
    RadioButton type1, type2;
    EditText address, reminder;
    TextView dateText, timeText, input;
    Button timebtn, datebtn, reservebtn;
    private String resType = "Shop";
    private String currentTime;
    private String currentDate;
    LinearLayout loc, rem;
    TextView date, time, location, remindertext;

    private static String addressText, brand, currDate, currTime, image1    , listid, model, name, price, reminderText, resid, shopuid, status, restype, uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_set_reservation);

        type = findViewById(R.id.type);
        type1 = findViewById(R.id.radioButton);
        type2 = findViewById(R.id.radioButton2);
        address = findViewById(R.id.address);
        reminder = findViewById(R.id.reminder);
        dateText = findViewById(R.id.dateText);
        timeText = findViewById(R.id.timeText);
        timebtn = findViewById(R.id.timebtn);
        datebtn = findViewById(R.id.datebtn);
        reservebtn = findViewById(R.id.reservebtn);
        input = findViewById(R.id.input);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        location = findViewById(R.id.location);
        remindertext = findViewById(R.id.reminder);
        loc = findViewById(R.id.loc);
        rem = findViewById(R.id.rem);


        //listeners

        datebtn.setOnClickListener(this);
        timebtn.setOnClickListener(this);
        reservebtn.setOnClickListener(this);

        type.setOnCheckedChangeListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#fefefe"));
        toolbar.setTitle("Edit Reservation");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        resId = getIntent().getStringExtra("resId");

        mAuth = FirebaseAuth.getInstance();


        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Reservation").child(resId);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                addressText = dataSnapshot.child("addressText").getValue().toString();
                brand = dataSnapshot.child("brand").getValue().toString();
                currDate = dataSnapshot.child("currentDate").getValue().toString();
                currTime = dataSnapshot.child("currTime").getValue().toString();
                image1 = dataSnapshot.child("image1").getValue().toString();
                listid = dataSnapshot.child("listid").getValue().toString();
                model = dataSnapshot.child("model").getValue().toString();
                name = dataSnapshot.child("name").getValue().toString();
                price = dataSnapshot.child("price").getValue().toString();
                reminderText = dataSnapshot.child("reminderText").getValue().toString();
                shopuid = dataSnapshot.child("shopuid").getValue().toString();
                status = dataSnapshot.child("currentDate").getValue().toString();
                restype = dataSnapshot.child("type").getValue().toString();
                uid = dataSnapshot.child("uid").getValue().toString();
                resid = dataSnapshot.child("resId").getValue().toString();

                date.setText(currDate);
                time.setText(currTime);
                if(resType.equals("Shop")){
                    type1.setChecked(true);
                    loc.setVisibility(View.GONE);
                    rem.setVisibility(View.GONE);
                }
                else{
                    type2.setChecked(true);
                    location.setText(addressText);
                    remindertext.setText(reminderText);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EditReservation.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.radioButton:
                resType = "Shop";
                input.setVisibility(View.GONE);
                address.setVisibility(View.GONE);
                reminder.setVisibility(View.GONE);
                Toast.makeText(EditReservation.this, resType, Toast.LENGTH_SHORT).show();
                break;

            case R.id.radioButton2:
                resType = "Home";
                input.setVisibility(View.VISIBLE);
                address.setVisibility(View.VISIBLE);
                reminder.setVisibility(View.VISIBLE);
                Toast.makeText(EditReservation.this, resType, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, i);
        c.set(Calendar.MONTH, i1);
        c.set(Calendar.DAY_OF_MONTH, i2);
        currentDate = DateFormat.getDateInstance().format(c.getTime());
        dateText.setText(currentDate);


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.datebtn){
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
        }

        if(id == R.id.timebtn){
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), "time picker");
        }

        if(id == R.id.reservebtn){
            final String addressText = address.getText().toString().trim();
            final String reminderText = reminder.getText().toString().trim();
            final String shopuid = getIntent().getStringExtra("shopuid");
            final String image1 = getIntent().getStringExtra("image1");
            final String uid = mAuth.getCurrentUser().getUid();
            final String name = getIntent().getStringExtra("name");
            final String model = getIntent().getStringExtra("model");
            final String brand = getIntent().getStringExtra("brand");
            final String price = getIntent().getStringExtra("price");
            reservation(model, brand,name, image1, addressText, reminderText, shopuid, currentDate, currentTime, uid, price);

        }
    }

    private void reservation(String model, String brand, String name, String image1, String addressText, String reminderText, String shopuid, String currentDate, String currentTime, String uid, String price) {
        String listingid = getIntent().getStringExtra("listingid");
        String status = "PENDING";

        mDatabaseRef = mDatabaseRef.child("Reservation");
        String resId = mDatabaseRef.push().getKey();
        Reservation reservation = new Reservation(model, brand, name, image1, addressText, reminderText, shopuid, currentDate, currentTime, uid, listingid, resType, resId, price, status);
        mDatabaseRef.child(resId).setValue(reservation).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(EditReservation.this, "Saved", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditReservation.this, ShopDashboard.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(EditReservation.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR, i);
        c.set(Calendar.MINUTE, i1);
        if (i >= 12){
            i = i - 12;
            currentTime = i + " : " + i1 + "0" + " PM";
        }
        else{
            currentTime = i + " : " + i1 + "0" + " AM";

        }

        timeText.setText("Hour: " + i + " Minute: " + i1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }



}
