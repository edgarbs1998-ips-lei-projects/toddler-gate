package bd_related;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }


    //Called when no database exists in disk and the helper class needs
    //to create a new one.
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("LOG BD","----------- COMECEI A CRIAR A TABELA ----------");
        try{

            db.execSQL(Password.DATABASE_CREATE);
            Log.d("LOG BD","------------ ACABEI DE CRIAR A TABELA ----------");
        }catch(Exception e){
            Log.e("Error", "exception");
        }
    }


    //Called when there is a database version mismatch meaning that the version
    // of the database on disk needs to be upgraded to the current version.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Upgrade the existing database to conform to the new version. Multiple
        //previous versions can be handled by comparing _oldVersion and _newVersion values.
        //The simplest case is to drop the old table and create a new one.
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + Password.TABLE_PASSWORDS);
            //Create a new one
            onCreate(db);
        }
    }
}
