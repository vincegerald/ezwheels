package vincegeralddelaccerna.ezwheels;

import android.content.Context;
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
        holder.brand.setText(uploadCurrent.getFinalBrand());
        holder.price.setText(uploadCurrent.getFinalPrice());
        holder.d1.setText(uploadCurrent.getFinalColor());
        holder.d2.setText(uploadCurrent.getFinalMileage());
        holder.d3.setText(uploadCurrent.getFinalTransmission());
        holder.d4.setText(uploadCurrent.getFinalYear());
        holder.price.setText(uploadCurrent.getFinalPrice());
        Picasso.get().load(uploadCurrent.getImagePath1()).fit().centerCrop().into(holder.image);

    }

    @Override
    public int getItemCount() {
         return mUploads.size();
    }

    public class DashboardCarViewHolder extends RecyclerView.ViewHolder{

        public TextView brand, model, price, d1, d2, d3, d4, seller;
        public ImageView image;
        public CardView item;

        public DashboardCarViewHolder(View itemView) {
            super(itemView);

            brand = itemView.findViewById(R.id.finalModel);
            price = itemView.findViewById(R.id.finalPrice);
            d1 = itemView.findViewById(R.id.finalDetails1);
            d2 = itemView.findViewById(R.id.finalDetails2);
            d3 = itemView.findViewById(R.id.finalDetails3);
            d4 = itemView.findViewById(R.id.finalDetails4);
            seller = itemView.findViewById(R.id.finalSeller);
            image = itemView.findViewById(R.id.finalImage);
            item = itemView.findViewById(R.id.finalCard);


        }
    }
}
