package vincegeralddelaccerna.ezwheels;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentHolder> {

    private Context mContext;
    private List<Payments> mUploads;
    DatabaseReference getListing, getMotor;

    FirebaseAuth mAuth;
    DatabaseReference getShop, getBuyer, getAdmin;
    private static double lat, lon;


    public PaymentAdapter(Context context, List<Payments> uploads) {
        mContext = context;
        mUploads = uploads;
    }


    @NonNull
    @Override
    public PaymentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.alllayout, parent, false);

        return new PaymentAdapter.PaymentHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final PaymentAdapter.PaymentHolder holder, int position) {
        final Payments uploadCurrent = mUploads.get(position);
        mAuth = FirebaseAuth.getInstance();
        getBuyer = FirebaseDatabase.getInstance().getReference("Buyer");
        getShop = FirebaseDatabase.getInstance().getReference("Shop");
        getAdmin = FirebaseDatabase.getInstance().getReference("Admin");
        holder.image.setVisibility(View.GONE);
        holder.type.setVisibility(View.GONE);
        holder.textStatus.setVisibility(View.GONE);
        holder.logoPeso.setVisibility(View.GONE);
        holder.imageView.setVisibility(View.GONE);
        holder.status.setVisibility(View.GONE);
        //holder.shop.setVisibility(uploadCurrent);
        holder.list.setText(uploadCurrent.getType());
        holder.price.setText(uploadCurrent.getAmount());
        holder.date.setText(uploadCurrent.getDate());

        if(mAuth.getCurrentUser().getUid().equals(uploadCurrent.getShopuid())){
            getBuyer.child(uploadCurrent.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        String firstname = dataSnapshot.child("firstname").getValue().toString();
                        String lastname = dataSnapshot.child("lastname").getValue().toString();
                        holder.shop.setText("Sender: " + firstname + " " + lastname);
                    }
                    else{
                        getShop.child(uploadCurrent.getUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    String firstname = dataSnapshot.child("firstname").getValue().toString();
                                    String lastname = dataSnapshot.child("lastname").getValue().toString();
                                    holder.shop.setText("Sender: " + firstname + " " + lastname);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(mContext, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(mContext, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        if(mAuth.getCurrentUser().getUid().equals(uploadCurrent.getUid())){
            getShop.child(uploadCurrent.getShopuid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        String firstname = dataSnapshot.child("firstname").getValue().toString();
                        String lastname = dataSnapshot.child("lastname").getValue().toString();
                        lat = Double.parseDouble(dataSnapshot.child("lat").getValue().toString());
                        lon = Double.parseDouble(dataSnapshot.child("lon").getValue().toString());
                        holder.shop.setText("Sent to: " + firstname + " " + lastname);
                    }
                    else{
                        getAdmin.child(uploadCurrent.getShopuid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    String name = dataSnapshot.child("name").getValue().toString();
                                    holder.shop.setText("Sent to: " + name);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(mContext, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(mContext, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PaymentScrolling.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lon", lon);
                intent.putExtra("pid", uploadCurrent.getId());
                intent.putExtra("uid", uploadCurrent.getUid());
                intent.putExtra("shopuid", uploadCurrent.getShopuid());
                mContext.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class PaymentHolder extends RecyclerView.ViewHolder{

        public TextView list, shop, date, type, status, price, textStatus, finalStatus;
        public ImageView image, logoPeso, imageView;
        public CardView item;

        public PaymentHolder(View itemView) {
            super(itemView);

            list = itemView.findViewById(R.id.finalList);
            shop = itemView.findViewById(R.id.finalShop);
            date = itemView.findViewById(R.id.finalDate);
            image = itemView.findViewById(R.id.finalImage);
            item = itemView.findViewById(R.id.finalCard);
            type = itemView.findViewById(R.id.finalType);
            status = itemView.findViewById(R.id.finalStatus);
            price = itemView.findViewById(R.id.finalPrice);
            logoPeso = itemView.findViewById(R.id.logoPeso);
            textStatus = itemView.findViewById(R.id.textStatus);
            imageView = itemView.findViewById(R.id.imageView6);
            finalStatus = itemView.findViewById(R.id.finalStatus);

        }
    }
}
