package com.yookos.mobileapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yookos.mobileapp.ApplicationController;
import com.yookos.mobileapp.R;
import com.yookos.mobileapp.models.HobbiesItem;

import java.util.ArrayList;

/**
 * Created by amukelanihlangwane on 18/05/15.
 */
public class HobbiesAdapter extends BaseAdapter {

    public ArrayList<HobbiesItem> hobbies;
    Context context;

    public HobbiesAdapter(Context context,ArrayList<HobbiesItem> hobbies) {

        this.hobbies = hobbies;
        this.context = context;

    }

    @Override
    public int getCount() {
        return hobbies.size();
    }

    @Override
    public HobbiesItem getItem(int i) {

        return hobbies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        View list;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            list = new View(context);


            list = inflater.inflate(R.layout.searchresultsitem, null);

            // set value into textview
            TextView name = (TextView) list.findViewById(R.id.txtresult);

            name.setText(hobbies.get(i).getName());


        } else {
            list = (View) convertView;
        }

        return list;
    }
}
