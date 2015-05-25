package com.yookos.mobileapp;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.internal.ho;
import com.yookos.mobileapp.adapter.HobbiesAdapter;
import com.yookos.mobileapp.models.HobbiesItem;

import java.util.ArrayList;
import java.util.List;


public class HobbiesandInterests extends ActionBarActivity {

   Toolbar toolbar;

   Button btnsave;

   ListView lvhobbies;
   EditText txtsearchhobbies;

   ArrayList<String> hobbies;

   HobbiesAdapter adapter;

   ArrayList<HobbiesItem> searchresults;

   HobbiesItem hitem;

    private final int TRIGGER_SERACH = 1;
    // Where did 1000 come from? It's arbitrary, since I can't find average android typing speed.
    private final long SEARCH_TRIGGER_DELAY_IN_MS = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobbiesand_interests);

        setToolbar();

        hobbies = new ArrayList<String>();

        searchresults = new ArrayList<HobbiesItem>();

        hobbies.add("Movies");
        hobbies.add("Entertainment");
        hobbies.add("Music");
        hobbies.add("Soccer");
        hobbies.add("Sports");
        hobbies.add("Sky Diving");
        hobbies.add("Skating");
        hobbies.add("Women");
        hobbies.add("Work");




        btnsave = (Button) findViewById(R.id.btnsave);
        lvhobbies = (ListView) findViewById(R.id.lvhobbies);
        //lvhobbies.setVisibility(View.GONE);
        txtsearchhobbies = (EditText) findViewById(R.id.edhobbies);



        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(HobbiesandInterests.this, CurrentcityActivity.class);
                startActivity(intent);
                finish();


            }
        });

        txtsearchhobbies.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {


              //  Toast.makeText(getApplicationContext(),"searching"+ charSequence.toString(), Toast.LENGTH_SHORT).show();

                if(adapter != null){

                    adapter.hobbies.clear();
                }

                List<String> listClone = new ArrayList<String>();
                for (String curVal : hobbies) {
                    if (curVal.contains(charSequence.toString())){
                        //listClone.add(string);
                        hitem = new HobbiesItem();
                        hitem.setName(curVal);
                        searchresults.add(hitem);


                    }
                }


//                for(int l =0; l< hobbies.size();l++){
//
//                    if(hobbies.get(i).contains(charSequence.toString())){
//
//                        hitem = new HobbiesItem();
//                        hitem.setName(hobbies.get(i));
//                        searchresults.add(hitem);
//                        Toast.makeText(getApplicationContext(),"setting", Toast.LENGTH_SHORT).show();
//                    }
//
//
//                }

                adapter = new HobbiesAdapter(getApplicationContext(), searchresults);

                lvhobbies.setAdapter(adapter);


            }

            @Override
            public void afterTextChanged(Editable s) {
                handler.removeMessages(TRIGGER_SERACH);
                handler.sendEmptyMessageDelayed(TRIGGER_SERACH, SEARCH_TRIGGER_DELAY_IN_MS);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hobbiesand_interests, menu);
        return true;
    }

    public void setToolbar(){

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();

            }
        });


    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == TRIGGER_SERACH) {



            }
        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_skip) {

            Intent intent = new Intent(HobbiesandInterests.this, CurrentcityActivity.class);
            startActivity(intent);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
