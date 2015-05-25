package com.yookos.mobileapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.yookos.mobileapp.R;
import com.yookos.mobileapp.models.HobbiesItem;
import com.yookos.mobileapp.models.RecommendFriendsItem;

import java.util.ArrayList;

/**
 * Created by amukelanihlangwane on 20/05/15.
 */
public class RecommendedFriednsAdapter extends BaseAdapter {

    public ArrayList<RecommendFriendsItem> friends;
    Context context;

    public RecommendedFriednsAdapter(Context context,ArrayList<RecommendFriendsItem> friends) {

        this.friends = friends;
        this.context = context;

    }

    @Override
    public int getCount() {
        return friends.size();
    }

    @Override
    public RecommendFriendsItem getItem(int i) {

        return friends.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        View list;


        ViewHolder holder = null;
        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

           // list = new View(context);
            holder = new ViewHolder();

            convertView = inflater.inflate(R.layout.friendsitem, null);

            // set value into textview
            holder.name = (TextView) convertView.findViewById(R.id.txtname);

            // set value into textview
            holder.email = (TextView) convertView.findViewById(R.id.txtemail);

            // set value into textview
            holder.contact = (TextView) convertView.findViewById(R.id.txtcontact);


            holder.btnonyookos = (Button) convertView.findViewById(R.id.btnonyookos);

            holder.btninvite = (Button) convertView.findViewById(R.id.btninvite);





            convertView.setTag(holder);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(friends.get(i).getName());
        holder.email.setText(friends.get(i).getEmail());
        holder.contact.setText(friends.get(i).getNumber());

        if(friends.get(i).getName().equals("Amu")){

            holder.btninvite.setVisibility(View.GONE);
            holder.btnonyookos.setVisibility(View.VISIBLE);
        }else{

            holder.btnonyookos.setVisibility(View.GONE);
            holder.btninvite.setVisibility(View.VISIBLE);
        }



        return convertView;
    }

    public class ViewHolder {
        TextView contact;
        TextView name;
        TextView email;
        Button btnonyookos;
        Button btninvite;
    }



}
