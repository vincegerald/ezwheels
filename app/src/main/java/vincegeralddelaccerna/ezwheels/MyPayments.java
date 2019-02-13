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


import static vincegeralddelaccerna.ezwheels.App.reservationReceived;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyPayments extends Fragment {


    public MyPayments() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    FloatingActionButton add;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    ImageView brokencar;
    TextView nolisting;

    private ProgressBar mProgressbar;

    PaymentAdapter mAdapter;
    NotificationManagerCompat notificationManagerCompat;

    private List<Payments> mUploads;

//    public void PushNotification(String title, String content) {
//        Intent notificationIntent = new Intent(getActivity(), ShopDashboard.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(getActivity(),0,notificationIntent,0);
//        Notification notification = new NotificationCompat.Builder(getContext(), reservationReceived)
//                .setSmallIcon(R.drawable.logo)
//                .setContentTitle(title)
//                .setContentText(content)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
//                .setStyle(new NotificationCompat.BigTextStyle()
//                .bigText(content))
//                .setContentIntent(contentIntent)
//                .build();
//
//        notificationManagerCompat.notify(8, notification);
//
//
//
//    }


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
        brokencar = v.findViewById(R.id.brokencar);
        nolisting = v.findViewById(R.id.nolisting);
        notificationManagerCompat = NotificationManagerCompat.from(getContext());

        mAuth = FirebaseAuth.getInstance();
        final String id = mAuth.getCurrentUser().getUid();
        Query query = FirebaseDatabase.getInstance().getReference("Payments")
                .orderByChild("uid").equalTo(id);
        databaseReference = FirebaseDatabase.getInstance().getReference("Reservation");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUploads.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot :dataSnapshot.getChildren()){
                        Payments payments = snapshot.getValue(Payments.class);
//                        if(reservation.getStatus().equals("PENDING") && reservation.getSeen().equals("false") && reservation.getShopuid().equals(id)){
//                            PushNotification("New Reservation", "You have a new reservation.");
//                            databaseReference.child(reservation.getResId()).child("seen").setValue("true");
//                        }

                        mUploads.add(payments);
                    }


                    mAdapter = new PaymentAdapter(getActivity(), mUploads);
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
