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

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {

    private Context mContext;
    private List<Favorites> mUploads;

    public FavoritesAdapter(Context context, List<Favorites> uploads) {
        mContext = context;
        mUploads = uploads;
    }


    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.dashboarditem_layout, parent, false);

        return new FavoritesAdapter.FavoritesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.FavoritesViewHolder holder, int position) {
        final Favorites uploadCurrent = mUploads.get(position);
        holder.brand.setText(uploadCurrent.getBrand() + " "+ uploadCurrent.getModel());
        holder.price.setText(uploadCurrent.getPrice());
        holder.d3.setText(uploadCurrent.getColor());
        holder.d4.setText(uploadCurrent.getYear());
        Picasso.get().load(uploadCurrent.getImage1()).fit().centerCrop().into(holder.image);
        holder.date.setText(uploadCurrent.getName());

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class FavoritesViewHolder extends RecyclerView.ViewHolder{

        public TextView brand, model, price, d1, d2, d3, d4, date;
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


        }
    }
}
