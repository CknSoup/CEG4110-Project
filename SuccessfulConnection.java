package com.example.andrewsjoberg.uploadtest;

import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Must be changed...
                String url = "http://18.224.124.230:1030/upload";
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/a.jpg"); // Name of the file that is to be sent
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                try {
                    HttpClient httpclient = new DefaultHttpClient();

                    HttpPost httppost = new HttpPost(url);


                    InputStreamEntity reqEntity = new InputStreamEntity(
                            new FileInputStream(file), -1);
                    reqEntity.setContentType("multipart/form-data");
                    reqEntity.setChunked(true); // Send in multiple parts if needed


                    httppost.setEntity(reqEntity);
                    HttpResponse response = httpclient.execute(httppost);
                    Log.i("Log response", response.getParams().toString() + "+++" + response.toString() + "+++" + response.getEntity().toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    }

