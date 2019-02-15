package vincegeralddelaccerna.ezwheels;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;


public class DashBoard extends AppCompatActivity  {


    private GoogleMap mMap;
    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        mAuth = FirebaseAuth.getInstance();

        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnav);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        final DashboardFragment dashboardFragment = new DashboardFragment();
        final SearchFragment searchFragment = new SearchFragment();
        final ShopAddListing shopAddListing = new ShopAddListing();
        final ProfileFragment profileFragment = new ProfileFragment();
        final OthersFragment othersFragment = new OthersFragment();
        final ShopmyReservation shopmyReservation = new ShopmyReservation();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if(id == R.id.dashboard){
                    setFragment(dashboardFragment);
                    return true;
                }


                if(id == R.id.reservation){
                    setFragment(shopmyReservation);
                    return  true;
                }

                if(id == R.id.others){
                    setFragment(othersFragment);
                    bottomNavigationView.setVisibility(View.GONE);
                    return  true;
                }

                if(id == R.id.profile){
                    setFragment(profileFragment);
                    return  true;
                }

                return false;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.dashboard);
    }

   private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.screen_area, fragment);
        fragmentTransaction.commit();
   }
}
