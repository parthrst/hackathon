package com.vapps.uvpa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity
{
    private EditText mUserEmail;
    private EditText mUserPassword;
    int resp;
    private ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    LinearLayout linearLayout;
    String regEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        getSupportActionBar().hide();
        mUserEmail = findViewById(R.id.username);
        mUserPassword = findViewById(R.id.password);
        sharedPreferences = getSharedPreferences("user_details",MODE_PRIVATE);
        linearLayout=findViewById(R.id.progressbar_layout);
    }

    //---------------------------Sign Up-------------------------------------//

    public void SignUp(View view)
    {
        startActivity(new Intent(LoginActivity.this, SignUp.class));
    }

    //--------------------------resetPassword------------------------------//
    public void resetPassword(View view) {

        startActivity(new Intent(LoginActivity.this,Forgotpassword.class));
     }
    //---------------------------Login-------------------------------------//

    public void login(View view)
    {
        JSONObject loginDetails = new JSONObject();
        JSONObject holder = new JSONObject();
        try {
            if (!(mUserEmail.getText().toString().equals("") || mUserPassword.getText().toString().equals("")))
            {
                loginDetails.put("email", mUserEmail.getText().toString());
                loginDetails.put("password", mUserPassword.getText().toString());
                holder.put("user", loginDetails);
                CredentialsVerifier task = new CredentialsVerifier();
                task.execute("http://www.vanikjain.com/api/sessions", holder.toString());
            }
            else
                {
                    Toast.makeText(LoginActivity.this, "Login or Password can't be blank!", Toast.LENGTH_SHORT).show();
                 }
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    //---------------------------AsyncTask-------------------------------------//


    class CredentialsVerifier extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPreExecute()
        { super.onPreExecute();
            linearLayout.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(String... params)
        {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                connection.addRequestProperty("Accept","application/json");
                connection.addRequestProperty("Content-Type","application/json");
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.connect();
                DataOutputStream outputStream=new DataOutputStream(connection.getOutputStream());
                outputStream.writeBytes(params[1]);
                Log.i("LOGIN JSON DATA",params[1]);
                resp=connection.getResponseCode();
                String response=getResponse(connection);
                 Log.i("RESPONSE CODE",String.valueOf(resp));
                 Log.i("RESPONSE",response);
                if(resp!=200)
                {
                    Log.i("LOGIN RESPONSE CODE",String.valueOf(resp));
                    return String.valueOf(resp);
                }
                return response;
            }
            catch (ProtocolException e)
            { e.printStackTrace();
            }
            catch (IOException e)
            { e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String response)
        {
            linearLayout.setVisibility(View.INVISIBLE);
            if (response != null)
            {
                if(response.equals("401"))
                {
                    Toast.makeText(LoginActivity.this, "Please verify your email first! " , Toast.LENGTH_SHORT).show();
                }
                else {
                    try
                    {
                        JSONObject jsonResponse = new JSONObject(response);
                        String success = jsonResponse.getString("success");
                        if (success.equals("true"))
                        {
                           Toast.makeText(getApplicationContext(),"Login successful!",Toast.LENGTH_SHORT);
                            JSONObject jsonUser = jsonResponse.getJSONObject("data");
                            String userid = jsonUser.getString("id");
                            String username = jsonUser.getString("name");
                            String email = jsonUser.getString("email");
                            String token = jsonUser.getString("auth_token");
                            String phone = jsonUser.getString("mobile");
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("username",username);
                            editor.putString("email",email);
                            editor.putString("phone",phone);
                            editor.putString("auth_token",token);
                            editor.putString("user_id",userid);
                            editor.apply();
                            startActivity(new Intent(LoginActivity.this,RepairOrder1.class));
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            else
            {
                Toast.makeText(LoginActivity.this, "Please Check Your Internet Connection and Try Again", Toast.LENGTH_SHORT).show();
            }
        }

    }
    class Reset extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPreExecute()
        { super.onPreExecute();
            //linearLayout.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(String... params)
        {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection=(HttpURLConnection)url.openConnection();
               // connection.addRequestProperty("Accept","application/json");
                connection.addRequestProperty("Content-Type","application/json");
                connection.setRequestMethod("POST");
                //connection.setDoOutput(true);
                connection.connect();
                DataOutputStream outputStream=new DataOutputStream(connection.getOutputStream());
                outputStream.writeBytes(params[1]);
                Log.i("LOGIN JSON DATA",params[1]);
                resp=connection.getResponseCode();
                String response=getResponse(connection);
                Log.i("RESPONSE CODE",String.valueOf(resp));
                Log.i("RESPONSE",response);
                if(resp!=200)
                {
                    Log.i("LOGIN RESPONSE CODE",String.valueOf(resp));
                    return String.valueOf(resp);
                }
                return response;
            }
            catch (ProtocolException e)
            { e.printStackTrace();
            }
            catch (IOException e)
            { e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String response)
        {
            //linearLayout.setVisibility(View.INVISIBLE);
            /*if (response != null)
            {
                if(response.equals("401"))
                {
                    Toast.makeText(LoginActivity.this, "Please enter Registered Email! " , Toast.LENGTH_SHORT).show();
                }
                else {
                    try
                    {
                        JSONObject jsonResponse = new JSONObject(response);
                        String success = jsonResponse.getString("success");
                        if (success.equals("true"))
                        {
                            Toast.makeText(getApplicationContext(),"Link sent successfully!Check your mail",Toast.LENGTH_SHORT);
                            JSONObject jsonUser = jsonResponse.getJSONObject("data");

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            else
            {
                Toast.makeText(LoginActivity.this, "Please Check Your Internet Connection and Try Again", Toast.LENGTH_SHORT).show();
            }*/
            Log.i("TEST",response);
        }

    }
      public String getResponse(HttpURLConnection httpURLConnection)
    {
        String result = "";
        try {
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            int data = inputStreamReader.read();
            while(data!= -1 )
            { result += (char)data;
                data = inputStreamReader.read();
            }
            return result;
               }
        catch(Exception e)
        {
            return e.getMessage();
        }
    }

}