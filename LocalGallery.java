package com.example.ceg4110.ceg4110group13project;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.net.Uri;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.widget.Button;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class LocalGallery extends AppCompatActivity implements Serializable{
    ImageView iv;
    Button homeBtn;
    Button viewGalleryBtn;
    Button saveBtn;
    List<ImageView> iml;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_gallery);
        Intent i = getIntent();
        iml = (ArrayList<ImageView>) i.getSerializableExtra("list");

        iv = (ImageView) findViewById(R.id.imageViewGallery);
        homeBtn = (Button) findViewById(R.id.galToHomeBtn);
        viewGalleryBtn = (Button) findViewById(R.id.button);
        saveBtn = (Button) findViewById(R.id.saveLocalGalleryImageBtn);

        homeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(LocalGallery.this, MainActivity.class);
                intent.putExtra("list",(Serializable) iml);
                startActivity(intent);
            }
        });

        viewGalleryBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //ADD TO IMAGE LIST
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            // Show the Selected Image on ImageView
            ImageView imageView = (ImageView) findViewById(R.id.imageViewGallery);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }
    }

}
