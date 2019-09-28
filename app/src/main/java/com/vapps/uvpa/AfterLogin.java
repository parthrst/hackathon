package com.vapps.uvpa;

import android.content.Intent;

import android.os.Handler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;



import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;


public class AfterLogin extends AppCompatActivity {
    private Spinner spinner;
    public String menuUserName = "";
    private URL iconUrl;
    public ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner_search);

        imageView = findViewById(R.id.icondp);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.brand_names,R.layout.support_simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        //user = FirebaseAuth.getInstance().getCurrentUser();

     //   menuUserName = user.getDisplayName();


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.repair_order1,menu);

        menu.add(menuUserName);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId()==R.id.SignOut)
        {
            SignOut();
            return true;

        }

        else if (item.getTitle() == menuUserName)
        {

            startActivity(new Intent(AfterLogin.this,UserSymbol.class));

        }
        return super.onOptionsItemSelected(item);
    }

    public void SignOut()
    {

        /*AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {

                        startActivity(new Intent(AfterLogin.this,MainActivity.class));
                        Toast.makeText(getApplicationContext(),"Signed Out!",Toast.LENGTH_LONG).show();
                        finish();
                    }
                });

*/
    }


    boolean twice;
    @Override
    public void onBackPressed() {

        if (twice == true)
        {
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }

        twice = true;

        Toast.makeText(this, "Please press BACK again to EXIT", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                twice = false;


            }
        }, 3000);
    }






}
