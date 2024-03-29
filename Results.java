package com.example.ceg4110.ceg4110group13project;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Button;
import android.graphics.Bitmap;
import android.widget.ProgressBar;

import java.io.Serializable;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class Results extends AppCompatActivity implements Serializable {
    String s1;
    String s2;
    ImageView ivv;
    ImageView iv;
    TextView tv;
    TextView f1Tv;
    TextView f2Tv;
    Button homeBtn;
    Button nextBtn;
    Button previousBtn;
    List<File> iml;
    int index;
    List<String> cvlist;
    Button green;
    Button red;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
        Intent i = getIntent();

        index = (Integer) i.getSerializableExtra("index");
        cvlist = (List<String>) i.getSerializableExtra("cvlist");
        iml = (ArrayList<File>) i.getSerializableExtra("list");

        s1 = cvlist.get(index * 2);
        s2 = cvlist.get(index * 2 + 1);

        iv = (ImageView) findViewById(R.id.resultsIV);
        tv = (TextView) findViewById(R.id.resultTV);
        f1Tv = (TextView) findViewById(R.id.s1TV);
        f2Tv = (TextView) findViewById(R.id.s2TV);
        homeBtn = (Button) findViewById(R.id.resultToMenuBtn);
        nextBtn = (Button) findViewById(R.id.nextBtn);
        previousBtn = (Button) findViewById(R.id.previousBtn);
        green = (Button) findViewById(R.id.green);
        red = (Button) findViewById(R.id.red);

        float f1 = Float.parseFloat(s1);
        float f2 = Float.parseFloat(s2);

        float answer = f1 - f2;
        if(answer < 0){
            tv.setText("No food here... :(");
        }
        else{
            tv.setText("Oh yes... I see food! :D");
        }

        if(f1 > 0 && f2 < 0){
            green.getLayoutParams().width=400;
            red.getLayoutParams().width=0;
        }
        else if(f1 < 0 && f2 > 0){
            green.getLayoutParams().width=0;
            red.getLayoutParams().width=400;
        }
        else if(f1 > 0 && f2 > 0){
            float total = f1 + f2;
            float greenWidth = f1 / total * 400;
            float redWidth = f2 / total * 400;
            int g = Math.round(greenWidth);
            int r = Math.round(redWidth);
            green.getLayoutParams().width=g;
            red.getLayoutParams().width=r;
        }
        else{
            float f3 = f1 * -1;
            float f4 = f2 * -1;
            float total = f3 + f4;
            float greenWidth = f4 / total * 400;
            float redWidth = f3 / total * 400;
            int g = Math.round(greenWidth);
            int r = Math.round(redWidth);
            green.getLayoutParams().width=g;
            red.getLayoutParams().width=r;
        }

        f1Tv.setText(s1);
        f2Tv.setText(s2);

        File file = iml.get(index);
        Bitmap b = BitmapFactory.decodeFile(file.getAbsolutePath());
        iv.setImageBitmap(b);

        if(index == 0){
            previousBtn.setEnabled(false);
        }

        if(index + 1 == iml.size()){
            nextBtn.setEnabled(false);
        }

        homeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                iml.clear();
                Intent intent = new Intent(Results.this, MenuScreen.class);
                intent.putExtra("list",(Serializable) iml);
                startActivity(intent);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                index = index + 1;
                Intent intent = new Intent(Results.this, Results.class);
                intent.putExtra("list", (Serializable) iml);
                intent.putExtra("cvlist", (Serializable) cvlist);
                intent.putExtra("index", (Serializable) index);
                startActivity(intent);
            }
        });

        previousBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                index = index - 1;
                Intent intent = new Intent(Results.this, Results.class);
                intent.putExtra("list", (Serializable) iml);
                intent.putExtra("cvlist", (Serializable) cvlist);
                intent.putExtra("index", (Serializable) index);
                startActivity(intent);
            }
        });

    }
}
