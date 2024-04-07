package com.cgemployment.cge_rozgarapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class btn_rojgar_alert_form extends AppCompatActivity implements btn_rojgar_alert_formListener  {

    LinearLayout back_alert;
    RecyclerView recyclerView;
    myadapter myadapter;
    LinearLayout Refresh_btn;
    Dialog dialog;

    List<StateMasterModelClass> StateMasterModelClassList = new ArrayList<>();
    List<AlertStatusModel> AlertStatusModelList = new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn_rojgar_alert_form);

        back_alert=findViewById(R.id.back_alert);
        recyclerView = findViewById(R.id.recview);
        Refresh_btn = findViewById(R.id.Refresh_btn);





        //////////////////////////////////ssl/////////////////////////////////////
        // handleSSLHandshake();
       // AllCertificatesAndHostsTruster.apply();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //////////////////////////////////////////////////////////////////////
        dialog = new Dialog(btn_rojgar_alert_form.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_bar);
        dialog.setCanceledOnTouchOutside(false);

        ///////////////////////////////////////////////////////////////
///////////////////////////////get_alert////////////////////
        get_alert();
        back_alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),dashboard.class));
                finish();
            }
        });

        Refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_alert();
            }
        });
    }


    public void get_alert() {
        dialog.show();
        AlertStatusModelList.clear();

        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("key", "ERojgargdvd");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = jsonObject.toString();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Constants_URL.GetVaccancyList, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            String  result = response.getString("statusCode");

                            if (result.equals("404")) {
                                dialog.dismiss();
                                String MSG = null;
                                try {
                                    MSG = response.getString("msg");;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                AlertDialog.Builder builder =
                                        new AlertDialog.Builder
                                                (btn_rojgar_alert_form.this);
                                View view = LayoutInflater.from(btn_rojgar_alert_form.this).inflate(
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


                            }else if(result.equals("200")){

                                dialog.dismiss();
                                JSONArray result_data = response.getJSONArray("listData");
                                for (int i = 0; i < result_data.length(); i++) {
                                    JSONObject output = result_data.getJSONObject(i);
                                    AlertStatusModel
                                            alertStatusModel = new AlertStatusModel(
                                            output.getString("departmentName"),/////web_service/////////////////
                                            output.getString("jobDescription"),
                                            output.getString("websiteUrl")
                                    );

                                    AlertStatusModelList.add(alertStatusModel);
                                }

                                myadapter = new myadapter(AlertStatusModelList, btn_rojgar_alert_form.this);
                                recyclerView.setAdapter(myadapter);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                            dialog.dismiss();

                            AlertDialog.Builder builder =
                                    new AlertDialog.Builder
                                            (btn_rojgar_alert_form.this);
                            View view = LayoutInflater.from(btn_rojgar_alert_form.this).inflate(
                                    R.layout.layout_error_dialog,
                                       findViewById(R.id.layoutDialogContainer)
                            );
                            builder.setCancelable(false);
                            builder.setView(view);
                            ((TextView) view.findViewById(R.id.textTitle))
                                    .setText( "असफल");

                            ((TextView) view.findViewById(R.id.textMessage))
                                    .setText(e.toString());
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

                AlertDialog.Builder builder =
                        new AlertDialog.Builder
                                (btn_rojgar_alert_form.this);
                View view = LayoutInflater.from(btn_rojgar_alert_form.this).inflate(
                        R.layout.layout_error_dialog,
                           findViewById(R.id.layoutDialogContainer)
                );
                builder.setCancelable(false);
                builder.setView(view);
                ((TextView) view.findViewById(R.id.textTitle))
                        .setText( "असफल");

                ((TextView) view.findViewById(R.id.textMessage))
                        .setText(error.toString());
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
        queue.add(jsonRequest);
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


    }

    @Override
    public void sentData(int position, String URL) {

        AlertDialog.Builder builder =
                new AlertDialog.Builder
                        (btn_rojgar_alert_form.this);
        View view = LayoutInflater.from(btn_rojgar_alert_form.this).inflate(
                R.layout.layout_warning_dialog,
                   findViewById(R.id.layoutDialogContainer)
        );
        builder.setCancelable(false);
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle))
                .setText("चेतावनी");
        ((TextView) view.findViewById(R.id.textMessage))
                .setText("क्या आप विभाग के वेबसाइट में जाना चाहते हैं?");
        ((Button) view.findViewById(R.id.buttonYes))
                .setText("हाँ");
        ((Button) view.findViewById(R.id.buttonNo))
                .setText("नहीं");
        ((ImageView) view.findViewById(R.id.imageIcon))
                .setImageResource(R.drawable.warning);
        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
                startActivity(intent);
            }
        });
        view.findViewById(R.id.buttonNo).setOnClickListener(new View.OnClickListener() {
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
    ////////////////////////on_back_press/////////////////////////////
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }
}