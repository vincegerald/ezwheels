package vincegeralddelaccerna.ezwheels;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class SetReservationFragment extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener {



    RadioGroup type;
    RadioButton type1, type2;
    EditText address, reminder;
    TextView dateText, timeText, input;
    Button timebtn, datebtn, reservebtn;
    Button pay, cancel, got, back;
    EditText code, sender;
    LinearLayout first, second, payment;
    private String resType = "At Shop";
    private String currentTime;
    private String currentDate;
    ImageView iv;
    ProgressBar progress;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef, paymentRef;
    private FirebaseAuth mAuth;
    private Uri uriImage;
    private static double amount = 0.2f;
    private static String imagePath1;
//    public SetReservationFragment() {
//        // Required empty public constructor
//    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_set_reservation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#fefefe"));
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
        input = findViewById(R.id.input);
        first = findViewById(R.id.first);
        second = findViewById(R.id.second);
        payment = findViewById(R.id.payment);
        pay = findViewById(R.id.pay);
        cancel = findViewById(R.id.cancel);
        got = findViewById(R.id.got);
        back = findViewById(R.id.back);
        iv = findViewById(R.id.proof);
        progress = findViewById(R.id.progress);
        sender = findViewById(R.id.sender);
        code = findViewById(R.id.code);

        pay.setOnClickListener(this);
        cancel.setOnClickListener(this);
        got.setOnClickListener(this);
        back.setOnClickListener(this);
        iv.setOnClickListener(this);

        //listeners

        datebtn.setOnClickListener(this);
        timebtn.setOnClickListener(this);
        reservebtn.setOnClickListener(this);

        type.setOnCheckedChangeListener(this);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        paymentRef = FirebaseDatabase.getInstance().getReference();



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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK){
           
           
            uriImage = data.getData();

            final String path = System.currentTimeMillis() + "." + getFileExtension(uriImage);
            progress.setVisibility(View.VISIBLE);
            StorageReference storageReference = mStorageRef.child("Images").child(path);
            storageReference.putFile(uriImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    mStorageRef.child("Images/"+path).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Toast.makeText(SetReservationFragment.this, "Proof image added", Toast.LENGTH_SHORT).show();
                            imagePath1 = uri.toString();
                            Picasso.get().load(uriImage).fit().centerCrop().into(iv);
                            progress.setVisibility(View.INVISIBLE);

                        }
                    });
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.proof){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 1);
        }

        if(id == R.id.got){
            second.setVisibility(View.GONE);
            payment.setVisibility(View.VISIBLE);
            first.setVisibility(View.GONE);
        }

        if(id == R.id.back){
            first.setVisibility(View.GONE);
            second.setVisibility(View.VISIBLE);
            payment.setVisibility(View.GONE);
        }

        if(id == R.id.cancel){
            finish();
        }

        if(id == R.id.datebtn){
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
        }

        if(id == R.id.timebtn){
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), "time picker");
        }

        if(id == R.id.reservebtn){
            first.setVisibility(View.GONE);
            second.setVisibility(View.VISIBLE);
        }

        if(id == R.id.pay){
            final String senderText = sender.getText().toString().trim();
            final String codeText = sender.getText().toString().trim();
            final String addressText = address.getText().toString().trim();
            final String reminderText = reminder.getText().toString().trim();
            final String shopuid = getIntent().getStringExtra("shopuid");
            final String image1 = getIntent().getStringExtra("image1");
            final String uid = mAuth.getCurrentUser().getUid();
            final String name = getIntent().getStringExtra("name");
            final String model = getIntent().getStringExtra("model");
            final String brand = getIntent().getStringExtra("brand");
            final String price = getIntent().getStringExtra("price");
            reservation(senderText, codeText,model, brand,name, image1, addressText, reminderText, shopuid, currentDate, currentTime, uid, price);
        }
    }

    private void reservation(final String senderTxt, final String codeText, String model, String brand, String name, String image1, String addressText, String reminderText, final String shopuid, String currentDate, String currentTime, String uid, String price) {
        String listingid = getIntent().getStringExtra("listingid");
        String status = "PENDING";
        final String payment = "Not yet received";
        mDatabaseRef = mDatabaseRef.child("Reservation");
        String resId = mDatabaseRef.push().getKey();
        String seen = "false";
        String fromSeen = "false";
        Reservation reservation = new Reservation(model, brand, name, image1, addressText, reminderText, shopuid, currentDate, currentTime, uid, listingid, resType, resId, price, status, seen, fromSeen, payment);
        mDatabaseRef.child(resId).setValue(reservation).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    String id = paymentRef.push().getKey();
                    final String type = "ReservationFee";
                    Payments payments = new Payments(imagePath1, senderTxt, codeText, mAuth.getCurrentUser().getUid(), id, amount,  type, shopuid);
                    paymentRef.child(id).setValue(payments).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SetReservationFragment.this, "Successfully Added Reservation. The shop will contact you as soon as possible", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(SetReservationFragment.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else{
                    Toast.makeText(SetReservationFragment.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR, i);
        c.set(Calendar.MINUTE, i1);
        String format;
        String minute;
        if(i == 0){
            i += 12;
            format = "AM";
        }
        else if(i == 12){
            format = "PM";
        }
        else if( i > 12){
            i -=12;
            format = "PM";
        }
        else{
            format = "AM";
        }

        if(i1 < 10){
            minute = "0" + i1;
        }

        else{
            minute = "" + i1;
        }

        currentTime = i + ":" + minute + ":" + format;



        timeText.setText(currentTime);
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
        switch (i){
            case R.id.radioButton:
                resType = "At Shop";
                amount = 199.00;
                input.setVisibility(View.GONE);
                address.setVisibility(View.GONE);
                reminder.setVisibility(View.GONE);
                //Toast.makeText(SetReservationFragment.this, resType, Toast.LENGTH_SHORT).show();
                break;

            case R.id.radioButton2:
                resType = "At Home";
                amount = 299.00;
                input.setVisibility(View.VISIBLE);
                address.setVisibility(View.VISIBLE);
                reminder.setVisibility(View.VISIBLE);
                //Toast.makeText(SetReservationFragment.this, resType, Toast.LENGTH_SHORT).show();
        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getApplication().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}
