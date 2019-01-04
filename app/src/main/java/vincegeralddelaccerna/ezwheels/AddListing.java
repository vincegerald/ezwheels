package vincegeralddelaccerna.ezwheels;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import static android.app.Activity.RESULT_OK;

public class AddListing extends Fragment implements View.OnClickListener {


    private static final int RESULT_LOAD_IMAGE1 = 1;
    ImageButton image;
    RecyclerView rv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_add_listing, null);
        image = v.findViewById(R.id.imageButton2);
        rv = v.findViewById(R.id.recyclerView1);

        image.setOnClickListener(this);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add Listing");
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.imageButton2){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE1);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOAD_IMAGE1 && resultCode == RESULT_OK){

            if(data.getClipData() != null){
                Toast.makeText(getActivity(), "Select Multiple images", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getActivity(), "Select Single image", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
