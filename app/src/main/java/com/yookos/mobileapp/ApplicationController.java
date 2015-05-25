package com.yookos.mobileapp;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by amukelanihlangwane on 11/05/15.
 */

public class ApplicationController extends Application {

    /**
     * Log or request TAG
     */
    public static final String TAG = "VolleyPatterns";

    /**
     * Shared Preferences
     */
    protected SharedPreferences preferences;

    /**
     * Global request queue for Volley
     */
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    JSONObject jresponse = null;

    /**
     * A singleton instance of the application class for easy access in other places
     */
    private static ApplicationController sInstance;

    private static Context mContext;

    @Override
    public void onCreate() {


        // initialize the singleton
        sInstance = this;
        Log.d(TAG, "the value is sinstance is " + sInstance);
        super.onCreate();
        mContext = getApplicationContext();

        mRequestQueue  = Volley.newRequestQueue(getApplicationContext());


    }

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        // This is called when the overall system is running low on memory
        // and actively running processes should trim their memory usage
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Called by the system when the device configuration changes while your
        // component is running. Unlike activities Application doesn't restart when
        // a configuration changes
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        // This method is for use in emulated process environments only.
        // You can simply forget about it because it will never be called on real device
    }

    /**
     * @return ApplicationController singleton instance
     */
    public static synchronized ApplicationController getInstance()
    {

        return sInstance;

    }

    public JSONObject fetchJSONObject(String requestUrl, JSONObject requestObject){

        mRequestQueue  = Volley.newRequestQueue(getApplicationContext());

        Toast.makeText(getApplicationContext(), "in login", Toast.LENGTH_SHORT).show();


        try {

            JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,requestUrl,requestObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("response", "" + response);
                            jresponse = response;
                            Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(getApplicationContext(),"error "+ error.getMessage(),Toast.LENGTH_LONG).show();
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

            mRequestQueue.add(jsObjRequest);



        } catch (Exception e) {
            e.getLocalizedMessage();
        }

        return jresponse;


    }

    /**
     * Method to get the shared preferences
     *
     * @return
     */
    public SharedPreferences getPreferences() {
        return preferences;
    }


    /**
     * Method to get a shared preference based on passed in key
     *
     * @param key - The key to search with
     * @return The preference value of the passed in key
     */
    public String getPreferenceByKey(String key) {
        if (preferences == null) {
            preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        }
        return preferences.getString(key, "");
    }

    /**
     * @return The Volley Request queue, the queue will be created if it is null
     */
    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (mRequestQueue == null) {

            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


    /**
     * Method to set values on the shared preferences editor
     *
     * @param context - The application context
     * @param key - The key to be set in the editor
     * @param value - The value for the key to set
     */
    public void setPreferences(Context context, String key, String value) {
        if (preferences == null) {
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);

        editor.commit();
    }


    public ImageLoader getmImageLoader() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (mImageLoader == null) {

            mImageLoader = new ImageLoader(this.mRequestQueue, new BitmapLruCache());

        }

        return mImageLoader;

    }

/**
 * Adds the specified request to the global queue, if tag is specified
 * then it is used else Default TAG is used.
 *
 * @param req
 * @param tag
 */

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

        VolleyLog.d("Adding request to queue: %s", req.getUrl());

        getRequestQueue().add(req);
    }

    /**
     * Adds the specified request to the global queue using the Default TAG.
     *
     * @param req
     */


    public <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        VolleyLog.d("Adding request to queue: %s", req.getUrl());
        req.setTag(TAG);

        getRequestQueue().add(req);
    }

    /**
     * Cancels all pending requests by the specified TAG, it is important
     * to specify a TAG so that the pending/ongoing requests can be cancelled.
     *
     * @param tag
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);


        }

    }

}