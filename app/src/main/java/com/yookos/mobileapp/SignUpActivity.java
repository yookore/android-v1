package com.yookos.mobileapp;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


public class SignUpActivity extends ActionBarActivity {

    //DECLARING VARIABLES

    Spinner spcountry, spdob, sptitle;
    TextView txtdob;

    String dob;

    private DatePickerDialog datepicker;
    private SimpleDateFormat dateFormatter;
    private int year;
    private int month;
    private int day;

    static final int DATE_DIALOG_ID = 999;

    EditText edfirstname, edsurname, edemail, edcellphonenumber, edpassword, edconfirmpassword, edusername;

    RadioButton rbmale, rbfemale;

    Button btnsignin, btnfacebook;

    SharedPreferences prefs;
    public Toolbar toolbar;
    String possibleEmail;

    public static String STRING_PAYLOAD = "payloadkey";
    public static String EXTRAS_PAYLOAD_KEY ="key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

//        ActionBar actionBar = getActionBar();
//        actionBar.setTitle("Sign Up");


        toolbar = (Toolbar) findViewById(R.id.app_bar);

        //getactivity();
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setLogo(R.drawable.ic_launcher);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setTitle("Sign Up");
        //toolbar.inflateMenu(R.menu.menu_profile);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignUpActivity.this,PreLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(getApplicationContext()).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
               possibleEmail = account.name;


            }
        }

        TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        String number = tm.getLine1Number();

        spcountry = (Spinner) findViewById(R.id.spcountry);
        spdob = (Spinner) findViewById(R.id.spdob);
        sptitle = (Spinner) findViewById(R.id.sptitle);
        edfirstname = (EditText) findViewById(R.id.edfirstname);
        edsurname = (EditText) findViewById(R.id.edlastname);
        edemail = (EditText) findViewById(R.id.edemailaddress);
        edcellphonenumber = (EditText) findViewById(R.id.edcellphonenumber);
        edpassword = (EditText) findViewById(R.id.edpassword);
        edconfirmpassword = (EditText) findViewById(R.id.edconfirmpassword);
        edusername = (EditText) findViewById(R.id.edusername);

        rbmale = (RadioButton) findViewById(R.id.rbmale);
        rbfemale = (RadioButton) findViewById(R.id.rbfemale);

        btnsignin = (Button) findViewById(R.id.btnsignup);
        btnfacebook = (Button) findViewById(R.id.btnsigninfacebook);

        prefs =  PreferenceManager.getDefaultSharedPreferences(this);


        edemail.setText(possibleEmail);

        edcellphonenumber.setText(number);


        ArrayAdapter titleadapter = ArrayAdapter.createFromResource(this, R.array.title_arrays, R.layout.spinner_item);
        sptitle.setAdapter(titleadapter);

        ArrayAdapter dobadapter = ArrayAdapter.createFromResource(this, R.array.country_array, R.layout.spinner_item);
        spdob.setAdapter(dobadapter);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.country_array, R.layout.spinner_item);
        spcountry.setAdapter(adapter);

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // AttemptRegister();

                Intent intent = new Intent(SignUpActivity.this, VerificationActivity.class);
                //intent.putExtra(EXTRAS_PAYLOAD_KEY,STRING_PAYLOAD);
                startActivity(intent);
            }
        });


        txtdob = (TextView) findViewById(R.id.txtdob);
        txtdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(DATE_DIALOG_ID);

            }
        });

    }

    public void AttemptRegister() {

        String firstname = edfirstname.getText().toString();
        String lastname = edsurname.getText().toString();
        String email = edemail.getText().toString();
        String cnumber = edcellphonenumber.getText().toString();
        String password = edpassword.getText().toString();
        String cpassword = edconfirmpassword.getText().toString();

    /*    if (sptitle.getSelectedItemPosition() == 0){

            Toast.makeText(getApplicationContext(), "Please select title", Toast.LENGTH_SHORT).show();

        }else
        */
        if(TextUtils.isEmpty(firstname) == true){

                edfirstname.setError(getString(R.string.firstname_empty));
                edfirstname.findFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edemail.getWindowToken(), 0);



            }else if(TextUtils.isEmpty(lastname) == true){

                edsurname.setError(getString(R.string.lastname_empty));
                edsurname.findFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edemail.getWindowToken(), 0);



            }else if(TextUtils.isEmpty(email) == true || isEmailValid(email) == false){


                edemail.setError(getString(R.string.invalidemail_empty));
                edemail.findFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edemail.getWindowToken(), 0);


            }else if(TextUtils.isEmpty(cnumber) == true){

                edcellphonenumber.setError(getString(R.string.number_empty));
                edcellphonenumber.findFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edemail.getWindowToken(), 0);




            }else if(TextUtils.isEmpty(password) == true ){

                edpassword.setError(getString(R.string.password_empty));
                edpassword.findFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edemail.getWindowToken(), 0);




            }else if(isPasswordValid(password) == false) {
                edpassword.setError("This password is too short");
                edpassword.findFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edemail.getWindowToken(), 0);



            }else if(TextUtils.isEmpty(cpassword) == true){

                edconfirmpassword.setError(getString(R.string.confirmp_empty));
                edconfirmpassword.findFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edemail.getWindowToken(), 0);



            }
            else if(!password.equals(cpassword)){

                edconfirmpassword.setError(getString(R.string.mismatchpassword_empty));
                edconfirmpassword.findFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edemail.getWindowToken(), 0);


            }else if(spcountry.getSelectedItemPosition() == 0){

                Toast.makeText(getApplicationContext(), "Select Country", Toast.LENGTH_SHORT).show();

            }else if(dob.equals("")){

                Toast.makeText(getApplicationContext(), "Select Date of Birth", Toast.LENGTH_SHORT).show();

            }else{

                Register();

            }


    }

    public void Register(){

        //AccessControl.getsInstance().setLoginContext(getApplication());


        RequestQueue request = Volley.newRequestQueue(getApplicationContext());
        String url = "http://private-6d36b-createnewuser.apiary-mock.com/";

        JSONObject loginRequestPayload=null;

        try{

            loginRequestPayload= new JSONObject();
            loginRequestPayload.put("title",sptitle.getSelectedItem().toString() );

            if(rbfemale.isChecked() == false) {
                loginRequestPayload.put("gender","Male");
            }else{
                loginRequestPayload.put("gender", "Female");
            }

            loginRequestPayload.put("password", edpassword.getText().toString());
            loginRequestPayload.put("firstname", edfirstname.getText().toString());
            loginRequestPayload.put("lastname", edsurname.getText().toString());
            loginRequestPayload.put("cellphone", edcellphonenumber.getText().toString());
            loginRequestPayload.put("email", edemail.getText().toString());
            loginRequestPayload.put("country", spcountry.getSelectedItem().toString());

        } catch (JSONException e) {


            e.printStackTrace();
        }
        Log.d("postjson", loginRequestPayload.toString());

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,url,loginRequestPayload,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("response", "" + response);

                        //Toast.makeText(getApplication(),"success",Toast.LENGTH_SHORT).show();
                        try {
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("email",edusername.getText().toString() );
                        editor.putString("password", edpassword.getText().toString());

                        editor.putString("firstname",response.getString("firstname"));

                        editor.putString("lastname", edsurname.getText().toString());
                        editor.commit();

                        Intent intent = new Intent(SignUpActivity.this, VerificationActivity.class);
                        intent.putExtra("email",response.getString("email"));
                        intent.putExtra("cellnumber",response.getString("cellnumber"));
                        startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplication(), "error " + error.getMessage(), Toast.LENGTH_LONG).show();
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


    }


    private boolean isEmailValid(String email) {

        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {

        return password.length() > 4;
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        year, month,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into textview
            txtdob.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));
            dob = new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" ").toString();


        }
    };



}
