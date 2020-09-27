package com.teamLP.truth.Users;

import android.content.Context;
import android.content.SharedPreferences;

import com.teamLP.truth.Users.model.UserData;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;

    public static final String USERLOGIN_SESSION = "userLoginSession";
    public static final String REMEMBERME_SESSION = "rememberMeSession";

    private static final String IS_LOGGEDIN = "IsLoggedIn";
    private static final String IS_REMEMBERME = "IsRememberMe";

    //User login strings
    public static final String KEY_USERNAME = "username";
    public static final String KEY_USERFULLNAME = "fullname";
    public static final String KEY_USERPHONE = "phoneNumber";
    public static final String KEY_USEREMAIL = "email";
    public static final String KEY_USERGENDER = "gender";
    public static final String KEY_USERAGE = "age";
    public static final String KEY_USERINFO = "owninfo";

    //Remember me strings
    public static final String KEY_PHONE = "phoneNumber";
    public static final String KEY_PASSWORD = "password";

    public SessionManager(Context _context, String session){
        context = _context;
        userSession = context.getSharedPreferences(session, Context.MODE_PRIVATE);
        editor = userSession.edit();
    }



    /*
    Remember me session
    */
    public void createUserLoginSession(UserData userData){
        editor.putBoolean(IS_LOGGEDIN, true);
        editor.putString(KEY_USERNAME, userData.getUsername());
        editor.putString(KEY_USERFULLNAME, userData.getFullname());
        editor.putString(KEY_USERPHONE,userData.getPhoneNumber());
        editor.putString(KEY_USEREMAIL, userData.getEmail());
        editor.putString(KEY_USERAGE, userData.getAge());
        editor.putString(KEY_USERGENDER, userData.getGender());
        editor.putString(KEY_USERINFO, userData.getOwninfo());
        editor.commit();
    }

    public HashMap<String,String> getUserLoginDataFromSession(){
        HashMap<String, String> loginData = new HashMap<>();

        loginData.put(KEY_USERNAME, userSession.getString(KEY_USERNAME, null));
        loginData.put(KEY_USERFULLNAME, userSession.getString(KEY_USERFULLNAME, null));
        loginData.put(KEY_USERPHONE, userSession.getString(KEY_USERPHONE, null));
        loginData.put(KEY_USEREMAIL, userSession.getString(KEY_USEREMAIL, null));
        loginData.put(KEY_USERGENDER, userSession.getString(KEY_USERGENDER, null));
        loginData.put(KEY_USERAGE, userSession.getString(KEY_USERAGE, null));
        loginData.put(KEY_USERINFO, userSession.getString(KEY_USERINFO, null));

        return loginData;
    }

    public Boolean checkLoggedIn(){
        if(userSession.getBoolean(IS_LOGGEDIN, false)){
            return true;
        }else {
            return false;
        }

    }

    public void logoutFromUserSession(){
        editor.clear();
        editor.commit();
    }

    /*
    Remember me session
    */
    public void createRememberMeSession(String _phone, String _password){
        editor.putBoolean(IS_REMEMBERME, true);
        editor.putString(KEY_PHONE, _phone);
        editor.putString(KEY_PASSWORD, _password);
        editor.commit();
    }

    public HashMap<String,String> getRememberMeDataFromSession(){
        HashMap<String, String> rememberMeData = new HashMap<>();

        rememberMeData.put(KEY_PHONE, userSession.getString(KEY_PHONE, null));
        rememberMeData.put(KEY_PASSWORD, userSession.getString(KEY_PASSWORD, null));

        return rememberMeData;
    }

    public Boolean checkRememberMe(){
        if(userSession.getBoolean(IS_REMEMBERME, false)){
            return true;
        }else {
            return false;
        }

    }

    public void logoutFromRememberMeSession(){
        editor.clear();
        editor.commit();
    }
}
