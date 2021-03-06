package vincegeralddelaccerna.ezwheels;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LoanReqAdapter extends RecyclerView.Adapter<LoanReqAdapter.LoanReqAdapterHolder> {

    private Context mContext;
    private List<LoanReq> mUploads;
    DatabaseReference getListing, getMotor;
    DatabaseReference getShop;
    private static String brand, model;
    private static double lat, lon;
    private static String name;

    public LoanReqAdapter(Context context, List<LoanReq> uploads) {
        mContext = context;
        mUploads = uploads;
    }


    @NonNull
    @Override
    public LoanReqAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.alllayout, parent, false);

        return new LoanReqAdapter.LoanReqAdapterHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final LoanReqAdapter.LoanReqAdapterHolder holder, int position) {
        final LoanReq uploadCurrent = mUploads.get(position);
        getListing = FirebaseDatabase.getInstance().getReference("Car");
        //Toast.makeText(mContext, uploadCurrent.getListId(), Toast.LENGTH_SHORT).show();
        getListing.child(uploadCurrent.getListId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String image = dataSnapshot.child("image").getValue().toString();
                    brand = dataSnapshot.child("finalBrand").getValue().toString();
                    model = dataSnapshot.child("finalModel").getValue().toString();
                    String price = dataSnapshot.child("finalPrice").getValue().toString();
                    Picasso.get().load(image).fit().centerCrop().into(holder.image);
                    holder.list.setText(brand + " " + model);
                    holder.price.setText(price);
                }
                else{
                    getMotor = FirebaseDatabase.getInstance().getReference("Motor");
                    getMotor.child(uploadCurrent.getListId()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                String image = dataSnapshot.child("image").getValue().toString();
                                brand = dataSnapshot.child("finalBrand").getValue().toString();
                                model = dataSnapshot.child("finalModel").getValue().toString();
                                String price = dataSnapshot.child("finalPrice").getValue().toString();
                                Picasso.get().load(image).fit().centerCrop().into(holder.image);
                                holder.list.setText(brand + " " + model);
                                holder.price.setText(price);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(mContext, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(mContext, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        holder.shop.setText("Applied Loan Company : " + uploadCurrent.getComp1() + " " + uploadCurrent.getComp2() + " " +  uploadCurrent.getComp3() + " " +  uploadCurrent.getComp4() + " " +  uploadCurrent.getComp5());
        holder.logoPeso.setVisibility(View.GONE);
        holder.type.setText("Months to pay: " + uploadCurrent.getMonth());
        holder.date.setVisibility(View.GONE);
        holder.dp.setVisibility(View.VISIBLE);
        holder.dp.setText("Downpayment: " + uploadCurrent.getDp());
        holder.imageView.setVisibility(View.GONE);
        if(uploadCurrent.getStatus().equals("PENDING")){
            holder.status.setText(uploadCurrent.getStatus());
            holder.status.setTextColor(Color.parseColor("#ffa500"));
          }
        else if(uploadCurrent.getStatus().equals("APPROVED")){
            holder.status.setText(uploadCurrent.getStatus());
            holder.status.setTextColor(Color.parseColor("#008000"));
        }
        else{
            holder.status.setText(uploadCurrent.getStatus());
            holder.status.setTextColor(Color.parseColor("#FF0000"));
        }

        getShop = FirebaseDatabase.getInstance().getReference("Shop");
        getShop.child(uploadCurrent.getShopUid()).addValueEventListener(new ValueEventListener() {
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

        //        holder.list.setText(uploadCurrent.getBrand() + " " + uploadCurrent.getModel());
//        holder.shop.setText(uploadCurrent.getName());
//        holder.date.setText(uploadCurrent.getCurrentDate() + " : " + uploadCurrent.getCurrentTime());
//        holder.type.setText(uploadCurrent.getType());
//        Picasso.get().load(uploadCurrent.getImage1()).fit().centerCrop().into(holder.image);
//        holder.price.setText(uploadCurrent.getPrice());
//        holder.status.setText(uploadCurrent.getStatus());
//        holder.logoPeso.setVisibility(View.GONE);
//
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, LoanReqScrolling.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lon", lon);
                intent.putExtra("shopname", name);
                intent.putExtra("aid", uploadCurrent.getAid());
                intent.putExtra("listid", uploadCurrent.getListId());
                intent.putExtra("shopid", uploadCurrent.getShopUid());
                intent.putExtra("brand", brand);
                intent.putExtra("model", model);
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class LoanReqAdapterHolder extends RecyclerView.ViewHolder{

        public TextView list, shop, date, type, status, price, dp;
        public ImageView image, logoPeso, imageView;
        public CardView item;

        public LoanReqAdapterHolder(View itemView) {
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
            dp = itemView.findViewById(R.id.dp);
            imageView = itemView.findViewById(R.id.imageView6);


        }
    }
}
