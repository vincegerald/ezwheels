package vincegeralddelaccerna.ezwheels;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShopReceivedReservations extends Fragment {


    public ShopReceivedReservations() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    FloatingActionButton add;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    private ProgressBar mProgressbar;

    ReservationAdapter mAdapter;

    private List<Reservation> mUploads;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_shop_received_reservations, container, false);

        recyclerView = v.findViewById(R.id.recyclerRequest);
        mProgressbar = v.findViewById(R.id.progress);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mUploads = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        String id = mAuth.getCurrentUser().getUid();
        Query query = FirebaseDatabase.getInstance().getReference("Reservation")
                .orderByChild("shopuid").equalTo(id);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot :dataSnapshot.getChildren()){

                        Reservation reservation = snapshot.getValue(Reservation.class);
                        mUploads.add(reservation);
                    }

                    mAdapter = new ReservationAdapter(getActivity(), mUploads);
                    recyclerView.setAdapter(mAdapter);
                    mProgressbar.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

}