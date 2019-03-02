package vincegeralddelaccerna.ezwheels;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DashboardCarAdapter extends RecyclerView.Adapter<DashboardCarAdapter.DashboardCarViewHolder> implements Filterable {

    private Context mContext;
    private List<Upload> mUploads;
    private List<Upload> temp;
    DatabaseReference databaseReference;
    private String name;
    private static double lat, lon;
    private static String shopname;

    public DashboardCarAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
        temp = new ArrayList<>(mUploads);
    }

    @NonNull
    @Override
    public DashboardCarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.dashboarditem_layout, parent, false);

        return new DashboardCarViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final DashboardCarViewHolder holder, int position) {

        final Upload uploadCurrent = mUploads.get(position);
        holder.stat.setVisibility(View.GONE);
//        final Shop pos = shop.get(position);

        holder.brand.setText(uploadCurrent.getFinalBrand() + " "+ uploadCurrent.getFinalModel());

        holder.d3.setText(uploadCurrent.getFinalColor());
        holder.d4.setText(uploadCurrent.getFinalYear());

        Picasso.get().load(uploadCurrent.getImage()).fit().centerCrop().into(holder.image);
        holder.price.setText(uploadCurrent.getFinalPrice());
        databaseReference = FirebaseDatabase.getInstance().getReference("Shop").child(uploadCurrent.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    name = dataSnapshot.child("name").getValue().toString();
                    float rating  = Float.parseFloat(dataSnapshot.child("rating").getValue().toString());
                    String rate = String.format("%,.1f", rating);
                    lat = Double.parseDouble(dataSnapshot.child("lat").getValue().toString());
                    lon = Double.parseDouble(dataSnapshot.child("lon").getValue().toString());
                    holder.date.setText(name + " " +  "("  +rate+ ")");
                    shopname = dataSnapshot.child("name").getValue().toString();
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
                Intent intent = new Intent(mContext, ScrollingActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lon", lon);
                intent.putExtra("image_url1", uploadCurrent.getImage());
                intent.putExtra("shopname", shopname);
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
                intent.putExtra("status", uploadCurrent.getStatus());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
         return mUploads.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<Upload> filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0){
                filteredList.addAll(temp);
            }
            else{
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(Upload item : temp){
                    if(item.getFinalModel().concat(item.getDoors()).concat(item.getSeries()).concat(item.getCategory()).concat(item.getFinalMileage()).
                    concat(item.getFinalTransmission()).concat(item.getFinalBrand()).concat(item.getFuel()).concat(item.getFinalPrice()).concat(item.finalPcondition).concat(item.finalTransmission).toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
                FilterResults results = new FilterResults();
                results.values = filteredList;

                return  results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mUploads.clear();
            mUploads.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class DashboardCarViewHolder extends RecyclerView.ViewHolder{

        public TextView brand, model, price, d1, d2, d3, d4, date, stat;
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
            stat = itemView.findViewById(R.id.status);


        }
    }

    DashboardCarAdapter(List<Upload> mUploads){
        this.mUploads = mUploads;
        temp = new ArrayList<>(mUploads);
    }
}
