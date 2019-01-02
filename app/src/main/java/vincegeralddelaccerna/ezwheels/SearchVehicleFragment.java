package vincegeralddelaccerna.ezwheels;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SearchVehicleFragment extends Fragment {
    Spinner brand, year, location;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.searchvehicle_fragment, null);
        brand = v.findViewById(R.id.spinner);

        String [] brands = {"Toyota", "Kia", "Honda", "Suzuki", "Chevrolet"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, brands);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        brand.setAdapter(adapter);
        return v;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Search Vehicle");
    }
}
