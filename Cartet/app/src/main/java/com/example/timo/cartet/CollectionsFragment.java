package com.example.timo.cartet;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class CollectionsFragment extends Fragment {

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
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.linearLayout);
        return view;
    }

}
