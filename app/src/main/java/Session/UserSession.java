package Session;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSession {
    public Context con;
    SharedPreferences sharedPreferences;
    public String userSession = "userSession";
    SharedPreferences.Editor editor;

    //jjjsiSij
    String is_login = "is_login";
    // end of jjjsiSij

    public UserSession(Context con)
    {
        this.con = con;
        sharedPreferences = con.getSharedPreferences(userSession, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void setIsLogin(boolean isLogin)
    {
        editor.putBoolean(this.is_login, isLogin);
        editor.apply();
    }
    public boolean isLogin()
    {
        return sharedPreferences.getBoolean(is_login, false);
    }
}
