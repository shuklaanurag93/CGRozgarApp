package com.cgemployment.cge_rozgarapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

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

import de.hdodenhof.circleimageview.CircleImageView;


public class splash_screen extends AppCompatActivity {



    private static int SPLASH_TIMER = 2000;
    TextView txt_version;
    String _versionname;
    Dialog dialog;
    CircleImageView cm_image, dcm_image;
    TextView cm_name, cmpost, dcm_name, dcm_post, dcm_dept;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ///////////////hock//////////////////////

        txt_version = findViewById(R.id.txt_version);
        cm_image = findViewById(R.id.cm_image);
        dcm_image = findViewById(R.id.dcm_image);
        cm_name = findViewById(R.id.cm_name);
        cmpost = findViewById(R.id.cmpost);
        dcm_name = findViewById(R.id.dcm_name);
        dcm_post = findViewById(R.id.dcm_post);
        dcm_dept = findViewById(R.id.dcm_dept);


        _versionname = "ऐप वर्जन : " + BuildConfig.VERSION_NAME;
        txt_version.setText(_versionname);


        // AllCertificatesAndHostsTruster.apply();

        //////////////////////////////////////////////////////////////////////
        dialog = new Dialog(splash_screen.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_bar);
        dialog.setCanceledOnTouchOutside(false);

        RojgarAppAssests();


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
                                String MSG = output.getString("cmphoto");

                                /////////////////////image_view////////////////////////////////
                               cm_name.setText(output.getString("cmname"));
                                cmpost.setText(output.getString("cmpostname"));


                                byte[] decodedString = Base64.decode(MSG, Base64.DEFAULT);
                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                cm_image.setImageBitmap(decodedByte);


                                dcm_name.setText(output.getString("ministername"));
                                dcm_post.setText(output.getString("ministerpostname"));
                                dcm_dept.setText(output.getString("ministerdepartment"));

                               String MSG2 = output.getString("ministerphoto");

                                byte[] decodedString2 = Base64.decode(MSG2, Base64.DEFAULT);
                                Bitmap decodedByte2 = BitmapFactory.decodeByteArray(decodedString2, 0, decodedString2.length);
                                dcm_image.setImageBitmap(decodedByte2);


                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        ///////////////////////////////////// version Ok ////////////////////////////////////////////////////////////////////////

                                        SharedPreferences sp = getSharedPreferences("empmpin", MODE_PRIVATE);

                                        if (sp.contains("empmpin")) {

                                            startActivity(new Intent(getApplicationContext(), mpin_login.class));
                                            finish();

                                        } else {

                                            startActivity(new Intent(getApplicationContext(), user_signup.class));
                                            finish();

                                        }

                                    }

                                }, SPLASH_TIMER);
                            } else if (result.equals("404")) {
                                dialog.dismiss();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        ///////////////////////////////////// version Ok ////////////////////////////////////////////////////////////////////////

                                        SharedPreferences sp = getSharedPreferences("empmpin", MODE_PRIVATE);

                                        if (sp.contains("empmpin")) {

                                            startActivity(new Intent(getApplicationContext(), mpin_login.class));
                                            finish();

                                        } else {

                                            startActivity(new Intent(getApplicationContext(), user_signup.class));
                                            finish();

                                        }

                                    }

                                }, SPLASH_TIMER);


                            } else {
                                dialog.dismiss();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        ///////////////////////////////////// version Ok ////////////////////////////////////////////////////////////////////////

                                        SharedPreferences sp = getSharedPreferences("empmpin", MODE_PRIVATE);

                                        if (sp.contains("empmpin")) {

                                            startActivity(new Intent(getApplicationContext(), mpin_login.class));
                                            finish();

                                        } else {

                                            startActivity(new Intent(getApplicationContext(), user_signup.class));
                                            finish();

                                        }

                                    }

                                }, SPLASH_TIMER);
                            }


                        } catch (JSONException e) {

                            dialog.dismiss();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    ///////////////////////////////////// version Ok ////////////////////////////////////////////////////////////////////////

                                    SharedPreferences sp = getSharedPreferences("empmpin", MODE_PRIVATE);

                                    if (sp.contains("empmpin")) {

                                        startActivity(new Intent(getApplicationContext(), mpin_login.class));
                                        finish();

                                    } else {

                                        startActivity(new Intent(getApplicationContext(), user_signup.class));
                                        finish();

                                    }

                                }

                            }, SPLASH_TIMER);

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                dialog.dismiss();


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        ///////////////////////////////////// version Ok ////////////////////////////////////////////////////////////////////////

                        SharedPreferences sp = getSharedPreferences("empmpin", MODE_PRIVATE);

                        if (sp.contains("empmpin")) {

                            startActivity(new Intent(getApplicationContext(), mpin_login.class));
                            finish();

                        } else {

                            startActivity(new Intent(getApplicationContext(), user_signup.class));
                            finish();

                        }

                    }

                }, SPLASH_TIMER);


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


                String credentials = Constants_URL.USERNAME + ":" + Constants_URL.PASSWORD;
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



}