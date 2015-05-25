package com.yookos.mobileapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class VerificationActivity extends ActionBarActivity {


    TextView emailverification, cellverification, oroption;
    Button btnproceed;

    EditText edverificationcode;

    Intent intent;

    public static String EXTRAS_PAYLOAD_KEY;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        emailverification = (TextView) findViewById(R.id.emailverification);
        cellverification = (TextView) findViewById(R.id.cellverification);
        oroption = (TextView) findViewById(R.id.oroption);

        btnproceed = (Button) findViewById(R.id.btnproceed);
        edverificationcode = (EditText) findViewById(R.id.edverificationcode);

        btnproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(VerificationActivity.this, HobbiesandInterests.class);
                startActivity(intent);
                finish();

            }
        });

        intent = getIntent();

        setToolbar();


if(intent.hasExtra("email")) {

    EXTRAS_PAYLOAD_KEY = "";

    if (intent.getStringExtra("email").equals("")) {

        oroption.setVisibility(View.GONE);

        emailverification.setVisibility(View.GONE);

    } else {

        emailverification.setText(intent.getStringExtra("email"));

    }

}


        if(intent.hasExtra("cellnumber")) {
            if (intent.getStringExtra("cellnumber").equals("")) {

                oroption.setVisibility(View.GONE);

                cellverification.setVisibility(View.GONE);


            } else {


                cellverification.setText(intent.getStringExtra("cellnumber"));

            }


        }




    }


    public void setToolbar(){

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);


        setSupportActionBar(toolbar);
        toolbar.setTitle("Stream");
        toolbar.setLogo(R.drawable.ic_launcher);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_verification, menu);
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
