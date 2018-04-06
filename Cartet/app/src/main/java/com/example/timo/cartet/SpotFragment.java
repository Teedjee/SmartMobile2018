package com.example.timo.cartet;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class SpotFragment extends Fragment {

    private static String Title = "Spot";

    TextView textViewLicence;
    SurfaceView cameraView;
    CameraSource cameraSource;
    final int requestCameraPermissionID = 1001;
    Intent intentRDW;

    public SpotFragment() {
        // Required empty public constructor
    }

    @Override
    public String toString(){
        return Title;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case requestCameraPermissionID:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                        return;
                    }
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            String[] manifestArray = {Manifest.permission.CAMERA};
            ActivityCompat.requestPermissions(getActivity(),
                    manifestArray,
                    requestCameraPermissionID);
        }

        View view = inflater.inflate(R.layout.fragment_spot, container, false);
        textViewLicence = (TextView) view.findViewById(R.id.text_licenceplate);
        cameraView = (SurfaceView) view.findViewById(R.id.surface_view);

        intentRDW = new Intent(getActivity(), RDWActivity.class);
        cameraView();

        return view;
    }

    protected void cameraView(){
        textViewLicence.setText("");
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getActivity().getApplicationContext()).build();
        if (!textRecognizer.isOperational()) {
            Log.e("MainActivity", "Detector dependencies are not yet available");
        } else {
            cameraSource = new CameraSource.Builder(getActivity().getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();
            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder surfaceHolder) {
                    try {
                        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.CAMERA}, requestCameraPermissionID);
                            return;
                        }
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                    cameraSource.stop();
                }
            });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {
                    final SparseArray<TextBlock> items = detections.getDetectedItems();
                    if(items.size() != 0){
                        textViewLicence.post(new Runnable() {
                            @Override
                            public void run() {
                                StringBuilder stringBuilder = new StringBuilder();
                                for(int i = 0; i < items.size(); i++){
                                    TextBlock item = items.valueAt(i);
                                    if(checkIfLicencePlate(item.getValue())){
                                        stringBuilder.append(item.getValue());
                                        stringBuilder.append("\n");
                                        //textViewLicence.setText(stringBuilder.toString());
                                        intentRDW.putExtra("licenceString", stringBuilder.toString());
                                        startActivity(intentRDW);
                                    }
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    private boolean checkIfLicencePlate(String string){
        int amountOfHyphens = 0;
        for(int i = 0; i < string.length(); i++){
            if(string.charAt(i) == '-'){
                amountOfHyphens++;
            }
        }
        if(amountOfHyphens == 2 && string.length() >= 8 && string.length() <= 10){
            return true;
        }
        else{
            return false;
        }
    }


}
