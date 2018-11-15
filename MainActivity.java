package com.example.ceg4110.ceg4110group13project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Serializable{
    Button takePictureBtn;
    Button localGalleryBtn;
    Button viewHistoryBtn;
    Button submitBtn;
    Button imageListBtn;
    Button informationBtn;
    TextView tv;
    List<ImageView> iml = new ArrayList<ImageView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
        try{
            iml = (ArrayList<ImageView>) i.getSerializableExtra("list");
            //Toast.makeText(this,"Works", Toast.LENGTH_SHORT).show();
        }
        catch(Exception e){
        }

        takePictureBtn = (Button) findViewById(R.id.takePictureBtn);
        localGalleryBtn = (Button) findViewById(R.id.imageBrowserBtn);
        viewHistoryBtn = (Button) findViewById(R.id.viewHistoryBtn);
        submitBtn = (Button) findViewById(R.id.submitPicturesBtn);
        imageListBtn = (Button) findViewById(R.id.imageListBtn);
        informationBtn = (Button) findViewById(R.id.infoBtn);
        tv = (TextView) findViewById(R.id.label);

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
                startActivity(new Intent(MainActivity.this, ViewHistory.class));
            }
        });

        imageListBtn.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view)  {
                startActivity(new Intent(MainActivity.this, ImageList.class));
            }
        });

        informationBtn.setOnClickListener(new View.OnClickListener()    {
            @Override
            public void onClick(View view)  {
                startActivity(new Intent(MainActivity.this, InformationPage.class));
            }
        });

    }
}
