package com.cgemployment.cge_rozgarapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class application_edit extends AppCompatActivity {
    Dialog dialog;
    TextInputEditText username_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_edit);

        username_tv=findViewById(R.id.username_tv);

        //////////////////////////////////////////////////////////////////////
        dialog = new Dialog(application_edit.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_bar);
        dialog.setCanceledOnTouchOutside(false);
        //////////////////////get_data_for_edit????????????????????
        get_data();
    }

    public void get_data() {
        dialog.show();

        SharedPreferences sp = getSharedPreferences("credentialsuser", MODE_PRIVATE);
        String userName = sp.getString("userName", "");//"No name defined" is the default value.
        String mobileno = sp.getString("mobile", "");//"No name defined" is the default value.

        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("key", "ERojgargdvd");
            jsonObject.put("mobileNo", mobileno);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = jsonObject.toString();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Constants_URL.GetCandidateDetailsForApplicationEdit, jsonObject,
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
                            try {
                                JSONObject mainrResult = response.getJSONObject("data");
                                String  _name = mainrResult.getString("name").trim();
                                username_tv.setText(_name);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        } else {
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
                                            (application_edit.this);
                            View view = LayoutInflater.from(application_edit.this).inflate(
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
                                    alertDialog.dismiss();

                                    startActivity(new Intent(getApplicationContext(), dashboard.class));
                                    finish();

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



                AlertDialog.Builder alert = new AlertDialog.Builder(
                        application_edit.this);
                alert.setTitle("सन्देश !");
                alert.setCancelable(false);
                alert.setMessage(error.toString());
                alert.setPositiveButton("ओके", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();

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
}