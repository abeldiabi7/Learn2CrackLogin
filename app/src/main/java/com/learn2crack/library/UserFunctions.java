package com.learn2crack.library;

/**
 * Author :Raj Amal
 * Email  :raj.amalw@learn2crack.com
 * Website:www.learn2crack.com
 **/

import android.content.Context;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class UserFunctions {

    private JSONParser jsonParser;

    //URL of the PHP API
    private static String indirizzo="192.168.1.13";
    private static String loginURL = "http://"+indirizzo+"/ANDROIDconnect1/learn2crack_login_api/";
    private static String registerURL = "http://"+indirizzo+"/ANDROIDconnect1/learn2crack_login_api/";
    private static String forpassURL = "http://"+indirizzo+"/ANDROIDconnect1/learn2crack_login_api/";
    private static String chgpassURL = "http://"+indirizzo+"/ANDROIDconnect1/learn2crack_login_api/";
    private static String tableURL = "http://"+indirizzo+"/ANDROIDconnect1/learn2crack_login_api/";
    private static String queryURL = "http://"+indirizzo+"/ANDROIDconnect1/learn2crack_login_api/";
    private static String insertSpamURL = "http://"+indirizzo+"/ANDROIDconnect1/learn2crack_login_api/";


    private static String login_tag = "login";
    private static String register_tag = "register";
    private static String forpass_tag = "forpass";
    private static String chgpass_tag = "chgpass";
    private static String table_tag = "table";
    private static String query_tag = "query";
    private static String insertSpam_tag= "insertspam";


    // constructor
    public UserFunctions(){
        jsonParser = new JSONParser();
    }

    /**
     * Function to Login
     **/

    public JSONObject loginUser(String email, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);

        return json;
    }

    /**
     * Function to change password
     **/

    public JSONObject chgPass(String newpas, String email){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", chgpass_tag));

        params.add(new BasicNameValuePair("newpas", newpas));
        params.add(new BasicNameValuePair("email", email));
        JSONObject json = jsonParser.getJSONFromUrl(chgpassURL, params);
        return json;
    }





    /**
     * Function to reset the password
     **/

    public JSONObject forPass(String forgotpassword){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", forpass_tag));
        params.add(new BasicNameValuePair("forgotpassword", forgotpassword));
        JSONObject json = jsonParser.getJSONFromUrl(forpassURL, params);
        return json;
    }






     /**
      * Function to  Register
      **/
    public JSONObject registerUser(String fname, String lname, String email, String uname, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", register_tag));
        params.add(new BasicNameValuePair("fname", fname));
        params.add(new BasicNameValuePair("lname", lname));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("uname", uname));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.getJSONFromUrl(registerURL,params);
        return json;
    }


    /**
     * Function to logout user
     * Resets the temporary data stored in SQLite Database
     * */
    public boolean logoutUser(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        db.resetTables();
        return true;
    }


    /**
     * Function to get the list of the fields and relative types of a table
     **/

    public JSONObject getFiels(String table ){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", table_tag));
        params.add(new BasicNameValuePair("table", table));
        JSONObject json = jsonParser.getJSONFromUrl(tableURL, params);
        return json;
    }

/*

    public int insert(){return 0;}
    public int delete(){return 0;}
    public int select(){return 0;}
    public int update(){return 0;}*/


    public JSONObject execute( String query)
    {///da usare solo in casi di testing
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag",query_tag));
        params.add(new BasicNameValuePair("query", query));
        JSONObject json = jsonParser.getJSONFromUrl(queryURL, params);///servono altri parametri come nome utente e password per validare?????
        return json;
    }
    public JSONObject insertSpam( String msg,String uid)
    {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag",insertSpam_tag));
        params.add(new BasicNameValuePair("msg", msg));
        params.add(new BasicNameValuePair("uid", uid));
        Log.d("daUID", uid);
        Log.d("daMSG", msg);

        JSONObject json=jsonParser.getJSONFromUrl(insertSpamURL, params);

        return json;
    }

}

