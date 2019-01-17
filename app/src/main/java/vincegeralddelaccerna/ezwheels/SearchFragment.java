package vincegeralddelaccerna.ezwheels;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements View.OnClickListener {


    //string array

    private static final String [] brands = new String[]{
            "Toyota","Suzuki","Mitsubishi","Kia","Chevrolet"
    };
    private static final String [] models = new String[]{
            "Fortuner","Wigo","LandCruiser","MonteroSport","Sportage"
    };
    private static final String [] years = new String[]{
            "2011","2012","2013","2014","2015","2016","2017","2018"
    };
    private static final String [] transmissions = new String[]{
            "Manual","Automatic"
    };
    private static final String [] colors = new String[]{
            "White","Black","Red","Blue","Yellow"
    };
    private static final String [] fuels = new String[]{
            "Diesel","Gasoline"
    };


    //strings

    private static String selectedType = "car";

    //properties
    AutoCompleteTextView brand, model, transmission, fuel, color, year;
    EditText price;
    RadioGroup type;
    RadioButton car, motor;
    Button search;


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        //auto complete view
        brand = v.findViewById(R.id.brand);
        model = v.findViewById(R.id.model);
        transmission = v.findViewById(R.id.transmission);
        fuel = v.findViewById(R.id.fuel);
        color = v.findViewById(R.id.color);
        year = v.findViewById(R.id.year);
        //radio group
        type = v.findViewById(R.id.type);
        //radio buttons
        car = v.findViewById(R.id.car);
        motor = v.findViewById(R.id.motor);
        //buttons
        search = v.findViewById(R.id.search);

        //listeners
        search.setOnClickListener(this);

        //adapters

        ArrayAdapter<String> brandAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, brands);
        brand.setAdapter(brandAdapter);

        ArrayAdapter<String> modelAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, models);
        model.setAdapter(modelAdapter);

        ArrayAdapter<String> transAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, transmissions);
        transmission.setAdapter(transAdapter);

        ArrayAdapter<String> fuelAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, fuels);
        fuel.setAdapter(fuelAdapter);

        ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, colors);
        color.setAdapter(colorAdapter);

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, years);
        year.setAdapter(yearAdapter);

        //radio group onclick

        type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.car:
                        selectedType = "car";
                        break;
                    case R.id.motor:
                        selectedType = "motor";
                }
            }
        });

        return v;
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if(id == R.id.search){
            final String finalBrand = brand.getText().toString();
            final String finalModel = model.getText().toString();
            final String finalTrans = transmission.getText().toString();
            final String finalFuel = fuel.getText().toString();
            final String finalColor = color.getText().toString();
            final String finalYear = year.getText().toString();
        }

    }
}
