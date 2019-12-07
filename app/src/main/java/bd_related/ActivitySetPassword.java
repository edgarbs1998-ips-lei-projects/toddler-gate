package bd_related;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.toddlergate12.R;

public class ActivitySetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);


        /*
        Button bt = (Button) findViewById(R.id.button_confirmNewPW);
        bt.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                System.out.println(" -------------------- entrei no onClick ---------------------");

            InsertNew(v);

            }
        });

         */
    }





    public void InsertNew(View v){
        System.out.println("------------------ entrei no InsertNew ------------------");

        EditText eNewPassword = (EditText) findViewById(R.id.editText_newPW);
        EditText eConfirmPassword = (EditText) findViewById(R.id.editText_confirmNewPW);

        String newPassword = eNewPassword.getText().toString();
        String confirmPassword = eConfirmPassword.getText().toString();


        Intent intent = new Intent(this, NewPassword.class);
        intent.putExtra("ET_NEW_PW", newPassword);
        intent.putExtra("ET_CONF_PW", confirmPassword);
        startActivity(intent);


        Toast toast2 = Toast.makeText(this, "saí do InsertNew", Toast.LENGTH_SHORT);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
