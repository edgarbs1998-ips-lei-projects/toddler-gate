package bd_related;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.toddlergate12.MainActivity;
import com.example.toddlergate12.R;

import java.util.regex.Pattern;

public class ActivitySetPassword extends AppCompatActivity {

    DataBaseHelper myDB;
    EditText editNewPW, editConfPW;
    Button btSubmitPW;
    String newPW, newConfPW;

    public static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +     //no minimo 1 digito
                    "(?=.*[a-zA-Z])" +      //qualquer letras
                    "(?=\\S+$)" +       //nao deixa meter espaços
                    ".{6,}" +       //no minimo 6 caracteres
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        myDB = new DataBaseHelper(this);

        btSubmitPW = (Button) findViewById(R.id.button_confirmNewPW);
        AddData();
    }

    public void AddData(){
        btSubmitPW.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                    editNewPW = (EditText) findViewById(R.id.editText_newPW);
                    editConfPW = (EditText) findViewById(R.id.editText_confirmNewPW);

                    newPW = editNewPW.getText().toString();
                    newConfPW = editConfPW.getText().toString();

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            ActivitySetPassword.this);

                    // set dialog message
                    alertDialogBuilder
                            .setNegativeButton("Ok",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // if this button is clicked, just close
                                    // the dialog box and do nothing
                                    dialog.cancel();
                                }
                            });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    if(newPW.isEmpty() || !PASSWORD_PATTERN.matcher(newPW).matches() || newConfPW.isEmpty() || !PASSWORD_PATTERN.matcher(newConfPW).matches()){
                        // set message
                        alertDialog.setMessage("Please insert a Password with 6 characters containing atleast 1 number.");
                        // show it
                        alertDialog.show();
                    }else{
                        if (newConfPW.equals(newPW)){
                            boolean isInserted = myDB.insertData(newConfPW);
                            if(isInserted) {
                                Toast.makeText(ActivitySetPassword.this, "Password Inserted!", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ActivitySetPassword.this, MainActivity.class));
                            }else {
                                Toast.makeText(ActivitySetPassword.this, "Password Not Inserted!", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(ActivitySetPassword.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    }
                }
        );
    }



}
