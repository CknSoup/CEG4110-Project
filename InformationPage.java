package com.example.ceg4110.ceg4110group13project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

public class InformationPage extends AppCompatActivity {
    private Button homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_page);

        homeBtn = (Button) findViewById(R.id.infoToHomeBtn);

        homeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(InformationPage.this, MainActivity.class));
            }
        });
    }
}
