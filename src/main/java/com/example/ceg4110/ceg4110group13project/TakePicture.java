package com.example.ceg4110.ceg4110group13project;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View;
import java.io.FileOutputStream;
import android.media.MediaScannerConnection;

import java.io.Serializable;
import java.util.Calendar;
import android.content.ContextWrapper;
import android.content.Context;
import android.graphics.Matrix;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class TakePicture extends AppCompatActivity{

    private Button pictureBtn;
    private Button homeBtn;
    private ImageView iv;
    Uri file;
    List<File> iml;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_picture);
        Intent i = getIntent();
        iml = (ArrayList<File>) i.getSerializableExtra("list");

        pictureBtn = (Button) findViewById(R.id.button_image);
        homeBtn = (Button) findViewById(R.id.returnBtn);
        iv = (ImageView) findViewById(R.id.imageview);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            pictureBtn.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        homeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(TakePicture.this, MenuScreen.class);
                intent.putExtra("list",(Serializable) iml);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == 0){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                pictureBtn.setEnabled(true);
            }
        }
    }

    public void takePicture(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            iv.setImageBitmap(thumbnail);
            Bitmap bm = Bitmap.createBitmap(thumbnail, 0 ,0, thumbnail.getWidth(), thumbnail.getHeight(), matrix, true);

            saveImage(bm);

//            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//            iv.setImageBitmap(thumbnail);
//            saveImage(thumbnail);
        }
        else{
            Intent intent = new Intent(TakePicture.this, TakePicture.class);
            intent.putExtra("list",(Serializable) iml);
            startActivity(intent);
        }
    }

    public String saveImage(Bitmap bm) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                //Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "seefood");
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }
}
