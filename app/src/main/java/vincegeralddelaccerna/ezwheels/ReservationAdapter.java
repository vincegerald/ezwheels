package vincegeralddelaccerna.ezwheels;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder> {

    private Context mContext;
    private List<Reservation> mUploads;
    DatabaseReference getShop;
    private static double lat, lon;
    private static String name;


    public ReservationAdapter(Context context, List<Reservation> uploads) {
        mContext = context;
        mUploads = uploads;
    }


    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.alllayout, parent, false);

        return new ReservationAdapter.ReservationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationAdapter.ReservationViewHolder holder, int position) {
        final Reservation uploadCurrent = mUploads.get(position);
        holder.list.setText(uploadCurrent.getBrand() + " " + uploadCurrent.getModel());
        holder.shop.setText(uploadCurrent.getName());
        holder.date.setText(uploadCurrent.getCurrentDate() + " : " + uploadCurrent.getCurrentTime());
        holder.type.setText(uploadCurrent.getType());
        Picasso.get().load(uploadCurrent.getImage1()).fit().centerCrop().into(holder.image);
        holder.price.setText(uploadCurrent.getPrice());
        holder.status.setText(uploadCurrent.getStatus());
        holder.logoPeso.setVisibility(View.GONE);
        getShop = FirebaseDatabase.getInstance().getReference("Shop");
        getShop.child(uploadCurrent.getShopuid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    lat = Double.parseDouble(dataSnapshot.child("lat").getValue().toString());
                    lon = Double.parseDouble(dataSnapshot.child("lon").getValue().toString());
                    name = dataSnapshot.child("name").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(mContext, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        if(uploadCurrent.getStatus().equals("PENDING")){
            holder.status.setTextColor(Color.parseColor("#ffa500"));
        }
        else if(uploadCurrent.getStatus().equals("APPROVED")){
            holder.status.setTextColor(Color.parseColor("#008000"));
        }
        else if(uploadCurrent.getStatus().equals("DECLINED")){
            holder.status.setTextColor(Color.parseColor("#FF0000"));
        }
        else{
            holder.status.setTextColor(Color.parseColor("#528aed"));
        }
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ReservationScrolling.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lon", lon);
                intent.putExtra("shopname", name);
                intent.putExtra("resId", uploadCurrent.getResId());
                intent.putExtra("listingId", uploadCurrent.getListid());
                intent.putExtra("shopId", uploadCurrent.getShopuid());
                intent.putExtra("uid", uploadCurrent.getUid());
                intent.putExtra("brand", uploadCurrent.getBrand());
                intent.putExtra("model", uploadCurrent.getModel());
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ReservationViewHolder extends RecyclerView.ViewHolder{

        public TextView list, shop, date, type, status, price;
        public ImageView image, logoPeso;
        public CardView item;

        public ReservationViewHolder(View itemView) {
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


        }
    }
}
