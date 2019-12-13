package custom_folder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.toddlergate12.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class CustomFolder extends AppCompatActivity {

    ListView listview;
    //description
    List<String> ListviewDescription = new ArrayList<>();
    List<String> PathsList = new ArrayList<>();
    //images
    List<Integer> ListviewImages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_folder);
        this.listview = findViewById(R.id.file_listView);

        this.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String currPath = PathsList.get(position);
                Uri currentFileUri = Uri.parse("file://" + currPath);
                String MimeType = getMimeType(currPath);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);

                if(MimeType.contains("image")){
                    intent.setDataAndType(currentFileUri, "image/*");
                    startActivity(intent);
                }

                if(MimeType.contains("video")){
                    intent.setDataAndType(currentFileUri, "video/*");

                    try {
                        startActivity(intent);
                    }
                    catch (ActivityNotFoundException e) {
                        Toast.makeText(CustomFolder.this,
                                "No Application Available to Play Videos",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                if(MimeType.contains("audio")){
                    intent.setDataAndType(currentFileUri, "audio/*");

                    try {
                        startActivity(intent);
                    }
                    catch (ActivityNotFoundException e) {
                        Toast.makeText(CustomFolder.this,
                                "No Application Available to Play Audio",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                if(MimeType.contains("pdf")){
                    intent.setDataAndType(Uri.parse("file://" + PathsList.get(position)), "application/pdf");
                    try {
                        startActivity(intent);
                    }
                    catch (ActivityNotFoundException e) {
                        Toast.makeText(CustomFolder.this,
                                "No Application Available to View PDF",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        List<HashMap<String,String>> aList = new ArrayList<>();


        String[] from = {
                "ListImages", "ListDescription"
        };

        int[] to = {
          R.id.listview_images, R.id.Description
        };
        String customFolderPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath();
        Log.d("Files", "Path: " + customFolderPath);
        File directory = new File(customFolderPath);
        File [] folderfiles = directory.listFiles();

        if(folderfiles != null) {
            Arrays.sort(folderfiles, new Comparator<File>() {
                @Override
                public int compare(File file1, File file2) {
                    if (getMimeType(file1.getPath()).equals(getMimeType(file2.getPath()))) {
                        return 0;
                    }
                    if (getMimeType(file1.getPath()).equals("")) {
                        return -1;
                    }
                    if (getMimeType(file2.getPath()).equals("")) {
                        return 1;
                    }
                    return getMimeType(file1.getPath()).compareTo(getMimeType(file2.getPath()));
                }
            });


            List<File> folderFilesList = Arrays.asList(folderfiles);


            for (File file : folderFilesList) {
                String fname = folderFilesList.get(folderFilesList.indexOf(file)).getName();
                String fpath = folderFilesList.get(folderFilesList.indexOf(file)).getPath();
                String fMimeType = getMimeType(fpath);

                if (fname != null && fpath != null) {

                    ListviewDescription.add(fname);
                    PathsList.add(fpath);
                    if (fMimeType.contains("image"))
                        ListviewImages.add(R.drawable.ic_picture_thumbnail);
                    if (fMimeType.contains("audio"))
                        ListviewImages.add(R.drawable.ic_audio_file);
                    if (fMimeType.contains("video"))
                        ListviewImages.add(R.drawable.ic_video_player);
                    if (getFileExtension(new File(fpath)).equals("pdf"))
                        ListviewImages.add(R.drawable.ic_pdf_icon);

                }
            }

            for (String title : ListviewDescription) {
                int current_index = ListviewDescription.indexOf(title);
                HashMap<String, String> hm = new HashMap<>();
                hm.put("ListDescription", ListviewDescription.get(current_index));
                hm.put("ListImages", Integer.toString(ListviewImages.get(current_index)));
                aList.add(hm);
            }
            SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.customfolder_listview_items, from, to);
            this.listview.setAdapter(simpleAdapter);
        }
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }

    private String getMimeType(String url)
    {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);

        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.toLowerCase());
        }
        return type;
    }
}
