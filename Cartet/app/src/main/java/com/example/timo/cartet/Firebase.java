package com.example.timo.cartet;

import android.provider.Settings;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static java.security.AccessController.getContext;

/**
 * Created by timojansen on 5-4-18.
 */

class Firebase {
    private FirebaseDatabase database;
    public FirebaseDatabase GetDatabase(){
        return database;
    }

    private DatabaseReference idReference;
    private DatabaseReference collectionsReference;
    private DatabaseReference carsReference;

    Firebase(String deviceID){
        database = FirebaseDatabase.getInstance();
        idReference = database.getReference(deviceID);
    }

    public void uploadCar(Car carToUpload, String collection){
        collectionsReference = idReference.child(collection);
        collectionsReference.setValue(collection);
        carsReference = collectionsReference.child(carToUpload.getVoertuigType());
        carsReference.setValue(carToUpload.ToString());
    }

    public Car getCar(){
        return null;
    }
}
