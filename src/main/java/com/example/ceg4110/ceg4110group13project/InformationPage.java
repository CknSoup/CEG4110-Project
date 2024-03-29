package com.example.ceg4110.ceg4110group13project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class InformationPage extends AppCompatActivity {
    private Button homeBtn;
    TextView tv;
    List<File> iml;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_page);
        Intent i = getIntent();
        iml = (ArrayList<File>) i.getSerializableExtra("list");

        homeBtn = (Button) findViewById(R.id.infoToHome);
        tv = (TextView) findViewById(R.id.textView2);

        tv.setText("Information Page on how this app works.\n\n" +
                "This app(seefood) is used to determine if a picture has food in it or not.\n\n" +
                "Menu Screen - The Image List button lets you to view the pictures you have selected for " +
                "submission.\nUnder the button is a label that tells you how many images are selected.\n" +
                "The take picture button lets you take a picture.\nThe View Local Picture Gallery button lets " +
                "you view your pictures that are saved on your device to add for submission.\n" +
                "The View Past History button lets you see your past submissions.\nThe submit images button " +
                "will upload all the images that have been added in your image list. The submit images button " +
                "will only be clickable if there is at least one item in your image list.\nThe Information " +
                "For This App button will take you to this page and it gives you information on how this app works.\n\n" +
                "Image List - All the images selected for submission will be shown here.\n" +
                "You can remove an image by selecting an image and clicking the remove from list " +
                "button.\nBy clicking the Menu Screen button, you return to the menu screen.\n\n" +
                "Take Picture - The Take Picture button lets you take a picture.\nWhen a picture is taken " +
                "it is placed in the imageview so you can see it.\nFrom here you can click the add for submission " +
                "button to add that picture to the image list. The add for submission button will only " +
                "be clickable if you have taken a picture.\nBy clicking the Menu Screen button, you return to " +
                "the menu screen.\n\nLocal Picture Gallery - The Browse From Gallery Button allows you " +
                "to view the pictures that are saved on your device. You can select an image, and that " +
                "image will display in the imageview.\nFrom here you can click the add for submission button " +
                "to add that picture to the image list. The add for submission button will only be clickable " +
                "if you have a picture selected from your picture gallery.\nBy clicking the Menu Screen button " +
                ", you return to the menu screen.\n\nView Past History - View's Past History. Right away " +
                "it will show the latest submission. It will show the image and it's results. There are seven buttons. " +
                "The menu screen button takes the user back to the menu screen, the first button will show the very first " +
                "submission in the database. The latest button will show the most recent submission. Next will show the next " +
                "submission after the current one and next 10 will jump forward ten submissions. Past will show " +
                "the previous submission and past 10 will jump backwards ten submissions. If you are already viewing the latest " +
                ", then the latest button, next button, and the next 10 button will be disabled. If you are viewing the " +
                "first, then the first button, past button, and the past 10 button will be disabled.\n\nSubmission - " +
                "When the submit images button is pressed, every image in the image list will be sent to our server " +
                "for analysis. An AI will determine if there is any food in the pictures. Results will be sent back " +
                "and the user will be able to view it. The image being analyzed will appear, along with it's results. " +
                "If the user submits multiple photos then they will be able to cycle through all of them. \n\nMade by " +
                "Jesse Liu and Andrew Sjoberg.");

        tv.setMovementMethod(new ScrollingMovementMethod());

        homeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(InformationPage.this, MenuScreen.class);
                intent.putExtra("list", (Serializable) iml);
                startActivity(intent);
            }
        });
    }
}
