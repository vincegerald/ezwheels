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
public class ShopmyTrades extends Fragment {


    public ShopmyTrades() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    FloatingActionButton add;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    private ProgressBar mProgressbar;

    TradeAdapter mAdapter;

    private List<Trade> mUploads;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_shopmy_trades, container, false);

        recyclerView = v.findViewById(R.id.recyclerRequest);
        mProgressbar = v.findViewById(R.id.progress);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mUploads = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        String id = mAuth.getCurrentUser().getUid();
        Query query = FirebaseDatabase.getInstance().getReference("Trade")
                .orderByChild("uid").equalTo(id);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot :dataSnapshot.getChildren()){

                        Trade trade = snapshot.getValue(Trade.class);
                        mUploads.add(trade);
                    }

                    mAdapter = new TradeAdapter(getActivity(), mUploads);
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
