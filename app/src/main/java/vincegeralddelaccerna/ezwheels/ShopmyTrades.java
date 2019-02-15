package vincegeralddelaccerna.ezwheels;


import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import static vincegeralddelaccerna.ezwheels.App.myReservations;
import static vincegeralddelaccerna.ezwheels.App.myTrades;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShopmyTrades extends Fragment {


    public ShopmyTrades() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    FloatingActionButton add;
    DatabaseReference databaseReference1;
    FirebaseAuth mAuth;
    ImageView brokencar;
    TextView nolisting;

    private ProgressBar mProgressbar;

    TradeAdapter mAdapter;
    NotificationManagerCompat notificationManagerCompat;

    private List<Trade> mUploads;

    public void PushNotification(String title, String content) {
        Intent notificationIntent = new Intent(getActivity(), ShopDashboard.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getActivity(),0,notificationIntent,0);
        Notification notification = new NotificationCompat.Builder(getContext(), myTrades)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(contentIntent)
                .setStyle(new NotificationCompat.BigTextStyle()
                 .bigText(content))
                .build();

        notificationManagerCompat.notify(3, notification);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_shopmy_trades, container, false);

        recyclerView = v.findViewById(R.id.recyclerRequest);
        mProgressbar = v.findViewById(R.id.progress);
        recyclerView.setHasFixedSize(true);
            notificationManagerCompat = NotificationManagerCompat.from(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mUploads = new ArrayList<>();
        brokencar = v.findViewById(R.id.brokencar);
        nolisting = v.findViewById(R.id.nolisting);

        databaseReference1 = FirebaseDatabase.getInstance().getReference("Reservation");
        mAuth = FirebaseAuth.getInstance();
        String id = mAuth.getCurrentUser().getUid();
        Query query = FirebaseDatabase.getInstance().getReference("Trade")
                .orderByChild("uid").equalTo(id);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUploads.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Trade trade = snapshot.getValue(Trade.class);
                        if(trade.getStatus().equals("APPROVED") && trade.getFromSeen().equals("false") && trade.getUid().equals(mAuth.getCurrentUser().getUid())){
                            PushNotification("Trade Approved","Your trade for " + trade.getBrand() + " " + trade.getModel() + " has been approved by the shop... Contact the shop for further details");
                            databaseReference1.child(trade.getTid()).child("fromSeen").setValue("true");
                        }
                        else if(trade.getStatus().equals("DECLINED") && trade.getFromSeen().equals("false") && trade.getUid().equals(mAuth.getCurrentUser().getUid())){
                            PushNotification("Trade Declined","Your trade for " + trade.getBrand() + " " + trade.getModel() + " has been declined by the shop... Contact the shop for further details");
                            databaseReference1.child(trade.getTid()).child("fromSeen").setValue("true");
                        }
                        mUploads.add(trade);
                    }

                    mAdapter = new TradeAdapter(getActivity(), mUploads);
                    recyclerView.setAdapter(mAdapter);
                    mProgressbar.setVisibility(View.INVISIBLE);
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
            }
        });

        return v;
    }

}
