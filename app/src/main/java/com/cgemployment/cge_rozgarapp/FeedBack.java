package com.cgemployment.cge_rozgarapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FeedBack extends AppCompatActivity {




    LinearLayout feedback_back,submit_feedback;
    TextView msg_lla;

    RadioGroup ratingOfSoftware,ratingOfUseInterface,satisfied;

    String feedback1="5";
    String feedback2="5";
    String feedback3="5";

    TextInputEditText feedback_tv;


    Dialog dialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);


        SharedPreferences sp = getSharedPreferences("credentialsuser", MODE_PRIVATE);
        String userName = sp.getString("userName", "");//"No name defined" is the default value.
        String mobileno = sp.getString("mobile", "");//"No name defined" is the default value.

        feedback_back = findViewById(R.id.feedback_back);
        submit_feedback = findViewById(R.id.submit_feedback);
        msg_lla = findViewById(R.id.msg_lla);
        ratingOfSoftware = findViewById(R.id.ratingOfSoftware);
        satisfied = findViewById(R.id.satisfied);
        feedback_tv = findViewById(R.id.feedback_tv);
        ratingOfUseInterface = findViewById(R.id.ratingOfUseInterface);
        //////////////////////////////////ssl/////////////////////////////////////
        // handleSSLHandshake();
      //  AllCertificatesAndHostsTruster.apply();

        //////////////////////////////////////////////////////////////////////
        dialog = new Dialog(FeedBack.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_bar);
        dialog.setCanceledOnTouchOutside(false);




        msg_lla.setVisibility(View.GONE);

        feedback_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), dashboard.class));
                finish();
            }
        });

        submit_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if (!validatesectiona() ) {
                    return;
                }
                get_feedback();
            }
        });


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }
    private boolean validatesectiona() {

        if (ratingOfSoftware.getCheckedRadioButtonId() == -1
                || ratingOfUseInterface.getCheckedRadioButtonId() == -1
                || satisfied.getCheckedRadioButtonId() == -1

        ) {

            Vibrator();
            AlertDialog.Builder builder =
                    new AlertDialog.Builder
                            (FeedBack.this);
            View view = LayoutInflater.from(FeedBack.this).inflate(
                    R.layout.layout_error_dialog,
                         findViewById(R.id.layoutDialogContainer)
            );
            builder.setCancelable(false);
            builder.setView(view);
            ((TextView) view.findViewById(R.id.textTitle))
                    .setText("असफल");

            ((TextView) view.findViewById(R.id.textMessage))
                    .setText("रेटिंग का चुनाव अनिवार्य हैं");
            ((Button) view.findViewById(R.id.buttonAction))
                    .setText("बंद करें");
            ((ImageView) view.findViewById(R.id.imageIcon))
                    .setImageResource(R.drawable.file);
            final AlertDialog alertDialog = builder.create();
            view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // no radio buttons are checked
                    msg_lla.setVisibility(View.VISIBLE);
                    msg_lla.setTextColor(Color.RED);
                    alertDialog.dismiss();

                }
            });
            if (alertDialog.getWindow() != null) {
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            alertDialog.show();
            return false;

        } else {
            msg_lla.setVisibility(View.GONE);
            // no radio buttons are checked
            return true;

        }

    }

    public void Vibrator() {
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);;
        vibe.vibrate(60);

    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.RA1:
                if (checked)
                  feedback1="5";
                break;
            case R.id.RA2:
                if (checked)
                    feedback1="4";
                break;
            case R.id.RA3:
                if (checked)
                    feedback1="3";
                break;
            case R.id.RA4:
                if (checked)
                    feedback1="2";
                break;
            case R.id.RA5:
                if (checked)
                    feedback1="1";
                break;

            case R.id.RB1:
                if (checked)
                    feedback2="5";
                break;

            case R.id.RB2:
                if (checked)
                    feedback2="4";
                break;

            case R.id.RB3:
                if (checked)
                    feedback2="3";
                break;

            case R.id.RB4:
                if (checked)
                    feedback2="2";
                break;
            case R.id.RB5:
                if (checked)
                    feedback2="1";
                break;

            case R.id.RC1:
                if (checked)
                    feedback3="5";
                break;
            case R.id.RC2:
                if (checked)
                    feedback3="4";
                break;
            case R.id.RC3:
                if (checked)
                    feedback3="3";
                break;
            case R.id.RC4:
                if (checked)
                    feedback3="2";
                break;
            case R.id.RC5:
                if (checked)
                    feedback3="1";
                break;

        }


    }

    public void get_feedback() {
        dialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();

        SharedPreferences sp = getSharedPreferences("credentialsuser", MODE_PRIVATE);
        String userName = sp.getString("userName", "");//"No name defined" is the default value.
        String mobileno = sp.getString("mobile", "");//"No name defined" is the default value.
        String suggestion="Nosuggestion";
        suggestion=feedback_tv.getText().toString();

        int ratingOfSoftware=Integer.parseInt(feedback1);
        int ratingOfUseInterface=Integer.parseInt(feedback2);
        int satisfied=Integer.parseInt(feedback3);



        try {
            jsonObject.put("userId", mobileno);
            jsonObject.put("ratingOfSoftware", ratingOfSoftware);
            jsonObject.put("ratingOfUseInterface", ratingOfUseInterface);
            jsonObject.put("satisfied", satisfied);
            jsonObject.put("suggestionRecommendation",suggestion) ;
            jsonObject.put("key", "ERojgargdvd");




        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = jsonObject.toString();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Constants_URL.FeedBacks, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        String result = null;
                        try {
                            result = response.getString("statusCode");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (result.equals("200")) {

                            dialog.dismiss();


                            String MSG = null;
                            try {
                                MSG = response.getString("msg");;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            AlertDialog.Builder builder =
                                    new AlertDialog.Builder
                                            (FeedBack.this);
                            View view = LayoutInflater.from(FeedBack.this).inflate(
                                    R.layout.layout_success_dialog,
                                        findViewById(R.id.layoutDialogContainer)
                            );
                            builder.setCancelable(false);
                            builder.setView(view);
                            ((TextView) view.findViewById(R.id.textTitle))
                                    .setText("सफल");


                            ((TextView) view.findViewById(R.id.textMessage))
                                    .setText(MSG);

                            ((Button) view.findViewById(R.id.buttonAction))
                                    .setText("ओके");

                            ((ImageView) view.findViewById(R.id.imageIcon))
                                    .setImageResource(R.drawable.correct);

                            final AlertDialog alertDialog = builder.create();
                            view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    alertDialog.dismiss();
                                    startActivity(new Intent(getApplicationContext(), dashboard.class));
                                    finish();


                                }
                            });
                            if (alertDialog.getWindow() != null){
                                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                            }
                            alertDialog.show();

                        }

                        else {
                            dialog.dismiss();

                            String MSG = null;
                            try {
                                MSG = response.getString("msg");
                                ;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            AlertDialog.Builder builder =
                                    new AlertDialog.Builder
                                            (FeedBack.this);
                            View view = LayoutInflater.from(FeedBack.this).inflate(
                                    R.layout.layout_error_dialog,
                                         findViewById(R.id.layoutDialogContainer)
                            );
                            builder.setCancelable(false);
                            builder.setView(view);
                            ((TextView) view.findViewById(R.id.textTitle))
                                    .setText("असफल");

                            ((TextView) view.findViewById(R.id.textMessage))
                                    .setText(MSG);
                            ((Button) view.findViewById(R.id.buttonAction))
                                    .setText("बंद करें");
                            ((ImageView) view.findViewById(R.id.imageIcon))
                                    .setImageResource(R.drawable.file);
                            final AlertDialog alertDialog = builder.create();
                            view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    feedback_tv.setText("");
                                    alertDialog.dismiss();

                                }
                            });
                            if (alertDialog.getWindow() != null) {
                                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                            }
                            alertDialog.show();
                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                dialog.dismiss();
                feedback_tv.setText("");


              /*  AlertDialog.Builder alert = new AlertDialog.Builder(
                        user_signup.this);
                alert.setTitle("सन्देश !");
                alert.setCancelable(false);
                alert.setMessage(error.toString());
                alert.setPositiveButton("ओके", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        OTP_ll.setVisibility(View.GONE);
                        userlogin_ll.setVisibility(View.VISIBLE);
                        mpin_ll.setVisibility(View.GONE);
                        dialog.dismiss();
                    }
                });
                alert.show();  */

                error.printStackTrace();
            }
        }) {    //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=utf-8");
                params.put("X-Content-Type-Options", "nosniff");
                params.put("X-XSS-Protection", "1; mode=block");
                params.put("X-Frame-Options", "deny");
                params.put("Content-Security-Policy", "default-src 'self'");
                params.put("Strict-Transport-Security", "63072000; includeSubDomains; preload");
                String credentials = Constants_URL.USERNAME+":"+Constants_URL.PASSWORD;
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(),
                        Base64.NO_WRAP);
                params.put("Authorization", auth);
                return params;
            }
        };
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonRequest);


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}