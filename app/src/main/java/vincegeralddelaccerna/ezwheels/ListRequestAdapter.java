package vincegeralddelaccerna.ezwheels;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ListRequestAdapter extends RecyclerView.Adapter<ListRequestAdapter.ListRequestViewHolder> {

    private Context mContext;
    private List<Upload> mUploads;

    public ListRequestAdapter(Context context, List<Upload> uploads){
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public ListRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false);

        return new ListRequestViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListRequestViewHolder holder, int position) {

        final Upload uploadCurrent = mUploads.get(position);
        holder.brand.setText(uploadCurrent.getFinalBrand());
        holder.model.setText(uploadCurrent.getFinalModel());
        holder.price.setText(uploadCurrent.getFinalPrice());
        holder.d1.setText(uploadCurrent.getFinalColor());
        holder.d2.setText(uploadCurrent.getFinalMileage());
        holder.d3.setText(uploadCurrent.getFinalTransmission());
        holder.d4.setText(uploadCurrent.getFinalYear());
        holder.price.setText(uploadCurrent.getFinalPrice());
        Picasso.get().load(uploadCurrent.getImagePath1()).fit().centerCrop().into(holder.image);

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, uploadCurrent.getFinalModel(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, Detailed_Listing.class);
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
                mContext.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ListRequestViewHolder extends RecyclerView.ViewHolder{

        public TextView brand, model, price, d1, d2, d3, d4, seller;
        public ImageView image;
        public CardView item;

        public ListRequestViewHolder(View itemView) {
            super(itemView);

            brand = itemView.findViewById(R.id.textView2);
            model = itemView.findViewById(R.id.textViewmodel);
            price = itemView.findViewById(R.id.textView4);
            d1 = itemView.findViewById(R.id.textView5);
            d2 = itemView.findViewById(R.id.textView6);
            d3 = itemView.findViewById(R.id.textView7);
            d4 = itemView.findViewById(R.id.textView8);
            seller = itemView.findViewById(R.id.textView9);
            image = itemView.findViewById(R.id.imageView7);
            item = itemView.findViewById(R.id.itemList);


        }
    }
}
