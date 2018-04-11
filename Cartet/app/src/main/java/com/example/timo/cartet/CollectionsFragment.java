package com.example.timo.cartet;


import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CollectionsFragment extends Fragment {
    ViewGroup collectionsLayout;

    private String android_id;
    private FirebaseDatabase database;
    private DatabaseReference idReference;
    private DatabaseReference collectionsReference;
    private DatabaseReference carsReference;

    private ExpandableListView expListView;
    private ExpandableListAdapter expListAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;

    private static String Title = "Collections";

    public CollectionsFragment() {
        // Required empty public constructor
    }

    @Override
    public String toString(){
        return Title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_collections, container, false);

        collectionsLayout = (ViewGroup) view.findViewById(R.id.linearLayout);
        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);

        android_id = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        database = FirebaseDatabase.getInstance();
        idReference = database.getReference(android_id);

        // Read from the database
        idReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<List<Car>> collectionsList = new ArrayList<>();
                for (DataSnapshot questionSnapshot: dataSnapshot.getChildren()) {
                    List<Car> cars = new ArrayList<>();
                    for(DataSnapshot carQuestionSnapshot: questionSnapshot.getChildren()){
                        String carString = (String)carQuestionSnapshot.getValue();
                        Car car = parseDataToClass(carString);
                        cars.add(car);
                    }
                    collectionsList.add(cars);
                }
                displayData(collectionsList);
                expListAdapter = new ExpandableListAdapter(getContext(), listDataHeader, listHash);
                expListView.setAdapter(expListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });



        return view;
    }

    private void displayData(List<List<Car>> collections) {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        int collectionCount = 0;
        for(List<Car> carList: collections){
            listDataHeader.add(carList.get(0).getMerk());
            List<String> kentekens = new ArrayList<>();

            int carCount = 0;
            for(Car car: carList){
                kentekens.add(carList.get(carCount).ToString());
                carCount++;
            }

            listHash.put(listDataHeader.get(collectionCount), kentekens);
            collectionCount++;
        }
    }

    private Car parseDataToClass(String allData){
        Car car = new Car(getValueFromKey(allData, "Aantal cilinders: "),
                getValueFromKey(allData, "Aantal zitplaatsen: "),
                getValueFromKey(allData, "Cilinderinhoud: "),
                getValueFromKey(allData, "Kleur: "),
                getValueFromKey(allData, "Serie: "),
                getValueFromKey(allData, "Voertuigtype: "),
                getValueFromKey(allData, "Kenteken: "),
                getValueFromKey(allData, "Lengte: "),
                getValueFromKey(allData, "Massa: "),
                getValueFromKey(allData, "Merk: "),
                getValueFromKey(allData, "Voertuigsoort: "));
        return car;
    }

    private String getValueFromKey(String allData, String key){
        int indexOfKey = allData.indexOf(key);
        int indexOfValue = indexOfKey + key.length();
        int endIndex = allData.indexOf("\n", indexOfValue);
        String value = allData.substring(indexOfValue, endIndex);

        return value;
    }
}
