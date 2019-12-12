package com.example.toddlergate12;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CameraMain extends AppCompatActivity implements SurfaceHolder.Callback2 {

    Camera camera;
    Camera.PictureCallback jpegCallback;
    SurfaceView csurfaceView;
    SurfaceHolder csurfaceHolder;
    ImageView imageCapture;
    final int CAMERA_REQUEST = 1;
    final int FILE_PERMISSION = 0;
    CameraMain scope = this;
    // Gallery directory name to store the images or videos
    public static final String GALLERY_DIRECTORY_NAME = "Toddler-Gate-CustomFolder";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_main);
        csurfaceView = (SurfaceView)findViewById(R.id.cameraSurfaceView);
        imageCapture = (ImageView)findViewById(R.id.captureImage);

        csurfaceHolder = csurfaceView.getHolder();
        if(ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
            if(ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, FILE_PERMISSION);
            }else{
                csurfaceHolder.addCallback(this);
                csurfaceHolder.setFormat(csurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            }
        }else{
            csurfaceHolder.addCallback(this);
            csurfaceHolder.setFormat(csurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }


        jpegCallback = new Camera.PictureCallback(){
            
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                /*Intent intent = new Intent(scope, ShowCaptureActivity.class);
                intent.putExtra("capture", data);
                startActivity(intent);*/

                if (data != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data , 0, data .length);

                    if(bitmap!=null){

                        File filedir=new File(
                                Environment
                                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), GALLERY_DIRECTORY_NAME);
                        if(!filedir.isDirectory()){
                            filedir.mkdir();
                        }

                       // filedir=new File(getBaseContext().getFilesDir()+"/dirr",System.currentTimeMillis()+".jpg");

                        File file = new File(filedir, System.currentTimeMillis()+".jpg");
                        Log.d("PHOTOPATH", filedir.getPath());

                        try
                        {
                            FileOutputStream fileOutputStream=new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG,100, fileOutputStream);

                            fileOutputStream.flush();
                            fileOutputStream.close();
                        }
                        catch(IOException e){
                            e.printStackTrace();
                        }
                        catch(Exception exception)
                        {
                            exception.printStackTrace();
                        }

                    }
                }
            }
        };

        imageCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });
    }

    private void captureImage() {
        camera.takePicture(null, null, jpegCallback);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        camera = Camera.open();
        Camera.Parameters parameters;
        parameters = camera.getParameters();
        camera.setDisplayOrientation(90);
        parameters.setPreviewFrameRate(30);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        camera.setParameters(parameters);
        try {
            camera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.startPreview();
    }

    @Override
    public void surfaceRedrawNeeded(SurfaceHolder holder) {

    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode) {
            case CAMERA_REQUEST:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    csurfaceHolder.addCallback(this);
                    csurfaceHolder.setFormat(csurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
                }
        }
    }
}
