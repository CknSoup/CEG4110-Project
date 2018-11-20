package com.example.ceg4110.ceg4110group13project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class ImageList extends AppCompatActivity {
    private Button homeBtn;
    List<File> iml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_list);
        Intent i = getIntent();
        iml = (ArrayList<File>) i.getSerializableExtra("list");

        for(int ii = 0; ii < iml.size(); ii++){
            String path = iml.get(ii).getPath();
            //ImageView iv = new ImageView();

        }

        homeBtn = (Button) findViewById(R.id.listToHome);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ImageList.this, MenuScreen.class);
                intent.putExtra("list", (Serializable) iml);
                startActivity(intent);
            }
        });
    }
}
