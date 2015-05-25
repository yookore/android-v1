package com.yookos.mobileapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;


public class StreamActivity extends ActionBarActivity {


    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);

        setToolbar();



    }


    public void setToolbar(){

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);


        setSupportActionBar(toolbar);

        //getSupportActionBar();
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setTitle("Stream");
        toolbar.inflateMenu(R.menu.menu_stream);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showMenu();

            }
        });




    }

    public void showMenu(){


        Intent intent = new Intent(StreamActivity.this, MenuActivity.class);

        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stream, menu);
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
