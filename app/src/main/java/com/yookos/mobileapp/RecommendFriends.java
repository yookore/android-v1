package com.yookos.mobileapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.yookos.mobileapp.adapter.RecommendedFriednsAdapter;
import com.yookos.mobileapp.models.RecommendFriendsItem;

import java.util.ArrayList;


public class RecommendFriends extends ActionBarActivity {


    Toolbar toolbar;

    ArrayList<RecommendFriendsItem> friends;

    RecommendFriendsItem frienditem;

    ListView friendslist;

    RecommendedFriednsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_friends);

        setToolbar();

        friends = new ArrayList<RecommendFriendsItem>();

        friendslist = (ListView) findViewById(R.id.friendslist);




        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection    = new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};

        Cursor people = getContentResolver().query(uri, projection, null, null, null);

        int indexName = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int indexNumber = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        int indexEmail = people.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS);

        people.moveToFirst();

        do {

            frienditem = new RecommendFriendsItem();

            String name   = people.getString(indexName);
            String number = people.getString(indexNumber);
            String email = people.getString(indexEmail);

            Log.d("name",name+" : "+number+" - "+email);
            frienditem.setName(name);
            frienditem.setEmail(email);
            frienditem.setNumber(number);
            frienditem.setState(false);

            friends.add(frienditem);

            // Do work...
        } while (people.moveToNext());

        adapter = new RecommendedFriednsAdapter(getApplicationContext(), friends);

        friendslist.setAdapter(adapter);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recommend_friends, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_skip) {

            Intent intent = new Intent(RecommendFriends.this, StreamActivity.class);
            startActivity(intent);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
