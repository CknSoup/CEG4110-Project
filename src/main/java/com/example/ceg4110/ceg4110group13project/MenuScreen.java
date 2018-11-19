package com.example.ceg4110.ceg4110group13project;

import android.content.Intent;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

import com.android.internal.http.multipart.MultipartEntity;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;

import java.lang.reflect.Array;
import java.net.URL;
import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import android.widget.Toast;
import java.io.FileInputStream;

public class MenuScreen extends AppCompatActivity implements Serializable{
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
        Intent i = getIntent();
        iml = (ArrayList<File>) i.getSerializableExtra("list");

        takePictureBtn = (Button) findViewById(R.id.takePictureBtn);
        localGalleryBtn = (Button) findViewById(R.id.imageBrowserBtn);
        viewHistoryBtn = (Button) findViewById(R.id.viewHistoryBtn);
        submitBtn = (Button) findViewById(R.id.submitPicturesBtn);
        imageListBtn = (Button) findViewById(R.id.imageListBtn);
        informationBtn = (Button) findViewById(R.id.infoBtn);
        tv = (TextView) findViewById(R.id.label);


        if(iml.size() == 0){
            submitBtn.setEnabled(false);
        }

        String s = "Images in Image List: " + imageCount;
        tv.setText(s);

        takePictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuScreen.this, TakePicture.class);
                i.putExtra("list", (Serializable) iml);
                startActivity(i);
            }
        });

        localGalleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {
                Intent i = new Intent(MenuScreen.this, LocalGallery.class);
                i.putExtra("list", (Serializable) iml);
                startActivity(i);
            }
        });

        viewHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuScreen.this, ViewHistory.class);
                i.putExtra("list", (Serializable) iml);
                startActivity(i);
            }
        });

        imageListBtn.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view)  {
                Intent i = new Intent(MenuScreen.this, ImageList.class);
                i.putExtra("list", (Serializable) iml);
                startActivity(i);
            }
        });

        informationBtn.setOnClickListener(new View.OnClickListener()    {
            @Override
            public void onClick(View view)  {
                Intent i = new Intent(MenuScreen.this, InformationPage.class);
                i.putExtra("list", (Serializable) iml);
                startActivity(i);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String url = "http://18.224.124.230:1030/upload";
                    //HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/a.jpg");
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    HttpClient httpclient = new DefaultHttpClient();

                    HttpPost httppost = new HttpPost(url);

                    InputStreamEntity reqEntity = new InputStreamEntity(
                            new FileInputStream(file), -1);
                    reqEntity.setContentType("multipart/form-data");
                    reqEntity.setChunked(true); // Send in multiple parts if needed


                    httppost.setEntity(reqEntity);
                    HttpResponse response = httpclient.execute(httppost);

                    // How to process the response from the server, places the confidences in the f[] array
                    ResponseHandler<String> handler = new BasicResponseHandler();
                    String body = handler.handleResponse(response);
                    String[] floats = body.split(" ");
                    float[] f = {Float.parseFloat(floats[0]), Float.parseFloat(floats[1])};
                    Log.i("Log response", f[0] + " " + f[1]);

                    //connection.disconnect();
                    Toast.makeText(MenuScreen.this, "Works", Toast.LENGTH_SHORT).show();
                }
                catch(MalformedURLException e){
                }
                catch(IOException e){
                }
            }
        });
    }
}