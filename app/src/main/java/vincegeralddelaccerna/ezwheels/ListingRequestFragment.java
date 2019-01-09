package vincegeralddelaccerna.ezwheels;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ListingRequestFragment extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    FloatingActionButton add;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    FirebaseRecyclerOptions<Upload> options;
    FirebaseRecyclerAdapter<Upload, ListRequestHolder> adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.listingrequest_fragment, null);
        recyclerView = v.findViewById(R.id.recyclerRequest);
        recyclerView.setHasFixedSize(true);

        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getUid().toString();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(uid).child("Listing Request");
        add = v.findViewById(R.id.fab);
        add.setOnClickListener(this);

        options = new FirebaseRecyclerOptions.Builder<Upload>()
                .setQuery(databaseReference, Upload.class).build();

        adapter = new FirebaseRecyclerAdapter<Upload, ListRequestHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ListRequestHolder holder, int position, @NonNull Upload model) {

                Picasso.get().load(model.getImagePath1()).into(holder.image1, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }   
                });

                holder.name.setText(model.getFinalBrand());
                holder.price.setText(model.getFinalPrice());
                holder.d1.setText(model.getFinalColor());
                holder.d2.setText(model.getFinalMileage());
                holder.d3.setText(model.getFinalTransmission());
                holder.d4.setText(model.getFinalYear());
                holder.s1.setText(model.getFinalPrice());


            }

            @NonNull
            @Override
            public ListRequestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent,false);

                return new ListRequestHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);
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

    @Override
    public void onStart() {
        super.onStart();

        if(adapter != null){
            adapter.startListening();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapter != null){
            adapter.startListening();
        }
    }

    @Override
    public void onStop() {
        if(adapter != null){
            adapter.startListening();
        }
        super.onStop();
    }
}
