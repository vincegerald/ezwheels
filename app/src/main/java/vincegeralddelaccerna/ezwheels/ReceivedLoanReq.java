package vincegeralddelaccerna.ezwheels;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
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

public class ReceivedLoanReq extends Fragment {



    public ReceivedLoanReq(){

    }

    RecyclerView recyclerView;
    FloatingActionButton add;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    ImageView brokencar;
    TextView nolisting;
    //SearchView searchView;
    Query query;

    private ProgressBar mProgressbar;

    LoanReqAdapter mAdapter;

    private List<LoanReq> mUploads;
    //ArrayList<Upload> temp = new ArrayList<Upload>(mUploads);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_received_loan_req, container, false);
        recyclerView = v.findViewById(R.id.recyclerRequest);
        mProgressbar = v.findViewById(R.id.progress);
        brokencar = v.findViewById(R.id.brokencar);
        nolisting = v.findViewById(R.id.nolisting);
        //searchView = v.findViewById(R.id.search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mUploads = new ArrayList<>();


        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid().toString();
        query = FirebaseDatabase.getInstance().getReference("Loan Requests")
                .orderByChild("shopUid").equalTo(mAuth.getCurrentUser().getUid());

        //        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        //        //            @Override
        //        //            public boolean onQueryTextSubmit(String s) {
        //        //                for(int i = 0; i <= mUploads.size(); i++){
        //        //                    Upload upload = new Upload();
        //        //                    String searchedName = mUploads.get(i).getFinalBrand().concat(mUploads.get(i).getFinalModel());
        //        //                    if(searchedName.contains(s)){
        //        //                        temp.add(mUploads.add(upload(i)));
        //        //                    }
        //        //                }
        //        //                return false;
        //        //            }
        //        //
        //        //            @Override
        //        //            public boolean onQueryTextChange(String s) {
        //        //
        //        //                return false;
        //        //            }
        //        //        });




        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUploads.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                        LoanReq loanReq = postSnapshot.getValue(LoanReq.class);
                        mUploads.add(loanReq);
                    }

                    mAdapter = new LoanReqAdapter(getActivity(), mUploads);
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

//    private void search(String s){
//
//        Query query = databaseReference.orderByChild("brand")
//                .startAt(s)
//                .endAt(s + "\uf8ff");
//
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
}
