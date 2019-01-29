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

import com.squareup.picasso.Picasso;

import java.util.List;

public class DashboardCarAdapter extends RecyclerView.Adapter<DashboardCarAdapter.DashboardCarViewHolder> {

    private Context mContext;
    private List<Upload> mUploads;
    private List<Shop> shop;

    public DashboardCarAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public DashboardCarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.dashboarditem_layout, parent, false);

        return new DashboardCarViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardCarViewHolder holder, int position) {

        final Upload uploadCurrent = mUploads.get(position);
//        final Shop pos = shop.get(position);
        holder.brand.setText(uploadCurrent.getFinalBrand() + " "+ uploadCurrent.getFinalModel());
        holder.price.setText(uploadCurrent.getFinalPrice());
        holder.d3.setText(uploadCurrent.getFinalColor());
        holder.d4.setText(uploadCurrent.getFinalYear());
        holder.price.setText(uploadCurrent.getFinalPrice());
        Picasso.get().load(uploadCurrent.getImagePath1()).fit().centerCrop().into(holder.image);
        holder.date.setText(uploadCurrent.getDate());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ScrollingActivity.class);
                intent.putExtra("image_url1", uploadCurrent.getImage());
                intent.putExtra("image_url2", uploadCurrent.getImagePath1());
                intent.putExtra("image_url3", uploadCurrent.getImagePath2());
                intent.putExtra("image_url4", uploadCurrent.getImagePath3());
                intent.putExtra("videoUrl", uploadCurrent.getVideoPath());
                intent.putExtra("brand", uploadCurrent.getFinalBrand());
                intent.putExtra("model", uploadCurrent.getFinalModel());
                intent.putExtra("year", uploadCurrent.getFinalYear());
                intent.putExtra("color", uploadCurrent.getFinalColor());
                intent.putExtra("transmission", uploadCurrent.getFinalTransmission());
                intent.putExtra("pricecondition", uploadCurrent.getFinalPcondition());
                intent.putExtra("mileage", uploadCurrent.getFinalMileage());
                intent.putExtra("price", uploadCurrent.getFinalPrice());
                intent.putExtra("uid", uploadCurrent.getUid());
                intent.putExtra("fuel", uploadCurrent.getFuel());
                intent.putExtra("date", uploadCurrent.getDate());
                intent.putExtra("edition", uploadCurrent.getEdition());
                intent.putExtra("info", uploadCurrent.getInfo());
                intent.putExtra("series", uploadCurrent.getSeries());
                intent.putExtra("listingid", uploadCurrent.getListid());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
         return mUploads.size();
    }

    public class DashboardCarViewHolder extends RecyclerView.ViewHolder{

        public TextView brand, model, price, d1, d2, d3, d4, date;
        public ImageView image;
        public CardView item;

        public DashboardCarViewHolder(View itemView) {
            super(itemView);

            brand = itemView.findViewById(R.id.finalModel);
            price = itemView.findViewById(R.id.finalPrice);
            d3 = itemView.findViewById(R.id.finalDetails3);
            d4 = itemView.findViewById(R.id.finalDetails4);
            date = itemView.findViewById(R.id.finalDate);
            image = itemView.findViewById(R.id.finalImage);
            item = itemView.findViewById(R.id.finalCard);


        }
    }
}
