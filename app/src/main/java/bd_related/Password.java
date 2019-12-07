package bd_related;

public class Password {
    public static final String TABLE_PASSWORDS = "PASSWORDS";
    public static final String KEY_PASSWORD = "pw";
    private String password;

    public static final String DATABASE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + Password.TABLE_PASSWORDS +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Password.KEY_PASSWORD + " TEXT NOT NULL)";

    public Password(){
        this.password = "Example bd_related.Password";
    }

    public Password(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
