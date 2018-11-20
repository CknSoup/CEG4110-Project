package com.example.ceg4110.ceg4110group13project;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Button;

import java.io.Serializable;
import java.io.File;
import java.lang.reflect.Array;
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
    List<File> iml;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
        Intent i = getIntent();

        s1 = (String )i.getSerializableExtra("f1");
        s2 = (String) i.getSerializableExtra("f2");
        ivv = (ImageView) i.getSerializableExtra("ivv");
        iml = (ArrayList<File>) i.getSerializableExtra("list");

        iv = (ImageView) findViewById(R.id.resultsIV);
        tv = (TextView) findViewById(R.id.resultTV);
        f1Tv = (TextView) findViewById(R.id.s1TV);
        f2Tv = (TextView) findViewById(R.id.s2TV);

        float f1 = Float.parseFloat(s1);
        float f2 = Float.parseFloat(s2);

        float answer = f1 - f2;
        if(answer < 0){
            tv.setText("No food here... :(");
        }
        else{
            tv.setText("Oh yes... I see food! :D");
        }

        f1Tv.setText(s1);
        f2Tv.setText(s2);

        homeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Results.this, MenuScreen.class);
                intent.putExtra("list",(Serializable) iml);
                startActivity(intent);
            }
        });

    }
}
