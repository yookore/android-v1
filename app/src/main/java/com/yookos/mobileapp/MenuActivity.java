package com.yookos.mobileapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MenuActivity extends ActionBarActivity {

    /*private SharedPreferences preferences;*/
    private String displayName;
    private ImageView menuProfileImage;
    private ImageView imgBlogsMenuIcon;
    private ImageView imgUserProfileMenuIcon;
    private ImageView imgFriendsMenuIcon;
    public ImageView imgGroupsMenuIcon;
    public ImageView imgPhotosMenuIcon, user_pages;
    private TextView txtUserFullName;
    private LinearLayout settingsMenuIcon;

    TextView friends_count;

    NetworkImageView menu_profile_image;
    ImageLoader imageloader;
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        toolbar = (Toolbar) findViewById(R.id.app_bar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setTitle("Menu");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();
            }
        });
//        ActionBar actionBar = getActionBar();
//        actionBar.setTitle("Menu");

       // fetchProfileInformation();

        imageloader = ApplicationController.getInstance().getmImageLoader();

        menu_profile_image = (NetworkImageView) findViewById(R.id.menu_profile_image);
        user_pages = (ImageView) findViewById(R.id.user_pages);

        menu_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);

            }
        });

/*
        if(!ApplicationController.getInstance().getPreferenceByKey("profilepicture").equals("") && !ApplicationController.getInstance().getPreferenceByKey("profilepicture").equals("null")){

           // Toast.makeText(getApplicationContext(),ApplicationController.getInstance().getPreferenceByKey("profilepicture"),Toast.LENGTH_SHORT ).show();

            menu_profile_image.setImageUrl(ApplicationController.getInstance().getPreferenceByKey("profilepicture"),imageloader);
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // Add clipping to displayPictures
                menu_profile_image.setClipToOutline(true);

            } else {
                // do something for phones running an SDK before froyo
            }
        }
*/

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setDisplayUseLogoEnabled(false);
            toolbar.setLogo(R.drawable.logo_filler);
            toolbar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background_pre_jellybeans));
        }

        /*preferences = PreferenceManager.getDefaultSharedPreferences(this);*/

        //displayName = ApplicationController.getInstance().getPreferenceByKey("fullname");
        // preferences.getString("Firstname","Yookos User") + " " + preferences.getString("Lastname","");



        settingsMenuIcon = (LinearLayout) findViewById(R.id.settings);

        imgGroupsMenuIcon = (ImageView) findViewById(R.id.imggroups);
        imgBlogsMenuIcon =(ImageView) findViewById(R.id.blogs);
        imgUserProfileMenuIcon =(ImageView) findViewById(R.id.user_profile);
        imgFriendsMenuIcon =(ImageView) findViewById(R.id.user_friends);
        imgPhotosMenuIcon = (ImageView) findViewById(R.id.user_photos);
        friends_count = (TextView) findViewById(R.id.menu_stats_friends_count);
        menuProfileImage = (ImageView) findViewById(R.id.menu_profile_image);

        //GetFriends();
       // fetchProfileInformation();
        // Group
        imgGroupsMenuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Intent intent = new Intent(getApplicationContext(), GroupsActivity.class);
                startActivity(intent);
*/
            }
        });

        // Settings
        settingsMenuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);

            }
        });

        // Blogs
        imgBlogsMenuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
      /*          Intent intent = new Intent(getApplicationContext(), BlogsActivity.class);
                startActivity(intent);*/

            }
        });

        // Profile
        imgUserProfileMenuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                intent.putExtra("displayName", displayName);
                intent.putExtra("username", ApplicationController.getInstance().getPreferenceByKey("username",""));

                startActivity(intent);*/

            }
        });

        // Set intent for profile image onclick event
        menuProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);

               /* Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                intent.putExtra("displayName", displayName);
                intent.putExtra("username", ApplicationController.getInstance().getPreferenceByKey("username",""));

                startActivity(intent);*/
            }
        });

        // Friends
        imgFriendsMenuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent intent = new Intent(getApplicationContext(), FriendsActivity.class);
                startActivity(intent);*/

            }
        });

        // Photos
        imgPhotosMenuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(getApplicationContext(), PhotosActivity.class);
                startActivity(intent);*/

            }
        });


        //Pages
        user_pages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(getApplicationContext(), GroupsActivity.class);
                intent.putExtra("pages",true);
                startActivity(intent);*/

            }
        });


        txtUserFullName = (TextView)findViewById(R.id.menu_profile_fullname);
        txtUserFullName.setText(displayName);

      //  txtUserFullName.setTypeface(ApplicationController.getInstance(getAssets()).fontsCustomRobotoMedium);

    }


    public void GetFriends() {

        RequestQueue request = Volley.newRequestQueue(getApplicationContext());

        String url = "http://192.168.10.123:8000/api/socialgraph/friends/ponchy/";
        //String url = "http://41.160.30.173:3002/api/socialgraph/friends/"+ ApplicationController.getInstance().getPreferenceByKey("username","")+"/";

        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    JSONArray list = response;
                    JSONObject friend;
                    friends_count.setText(""+list.length());
                    //menu_stats_friends.setText("" + list.length());



                    //adapter = new FriendsProfileAdapter(getApplication(), friends);

                    //gvfriends.setAdapter(adapter);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        request.add(jsObjRequest);


    }
