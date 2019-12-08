package com.example.toddlergate12;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;

public class CustomFolder extends AppCompatActivity {

    ListView listview;
    String[] dados;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_folder);
        this.listview = (ListView) findViewById(R.id.file_listView);

        Rect rectangle = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight = rectangle.top;
        int contentViewTop =
                window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        int titleBarHeight= contentViewTop - statusBarHeight;

        Log.d("*** Elenasys :: ", "StatusBar Height= " + statusBarHeight + " , TitleBar Height = " + titleBarHeight);

        dados = new String[] { "Cupcake", "Donut", "Eclair", "Froyo", "Gingerbread",
                "Honeycomb", "Ice Cream Sandwich", "Jelly Bean",
                "KitKat", "Lollipop", "Marshmallow", "Nougat" };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dados);

        listview.setAdapter(adapter);
     /*   String path = Environment.getExternalStorageDirectory().toString()+"/Pictures";
        Log.d("Files", "Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles();
        Log.d("Files", "Size: "+ files.length);
        for (int i = 0; i < files.length; i++)
        {
            Log.d("Files", "FileName:" + files[i].getName());
        }*/

        Cursor mCursor = getContentResolver()
                .query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        null,
                        null,
                        null,
                        MediaStore.Images.Media.DEFAULT_SORT_ORDER);

        mCursor.moveToFirst();
        while(!mCursor.isAfterLast()) {
            Log.d("Files", " - _ID : " + mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media._ID)));
            Log.d("Files", " - File Name : " + mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)));
            Log.d("Files", " - File Path : " + mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA)));
            mCursor.moveToNext();
        }
        mCursor.close();
    }

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }
}
