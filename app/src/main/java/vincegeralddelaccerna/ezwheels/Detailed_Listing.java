package vincegeralddelaccerna.ezwheels;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;


import com.squareup.picasso.Picasso;

public class Detailed_Listing extends AppCompatActivity implements View.OnClickListener {

    TextView modelText, brandText, priceText;
    Toolbar toolbar1;
    ImageView imageView1;
    Button call, message;
    private String number;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed__listing);

        //action bar
        toolbar1 = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar1);


        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Listing Details");
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        call = findViewById(R.id.button12);
        message = findViewById(R.id.button13);
        call.setOnClickListener(this);
        message.setOnClickListener(this);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Listing Details");
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setDisplayShowHomeEnabled(true);

        String image1 = getIntent().getStringExtra("image_url1");
        String image2 = getIntent().getStringExtra("image_url2");
        String image3 = getIntent().getStringExtra("image_url3");
        String image4 = getIntent().getStringExtra("image_url4");
        String videoUrl = getIntent().getStringExtra("videoUrl");
        String brand = getIntent().getStringExtra("brand");
        String model = getIntent().getStringExtra("model");
        String year = getIntent().getStringExtra("year");
        String color = getIntent().getStringExtra("color");
        String transmission = getIntent().getStringExtra("transmission");
        String pricecondition = getIntent().getStringExtra("pricecondition");
        String mileage = getIntent().getStringExtra("mileage");
        String price = getIntent().getStringExtra("price");
        number = "09239567606";

        setDetails(image1, model, brand, price, number);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setDetails(String image1, String model, String brand, String price, String number){
        imageView1 = findViewById(R.id.imageDetails1);
        Picasso.get().load(image1).fit().centerCrop().into(imageView1);
        brandText = findViewById(R.id.brand);
        brandText.setText(brand+" - "+model);
        priceText  = findViewById(R.id.price);
        priceText.setText(price);


    }

    //handle onBackPressed

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button12) {

            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + number));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

        }

        if (view.getId() == R.id.button13) {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_VIEW);
            sendIntent.setData(Uri.parse("smsto:" + number));  // This ensures only SMS apps respond
            sendIntent.putExtra("sms_body", "Hi im interested in your listing in ezwheels!");
            if (sendIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(sendIntent);
            }
        }
    }
}