/*
    private void fetchProfileInformation() {
        String endpoint = "";



        RequestQueue request = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, endpoint, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("response", "" + response);

                        // finish();
                      //  populateStreamList(response);

                        try {
                            // Set the profile picture url
                            //if(!response.getString("imageurl").toString().equals("null")) {
                            ApplicationController.getInstance().setPreferences(getApplicationContext(), "profileImage", response.getString("imageurl"));


                            if (response.has("firstname") && response.has("lastname")) {
                                ApplicationController.getInstance().setPreferences(getApplicationContext(), "fullname", response.getString("firstname") + " " + response.getString("lastname"));
                            } else if (response.has("firstname")) {
                                ApplicationController.getInstance().setPreferences(getApplicationContext(), "fullname", response.getString("firstname"));
                            } else if (response.has("lastname")) {
                                ApplicationController.getInstance().setPreferences(getApplicationContext(), "fullname", response.getString("lastname"));
                            }
//                    txtProfileFullname.setText(ApplicationController.getInstance().getPreferenceByKey("fullname"));
//
//                    imgProfilePicture.setImageUrl(response.getString("imageurl"), imageLoader);
//                    imbProfileBackgroundPicture.setImageUrl(response.getString("imageurl"), imageLoader);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplication(), "You don't have any activities", Toast.LENGTH_LONG).show();
                        Log.d("responseerror", "" + error.getMessage());

                    }
                }) {

            @Override
            public String getBodyContentType() {

                return "application/json; charset=utf-8";

            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }


        };

        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        request.add(jsObjRequest);




*//*
        // Get Profile information
        ProfileModel.getsInstance().fetchJSONObject(this, endpoint, new BaseModel.FetchCallbackInterface() {
            @Override
            public void onFetchCompleted(JSONObject response) {

                try {
                    // Set the profile picture url
                    //if(!response.getString("imageurl").toString().equals("null")) {
                        ApplicationController.getInstance().setPreferences(getApplicationContext(), "profileImage", response.getString("imageurl"));


                    if (response.has("firstname") && response.has("lastname")) {
                        ApplicationController.getInstance().setPreferences(getApplicationContext(), "fullname", response.getString("firstname") + " " + response.getString("lastname"));
                    } else if (response.has("firstname")) {
                        ApplicationController.getInstance().setPreferences(getApplicationContext(), "fullname", response.getString("firstname"));
                    } else if (response.has("lastname")) {
                        ApplicationController.getInstance().setPreferences(getApplicationContext(), "fullname", response.getString("lastname"));
                    }
//                    txtProfileFullname.setText(ApplicationController.getInstance().getPreferenceByKey("fullname"));
//
//                    imgProfilePicture.setImageUrl(response.getString("imageurl"), imageLoader);
//                    imbProfileBackgroundPicture.setImageUrl(response.getString("imageurl"), imageLoader);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFetchCompleted(JSONArray jsonArray) {
                Toast errorToastMessage = Toast.makeText(getApplicationContext(), "We could not fetch your profile:", Toast.LENGTH_LONG);
                errorToastMessage.show();
            }

            @Override
            public void onFetchError(VolleyError error, String errorMessage) {
                Toast errorToastMessage = Toast.makeText(getApplicationContext(), "We could not fetch your profile:" + error.getLocalizedMessage(), Toast.LENGTH_LONG);
                errorToastMessage.show();
            }
        });
*//*
    }*/

    /**
     * A {@link android.view.ViewOutlineProvider} which clips the view with a circle
     *
     * @author pimisi - Added for image clipping
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private class ClipOutlineProvider extends ViewOutlineProvider {

        @Override
        public void getOutline(View view, Outline outline) {
            int diameter = R.dimen.round_button_diameter;
            outline.setOval(0, 0, diameter, diameter);
        }

    }


}
