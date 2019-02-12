package vincegeralddelaccerna.ezwheels;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ActivateAccount extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    Button cancel, got, back, pay;
    EditText code, sender;
    LinearLayout instructions, payment;
    ImageView iv;
    ProgressBar progressBar;
    private Uri uriImage;
    TextView name, contact;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mAuth;
    private static String imagePath2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate_account);

        cancel = findViewById(R.id.cancel);
        got = findViewById(R.id.got);
        back = findViewById(R.id.back);
        pay = findViewById(R.id.pay);
        code = findViewById(R.id.code);
        sender = findViewById(R.id.sender);
        instructions = findViewById(R.id.instructions);
        payment = findViewById(R.id.payment);
        iv = findViewById(R.id.proof);
        name = findViewById(R.id.name);
        contact = findViewById(R.id.contact);
        progressBar = findViewById(R.id.progress);
        toolbar =  findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#fefefe"));
        toolbar.setTitle("ACTIVATE ACCOUNT");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cancel.setOnClickListener(this);
        got.setOnClickListener(this);
        back.setOnClickListener(this);
        pay.setOnClickListener(this);
        iv.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == 1){

            uriImage = data.getData();
            final String path1 = System.currentTimeMillis() + "." + getFileExtension(uriImage);
            progressBar.setVisibility(View.VISIBLE);
            StorageReference storageReference = mStorageRef.child("Images").child(path1);
            storageReference.putFile(uriImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    mStorageRef.child("Images/"+path1).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Toast.makeText(ActivateAccount.this, "Proof Image Added", Toast.LENGTH_SHORT).show();
                            imagePath2 = uri.toString();
                            Picasso.get().load(uriImage).fit().centerCrop().into(iv);
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            });


        }
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if(id == R.id.cancel){
            this.finish();
        }

        if(id == R.id.got){
            instructions.setVisibility(View.GONE);
            payment.setVisibility(View.VISIBLE);
        }

        if(id == R.id.back){
            instructions.setVisibility(View.VISIBLE);
            payment.setVisibility(View.GONE);
        }

        if(id == R.id.pay){

            final AlertDialog.Builder builder = new AlertDialog.Builder(ActivateAccount.this);
            builder.setMessage("Proceed?").setCancelable(false)
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialogInterface, int i) {

                            final String codeText = code.getText().toString();
                            final String sendertext = sender.getText().toString();
                            final String uid = mAuth.getCurrentUser().getUid();
                            final String payid = mDatabaseRef.push().getKey();
                            final String amount = "999.00";
                            final String type = "Subscription";
                            final String shopuid = "";
                            Payments payments = new Payments(imagePath2, sendertext, codeText, uid, payid, amount, type, shopuid, "");
                            mDatabaseRef.child("Payments").child(payid).setValue(payments).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(ActivateAccount.this, "Request Submitted. Wait for the email confirmation", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(ActivateAccount.this, "An error occurred", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            dialogInterface.dismiss();

                        }
                    }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.setTitle("Activate Account");
            alertDialog.show();


        }

        if(id == R.id.proof){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 1);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getApplication().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
