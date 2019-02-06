package vincegeralddelaccerna.ezwheels;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;
import static vincegeralddelaccerna.ezwheels.App.myReservations;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShopmyReservation extends Fragment {


    public ShopmyReservation() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    ImageView brokencar;
    TextView nolisting;
    FloatingActionButton add;
    DatabaseReference databaseReference, databaseReference1;
    FirebaseAuth mAuth;

    private ProgressBar mProgressbar;

    ReservationAdapter mAdapter;
    private static String uid;

    NotificationManagerCompat notificationManagerCompat;
    private List<Reservation> mUploads;

    public void PushNotification(String title, String content) {
        Intent notificationIntent = new Intent(getActivity(), ShopDashboard.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getActivity(),0,notificationIntent,0);
        Notification notification = new NotificationCompat.Builder(getContext(), myReservations)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(contentIntent)
                .build();

        notificationManagerCompat.notify(1, notification);



//        NotificationCompat.Builder builder = new NotificationCompat.Builder()
//
//
//        //set
//        builder.setContentIntent(contentIntent);
//        builder.setSmallIcon(R.drawable.logo);
//        builder.setContentText(content);
//        builder.setContentTitle(title);
//        builder.setAutoCancel(true);
//        builder.setDefaults(Notification.PRIORITY_MAX);
//
//
//
//        Notification notification = builder.build();
//        nm.notify((int)System.currentTimeMillis(),notification);
//        NotificationManager nm = (NotificationManager)getActivity().getSystemService(NOTIFICATION_SERVICE);
//        Notification.Builder builder = new Notification.Builder(getActivity());
//        Intent notificationIntent = new Intent(getActivity(), ShopDashboard.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(getActivity(),0,notificationIntent,0);

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_shopmy_reservation, container, false);

        recyclerView = v.findViewById(R.id.recyclerRequest);
        mProgressbar = v.findViewById(R.id.progress);
        recyclerView.setHasFixedSize(true);
        notificationManagerCompat = NotificationManagerCompat.from(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        brokencar = v.findViewById(R.id.brokencar);
        nolisting = v.findViewById(R.id.nolisting);
        mUploads = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        String id = mAuth.getCurrentUser().getUid();
        Query query = FirebaseDatabase.getInstance().getReference("Reservation")
                .orderByChild("uid").equalTo(id);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser =  mAuth.getCurrentUser();
        uid = currentUser.getUid();
//        if(currentUser != null){
//            databaseReference = FirebaseDatabase.getInstance().getReference("Reservation");
//            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    if(dataSnapshot.exists()){
//                        String id = dataSnapshot.child("uid").getValue().toString();
//                        if(id.equals(uid)){
//                            String model = dataSnapshot.child("model").getValue().toString();
//                            String brand = dataSnapshot.child("brand").getValue().toString();
//                            String status = dataSnapshot.child("status").getValue().toString();
//                            if(status.equals("APPROVED")){
//                                PushNotification("Reservation Approved","Your reservation for " + brand + " " + model + " has been approved by the shop");
//                            }
//                            else{
//                                PushNotification("Reservation Declined","Your reservation for " + brand + " " + model + " has been declined by the shop. For more details contact the shop");
//                            }
//                        }
//                    }
//                    else{
//                        databaseReference1 = FirebaseDatabase.getInstance().getReference("Buyers").child(uid);
//                        databaseReference1.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                if(dataSnapshot.exists()){
//
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//                                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        }


//        query.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                if(dataSnapshot.exists()){
//
//                }
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUploads.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot :dataSnapshot.getChildren()){
                        Reservation reservation = snapshot.getValue(Reservation.class);
                        String status = reservation.getStatus();
                        if(status.equals("APPROVED")){
                            PushNotification("Reservation Approved","Your reservation for " + reservation.getBrand() + " " + reservation.getModel() + " has been approved by the shop... Contact the shop for further details");
                        }
                        else if(status.equals("DECLINED")){
                            PushNotification("Reservation Declined","Your reservation for " + reservation.getBrand() + " " + reservation.getModel() + " has been declined by the shop... Contact the shop for further details");
                        }
                        mUploads.add(reservation);
                    }

                    mAdapter = new ReservationAdapter(getActivity(), mUploads);
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


