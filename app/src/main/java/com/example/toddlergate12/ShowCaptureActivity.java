package com.example.toddlergate12;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class ShowCaptureActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_capture);

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        byte[] b = extras.getByteArray("capture");

        if(b != null) {
            imageView = (ImageView) findViewById(R.id.captureImage);

            Bitmap decodeBitmap = BitmapFactory.decodeByteArray(b, 0, b.length);

            imageView.setImageBitmap(decodeBitmap);
        }
    }
}
