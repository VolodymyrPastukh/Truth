package com.teamLP.truth.Users;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;

    private static final String IS_REMEMBERME = "IsRememberMe";

    public static final String KEY_PHONE = "phoneNumber";
    public static final String KEY_PASSWORD = "password";

    public SessionManager(Context _context){
        context = _context;
        userSession = context.getSharedPreferences("rememberMeSession", Context.MODE_PRIVATE);
        editor = userSession.edit();
    }

    public void createRememberMeSession(String _phone, String _password){
        editor.putBoolean(IS_REMEMBERME, true);
        editor.putString(KEY_PHONE, _phone);
        editor.putString(KEY_PASSWORD, _password);
        editor.commit();
    }

    public HashMap<String,String> getRememberMeDataFromSession(){
        HashMap<String, String> loginData = new HashMap<>();

        loginData.put(KEY_PHONE, userSession.getString(KEY_PHONE, null));
        loginData.put(KEY_PASSWORD, userSession.getString(KEY_PASSWORD, null));

        return loginData;
    }

    public Boolean checkRememberMe(){
        if(userSession.getBoolean(IS_REMEMBERME, false)){
            return true;
        }else {
            return false;
        }

    }

    public void logoutFromSession(){
        editor.clear();
        editor.commit();
    }
}
