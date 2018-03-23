package com.example.timo.cartet;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class RDWActivity extends AppCompatActivity {

    TextView textViewLicence;
    TextView textViewBrand;
    ProgressBar progressBar;
    String licenceToCheck;
    Car carFound;

    String appToken = "bPg8jKW5MxNfiUbEqMnCvxwZj";
    URL rdwURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdw);

        textViewLicence = (TextView) findViewById(R.id.textViewLicence);
        textViewBrand = (TextView) findViewById(R.id.textViewBrand);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        licenceToCheck = getIntent().getExtras().getString("licenceString");
        licenceToCheck = prepareLicence(licenceToCheck);
        textViewLicence.setText(licenceToCheck);
        carFound = findCar(licenceToCheck);
    }

    private String prepareLicence(String licenceToCheck) {
        if(licenceToCheck.length() > 8){
            licenceToCheck = licenceToCheck.substring(licenceToCheck.length() - 9, 8);
        }

        String finishedLicence = "";
        for(int i = 0; i < licenceToCheck.length(); i++){
            if(licenceToCheck.charAt(i) != '-'){
                finishedLicence += licenceToCheck.charAt(i);
            }
        }
        return finishedLicence;
    }

    private Car findCar(final String licence){
        //hier komt het opvragen van data bij het RDW

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(progressBar.VISIBLE);

                try{
                    rdwURL = new URL("https://opendata.rdw.nl/resource/m9d7-ebf2.json"
                            + "?kenteken=" + licence);
                }
                catch (MalformedURLException e){
                    Log.e("MalformedURL", "Can't connect to the RDW..");
                }

                try{
                    HttpsURLConnection myConnection = (HttpsURLConnection) rdwURL.openConnection();
                    myConnection.setRequestProperty("X-App-Token", appToken);
                    myConnection.connect();

                    if (myConnection.getResponseCode() == 200) {
                        //connection established, start reading
                        textViewBrand.setText("Trying to read");
                       
                    }
                    else {
                        textViewBrand.setText("ERROR");
                    }
                }
                catch(Exception e){
                    Log.e("ERROR", "Something went wrong..");
                }
            }
        });

        return null;
    }
}
