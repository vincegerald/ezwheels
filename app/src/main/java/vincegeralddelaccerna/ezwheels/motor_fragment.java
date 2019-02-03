package vincegeralddelaccerna.ezwheels;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class motor_fragment extends Fragment {

    RecyclerView recyclerView;
    FloatingActionButton add;

    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    private ProgressBar mProgressbar;

    DashboardCarAdapter mAdapter;

    private List<Upload> mUploads;

    public motor_fragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.motor_fragment, container, false);
        recyclerView = v.findViewById(R.id.recyclerRequest);
        mProgressbar = v.findViewById(R.id.progress);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mUploads = new ArrayList<>();


        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();

        //databaseReference = FirebaseDatabase.getInstance();



        Query query = FirebaseDatabase.getInstance().getReference("Motor")
                .orderByChild("status").equalTo("AVAILABLE");

        query.addValueEventListener(new ValueEventListener() {
            @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                            Upload upload = postSnapshot.getValue(Upload.class);
                            mUploads.add(upload);

                }

                mAdapter = new DashboardCarAdapter(getActivity(), mUploads);
                recyclerView.setAdapter(mAdapter);
                mProgressbar.setVisibility(View.INVISIBLE);

            }

            private void addShopDetails(String shopId) {
                FirebaseDatabase.getInstance().getReference("Shop").child(shopId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String shopName = dataSnapshot.child("name").getValue().toString();
                        Toast.makeText(getActivity(), shopName, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressbar.setVisibility(View.INVISIBLE);
            }
        });
        return v;
    }
}
