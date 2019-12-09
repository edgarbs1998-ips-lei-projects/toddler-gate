package custom_folder;

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
import android.widget.SimpleAdapter;

import com.example.toddlergate12.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CustomFolder extends AppCompatActivity {

    ListView listview;
    String[] dados;
    //title
    //List<String> ListviewTitle = new ArrayList<>(Arrays.asList("Listview Title 1", "Listview Title 1","Listview Title 3"));
    List<String> ListviewTitle = new ArrayList<>();
    //description
    //List<String> ListviewDescription = new ArrayList<>(Arrays.asList("Listview Description 1", "Listview Description 1","Listview Description 3"));
    List<String> ListviewDescription = new ArrayList<>();

    //images
    //List<Integer> ListviewImages = new ArrayList<>(Arrays.asList(R.drawable.ic_picture_thumbnail, R.drawable.ic_picture_thumbnail,R.drawable.ic_picture_thumbnail));
    List<Integer> ListviewImages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_folder);
        this.listview = (ListView) findViewById(R.id.file_listView);

        List<HashMap<String,String>> aList = new ArrayList<>();


        String[] from = {
                "ListImages", "ListTitle", "ListDescription"
        };

        int[] to = {
          R.id.listview_images, R.id.Title, R.id.Description
        };





      /*  dados = new String[] { "Cupcake", "Donut", "Eclair", "Froyo", "Gingerbread",
                "Honeycomb", "Ice Cream Sandwich", "Jelly Bean",
                "KitKat", "Lollipop", "Marshmallow", "Nougat" };*/

        ArrayList<String> imagens = new ArrayList<>();

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, imagens);

        //listview.setAdapter(adapter);
     /*   String path = Environment.getExternalStorageDirectory().toString()+"/Pictures";
        Log.d("Files", "Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles();
        Log.d("Files", "Size: "+ files.length);
        for (int i = 0; i < files.length; i++)
        {
            Log.d("Files", "FileName:" + files[i].getName());
        }*/

       /* Cursor mCursor = getContentResolver()
                .query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        null,
                        null,
                        null,
                        MediaStore.Images.Media.DEFAULT_SORT_ORDER);*/

        Cursor mCursor = getContentResolver().query(
                MediaStore.Files.getContentUri("external"),
                null,
                MediaStore.Images.Media.DATA + " like ? ",
                new String[] {"%ToddlerGate-Screenshots%"},
                null);

        mCursor.moveToFirst();


        while(!mCursor.isAfterLast()) {
            String image_name = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
            String file_ID = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media._ID));
            String current_filePath = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));

            if(image_name != null && file_ID != null && current_filePath != null){
                //imagens.add(image_name);
                ListviewTitle.add(image_name);
                ListviewDescription.add(file_ID);
                if(getFileExtension(new File(current_filePath)).equals("png"))
                 ListviewImages.add(R.drawable.ic_picture_thumbnail);
            }

            Log.d("Files", " - _ID : " + mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media._ID)));
            Log.d("Files", " - File Name : " + mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)));
            Log.d("Files", " - File Path : " + mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA)));
            mCursor.moveToNext();
        }

        for (String title : ListviewTitle){
            int current_index = ListviewTitle.indexOf(title);
            HashMap<String, String> hm = new HashMap<>();
            hm.put("ListTitle",ListviewTitle.get(current_index));
            hm.put("ListDescription",ListviewDescription.get(current_index));
            hm.put("ListImages",Integer.toString(ListviewImages.get(current_index)));
            aList.add(hm);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(),aList, R.layout.customfolder_listview_items, from,to);
        this.listview.setAdapter(simpleAdapter);
        //listview.setAdapter(adapter);
        mCursor.close();
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
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
