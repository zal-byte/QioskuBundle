package Session;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSession {
    public Context con;
    SharedPreferences sharedPreferences;
    public String userSession = "userSession";
    public String u_name = "u_name";
    public String u_address = "u_address";
    public String u_phone = "u_phone";
    public String u_email = "u_email";
    public String role = "role";
    public String token = "token";
    SharedPreferences.Editor editor;

    //jjjsiSij
    String is_login = "is_login";
    // end of jjjsiSij

    public UserSession(Context con) {
        this.con = con;
        sharedPreferences = con.getSharedPreferences(userSession, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setIsLogin(boolean isLogin, String u_name, String u_address, String u_phone, String u_email, String role, String token) {
        editor.putBoolean(this.is_login, isLogin);
        editor.putString(this.u_name, u_name);
        editor.putString(this.u_address, u_address);
        editor.putString(this.u_phone, u_phone);
        editor.putString(this.u_email, u_email);
        editor.putString(this.role, role);
        editor.putString(this.token, token);
        editor.apply();
        editor.commit();
    }

    public boolean isLogin() {
        return sharedPreferences.getBoolean(this.is_login, false);
    }
}
