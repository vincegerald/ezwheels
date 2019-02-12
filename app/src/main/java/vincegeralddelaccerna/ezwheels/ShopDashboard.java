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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShopDashboard extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference checkIfActivated;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopdashboard);

        //firebase
        mAuth = FirebaseAuth.getInstance();
        checkIfActivated = FirebaseDatabase.getInstance().getReference("Shop");

        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnav);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        checkIfActivated = FirebaseDatabase.getInstance().getReference("Shop").child(mAuth.getCurrentUser().getUid());
        checkIfActivated.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    status = dataSnapshot.child("status").getValue().toString();
                    if(status.equals("NOT ACTIVATED")){
                        bottomNavigationView.getMenu().removeItem(R.id.mylisting);
                    }

                }
                else{
                    Toast.makeText(ShopDashboard.this, "Error Occured", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ShopDashboard.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });





        final ShopDashBoardFragment shopDashboard = new ShopDashBoardFragment();
        final ShopAddListing shopAddListing = new ShopAddListing();
        final ReservationFragment reservationFragment = new ReservationFragment();
        final SearchFragment searchFragment = new SearchFragment();
        final ShopothersFragment shopothersFragment = new ShopothersFragment();
        final ShopmyListings shopmyListings = new ShopmyListings();
        final loanreqq loanreqq = new loanreqq();
        final ProfileFragment profileFragment = new ProfileFragment();
        final FavoriteFragment favoriteFragment = new FavoriteFragment();

        //if(status.e)


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if(id == R.id.dashboard){
                    setFragment(shopDashboard);
                    return true;
                }

                if(id == R.id.favorites){
                    setFragment(favoriteFragment);
                    return  true;
                }
//
                if(id == R.id.mylisting){
                    setFragment(shopmyListings);
                    return  true;
                }

                if(id == R.id.others){
                    setFragment(shopothersFragment);
                    bottomNavigationView.setVisibility(View.GONE);
                    return  true;
                }
//
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
