package com.learn2crack;

/**
 * Author :Raj Amal
 * Email  :raj.amalw@learn2crack.com
 * Website:www.learn2crack.com
 **/

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.learn2crack.library.DatabaseHandler;
import com.learn2crack.library.UserFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class Spam extends Activity {
    Button btn_sendSpam;
    EditText txt_messaggio;



    private static String KEY_SUCCESS = "success";
    private static String KEY_UID = "uid";



















    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.spamboard);
        btn_sendSpam=(Button)findViewById(R.id.button_sendSpam);
        txt_messaggio=(EditText)findViewById(R.id.editText_messaggioSpam);



        btn_sendSpam.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ///aaaaaaaaaaaaaaaaa sendSpam

                NetAsync(view);
            }});
    }

    private class NetCheck extends AsyncTask<String,String,Boolean>







    {
        private ProgressDialog nDialog;







        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            nDialog = new ProgressDialog(Spam.this);
            nDialog.setMessage("Loading..");
            nDialog.setTitle("Checking Network");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... args){




            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
                try {
                    URL url = new URL("http://www.google.com");
                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                    urlc.setConnectTimeout(3000);
                    urlc.connect();
                    if (urlc.getResponseCode() == 200) {
                        return true;
                    }
                } catch (MalformedURLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }



            }
            return false;

        }
        @Override
        protected void onPostExecute(Boolean th){

            if(th == true){
                nDialog.dismiss();
                new ProcessSend().execute();




            }
            else{
                nDialog.dismiss();





            }





        }
    }









    /**
     * Async Task to get and send data to My Sql database through JSON respone.
     **/
    private class ProcessSend extends AsyncTask<String, String, JSONObject> {






        private ProgressDialog pDialog;

        String messaggio;










        @Override
        protected void onPreExecute() {
            super.onPreExecute();









            messaggio = txt_messaggio.getText().toString();




/*
            pDialog = new ProgressDialog(Spam.this);
            pDialog.setTitle("Contacting Servers");
            pDialog.setMessage("Posting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();*/
        }

        @Override
        protected JSONObject doInBackground(String... args) {


            UserFunctions userFunction = new UserFunctions();
            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
            HashMap<String,String> user = new HashMap<String, String>();
            user = db.getUserDetails();
            JSONObject json = userFunction.insertSpam(messaggio,user.get("uid"));

            return json;
        }









        @Override
        protected void onPostExecute(JSONObject json) {


            try {
                if (json.getString(KEY_SUCCESS) != null) {



                    String res = json.getString(KEY_SUCCESS);

                    if(Integer.parseInt(res) == 1){
                     /*   pDialog.setMessage("Message sent");
                        pDialog.setTitle("Getting Data");
                       */ DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                        JSONObject json_user = json.getJSONObject("message");



                        finish();
                    }else{







/*                        pDialog.dismiss();
*/
Log.d("daniele", "erroreee");





                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }


    public void NetAsync(View view){
        new NetCheck().execute();
    }
}