package vincegeralddelaccerna.ezwheels;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListingRequestFragment extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    FloatingActionButton add;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    private ProgressBar mProgressbar;

    ListRequestAdapter mAdapter;

    private List<Upload> mUploads;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.listingrequest_fragment, null);
        recyclerView = v.findViewById(R.id.recyclerRequest);
        mProgressbar = v.findViewById(R.id.progress);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mUploads = new ArrayList<>();


        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid().toString();
        databaseReference = FirebaseDatabase.getInstance().getReference("Buyers").child(uid).child("Listing Request");
        add = v.findViewById(R.id.fab);
        add.setOnClickListener(this);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Upload upload = postSnapshot.getValue(Upload.class);
                    mUploads.add(upload);
                }

                mAdapter = new ListRequestAdapter(getActivity(), mUploads);
                recyclerView.setAdapter(mAdapter);
                mProgressbar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressbar.setVisibility(View.INVISIBLE);
            }
        });


        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Listing Request");
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.fab){
            AddListing listing = new AddListing();
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.screen_area, listing, listing.getTag())
                    .commit();
        }
    }


}
