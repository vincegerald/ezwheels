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

import com.squareup.picasso.Picasso;

import java.util.List;

public class TradeAdapter extends RecyclerView.Adapter<TradeAdapter.TradeViewHolder> {

    private Context mContext;
    private List<Trade> mUploads;

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
        if(uploadCurrent.getType().equals("I WILL ADD")){
            holder.uadd.setVisibility(View.VISIBLE);
            holder.saad.setVisibility(View.GONE);
            holder.uadd.setText(uploadCurrent.getAddPrice());
        }
        else{
            holder.uadd.setVisibility(View.GONE);
            holder.saad.setVisibility(View.VISIBLE);
            holder.saad.setText(uploadCurrent.getShopAddPrice());
        }

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class TradeViewHolder extends RecyclerView.ViewHolder{

        public TextView list, shop, date, type, status, price, offer, uadd, saad;
        public ImageView image;
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
            uadd = itemView.findViewById(R.id.uadd);
            saad = itemView.findViewById(R.id.sadd);


        }
    }
}
