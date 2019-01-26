package vincegeralddelaccerna.ezwheels;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TradeAdapter extends RecyclerView.Adapter<TradeAdapter.TradeViewHolder> {

    private Context mContext;
    private List<Trade> mUploads;

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
        holder.list.setText(uploadCurrent.getBrand() + " " + uploadCurrent.getModel());
        holder.shop.setText(uploadCurrent.getFbrand() + " " + uploadCurrent.getFmodel());
        holder.date.setText(uploadCurrent.getName());
        holder.type.setText(uploadCurrent.getType());
        Picasso.get().load(uploadCurrent.getImage1()).fit().centerCrop().into(holder.image);
        holder.price.setText(uploadCurrent.getPriceList());
        holder.status.setText(uploadCurrent.getStatus());

        if(uploadCurrent.getStatus().equals("PENDING")){
            holder.status.setTextColor(Color.parseColor("#ffa500"));
        }
        else if(uploadCurrent.getStatus().equals("ACCEPTED")){
            holder.status.setTextColor(Color.parseColor("#008000"));
        }
        else{
            holder.status.setTextColor(Color.parseColor("#FF0000"));
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

        if(id.equals(uploadCurrent.getShopuid())){
            holder.toffer.setVisibility(View.VISIBLE);
            holder.offer.setVisibility(View.GONE);
            holder.offer.setVisibility(View.GONE);

            if(uploadCurrent.getType().equals("I WILL ADD")){

            }

            else if(uploadCurrent.getType().equals("SHOP WILL ADD")){
                holder.type1.setVisibility(View.VISIBLE);
            }
        }



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
