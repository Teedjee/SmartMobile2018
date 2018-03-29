package com.example.timo.cartet;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
                //progressBar.setVisibility(progressBar.VISIBLE);

                try{
                    rdwURL = new URL("https://opendata.rdw.nl/resource/m9d7-ebf2.json?"
                            + "$$app_token=" + appToken
                            + "&kenteken=" + licence);
                }
                catch (MalformedURLException e){
                    Log.e("MalformedURL", "Can't connect to the RDW..");
                }

                try{
                    HttpURLConnection myConnection = (HttpsURLConnection) rdwURL.openConnection();
                    myConnection.connect();

                    if (myConnection.getResponseCode() == 200) {
                        //connection established, start reading
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(myConnection.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }
                        bufferedReader.close();
                        Car car = parseDataToClass(stringBuilder.toString());
                        textViewBrand.setText(car.ToString());
                        //progressBar.setVisibility(progressBar.GONE);
                        myConnection.disconnect();
                    }
                    else {
                        textViewBrand.setText("ERROR");
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        return null;
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
        int endIndexOfValue = allData.indexOf(",", indexOfValue);
        String value = allData.substring(indexOfValue + 2, endIndexOfValue - 1);

        return value;
    }
}
