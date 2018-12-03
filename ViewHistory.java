package com.example.ceg4110.ceg4110group13project;

import android.app.SearchableInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.StrictMode;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.Serializable;
import java.io.File;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.net.URLConnection;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.io.IOException;

public class ViewHistory extends AppCompatActivity implements Serializable{
    ImageView imageView;
    TextView tv1;
    TextView tv2;
    List<File> iml;
    Button homeBtn;
    Button fBtn;
    Button lBtn;
    Button nxtBtn;
    Button nxt10Btn;
    Button backBtn;
    Button back10Btn;
    TextView tv3;
    TextView tv4;
    int page;
    int most;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_history);
        Intent i = getIntent();
        iml = (ArrayList<File>) i.getSerializableExtra("list");
        page = (Integer) i.getSerializableExtra("page");

        homeBtn = (Button) findViewById(R.id.historyToHomeBtn);
        fBtn = (Button) findViewById(R.id.firstBtn);
        lBtn = (Button) findViewById(R.id.latestBtn);
        nxtBtn = (Button) findViewById(R.id.nBtn);
        nxt10Btn = (Button) findViewById(R.id.n2Btn);
        backBtn = (Button) findViewById(R.id.bBtn);
        back10Btn = (Button) findViewById(R.id.b2Btn);
        imageView = (ImageView) findViewById(R.id.pastImage);

        tv1 = (TextView) findViewById(R.id.pastCI1);
        tv2 = (TextView) findViewById(R.id.pastCI2);
        tv3 = (TextView) findViewById(R.id.historytv);
        tv4 = (TextView) findViewById(R.id.historytv2);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "foodpic");
        File ff = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "foodci");
        int count;
        URL url;
        try{
            String url3 = "http://18.224.124.230:1030/latest";

            HttpClient client3 = new DefaultHttpClient();
            HttpGet httpGet3 = new HttpGet(url3);

            HttpResponse response3 = client3.execute(httpGet3);
            ResponseHandler<String> handler3 = new BasicResponseHandler();
            String body3 = handler3.handleResponse(response3);

            most = Integer.parseInt(body3);
            if(page == -1){
                page = most;
            }
        }
        catch(Exception e){
        }

        if(page == most){
            nxtBtn.setEnabled(false);
            nxt10Btn.setEnabled(false);
            lBtn.setEnabled(false);
        }

        if(page == 0){
            backBtn.setEnabled(false);
            back10Btn.setEnabled(false);
            fBtn.setEnabled(false);
        }

        try {
            // latest
            // picture/number
            // /confidence/number
            url = new URL("http://18.224.124.230:1030/picture/" + page);
            URLConnection connection = url.openConnection();
            connection.connect();
            int LengthOfFile = connection.getContentLength();
            long total = 0;
            InputStream input = new BufferedInputStream(url.openStream());
            OutputStream output = new FileOutputStream(f);
            byte data[] = new byte[1024];
            while ((count = input.read(data)) != -1) {
                total += count;
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();

            Bitmap b = BitmapFactory.decodeFile(f.getAbsolutePath());
            imageView.setImageBitmap(b);

            String url2 = "http://18.224.124.230:1030/confidence/" + page;

            HttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url2);
            try{
                HttpResponse response = client.execute(httpGet);
                ResponseHandler<String> handler = new BasicResponseHandler();
                String body = handler.handleResponse(response);

                String[] floats = body.split(" ");
                float[] f2 = {Float.parseFloat(floats[0]), Float.parseFloat(floats[1])};
                Log.i("Log response", f2[0] + " " + f2[1]);
                String s1 = String.valueOf(f2[0]);
                String s2 = String.valueOf(f2[1]);
                tv1.setText(s1);
                tv2.setText(s2);
                tv3.setText("Submission number " + (page + 1) + " out of " + (most + 1));
                float ci1 = Float.parseFloat(s1);
                float ci2 = Float.parseFloat(s2);
                float answer = ci1 - ci2;
                if(answer < 0){
                    tv4.setText("No food here... :(");
                }
                else{
                    tv4.setText("Oh yes... I see food! :D");
                }
            }
            catch(Exception e){

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        homeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(ViewHistory.this, MenuScreen.class);
                i.putExtra("list", (Serializable) iml);
                startActivity(i);
            }
        });

        fBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page = 0;
                Intent i = new Intent(ViewHistory.this, ViewHistory.class);
                i.putExtra("list", (Serializable) iml);
                i.putExtra("page", (Serializable) page);
                startActivity(i);
            }
        });

        lBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page = most;
                Intent i = new Intent(ViewHistory.this, ViewHistory.class);
                i.putExtra("list", (Serializable) iml);
                i.putExtra("page", (Serializable) page);
                startActivity(i);
            }
        });

        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page = page + 1;
                Intent i = new Intent(ViewHistory.this, ViewHistory.class);
                i.putExtra("list", (Serializable) iml);
                i.putExtra("page", (Serializable) page);
                startActivity(i);
            }
        });

        nxt10Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                page = page + 10;
                if(page > most){
                    page = most;
                }
                Intent i = new Intent(ViewHistory.this, ViewHistory.class);
                i.putExtra("list", (Serializable) iml);
                i.putExtra("page", (Serializable) page);
                startActivity(i);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                page = page - 1;
                Intent i = new Intent(ViewHistory.this, ViewHistory.class);
                i.putExtra("list", (Serializable) iml);
                i.putExtra("page", (Serializable) page);
                startActivity(i);
            }
        });

        back10Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                page = page - 10;
                if(page < 0){
                    page = 0;
                }
                Intent i = new Intent(ViewHistory.this, ViewHistory.class);
                i.putExtra("list", (Serializable) iml);
                i.putExtra("page", (Serializable) page);
                startActivity(i);
            }
        });

    }
}
