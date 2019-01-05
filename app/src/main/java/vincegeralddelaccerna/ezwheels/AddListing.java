package vincegeralddelaccerna.ezwheels;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.VideoView;

import co.gofynd.gravityview.GravityView;

import static android.app.Activity.RESULT_OK;

public class AddListing extends Fragment implements View.OnClickListener {

    private static final String [] brands = new String[]{
      "Toyota","Suzuki","Mitsubishi","Kia","Chevrolet"
    };
    private static final String [] model = new String[]{
            "Fortuner","Wigo","LandCruiser","MonteroSport","Sportage"
    };
    private static final String [] year = new String[]{
            "2011","2012","2013","2014","2015","2016","2017","2018"
    };
    private static final String [] transmission = new String[]{
            "Manual","Automatic"
    };
    private static final String [] color = new String[]{
            "White","Black","Red","Blue","Yellow"
    };
    private static final String [] priceCondition = new String[]{
            "Negotiable","Fixed"
    };
    private static final int MULTIPLE_IMAGE = 2;
    private static final int SINGLE_VIDEO = 1;
    private static final int PANORAMA_IMAGE = 3 ;
    ImageButton image;
    AutoCompleteTextView brandText, modelText, yearText, transmissionText, colorText, priceConditionText;
    Button btn3, btn4, btnBack, btnStep2, btn6, btn5, btn7, btn8, btn9, btn10, addImage, btnVideo, addPanorama;
    ScrollView addList1, addList2;
    LinearLayout addList3, addList4, addList5;
    ImageView image1, image2, image3, image4, imagePanorama;
    VideoView videoView;
    MediaController mediaController;
    GravityView gravityView;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_add_listing, container, false);

        brandText = v.findViewById(R.id.brandText);
        modelText = v.findViewById(R.id.modelText);
        yearText = v.findViewById(R.id.yearText);
        colorText = v.findViewById(R.id.colorText);
        transmissionText = v.findViewById(R.id.transmissionText);
        priceConditionText = v.findViewById(R.id.PriceCondition);
        videoView = v.findViewById(R.id.videoView);
        mediaController = new MediaController(getActivity());
        btn3 = v.findViewById(R.id.button3);
        btn4 = v.findViewById(R.id.button4);
        btn5 = v.findViewById(R.id.button5);
        btn6 = v.findViewById(R.id.button6);
        btn7 = v.findViewById(R.id.button7);
        btn8 = v.findViewById(R.id.button8);
        btn9 = v.findViewById(R.id.button9);
        btn10 = v.findViewById(R.id.button10);
        image1 = v.findViewById(R.id.image1);
        image2 = v.findViewById(R.id.image2);
        image3 = v.findViewById(R.id.image3);
        image4 = v.findViewById(R.id.image4);
        addImage = v.findViewById(R.id.addImage);
        btnBack = v.findViewById(R.id.btnBack);
        btnStep2 = v.findViewById(R.id.btnStep2);
        addList1 = v.findViewById(R.id.addList1);
        addList2 = v.findViewById(R.id.addList2);
        addList3 = v.findViewById(R.id.addList3);
        addList4 = v.findViewById(R.id.addList4);
        addList5 = v.findViewById(R.id.addList5);
        btnVideo = v.findViewById(R.id.btnVideo);
        addPanorama = v.findViewById(R.id.addPanorama);
        imagePanorama = v.findViewById(R.id.imageView15);
        ArrayAdapter<String> brandAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, brands);
        brandText.setAdapter(brandAdapter);
        ArrayAdapter<String> modelAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, model);
        modelText.setAdapter(modelAdapter);
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, year);
        yearText.setAdapter(yearAdapter);
        ArrayAdapter<String> transmissionAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, transmission);
        transmissionText.setAdapter(transmissionAdapter);
        ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, color);
        colorText.setAdapter(colorAdapter);
        ArrayAdapter<String> condtionAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, priceCondition);
        priceConditionText.setAdapter(condtionAdapter);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnStep2.setOnClickListener(this);
        addImage.setOnClickListener(this);
        btnVideo.setOnClickListener(this);
        addPanorama.setOnClickListener(this);

        gravityView = GravityView.getInstance(getActivity());

        if(!gravityView.deviceSupported()){
            Bitmap bitmapFactory = BitmapFactory.decodeResource(getResources(), R.drawable.panorama);
            imagePanorama.setImageBitmap(bitmapFactory);
        }
        else{
            gravityView.setImage(imagePanorama, R.drawable.panorama).center();
        }

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });



        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add Listing");
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button3){
            ListingRequestFragment listing = new ListingRequestFragment();
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.screen_area, listing, listing.getTag())
                    .commit();
        }
        if(view.getId() == R.id.button4){
            addList1.setVisibility(View.GONE);
            addList2.setVisibility(View.VISIBLE);
        }

        if(view.getId() == R.id.btnBack){
            addList1.setVisibility(View.VISIBLE);
            addList2.setVisibility(View.GONE);
        }

        if(view.getId() == R.id.btnStep2){
            addList1.setVisibility(View.GONE);
            addList2.setVisibility(View.GONE);
            addList3.setVisibility(View.VISIBLE);
        }

        if(view.getId() == R.id.button5){
            addList1.setVisibility(View.GONE);
            addList2.setVisibility(View.GONE);
            addList3.setVisibility(View.GONE);
            addList4.setVisibility(View.VISIBLE);
        }

        if(view.getId() == R.id.button6){
            addList1.setVisibility(View.GONE);
            addList2.setVisibility(View.VISIBLE);
            addList3.setVisibility(View.GONE);
        }

        if(view.getId() == R.id.button7){
            addList1.setVisibility(View.GONE);
            addList2.setVisibility(View.GONE);
            addList3.setVisibility(View.VISIBLE);
            addList4.setVisibility(View.GONE);
        }

        if(view.getId() == R.id.button8){
            addList1.setVisibility(View.GONE);
            addList2.setVisibility(View.GONE);
            addList3.setVisibility(View.GONE);
            addList4.setVisibility(View.GONE);
            addList5.setVisibility(View.VISIBLE);
        }

        if(view.getId() == R.id.button9){
            addList1.setVisibility(View.GONE);
            addList2.setVisibility(View.GONE);
            addList3.setVisibility(View.GONE);
            addList4.setVisibility(View.VISIBLE);
            addList5.setVisibility(View.GONE);
        }

        if(view.getId() == R.id.button10){
            Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
        }

        if(view.getId() == R.id.addImage){

            Intent intent = new Intent();
            intent.setType("panorama/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select 4 images"), MULTIPLE_IMAGE);
        }

        if(view.getId() == R.id.btnVideo){
            Intent gallery = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
//            startActivityForResult(Intent.createChooser(new Intent().
//                    setAction(Intent.ACTION_GET_CONTENT).
//                    setType("video/mp4"),
//                    "SELECT VIDEO"),
//                    SINGLE_VIDEO);
            startActivityForResult(gallery, SINGLE_VIDEO);
        }

        if(view.getId() == R.id.addPanorama){
            startActivityForResult(Intent.createChooser(new Intent().
                    setAction(Intent.ACTION_GET_CONTENT).
                    setType("panorama/*"),
                    "Select panorama"),
                    PANORAMA_IMAGE);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SINGLE_VIDEO && resultCode == RESULT_OK){
            Uri videoUri = data.getData();
            if(videoUri != null){
                videoView.setVideoURI(videoUri);
                videoView.start();

            }
        }

        else if(requestCode == PANORAMA_IMAGE && resultCode == RESULT_OK){
            Uri imageUri = data.getData();

        }

        else if(requestCode == MULTIPLE_IMAGE && resultCode == RESULT_OK){
            ClipData clipData = data.getClipData();

            if(clipData != null){

                image1.setImageURI(clipData.getItemAt(0).getUri());
                image2.setImageURI(clipData.getItemAt(1).getUri());
                image3.setImageURI(clipData.getItemAt(2).getUri());
                image4.setImageURI(clipData.getItemAt(3).getUri());

                for(int i  = 0; i < clipData.getItemCount(); i++){
                    ClipData.Item item = clipData.getItemAt(i);
                    Uri uri = item.getUri();
                    Log.e("image", uri.toString());
                }
            }
        }


    }
}
