package vincegeralddelaccerna.ezwheels;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.flags.Flag;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class car_fragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {



    public car_fragment(){

    }

    RecyclerView recyclerView;
    FloatingActionButton add;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    ImageView brokencar, sort;
    TextView nolisting;
    SearchView searchView;
    Query query;

    private ProgressBar mProgressbar;

    DashboardCarAdapter mAdapter;

    private List<Upload> mUploads;
    //ArrayList<Upload> temp = new ArrayList<Upload>(mUploads);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.car_fragment, container, false);
        recyclerView = v.findViewById(R.id.recyclerRequest);
        mProgressbar = v.findViewById(R.id.progress);
        brokencar = v.findViewById(R.id.brokencar);
        nolisting = v.findViewById(R.id.nolisting);
        searchView = v.findViewById(R.id.search);
        sort = v.findViewById(R.id.sort);
        sort.setOnClickListener(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mUploads = new ArrayList<>();


        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid().toString();
        query = FirebaseDatabase.getInstance().getReference("Car")
                .orderByChild("status").equalTo("AVAILABLE");

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                        mAdapter.getFilter().filter(s);
                    return false;
                }
            });




            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mUploads.clear();
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                            Upload upload = postSnapshot.getValue(Upload.class);
                            mUploads.add(upload);
                        }

                        mAdapter = new DashboardCarAdapter(getActivity(), mUploads);
                        recyclerView.setAdapter(mAdapter);
                        mProgressbar.setVisibility(View.GONE);
                        nolisting.setVisibility(View.GONE);
                        brokencar.setVisibility(View.GONE);
                    }
                    else{
                        nolisting.setVisibility(View.VISIBLE);
                        brokencar.setVisibility(View.VISIBLE);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    mProgressbar.setVisibility(View.GONE);
                }
            });


        return v;
    }


    @Override
    public void onClick(View view) {

        int id = view.getId();

        if(id == R.id.sort){
            //Toast.makeText(getActivity(), "Sort", Toast.LENGTH_SHORT).show();
            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            final Spinner spinner = new Spinner(getContext());
//
            final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.filters, android.R.layout.simple_list_item_1);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    //Toast.makeText(getContext(), adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
                    String filter = adapterView.getItemAtPosition(i).toString();

                    if(filter.equals("Less than 100k")){
                        mAdapter.getFilter().filter("100,000");
                    }
                    else if(filter.equals("100k - 300k")){
                        mAdapter.getFilter().filter("101,000".concat("300,000"));
                    }
                    else if(filter.equals("301k - 500k")){
                        mAdapter.getFilter().filter("301,000".concat("500,000"));
                    }
                    else if(filter.equals("501k - 800k")){
                        mAdapter.getFilter().filter("501,000".concat("800,000"));
                    }
                    else{
                        mAdapter.getFilter().filter("801,000");
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            spinner.setPadding(40, 40, 40, 40);
            builder.setView(spinner);

            builder.setMessage("Sort Data").setCancelable(false)
                    .setPositiveButton("GO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();

                        }
                    }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();


                        }
                    }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.setTitle("Search Filters");
            alertDialog.show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {



    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
