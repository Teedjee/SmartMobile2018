package com.example.timojansen.myfirstapplication;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Type;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final String message = extras.getString(MainActivity.EXTRA_MESSAGE);
        byte[] byteArray = extras.getByteArray("image");
        final Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        ImageView imageView = findViewById(R.id.finishedPhoto);
        imageView.setImageBitmap(bmp);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView2);
        textView.setText(message);

        Button buttonCapitalize = findViewById(R.id.buttonCapitalize);
        buttonCapitalize.setOnClickListener(new View.OnClickListener(){
           public void onClick(View v){
               capitalize();
           }
        });

        Button buttonDecapitalize = findViewById(R.id.buttonDecapitalize);
        buttonDecapitalize.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                decapitalize();
            }
        });

        Button buttonBold = findViewById(R.id.buttonBold);
        buttonBold.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                bold();
            }
        });

        Button buttonCursive = findViewById(R.id.buttonCursive);
        buttonCursive.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                cursive();
            }
        });

        Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                saveImage(bmp, message);
            }
        });
    }

    public File getPublicAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);

        return file;
    }

    public void saveImage(Bitmap bitmapToSave, String name)
    {
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root.getAbsolutePath()+"/DCIM/VochtigeMemes/" + name +".jpg");

        try {
            /*file.createNewFile();
            FileOutputStream ostream = new FileOutputStream(file);
            bitmapToSave.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
            ostream.close();*/
            MediaStore.Images.Media.insertImage(getContentResolver(), bitmapToSave, name + ".jpg" , "goeie");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void capitalize()
    {
        TextView textView = findViewById(R.id.textView2);
        String newMessage = textView.getText().toString();
        textView.setText(newMessage.toUpperCase());
    }

    public void decapitalize()
    {
        TextView textView = findViewById(R.id.textView2);
        String newMessage = textView.getText().toString();
        textView.setText(newMessage.toLowerCase());
    }

    public void bold()
    {
        TextView textView = findViewById(R.id.textView2);
        Typeface boldTypeFace = Typeface.defaultFromStyle(Typeface.BOLD);
        textView.setTypeface(boldTypeFace);
    }

    public void cursive()
    {
        TextView textView = findViewById(R.id.textView2);
        Typeface cursiveTypeFace = Typeface.defaultFromStyle(Typeface.ITALIC);
        textView.setTypeface(cursiveTypeFace);
    }
}
