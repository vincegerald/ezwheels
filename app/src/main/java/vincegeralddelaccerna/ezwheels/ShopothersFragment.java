package vincegeralddelaccerna.ezwheels;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShopothersFragment extends Fragment {


    public ShopothersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_shopothers, container, false);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) v.findViewById(R.id.bottomnav1);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        final ReservationFragment reservationFragment = new ReservationFragment();
        final TradeinFragment tradeinFragment = new TradeinFragment();
        final FavoriteFragment favoriteFragment = new FavoriteFragment();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if(id == R.id.favorites){
                    setFragment(favoriteFragment);
                    return true;
                }

                if(id == R.id.tradein){
                    setFragment(tradeinFragment);
                    return  true;
                }

                if(id == R.id.reservation){
                    setFragment(reservationFragment);
                    return true;
                }

                if(id == R.id.home){
                    Intent intent = new Intent(getActivity(), ShopDashboard.class);
                    startActivity(intent);
                }
                return false;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.reservation);

        return v;
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.screen_area1, fragment);
        fragmentTransaction.commit();
    }

}
