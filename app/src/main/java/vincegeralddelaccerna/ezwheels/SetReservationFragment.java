package vincegeralddelaccerna.ezwheels;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class SetReservationFragment extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener {



    RadioGroup type;
    RadioButton type1, type2;
    EditText address, reminder;
    TextView dateText, timeText;
    Button timebtn, datebtn, reservebtn;
    private String resType = "shop";
    private String currentTime;
    private String currentDate;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;
//    public SetReservationFragment() {
//        // Required empty public constructor
//    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_set_reservation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#000000"));
        toolbar.setTitle("Reservation");
        setSupportActionBar(toolbar);

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

        //listeners

        datebtn.setOnClickListener(this);
        timebtn.setOnClickListener(this);
        reservebtn.setOnClickListener(this);

        type.setOnCheckedChangeListener(this);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();


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

        if(id == R.id.reserveButton){
            final String addressText = address.getText().toString().trim();
            final String reminderText = reminder.getText().toString().trim();
            final String shopuid = getIntent().getStringExtra("shopuid");
            final String uid = mAuth.getCurrentUser().getUid();
            reservation(addressText, reminderText, shopuid, currentDate, currentTime, uid);

        }
    }

    private void reservation(String addressText, String reminderText, String shopuid, String currentDate, String currentTime, String uid) {

        Reservation reservation = new Reservation(addressText,reminderText, shopuid, currentDate, currentTime, uid);

        mDatabaseRef.child("Reservation").push().setValue(reservation);
    }


    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR, i);
        c.set(Calendar.MINUTE, i1);
        currentTime = DateFormat.getDateInstance().format(c.getTime());
        timeText.setText("Hour: " + i + " Minute: " + i1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioButton:
                        resType = "shop";
                        Toast.makeText(SetReservationFragment.this, resType, Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.radioButton2:
                        resType = "home";
                        Toast.makeText(SetReservationFragment.this, resType, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}