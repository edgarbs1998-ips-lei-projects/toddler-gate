package bd_related;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.toddlergate12.EntryActivity;
import com.example.toddlergate12.MainActivity;
import com.example.toddlergate12.R;

public class ActivitySetPassword extends AppCompatActivity {

    DataBaseHelper myDB;
    EditText editNewPW, editConfPW;
    Button btSubmitPW;
    String newPW, newConfPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        myDB = new DataBaseHelper(this);

        btSubmitPW = (Button) findViewById(R.id.button_confirmNewPW);

        AddData();
        //eActivity.ViewAllData();
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

                        Log.e("ASP", "------------- newConfPW 2 -> " + newConfPW);
                        Log.e("ASP", "------------- newPW 2 -> " + newPW);
                        if (newConfPW.equals(newPW)){
                            boolean isInserted = myDB.insertData(newConfPW);
                            if(isInserted) {
                                Toast.makeText(ActivitySetPassword.this, "Password Inserted!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ActivitySetPassword.this, MainActivity.class));
                            }else {
                                Toast.makeText(ActivitySetPassword.this, "Password Not Inserted!", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(ActivitySetPassword.this, "Password nao sao iguais!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }



}
