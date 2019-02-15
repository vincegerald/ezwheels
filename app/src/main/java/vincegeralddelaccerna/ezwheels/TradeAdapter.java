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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TradeAdapter extends RecyclerView.Adapter<TradeAdapter.TradeViewHolder> {

    private Context mContext;
    private List<Trade> mUploads;
    DatabaseReference getShop, getBuyer, getAdmin;
    private static double lat, lon;
    private static String name;


    //firebase
    private FirebaseAuth mAuth;

    public TradeAdapter(Context context, List<Trade> uploads) {
        mContext = context;
        mUploads = uploads;
    }


    @NonNull
    @Override
    public TradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.alllayout, parent, false);

        return new TradeAdapter.TradeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TradeAdapter.TradeViewHolder holder, int position) {
        final Trade uploadCurrent = mUploads.get(position);
        holder.list.setText(uploadCurrent.getBrand() + " " + uploadCurrent.getModel() + "(" + uploadCurrent.getYear() + ")");
        holder.shop.setText(uploadCurrent.getFbrand() + " " + uploadCurrent.getFmodel() + "("  +uploadCurrent.getFyear() + ")");
        holder.date.setText(uploadCurrent.getName());
        holder.type.setText(uploadCurrent.getType());
        Picasso.get().load(uploadCurrent.getImage1()).fit().centerCrop().into(holder.image);
        holder.price.setText(uploadCurrent.getPriceList());
        holder.status.setText(uploadCurrent.getStatus());

        if(uploadCurrent.getStatus().equals("PENDING")){
            holder.status.setTextColor(Color.parseColor("#ffa500"));
        }
        else if(uploadCurrent.getStatus().equals("APPROVED")){
            holder.status.setTextColor(Color.parseColor("#008000"));
        }
        else{
            holder.status.setTextColor(Color.parseColor("#ff0000"));
        }
        mAuth = FirebaseAuth.getInstance();
        String id = mAuth.getUid().toString();

        if(uploadCurrent.getType().equals("I WILL ADD")){
            holder.offer.setVisibility(View.VISIBLE);
            holder.add.setVisibility(View.VISIBLE);
            holder.logoPeso.setVisibility(View.VISIBLE);
            holder.add.setText(uploadCurrent.getAddPrice());
        }
        else if(uploadCurrent.getType().equals("SHOP WILL ADD")){

            holder.shopaad.setVisibility(View.VISIBLE);
            holder.offer.setVisibility(View.VISIBLE);
            holder.logoPeso.setVisibility(View.VISIBLE);
            holder.shopaad.setText(uploadCurrent.getShopAddPrice());
        }
        else{
            holder.offer.setVisibility(View.VISIBLE);
            holder.logoPeso.setVisibility(View.GONE);
        }

        if(uploadCurrent.getShopuid().equals(mAuth.getCurrentUser().getUid())){

            holder.offer.setVisibility(View.GONE);
            holder.toffer.setVisibility(View.VISIBLE);
            if(uploadCurrent.getType().equals("SWAP")){
                holder.type.setText(uploadCurrent.getType());
            }
            else if(uploadCurrent.getType().equals("I WILL ADD")){
                holder.type.setText("BUYER WILL ADD");
                holder.add.setTextColor(Color.parseColor("#008000"));
            }
            else{
                holder.type.setText("YOU WILL ADD");
                holder.shopaad.setTextColor(Color.parseColor("#FF0000"));
            }

        }

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
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TradeScrolling.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lon", lon);
                intent.putExtra("shopname", name);
                intent.putExtra("listId", uploadCurrent.getListingid());
                intent.putExtra("brand", uploadCurrent.getBrand());
                intent.putExtra("model", uploadCurrent.getModel());
                intent.putExtra("tid", uploadCurrent.getTid());
                intent.putExtra("image1", uploadCurrent.getImage1());
                intent.putExtra("price", uploadCurrent.getPriceList());
                intent.putExtra("uid", uploadCurrent.getUid());
                intent.putExtra("year", uploadCurrent.getYear());
                mContext.startActivity(intent);
            }
        });





    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class TradeViewHolder extends RecyclerView.ViewHolder{

        public TextView list, shop, date, type, type1, status, price, offer, add, shopaad, toffer;
        public ImageView image, logoPeso;
        public CardView item;

        public TradeViewHolder(View itemView) {
            super(itemView);

            list = itemView.findViewById(R.id.finalList);
            shop = itemView.findViewById(R.id.finalShop);
            date = itemView.findViewById(R.id.finalDate);
            image = itemView.findViewById(R.id.finalImage);
            item = itemView.findViewById(R.id.finalCard);
            type = itemView.findViewById(R.id.finalType);
            status = itemView.findViewById(R.id.finalStatus);
            price = itemView.findViewById(R.id.finalPrice);
            offer = itemView.findViewById(R.id.Offer);
            add = itemView.findViewById(R.id.uadd);
            shopaad = itemView.findViewById(R.id.sadd);
            logoPeso = itemView.findViewById(R.id.logoPeso);
            toffer = itemView.findViewById(R.id.offer);
            type1 = itemView.findViewById(R.id.finalType1);

        }
    }
}
