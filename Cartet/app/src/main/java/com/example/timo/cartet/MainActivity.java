package com.example.timo.cartet;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {
    SpotFragment fragmentSpot = new SpotFragment();
    CollectionsFragment fragmentCollections = new CollectionsFragment();
    ProfileFragment fragmentProfile = new ProfileFragment();

    //private String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_spot:
                    setFragment(fragmentSpot);
                    return true;
                case R.id.navigation_collections:
                    setFragment(fragmentCollections);
                    return true;
                case R.id.navigation_profile:
                    setFragment(fragmentProfile);
                    return true;
            }
            return false;
        }
    };

    private void setFragment(Fragment fragment){
        setTitle((String)fragment.toString());
        FragmentTransaction fragmentTransactionSpot = getSupportFragmentManager().beginTransaction();
        fragmentTransactionSpot.replace(R.id.frame, fragment, fragment.getTag());
        fragmentTransactionSpot.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFragment(fragmentSpot);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
