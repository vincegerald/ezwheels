package vincegeralddelaccerna.ezwheels;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ListRequestHolder extends RecyclerView.ViewHolder {

    public TextView name, price, d1, d2, d3, d4, s1;
    public ImageView image1;

    public ListRequestHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.textView2);
        price = itemView.findViewById(R.id.textView4);
        d1 = itemView.findViewById(R.id.textView5);
        d2 = itemView.findViewById(R.id.textView6);
        d3 = itemView.findViewById(R.id.textView7);
        d4 = itemView.findViewById(R.id.textView8);
        s1 = itemView.findViewById(R.id.textView9);
        image1 = itemView.findViewById(R.id.imageView7);
    }
}
