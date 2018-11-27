package com.example.ceg4110.ceg4110group13project;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class ImageList extends AppCompatActivity {
    private Button homeBtn;
    Button nextBtn;
    Button previousBtn;
    Button deleteBtn;
    ImageView iv;
    List<File> iml;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_list);
        Intent i = getIntent();
        iml = (ArrayList<File>) i.getSerializableExtra("list");
        index = (Integer) i.getSerializableExtra("index");

        homeBtn = (Button) findViewById(R.id.listToHome);
        nextBtn = (Button) findViewById(R.id.listNext);
        previousBtn = (Button) findViewById(R.id.listPrevious);
        deleteBtn = (Button) findViewById(R.id.removeFromListBtn);
        iv = (ImageView) findViewById(R.id.imageListImageView);

        File file = iml.get(index);
        Bitmap b = BitmapFactory.decodeFile(file.getAbsolutePath());
        iv.setImageBitmap(b);

        if(index == 0){
            previousBtn.setEnabled(false);
        }
        if(index + 1 == iml.size()){
            nextBtn.setEnabled(false);
        }

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ImageList.this, MenuScreen.class);
                intent.putExtra("list", (Serializable) iml);
                startActivity(intent);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = index + 1;
                Intent intent = new Intent(ImageList.this, ImageList.class);
                intent.putExtra("list",(Serializable) iml);
                intent.putExtra("index", (Serializable) index);
                startActivity(intent);
            }
        });

        previousBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                index = index - 1;
                Intent intent = new Intent(ImageList.this, ImageList.class);
                intent.putExtra("list", (Serializable) iml);
                intent.putExtra("index", (Serializable) index);
                startActivity(intent);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(ImageList.this, "Image removed", Toast.LENGTH_SHORT).show();
                iml.remove(index);
                if(iml.size() == 0){
                    Toast.makeText(ImageList.this, "No images, returning to menu screen", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ImageList.this, MenuScreen.class);
                    intent.putExtra("list", (Serializable) iml);
                    startActivity(intent);
                }
                else if(index == 0){
                    index = 0;
                    Intent intent = new Intent(ImageList.this, ImageList.class);
                    intent.putExtra("list", (Serializable) iml);
                    intent.putExtra("index", (Serializable) index);
                    startActivity(intent);
                }
                else{
                    index = index - 1;
                    Intent intent = new Intent(ImageList.this, ImageList.class);
                    intent.putExtra("list", (Serializable) iml);
                    intent.putExtra("index", (Serializable) index);
                    startActivity(intent);
                }
            }
        });
    }
}
