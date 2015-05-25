package com.yookos.mobileapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.pkmmte.view.CircularImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class UploadProfilePhotoActivity extends ActionBarActivity {

    CircularImageView circularImageView;
    Toolbar toolbar;

    public int SELECT_PHOTO =100;
    Button btnupload;

    File imgFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_profile_photo);

        setToolbar();

        btnupload = (Button)findViewById(R.id.btnupload);

        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UploadProfilePhotoActivity.this, RecommendFriends.class);
                startActivity(intent);
                finish();

            }
        });

        circularImageView = (CircularImageView)findViewById(R.id.profilep);
        circularImageView.setBorderColor(getResources().getColor(R.color.GrayLight));
        //circularImageView.setBorderWidth(10);
        circularImageView.setSelectorColor(getResources().getColor(R.color.BlueLightTransparent));
        circularImageView.setSelectorStrokeColor(getResources().getColor(R.color.BlueDark));
       // circularImageView.setSelectorStrokeWidth(10);
        circularImageView.addShadow();

        circularImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");

                startActivityForResult(Intent.createChooser(photoPickerIntent, "Select Profile Photo"), SELECT_PHOTO);


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case 100:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();

                    imgFile = new File(getPath(selectedImage));

                    InputStream imageStream = null;
                    try {
                        imageStream = getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);

                    circularImageView.setImageBitmap(yourSelectedImage);
                   // new UploadFileToServer().execute();
                }
        }
    }


//    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
//        @Override
//        protected void onPreExecute() {
//            // setting progress bar to zero
//            //progressBar.setProgress(0);
//            super.onPreExecute();
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... progress) {
//            // Making progress bar visible
//            //progressBar.setVisibility(View.VISIBLE);
//
//            // updating progress bar value
//            // progressBar.setProgress(progress[0]);
//
//            // updating percentage value
//            // txtPercentage.setText(String.valueOf(progress[0]) + "%");
//        }
//
//        @Override
//        protected String doInBackground(Void... params) {
//            return uploadFile();
//        }
//
//        @SuppressWarnings("deprecation")
//        private String uploadFile() {
//            String responseString = null;
//
//            HttpClient httpclient = new DefaultHttpClient();
//            //Log.d("url", "http://192.168.10.125:8080/audio");
//            HttpPost httppost = new HttpPost("http://41.160.30.173:3002/auth/profile/uploadphoto?username="+ApplicationController.getInstance().getPreferenceByKey("username",""));
//
////            httppost.setHeader("Accept", "application/json");
//            //httppost.setHeader("Content-Type", "multipart/form-data");
//
//            try {
//                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
//                        new AndroidMultiPartEntity.ProgressListener() {
//
//                            @Override
//                            public void transferred(long num) {
//                                // publishProgress((int) ((num / (float) totalSize) * 100));
//                            }
//                        });
//
//                //File sourceFile = new File(imgpath);
//
//                // Adding file data to http body
//                entity.addPart("filedata", new FileBody(imgFile));
//
//
//
//                //totalSize = entity.getContentLength();
//                httppost.setEntity(entity);
//
//                // Making server call
//                HttpResponse response = httpclient.execute(httppost);
//                HttpEntity r_entity = response.getEntity();
//
//                int statusCode = response.getStatusLine().getStatusCode();
//                if (statusCode < 299) {
//                    // Server response
//                    responseString = EntityUtils.toString(r_entity);
//                    Log.d("ProfileResponse", responseString);
//                    //Toast.makeText(getApplicationContext(), ""+responseString, Toast.LENGTH_SHORT).show();
//                    try {
//                        JSONObject data = new JSONObject(responseString);
//
//                        originalFilename = data.getString("fileUrl");
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//
//                    //finish();
//                } else {
//                    responseString = "Error occurred! Http Status Code: "
//                            + statusCode;
//                }
//
//            } catch (ClientProtocolException e) {
//                responseString = e.toString();
//            } catch (IOException e) {
//                responseString = e.toString();
//            }
//
//            return responseString;
//
//        }
//
//
//
//
//        @Override
//        protected void onPostExecute(String result) {
//            Log.e("Server Response", "Response from server: " + result);
//
//            // showing the server response in an alert dialog
//            //showAlert(result);
//            //finish();
//
//            SetProfileUpload();
//
//            super.onPostExecute(result);
//        }
//
//    }



    public String getPath(Uri uri) {

        if( uri == null ) {
            return null;
        }

        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }

        return uri.getPath();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_upload_profile_photo, menu);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_skip) {

            Intent intent = new Intent(UploadProfilePhotoActivity.this, RecommendFriends.class);
            startActivity(intent);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
