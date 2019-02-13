package vincegeralddelaccerna.ezwheels;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Calendar;

public class ReservationPayment extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    private static String shopuid, firstname, lastname, contact, resid;
    DatabaseReference getShopDetails, changeReserved;
    TextView name, con;
    ImageView proof;
    EditText sender, code;
    Button cancel, got, pay, back;
    LinearLayout payment, second;
    DatabaseReference res;
    private Uri imageUri;
    private StorageReference mStorageRef;
    private static String imagePath1;
    ProgressBar p1;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_payment);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#fefefe"));
        toolbar.setTitle("Reservation Payment");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.name);
        con = findViewById(R.id.contact);
        cancel = findViewById(R.id.cancel);
        got = findViewById(R.id.got);
        payment = findViewById(R.id.payment);
        second = findViewById(R.id.second);
        pay = findViewById(R.id.pay);
        back = findViewById(R.id.back);
        sender = findViewById(R.id.sender);
        code = findViewById(R.id.code);
        proof = findViewById(R.id.proof);
        p1 = findViewById(R.id.progress);


        cancel.setOnClickListener(this);
        got.setOnClickListener(this);
        pay.setOnClickListener(this);
        back.setOnClickListener(this);
        proof.setOnClickListener(this);

        getShopDetails = FirebaseDatabase.getInstance().getReference("Shop");
        res = FirebaseDatabase.getInstance().getReference("Payments");
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        changeReserved = FirebaseDatabase.getInstance().getReference("Reservation");




        shopuid = getIntent().getStringExtra("shopuid");
        resid = getIntent().getStringExtra("resid");


        getShopDetails.orderByChild("uid").equalTo(shopuid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    firstname = dataSnapshot.child("firstname").getValue().toString();
                    lastname = dataSnapshot.child("lastname").getValue().toString();
                    contact = dataSnapshot.child("contact").getValue().toString();
                    name.setText("Name: " + firstname + " " + lastname);
                    con.setText("Contact: " + contact);
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
                Toast.makeText(ReservationPayment.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.got){
            second.setVisibility(View.GONE);
            payment.setVisibility(View.VISIBLE);
        }

        if(id == R.id.back){
            second.setVisibility(View.VISIBLE);
            payment.setVisibility(View.GONE);
        }

        if(id == R.id.pay){
            final String senderText = sender.getText().toString();
            final String codeText = code.getText().toString();
            String pid = res.push().getKey();
            String amount = "999.00";
            String type = "Reservation Fee";
            Calendar calendar = Calendar.getInstance();
            String date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
            //String image, String name, String code, String uid, String id, String amount, String type, String shopuid
            String seen = "false";
            Payments payments = new Payments(imagePath1, senderText, codeText, mAuth.getCurrentUser().getUid(), pid,  amount, type, shopuid, resid, date, seen);
            res.child(pid).setValue(payments).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        changeReserved.child(resid).child("reserved").setValue("true");
                        finish();
                    }
                    else{
                        Toast.makeText(ReservationPayment.this, "Error", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });
         }

        if(id == R.id.proof){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 1);
        }

        if(id == R.id.cancel){
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK){


            imageUri = data.getData();


            final String path = System.currentTimeMillis() + "." + getFileExtension(imageUri);
            p1.setVisibility(View.VISIBLE);
            StorageReference storageReference = mStorageRef.child("Images").child(path);
            storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    mStorageRef.child("Images/"+path).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Toast.makeText(ReservationPayment.this, "Proof Image Added", Toast.LENGTH_SHORT).show();
                            imagePath1 = uri.toString();
                            Picasso.get().load(imageUri).fit().centerCrop().into(proof);
                            p1.setVisibility(View.INVISIBLE);

                        }
                    });
                }
            });
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getApplication().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}
