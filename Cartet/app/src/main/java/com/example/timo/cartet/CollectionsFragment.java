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

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

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
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        android_id = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        database = FirebaseDatabase.getInstance();
        idReference = database.getReference(android_id);

        // Read from the database
        idReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int numberOfCollections = 0;
                for (DataSnapshot questionSnapshot: dataSnapshot.getChildren()) {
                    List<Car> cars = new ArrayList<>();
                    for(DataSnapshot carQuestionSnapshot: questionSnapshot.getChildren()){
                        String carString = (String)carQuestionSnapshot.getValue();
                        Car car = parseDataToClass(carString);
                        cars.add(car);
                    }
                    addCollection(questionSnapshot.getKey(), cars, numberOfCollections);
                }
                listAdapter = new ExpandableListAdapter(getContext(), listDataHeader, listDataChild);
                expListView.setAdapter(listAdapter);
                numberOfCollections++;
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        return view;
    }

    private Car parseDataToClass(String allData){
        Car car = new Car(getValueFromKey(allData, "\"aantal_cilinders\""),
                getValueFromKey(allData, "\"aantal_zitplaatsen\""),
                getValueFromKey(allData, "\"cilinderinhoud\""),
                getValueFromKey(allData, "\"eerste_kleur\""),
                getValueFromKey(allData, "\"handelsbenaming\""),
                getValueFromKey(allData, "\"inrichting\""),
                getValueFromKey(allData, "\"kenteken\""),
                getValueFromKey(allData, "\"lengte\""),
                getValueFromKey(allData, "\"massa_ledig_voertuig\""),
                getValueFromKey(allData, "\"merk\""),
                getValueFromKey(allData, "\"voertuigsoort\""));

        return car;
    }

    private String getValueFromKey(String allData, String key){
        int indexOfKey = allData.indexOf(key);
        int indexOfValue = allData.indexOf(":", indexOfKey);
        int endIndexOfValue = allData.indexOf("\n", indexOfValue);
        String value = allData.substring(indexOfValue + 1, endIndexOfValue);

        return value;
    }

    private void addCollection(String collectionName, List<Car> cars, int number){
        
        List<String> collection = new ArrayList<>();
        for(Car car: cars){
            collection.add(car.getKenteken());
        }
        listDataHeader.add(collectionName);
        listDataChild.put(listDataHeader.get(number), collection);
    }
}
