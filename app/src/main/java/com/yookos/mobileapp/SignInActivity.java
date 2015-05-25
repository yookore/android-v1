package com.yookos.mobileapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SignInActivity extends ActionBarActivity {

    EditText edpassword, edusername;

    Button btnsignin;

    public Toolbar toolbar;

    ApplicationController app;

    RequestQueue mRequestQueue;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        setToolbar();



        app = new ApplicationController();

        edusername = (EditText) findViewById(R.id.edusername);
        edpassword = (EditText) findViewById(R.id.edpassword);
        btnsignin = (Button) findViewById(R.id.btnsignin);

        prefs =  PreferenceManager.getDefaultSharedPreferences(this);

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             AttemptLogin();

            }
        });

    }

   public void setToolbar(){


       toolbar = (Toolbar) findViewById(R.id.app_bar);

       toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
       toolbar.setTitle("SignIn");
       //toolbar.inflateMenu(R.menu.menu_profile);

       toolbar.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent intent = new Intent(SignInActivity.this,PreLoginActivity.class);
               startActivity(intent);
               finish();
           }
       });

   }

    public void AttemptLogin() {

        String password = edpassword.getText().toString();
        String username = edusername.getText().toString();

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(username) == true) {

            edusername.setError(getString(R.string.username_empty));
            edusername.findFocus();

        } else if (TextUtils.isEmpty(password) == true) {
            edpassword.setError(getString(R.string.password_empty));
            edpassword.findFocus();
        } else if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            edpassword.setError(getString(R.string.error_invalid_password));
            edpassword.findFocus();
        } else {

            JSONObject loginRequestPayload = null;

            try {
                loginRequestPayload = new JSONObject();
                loginRequestPayload.put("username", username);
                loginRequestPayload.put("password", password);

            } catch (JSONException e) {


                e.printStackTrace();
            }
            // Log.d("signin",""+ ApplicationController.getInstance().fetchJSONObject("http://private-bda01-login108.apiary-mock.com/",loginRequestPayload));

            mRequestQueue = Volley.newRequestQueue(getApplicationContext());

            //Toast.makeText(getApplicationContext(), "in login", Toast.LENGTH_SHORT).show();


            try {

                JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, "http://private-bda01-login108.apiary-mock.com/", loginRequestPayload,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                Log.d("response", "" + response);
                                //jresponse = response;
                              //  Toast.makeText(getApplicationContext(), "success"+ response, Toast.LENGTH_SHORT).show();

                                try {
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString("username",edusername.getText().toString() );
                                    editor.putString("password", edpassword.getText().toString());

                                    editor.putString("firstname",response.getString("Firstname"));

                                    editor.putString("lastname", response.getString("Lastname"));
                                    editor.commit();


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                Intent intent = new Intent(SignInActivity.this, StreamActivity.class);
                                startActivity(intent);

                                finish();

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(getApplicationContext(), "error " + error.getMessage(), Toast.LENGTH_LONG).show();
                               // Log.d("responseerror", "" + error.getMessage());

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

                mRequestQueue.add(jsObjRequest);


            } catch (Exception ex) {

                ex.printStackTrace();

            }

        }

    }


    /*public void LogMeIn(String username, String password){

        RequestQueue request = Volley.newRequestQueue(getModelContext());
        String url = "http://192.168.10.144:3000/auth/login";

        JSONObject loginRequestPayload=null;

    try{
        loginRequestPayload= new JSONObject();
        loginRequestPayload.put("username", username);
        loginRequestPayload.put("password", password);

    } catch (JSONException e) {


        e.printStackTrace();
    }
        Log.d("postjson", loginRequestPayload.toString());

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,url,loginRequestPayload,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("response", "" + response);

                        Toast.makeText(getApplication(), "success", Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplication(),"error "+ error.getMessage(),Toast.LENGTH_LONG).show();
                        Log.d("responseerror", "" + error.getMessage());

                    }
                }){

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

    }*/

    /**
     * This method handles the validation of passwords
     *
     * @param password
     * @return
     */
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

}
