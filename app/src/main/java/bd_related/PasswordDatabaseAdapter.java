package bd_related;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class PasswordDatabaseAdapter {
    private static final String DATABASE_NAME = "BlocoNotasDB.db";
    static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase db;
    private static DataBaseHelper dbHelper;

    public PasswordDatabaseAdapter(Context context){
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public PasswordDatabaseAdapter open() throws SQLException{
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){ db.close(); }

    public long insertPassword(String password){
        long id = -1;
        try{
            ContentValues newValues = new ContentValues();
            newValues.put(Password.KEY_PASSWORD, password);
            System.out.println("------------- password " + password);
            System.out.println("---------- newValues -> " + newValues);

            db = dbHelper.getWritableDatabase();
            System.out.println("----------------------- db -> " + db + " --------------------");
            id = db.insert(Password.TABLE_PASSWORDS, null, newValues);
        } catch(Exception ex){
            System.out.println("Exceptions: " + ex);
        }
        return id;
    }

    public List<Password> getPasswordList(){
        List<Password> passwords = new ArrayList<>();
        Cursor cursor = dbHelper.getReadableDatabase().query(Password.TABLE_PASSWORDS,
                new String[]{Password.KEY_PASSWORD},
                null, null, null, null, null);

        if(cursor.moveToFirst()){
            do{
                Password password = new Password();
                password.setPassword(cursor.getString(cursor.getColumnIndex(Password.KEY_PASSWORD)));
                passwords.add(password);
            }while(cursor.moveToNext());
        }
        return passwords;
    }
}
