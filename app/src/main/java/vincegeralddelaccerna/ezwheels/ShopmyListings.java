package vincegeralddelaccerna.ezwheels;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShopmyListings extends Fragment {


    public ShopmyListings() {
        // Required empty public constructor
    }


    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_shopmy_listings, container, false);

        tabLayout = v.findViewById(R.id.tablayout);
        viewPager = v.findViewById(R.id.viewpager);
        adapter = new ViewPagerAdapter(getFragmentManager());

        adapter.AddFragment(new carlist_fragment(), "CARS");
        adapter.AddFragment(new motorlist_fragment(), "MOTORCYCLES");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return v;
    }

}
