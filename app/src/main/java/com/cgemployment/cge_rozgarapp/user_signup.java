package com.cgemployment.cge_rozgarapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chaos.view.PinView;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class user_signup extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
TextView res,otp_txt_msg;
LinearLayout userlogin_ll,OTP_ll,send_otp,mpindone,Otp_submit,mpin_ll;
TextInputEditText mobile_tv,username_tv;
TextInputLayout username_layout,mobile_tv_layout;
PinView PinViewOTP;
TextView otp_res;

PinView  PinViewMpin,PinViewCMpin;
int MY_SOCKET_TIMEOUT_MS=50000;
    Dialog dialog;
    TextView signupmsg;

    CircleImageView cm_image,dcm_image;
    TextView cm_name,cmpost,dcm_name,dcm_post,dcm_dept;

    CheckBox cpatch_checkbox;



   String SITE_KEY = "6Lew3HMpAAAAAPA9IwcWmCom_b6BYtE9_Jk3QrKX";
    String SECRET_KEY = "6Lew3HMpAAAAAE21yeA_nSV18briNrIjmlEpjzoi";

    
    RequestQueue queue;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);
        userlogin_ll=findViewById(R.id.userlogin_ll);
        OTP_ll=findViewById(R.id.OTP_ll);
        send_otp=findViewById(R.id.send_otp);
        mobile_tv=findViewById(R.id.mobile_tv);
        otp_txt_msg=findViewById(R.id.otp_txt_msg);
        mpindone=findViewById(R.id.mpindone);
        Otp_submit=findViewById(R.id.Otp_submit);
        mpin_ll=findViewById(R.id.mpin_ll);
        mobile_tv_layout=findViewById(R.id.mobile_tv_layout);
        otp_res=findViewById(R.id.otp_res);
        username_layout=findViewById(R.id.username_layout);
        username_tv=findViewById(R.id.username_tv);
        mobile_tv_layout=findViewById(R.id.mobile_tv_layout);
        PinViewOTP=findViewById(R.id.PinViewOTP);
        PinViewMpin=findViewById(R.id.PinViewMpin);
        PinViewCMpin=findViewById(R.id.PinViewCMpin);
        signupmsg=findViewById(R.id.signupmsg);
        cpatch_checkbox=findViewById(R.id.cpatch_checkbox);

        cm_image=findViewById(R.id.cm_image);
        dcm_image=findViewById(R.id.dcm_image);
        cm_name=findViewById(R.id.cm_name);
        cmpost=findViewById(R.id.cmpost);
        dcm_name=findViewById(R.id.dcm_name);
        dcm_post=findViewById(R.id.dcm_post);
        dcm_dept=findViewById(R.id.dcm_dept);


        userlogin_ll.setVisibility(View.VISIBLE);
        OTP_ll.setVisibility(View.GONE);
        mpin_ll.setVisibility(View.GONE);
        send_otp.setVisibility(View.GONE);


        cpatch_checkbox.setChecked(false);


        //////////////////////////////////////////////////////////////////////
        dialog = new Dialog(user_signup.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_bar);
        dialog.setCanceledOnTouchOutside(false);

        ///////////////////////////////////////////////////////////////

        //////////////////////////////////ssl/////////////////////////////////////
        // handleSSLHandshake();
       //  AllCertificatesAndHostsTruster.apply();


        RojgarAppAssests();



        send_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (!validateUserName() ) {
                    return;
                }
              //  OTP_ll.setVisibility(View.VISIBLE);
             //   userlogin_ll.setVisibility(View.GONE);
              //  mpin_ll.setVisibility(View.GONE);

               // otp_txt_msg.setText("इस "+mobile_tv.getText().toString().trim()+" मोबाइल नंबर में भेजा गया 4 अंको का ओटीपी दर्ज करें ");

                  send_Otp();
            }
        });

        Otp_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ////////////////////for_play_store//////////////////////
                String mob=mobile_tv.getText().toString().trim();
                String otp=PinViewOTP.getText().toString().trim();
                if (!validateOtp()) {
                    return;
                }
                if(mob.equals("9876543210")&&otp.equals("9812")){
                    OTP_ll.setVisibility(View.GONE);
                    userlogin_ll.setVisibility(View.GONE);
                    mpin_ll.setVisibility(View.VISIBLE);
                }else {
                    ValidateOTP();
                    //  OTP_ll.setVisibility(View.GONE);
                    //   userlogin_ll.setVisibility(View.GONE);
                    //  mpin_ll.setVisibility(View.VISIBLE);
                }





            }
        });

        mpindone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if (!validatempin()  ) {
                    return;
                }


                //////////////////////////////////SharedPreferences/////////////////////////////////////////////////////////
                SharedPreferences sp = getSharedPreferences("empmpin", MODE_PRIVATE);
                SharedPreferences.Editor editoremp = sp.edit();
                editoremp.putString("empmpin", PinViewCMpin.getText().toString());
                editoremp.commit();
                editoremp.apply();
                startActivity(new Intent(getApplicationContext(),mpin_login.class));
                finish();

            }
        });

    }

    public void send_Otp() {
        dialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("mobileNo", mobile_tv.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObject.put("key", "ERojgargdvd");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = jsonObject.toString();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Constants_URL.send_Otp, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        String result = null;
                        try {
                            result = response.getString("statusCode");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (result.equals("201")) {

                            dialog.dismiss();

                            //////////////////////////////////SharedPreferences/////////////////////////////////////////////////////////
                            SharedPreferences sp = getSharedPreferences("credentialsuser", MODE_PRIVATE);
                            SharedPreferences.Editor editoremp = sp.edit();
                            editoremp.putString("userName", username_tv.getText().toString().trim());
                            editoremp.putString("mobile", mobile_tv.getText().toString().trim());
                            editoremp.commit();
                            editoremp.apply();


                            String MSG = null;
                            try {
                                MSG = response.getString("msg");;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            AlertDialog.Builder builder =
                                    new AlertDialog.Builder
                                            (user_signup.this);
                            View view = LayoutInflater.from(user_signup.this).inflate(
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

                                    OTP_ll.setVisibility(View.VISIBLE);
                                    userlogin_ll.setVisibility(View.GONE);
                                    mpin_ll.setVisibility(View.GONE);

                                    otp_txt_msg.setText("इस "+mobile_tv.getText().toString().trim()+" मोबाइल नंबर में भेजा गया 4 अंको का ओटीपी दर्ज करें ");
                               

                                }
                            });
                            if (alertDialog.getWindow() != null){
                                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                            }
                            alertDialog.show();


/*
                            String MSG = null;
                            try {
                                MSG = response.getString("msg");;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            AlertDialog.Builder alert = new AlertDialog.Builder(
                                    user_signup.this);
                            alert.setTitle("सन्देश !");
                            alert.setCancelable(false);
                            alert.setMessage(MSG);
                            alert.setPositiveButton("ओके", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    OTP_ll.setVisibility(View.VISIBLE);
                                    userlogin_ll.setVisibility(View.GONE);
                                    mpin_ll.setVisibility(View.GONE);

                                    otp_txt_msg.setText("इस "+mobile_tv.getText().toString().trim()+" मोबाइल नंबर में भेजा गया 4 अंको का ओटीपी दर्ज करें ");
                                    dialog.dismiss();
                                }
                            });
                            alert.show();  */

                        }

                        else {
                            dialog.dismiss();
                            mobile_tv.setText("");
                            String MSG = null;
                            try {
                                MSG = response.getString("msg");;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            AlertDialog.Builder builder =
                                    new AlertDialog.Builder
                                            (user_signup.this);
                            View view = LayoutInflater.from(user_signup.this).inflate(
                                    R.layout.layout_error_dialog,
                                     findViewById(R.id.layoutDialogContainer)
                            );
                            builder.setCancelable(false);
                            builder.setView(view);
                            ((TextView) view.findViewById(R.id.textTitle))
                                    .setText( "असफल");

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
                                    alertDialog.dismiss();

                                }
                            });
                            if (alertDialog.getWindow() != null){
                                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                            }
                            alertDialog.show();

                            /*
                            AlertDialog.Builder alert = new AlertDialog.Builder(
                                    user_signup.this);
                            alert.setTitle("सन्देश !");
                            alert.setCancelable(false);
                            alert.setMessage(MSG);
                            alert.setPositiveButton("ओके", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            alert.show(); */
                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                dialog.dismiss();


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
                params.put("Content-Type", "application/json; charset=utf-8");
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



    public void ValidateOTP() {

        dialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("mobileNo", mobile_tv.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObject.put("key", "ERojgargdvd");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObject.put("otp",PinViewOTP.getText().toString().trim() );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = jsonObject.toString();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Constants_URL.ValidateOTP, jsonObject,
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
                        /*    String MSG = null;
                            try {
                                MSG = response.getString("msg");;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(user_signup.this, MSG, Toast.LENGTH_SHORT).show(); */
                            OTP_ll.setVisibility(View.GONE);
                            userlogin_ll.setVisibility(View.GONE);
                            mpin_ll.setVisibility(View.VISIBLE);


                        } else if(result.equals("601")){
                            dialog.dismiss();
                            PinViewOTP.setText("");

                            String MSG = null;
                            try {
                                MSG = response.getString("msg");;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            AlertDialog.Builder builder =
                                    new AlertDialog.Builder
                                            (user_signup.this);
                            View view = LayoutInflater.from(user_signup.this).inflate(
                                    R.layout.layout_error_dialog,
                                    findViewById(R.id.layoutDialogContainer)
                            );
                            builder.setCancelable(false);
                            builder.setView(view);
                            ((TextView) view.findViewById(R.id.textTitle))
                                    .setText( "असफल");

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
                                    userlogin_ll.setVisibility(View.VISIBLE);
                                    cpatch_checkbox.setChecked(false);
                                    username_tv.setText("");
                                    mobile_tv.setText("");
                                    send_otp.setVisibility(View.GONE);

                                    OTP_ll.setVisibility(View.GONE);
                                    mpin_ll.setVisibility(View.GONE);


                                    alertDialog.dismiss();


                                }
                            });
                            if (alertDialog.getWindow() != null){
                                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                            }
                            alertDialog.show();

                        }
                        else {

                            dialog.dismiss();
                            PinViewOTP.setText("");

                            String MSG = null;
                            try {
                                MSG = response.getString("msg");;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            AlertDialog.Builder builder =
                                    new AlertDialog.Builder
                                            (user_signup.this);
                            View view = LayoutInflater.from(user_signup.this).inflate(
                                    R.layout.layout_error_dialog,
                                     findViewById(R.id.layoutDialogContainer)
                            );
                            builder.setCancelable(false);
                            builder.setView(view);
                            ((TextView) view.findViewById(R.id.textTitle))
                                    .setText( "असफल");

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
                                    alertDialog.dismiss();

                                }
                            });
                            if (alertDialog.getWindow() != null){
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

            /*    AlertDialog.Builder alert = new AlertDialog.Builder(
                        user_signup.this);
                alert.setTitle("सन्देश !");
                alert.setCancelable(false);
                alert.setMessage(error.toString());
                alert.setPositiveButton("ओके", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

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

    //////////////////////////////////////validation//////////////////////////
    private boolean validateUserName() {

        String val = username_layout.getEditText().getText().toString().trim();
        String val2 = mobile_tv_layout.getEditText().getText().toString().trim();
        if (val.equals("") || val.length() <2 ) {
            Vibrator();
            showErrorDialog("कृपया उपयोगकर्ता का वैध नाम दर्ज करें |");
            username_tv.setText("");
            cpatch_checkbox.setChecked(false);
            return false;
        }else   if (val.equals("") ) {
            Vibrator();
            showErrorDialog("कृपया 10 अंको का मोबाइल नंबर दर्ज करें |");
            mobile_tv.setText("");
            cpatch_checkbox.setChecked(false);
            return false;
        }

        else   if (val2.equals("")  || val2.length() != 10) {
            Vibrator();
            showErrorDialog("कृपया 10 अंको का मोबाइल नंबर दर्ज करें |");
            mobile_tv.setText("");
            cpatch_checkbox.setChecked(false);
            return false;
        }

        else {
            return true;

        }
    }

    private boolean validateOtp() {

        String val = PinViewOTP.getText().toString().trim();
        if (val.equals("") || val.length() != 4) {
            Vibrator();


            AlertDialog.Builder builder =
                    new AlertDialog.Builder
                            (user_signup.this);
            View view1 = LayoutInflater.from(user_signup.this).inflate(
                    R.layout.layout_error_dialog,
                     findViewById(R.id.layoutDialogContainer)
            );
            builder.setCancelable(false);
            builder.setView(view1);
            ((TextView) view1.findViewById(R.id.textTitle))
                    .setText( "असफल");

            ((TextView) view1.findViewById(R.id.textMessage))
                    .setText("कृपया 4 अंको का ओटीपी दर्ज करें |");
            ((Button) view1.findViewById(R.id.buttonAction))
                    .setText("बंद करें");
            ((ImageView) view1.findViewById(R.id.imageIcon))
                    .setImageResource(R.drawable.file);
            final AlertDialog alertDialog = builder.create();
            view1.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view1) {
                    PinViewOTP.setError("कृपया 4 अंको का ओटीपी दर्ज करें |");
                    PinViewOTP.setText("");
                    otp_res.setTextColor(Color.RED);
                    otp_res.setTextSize(15);
                    otp_res.setText("कृपया 4 अंको का ओटीपी दर्ज करें |");
                    alertDialog.dismiss();

                }
            });
            if (alertDialog.getWindow() != null){
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            alertDialog.show();




            return false;
        } else {
            PinViewOTP.setError(null);
            return true;

        }
    }
    private boolean validatempin() {

        String val = PinViewMpin.getText().toString().trim();
        String val2 = PinViewCMpin.getText().toString().trim();
        if (val.equals("") || val.length() != 4) {
            Vibrator();
            showErrorDialog("4 अंको का एम पिन दर्ज करें.");
             PinViewMpin.setText("");
            return false;
        }
        else  if (val2.equals("")  || val2.length() != 4) {
            showErrorDialog("4 अंको का एम पिन दर्ज करें.");
            Vibrator();
            PinViewCMpin.setText("");
            return false;
        }else if (!val.equals(val2)){
            Vibrator();
            showErrorDialog("प्रविष्ट एमपिन और अनुरूप एमपिन समान नहीं है");
            PinViewCMpin.setText("");
            return false;

        }
        else {
            return true;

        }
    }




    public void Vibrator() {
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);;
        vibe.vibrate(60);

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


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
    private void showErrorDialog(String msg) {

        AlertDialog.Builder builder =
                new AlertDialog.Builder
                        (user_signup.this);
        View view = LayoutInflater.from(user_signup.this).inflate(
                R.layout.layout_error_dialog,
                  findViewById(R.id.layoutDialogContainer)
        );
        builder.setCancelable(false);
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle))
                .setText("असफल");

        ((TextView) view.findViewById(R.id.textMessage))
                .setText(msg);
        ((Button) view.findViewById(R.id.buttonAction))
                .setText("बंद करें");
        ((ImageView) view.findViewById(R.id.imageIcon))
                .setImageResource(R.drawable.file);

        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();

            }
        });
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
    public void RojgarAppAssests() {
        dialog.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("key", "ERojgargdvd");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        final String requestBody = jsonObject.toString();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Constants_URL.RojgarAppAssests, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject mainrResult = response.getJSONObject("result");
                            String result = mainrResult.getString("statusCode");

                            if (result.equals("200")) {
                                dialog.dismiss();

                                JSONObject output = mainrResult.getJSONObject("data");
                                String  MSG = output.getString("cmphoto");

                                /////////////////////image_view////////////////////////////////
                                cm_name.setText(output.getString("cmname"));
                                cmpost.setText(output.getString("cmpostname"));


                                byte[] decodedString = Base64.decode(MSG, Base64.DEFAULT);
                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                cm_image.setImageBitmap(decodedByte);


                                dcm_name.setText(output.getString("ministername"));
                                dcm_post.setText(output.getString("ministerpostname"));
                                dcm_dept.setText(output.getString("ministerdepartment"));

                                String  MSG2 = output.getString("ministerphoto");
                                byte[] decodedString2 = Base64.decode(MSG2, Base64.DEFAULT);
                                Bitmap decodedByte2 = BitmapFactory.decodeByteArray(decodedString2, 0, decodedString2.length);
                                dcm_image.setImageBitmap(decodedByte2);

                            }else if(result.equals("404")){
                                dialog.dismiss();





                            }

                            else{

                                dialog.dismiss();


                            }


                        } catch (JSONException e) {

                            dialog.dismiss();


                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                dialog.dismiss();


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

    public void Check_checkBox(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {

            case R.id.cpatch_checkbox:
                if (checked) {

                    if (!validateUserName() ) {
                        return;
                    }
                    verifyGoogleReCAPTCHA();


                  //  send_otp.setVisibility(View.VISIBLE);

                } else {
                    send_otp.setVisibility(View.GONE);
                    username_tv.setText("");
                    mobile_tv.setText("");

                }

        }
    }

    private void verifyGoogleReCAPTCHA() {

        // below line is use for getting our safety
        // net client and verify with reCAPTCHA
        SafetyNet.getClient(this).verifyWithRecaptcha(SITE_KEY)
                // after getting our client we have
                // to add on success listener.
                .addOnSuccessListener(this, new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                    @Override
                    public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
                        // in below line we are checking the response token.
                        if (!response.getTokenResult().isEmpty()) {
                            // if the response token is not empty then we
                            // are calling our verification method.
                            handleVerification(response.getTokenResult());
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // this method is called when we get any error.
                        if (e instanceof ApiException) {
                            ApiException apiException = (ApiException) e;
                            // below line is use to display an error message which we get.
                            Log.d("TAG", "Error message: " +
                                    CommonStatusCodes.getStatusCodeString(apiException.getStatusCode()));
                        } else {
                            // below line is use to display a toast message for any error.
                            Toast.makeText(user_signup.this, "Error found is : " + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



protected void handleVerification(final String responseToken) {

      //  Toast.makeText(getApplicationContext(), responseToken, Toast.LENGTH_LONG).show();
        // inside handle verification method we are
        // verifying our user with response token.
        // url to sen our site key and secret key
        // to below url using POST method.

        StringRequest request = new StringRequest(Request.Method.POST, Constants_URL.recaptchaUrl, new com.android.volley.Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                // response is successful or not.
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("success")) {
                        // if the response is successful then we are
                        // showing below toast message.
                         send_otp.setVisibility(View.VISIBLE);
                        cpatch_checkbox.setTextColor(Color.GREEN);

                      //  Toast.makeText(user_signup.this, "User verified with reCAPTCHA", Toast.LENGTH_SHORT).show();
                    } else {
                        // if the response if failure we are displaying
                        // a below toast message.
                          send_otp.setVisibility(View.GONE);
                          cpatch_checkbox.setChecked(false);

                        Toast.makeText(getApplicationContext(), String.valueOf(jsonObject.getString("error-codes")), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {
                    // if we get any exception then we are
                    // displaying an error message in logcat.
                    cpatch_checkbox.setChecked(false);
                    Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();

                    Log.d("TAG", "JSON exception: " + ex.getMessage());
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // inside error response we are displaying
                // a log message in our logcat.
                cpatch_checkbox.setChecked(false);

                Log.d("TAG", "Error message: " + error.getMessage());

            }
        }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("secret", SECRET_KEY);
                map.put("response", responseToken);
                return map;

            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }


    ////////////////network check/////////////////////////////////
    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();


    }

}