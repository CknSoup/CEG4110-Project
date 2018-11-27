package com.example.ceg4110.ceg4110group13project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class MainActivity extends AppCompatActivity implements Serializable{
    Button takePictureBtn;
    Button localGalleryBtn;
    Button viewHistoryBtn;
    Button submitBtn;
    Button imageListBtn;
    Button informationBtn;
    TextView tv;
    int imageCount = 0;
    List<File> iml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iml = new ArrayList<File>();

        takePictureBtn = (Button) findViewById(R.id.takePictureBtn);
        localGalleryBtn = (Button) findViewById(R.id.imageBrowserBtn);
        viewHistoryBtn = (Button) findViewById(R.id.viewHistoryBtn);
        submitBtn = (Button) findViewById(R.id.submitPicturesBtn);
        imageListBtn = (Button) findViewById(R.id.imageListBtn);
        informationBtn = (Button) findViewById(R.id.infoBtn);
        tv = (TextView) findViewById(R.id.label);

        submitBtn.setEnabled(false);
        imageListBtn.setEnabled(false);

        takePictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, TakePicture.class);
                i.putExtra("list", (Serializable) iml);
                startActivity(i);
            }
        });

        localGalleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {
                Intent i = new Intent(MainActivity.this, LocalGallery.class);
                i.putExtra("list", (Serializable) iml);
                startActivity(i);
            }
        });

        viewHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ViewHistory.class);
                i.putExtra("list", (Serializable) iml);
                startActivity(i);
            }
        });

        imageListBtn.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view)  {
                Intent i = new Intent(MainActivity.this, ImageList.class);
                i.putExtra("list", (Serializable) iml);
                startActivity(i);
            }
        });

        informationBtn.setOnClickListener(new View.OnClickListener()    {
            @Override
            public void onClick(View view)  {
                Intent i = new Intent(MainActivity.this, InformationPage.class);
                i.putExtra("list", (Serializable) iml);
                startActivity(i);
            }
        });
    }
}
