package com.example.toddlergate12;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

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

    int gallery_grid_Images[]={R.drawable.frame_1, R.drawable.frame_2};
    ViewFlipper viewFlipper;


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

        viewFlipper = (ViewFlipper) findViewById(R.id.flipper);
        for(int i=0;i<gallery_grid_Images.length;i++)
        {
            //  This will create dynamic image view and add them to ViewFlipper
            setFlipperImage(gallery_grid_Images[i]);
        }

        jpegCallback = new Camera.PictureCallback(){
            
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                if (data != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data , 0, data .length);

                    bitmap = rotate(bitmap);
                    Bitmap overlay = BitmapFactory.decodeResource(getResources(), R.drawable.frame_2);
                    overlay = Bitmap.createScaledBitmap(overlay, bitmap.getWidth(), bitmap.getHeight(), false);
                    Canvas canvas = new Canvas(bitmap);
                    canvas.drawBitmap(overlay, new Matrix(), null);
                    if(bitmap != null){

                        File filedir=new File(
                                Environment
                                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), GALLERY_DIRECTORY_NAME);
                        if(!filedir.isDirectory()){
                            filedir.mkdir();
                        }

                        File file = new File(filedir, System.currentTimeMillis()+".jpg");

                        try
                        {
                            FileOutputStream fileOutputStream=new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG,100, fileOutputStream);

                            fileOutputStream.flush();
                            fileOutputStream.close();
                            Toast toast = Toast.makeText(getApplicationContext(),"Photo taken",Toast.LENGTH_SHORT);
                            toast.show();
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

        viewFlipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.showNext();
            }
        });
    }

    private void setFlipperImage(int res) {
        Log.i("Set Filpper Called", res+"");
        ImageView image = new ImageView(getApplicationContext());
        image.setBackgroundResource(res);
        viewFlipper.addView(image);
    }

    private Bitmap rotate(Bitmap bitmap) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate(90);

        return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
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

        Camera.Size bestSize = null;
        List<Camera.Size> sizeList = camera.getParameters().getSupportedPreviewSizes();
        bestSize = sizeList.get(0);
        for(int i = 0; i < sizeList.size(); i++){
            if((sizeList.get(i).width * sizeList.get(i).height) > (bestSize.width * bestSize.height)){
                bestSize = sizeList.get(i);
            }

        }
        parameters.setPreviewSize(bestSize.width, bestSize.height);
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
