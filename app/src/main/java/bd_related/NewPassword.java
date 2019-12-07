package bd_related;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.toddlergate12.MainActivity;
import com.example.toddlergate12.R;

public class NewPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_set_password);



        InsertPassword(getIntent());

    }

    public void InsertPassword(Intent intent){
        //get instance to db
        PasswordDatabaseAdapter insertDBAdapter = new PasswordDatabaseAdapter(getApplicationContext());
        insertDBAdapter = insertDBAdapter.open();

        String newPassword = intent.getStringExtra("ET_NEW_PW");
        String confirmPassword = intent.getStringExtra("ET_CONF_PW");

        System.out.println("----------------- newPassword " + newPassword + " --------------");
        System.out.println("----------------- confirmPassword " + confirmPassword + " --------------");

        if(confirmPassword.equals(newPassword)){
            insertDBAdapter.insertPassword(confirmPassword);
            System.out.println("------- SUCCESS ---------");
            Intent intentReturnSuccess = new Intent(NewPassword.this, MainActivity.class);
        }else{
            System.out.println("---------- ERRO ----------");
            Intent intentReturnFailed = new Intent(NewPassword.this, ActivitySetPassword.class);
        }



    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
