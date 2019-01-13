package vincegeralddelaccerna.ezwheels;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DashBoardFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.dashboard_fragment, container, false);

        tabLayout = v.findViewById(R.id.tablayout);
        viewPager = v.findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.AddFragment(new car_fragment(), "Car");
        adapter.AddFragment(new motor_fragment(), "Motorcycles");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Dashboard");
    }
}
