package vincegeralddelaccerna.ezwheels;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {

    private Context mContext;
    private List<Favorites> mUploads;
    DatabaseReference databaseReference;
    private static double lat, lon;

    //firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;
    private static String id;


    public FavoritesAdapter(Context context, List<Favorites> uploads) {
        mContext = context;
        mUploads = uploads;
    }


    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.dashboarditem_layout, parent, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("Shop");

        return new FavoritesAdapter.FavoritesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoritesAdapter.FavoritesViewHolder holder, int position) {
        final Favorites uploadCurrent = mUploads.get(position);
        id = uploadCurrent.getUid();
        holder.stat.setVisibility(View.GONE);
        holder.brand.setText(uploadCurrent.getBrand() + " "+ uploadCurrent.getModel());
        holder.price.setText(uploadCurrent.getPrice());
        holder.d3.setText(uploadCurrent.getColor());
        holder.d4.setText(uploadCurrent.getYear());
        Picasso.get().load(uploadCurrent.getImage1()).fit().centerCrop().into(holder.image);

        databaseReference.child(uploadCurrent.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    float rating = Float.parseFloat(dataSnapshot.child("rating").getValue().toString());
                    String rate = String.format("%,.1f", rating);
                    lat = Double.parseDouble(dataSnapshot.child("lat").getValue().toString());
                    lon = Double.parseDouble(dataSnapshot.child("lon").getValue().toString());
                    holder.date.setText(uploadCurrent.getName() + " (" + rate + ")");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ScrollingActivity1.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lon", lon);
                intent.putExtra("listId", uploadCurrent.getListingid());
                intent.putExtra("brand", uploadCurrent.getBrand());
                intent.putExtra("model", uploadCurrent.getModel());
                intent.putExtra("fid", uploadCurrent.getFid());
                mContext.startActivity(intent);
            }
        });

    }


//    private void getData(){
//        mAuth = FirebaseAuth.getInstance();
//        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
//        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Shop").child(uid);
//
//    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class FavoritesViewHolder extends RecyclerView.ViewHolder{

        public TextView brand, model, price, d1, d2, d3, d4, date, stat;
        public ImageView image;
        public CardView item;


        public FavoritesViewHolder(View itemView) {
            super(itemView);

            brand = itemView.findViewById(R.id.finalModel);
            price = itemView.findViewById(R.id.finalPrice);
            d3 = itemView.findViewById(R.id.finalDetails3);
            d4 = itemView.findViewById(R.id.finalDetails4);
            date = itemView.findViewById(R.id.finalDate);
            image = itemView.findViewById(R.id.finalImage);
            item = itemView.findViewById(R.id.finalCard);
            stat = itemView.findViewById(R.id.status);


        }
    }
}
