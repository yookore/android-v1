package com.yookos.mobileapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;



public class SettingsActivity extends ActionBarActivity {

    Button signoutBtn;
    SharedPreferences preferences;
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setTitle("Settings");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        signoutBtn = (Button) findViewById(R.id.signoutBtn);


        signoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ApplicationController.getInstance().setPreferences(getApplicationContext(),"firstname","");
                ApplicationController.getInstance().setPreferences(getApplicationContext(),"status","");
                ApplicationController.getInstance().setPreferences(getApplicationContext(),"profilepicture","");
                Intent intent = new Intent(SettingsActivity.this, PreLoginActivity.class);
                startActivity(intent);

                finish();


            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
