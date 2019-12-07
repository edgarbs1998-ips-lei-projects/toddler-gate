package parents_area;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.toddlergate12.R;

public class parents_area_main extends AppCompatActivity implements View.OnClickListener
{

    private ConstraintLayout location_button;
    private ConstraintLayout password_button;
    private ConstraintLayout profiles_button;
    private ConstraintLayout time_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_parents_area_main);
        location_button = findViewById(R.id.layout_location);
        location_button.setOnClickListener(this);

        password_button = findViewById(R.id.layout_password);
        password_button.setOnClickListener(this);

        profiles_button = findViewById(R.id.layout_profiles);
        profiles_button.setOnClickListener(this);

        time_button = findViewById(R.id.layout_time_limits);
        time_button.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set dialog message
        alertDialogBuilder
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        switch (view.getId()) {

            case R.id.layout_location:
                    // set message
                    alertDialog.setMessage("Location Button Clicked");
                    // show it
                    alertDialog.show();
                break;

            case R.id.layout_password:
                //set message
                alertDialog.setMessage("bd_related.Password Button Clicked");
                // show it
                alertDialog.show();
                break;

            case R.id.layout_profiles:
                alertDialog.setMessage("Profiles Button Clicked");
                // show it
                alertDialog.show();
                break;

            case R.id.layout_time_limits:
                alertDialog.setMessage("Time Limits Button Clicked");
                // show it
                alertDialog.show();
                break;

            default:
                break;
        }
    }

}
