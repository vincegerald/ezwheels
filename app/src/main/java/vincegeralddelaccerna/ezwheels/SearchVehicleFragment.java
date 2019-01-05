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
import java.util.List;

public class SearchVehicleFragment extends Fragment {
    Spinner brand, year, location;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.searchvehicle_fragment,container,false);

        brand = v.findViewById(R.id.spinner);
        year = v.findViewById(R.id.spinner2);
        location = v.findViewById(R.id.spinner3);


        List<Brands> brandsList = new ArrayList<>();
        List<Location> locationList = new ArrayList<>();
        List<Model> modelList = new ArrayList<>();
        Brands brand1 = new Brands("Choose brand");
        brandsList.add(0, brand1);
        Brands brand2 = new Brands("Honda");
        brandsList.add(brand2);
        Brands brand3 = new Brands("Suzuki");
        brandsList.add(brand3);
        Brands brand4 = new Brands("Kia");
        brandsList.add(brand4);
        Brands brand5 = new Brands("Toyota");
        brandsList.add(brand5);
        Location location1 = new Location("Choose Location");
        locationList.add(0, location1);
        Location location2 = new Location("Cebu City");
        locationList.add(location2);
        Location location3 = new Location("Mandaue City");
        locationList.add(location3);
        Location location4 = new Location("Lapu-lapu City");
        locationList.add(location4);
        Model model1 = new Model("Choose Model and Year","");
        modelList.add(0,model1);
        Model model2 = new Model("Vios","2018");
        modelList.add(model2);
        Model model3 = new Model("Fortuner","2018");
        modelList.add(model3);
        Model model4 = new Model("Montero Sport","2018");
        modelList.add(model4);
        ArrayAdapter<Brands> adapter;
        ArrayAdapter<Location> locationAdapter;
        ArrayAdapter<Model> modelAdapter;
        adapter = new ArrayAdapter<Brands>(getActivity(), android.R.layout.simple_spinner_item, brandsList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brand.setAdapter(adapter);
        locationAdapter = new ArrayAdapter<Location>(getActivity(), android.R.layout.simple_spinner_item, locationList);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location.setAdapter(locationAdapter);
        modelAdapter = new ArrayAdapter<Model>(getActivity(), android.R.layout.simple_spinner_item, modelList);
        modelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(modelAdapter);
        return v;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Search Vehicle");
    }
}
