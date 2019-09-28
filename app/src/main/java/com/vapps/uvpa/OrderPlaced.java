package com.vapps.uvpa;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class OrderPlaced extends AppCompatActivity {
    String custid="", orderId="", mid="";
    SharedPreferences sharedPreferences;
    JSONObject orderDetail;
    JSONObject orderDetails;
    String userid;
    Intent intent;

    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);
        sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
        userid=sharedPreferences.getString("user_id",null);
        orderDetail = new JSONObject();
        JSONObject orderHolder = new JSONObject();


        try {
            // orderDetails.put("user_id","13");
            orderDetail.put("user_id",userid);
            orderDetail.put("order_id",sharedPreferences.getString("order_id",null));

            // orderDetails.put("street",landmark.getText().toString());
            // orderDetails.put("area","Urrapakkam");
            //orderDetails.put("city",city);
            orderHolder.put("mobbucket",orderDetail);
            Log.i("VANIK",orderHolder.toString());
        }

        catch (JSONException e)
        {
            e.printStackTrace();
        }
        Payment payment = new Payment();
        payment.execute("https://www.repairbuck.com/mobbuckets.json?auth_token="+sharedPreferences.getString("auth_token",null),orderHolder.toString());


        if (Build.VERSION.SDK_INT > 21)
        {
            Window window = getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorBlack));
        }
        new Handler().postDelayed
                (new Runnable() {
                    @Override
                    public void run()
                    {


                            Intent intent = new Intent(OrderPlaced.this, RepairOrder1.class);
                            startActivity(intent);

                        finish();
                    }
                }, 700);
    }
    class Payment extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // loadingMsg.setText("Loading");
           // linearLayout.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String result;
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                //connection.addRequestProperty("Accept","application/json");
                connection.addRequestProperty("Content-Type","application/json");
                connection.setRequestMethod("POST");
                // connection.setDoOutput(true);
                connection.connect();
                DataOutputStream outputStream=new DataOutputStream(connection.getOutputStream());
                outputStream.writeBytes(params[1]);
                Log.i("VANIK",params[1]);
                result = getResponse(connection);
                Log.i("VANIK",result);
                return result;

            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String response)
        {

          //  linearLayout.setVisibility(View.INVISIBLE);
            super.onPostExecute(response);
            try {
                JSONObject jsonResponse = new JSONObject(response);
                Log.i("VANIK",jsonResponse.toString());
                // JSONObject dets = new JSONObject(location);
                //   Log.i("VANIK",dets.toString());
                orderDetails = new JSONObject();
                orderDetails.put("user_id",sharedPreferences.getString("user_id", null));
                orderDetails.put("mobbucket_id", jsonResponse.getString("id"));
                orderDetails.put("name",sharedPreferences.getString("username", null));
                orderDetails.put("email",sharedPreferences.getString("email", null));
                orderDetails.put("mobile", sharedPreferences.getString("phone", null));
                //  orderDetails.put("city", dets.getString("city"));
                Log.i("VANIK1", orderDetails.toString());


            }

            catch (JSONException e) {
                e.printStackTrace();
            }

            //Log.i("RESPONSE",response);
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
        { return e.getMessage();
        }
    }
}
