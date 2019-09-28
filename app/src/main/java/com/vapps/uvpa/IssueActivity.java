package com.vapps.uvpa;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class IssueActivity extends AppCompatActivity
{
    Intent i;
   CheckBox checkBox0,checkBox1,checkBox2,checkBox3,checkBox4,checkBox5,checkBox6,checkBox7;
   JSONObject jsonObj;
   ArrayList<String> problemids;

    String[] type_repair=
            {"Battery Problem",
            "Button Problem",
            "Broken Screen",
            "Charging Problem",
            "Camera Problem",
            "Water Damage",
            "Headphone Jack Issue","Software Issue"};

    //String[] est_price={"1500","1000","2500","1500","2000","1750","700","1000"};
    public void nextActivity(View view) {
        Intent k = new Intent(IssueActivity.this, BackupPhoneSelection.class);
        if (problemids.size() == 0)
        {
            Toast.makeText(this, "Select at least one issue!", Toast.LENGTH_SHORT).show();
        }
        else
        {
        try {
            String str = i.getStringExtra("repair");
            jsonObj = new JSONObject(str);
            Log.i("META10",problemids.toString());
            JSONArray jsonArray = new JSONArray(problemids);
            Log.i("META10",jsonArray.toString());
            jsonObj.put("problem_ids",jsonArray);

            } catch(JSONException e)
        {
                e.printStackTrace();
            }
            k.putExtra("param", jsonObj.toString());
            k.putExtra("gadget",i.getStringExtra("gadget"));
            Log.i("gadget",i.getStringExtra("gadget"));
            startActivity(k);

        }
    }
    public void value(View view){
        boolean checked=((CheckBox)view).isChecked();
        switch(view.getId()){
            case R.id.prob:
                  if(checked){
                      problemids.add("0");
                                       }
                  else
                      problemids.remove("0");
                break;
            case R.id.prob1:
                if(checked){
                    problemids.add("1");
                }
                else
                    problemids.remove("1");
                break;
            case R.id.prob2:
                if(checked){
                    problemids.add("2");
                }
                else
                    problemids.remove("2");
                break;
            case R.id.prob3:
                if(checked){
                    problemids.add("3");
                }
                else
                    problemids.remove("3");
                break;
            case R.id.prob4:
                if(checked){
                    problemids.add("4");
                }
                else
                    problemids.remove("4");
                break;
            case R.id.prob5:
                if(checked){
                    problemids.add("5");
                }
                else
                    problemids.remove("5");
                break;
            case R.id.prob6:
                if(checked){
                    problemids.add("6");
                }
                else
                    problemids.remove("6");
                break;
            case R.id.prob7:
                if(checked){
                    problemids.add("7");
                }
                else
                    problemids.remove("7");
                break;
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);
        checkBox0=findViewById(R.id.prob);checkBox1=findViewById(R.id.prob1);
        checkBox2=findViewById(R.id.prob2);checkBox3=findViewById(R.id.prob3);
        checkBox4=findViewById(R.id.prob4);checkBox5=findViewById(R.id.prob5);
        checkBox6=findViewById(R.id.prob6);checkBox7=findViewById(R.id.prob7);
        checkBox0.setText(type_repair[0]);checkBox1.setText(type_repair[1]);
        checkBox2.setText(type_repair[2]);checkBox3.setText(type_repair[3]);
        checkBox4.setText(type_repair[4]);checkBox5.setText(type_repair[5]);
        checkBox6.setText(type_repair[6]);checkBox7.setText(type_repair[7]);


        problemids=new ArrayList<>();

        i=getIntent();


    }

}

