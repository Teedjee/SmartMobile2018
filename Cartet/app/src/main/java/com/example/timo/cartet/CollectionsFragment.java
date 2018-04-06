package com.example.timo.cartet;


import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


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

        android_id = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        database = FirebaseDatabase.getInstance();
        idReference = database.getReference(android_id);

        // Read from the database
        idReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot questionSnapshot: dataSnapshot.getChildren()) {
                    String key = questionSnapshot.getKey();
                    addCollection(key);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        return view;
    }

    private void addCollection(String collectionName){
        Button buttonCollection = new Button(getContext());
        buttonCollection.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        buttonCollection.setText(collectionName);
        collectionsLayout.addView(buttonCollection);
    }

}
