package com.cgemployment.cge_rozgarapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.material.navigation.NavigationView;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class dashboard extends AppCompatActivity {

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuIcon;
    LinearLayout btn_registration;
    LinearLayout job_renewal, btn_rojgar_alert;
    TextView txtname, txtmobile;
    LinearLayout log_out_sv_db, feedback;
    CardView registration_card, renewal_card;
    Dialog dialog;
    TextView lost_login_tv;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menuIcon = findViewById(R.id.menu_icon);
        btn_registration = findViewById(R.id.btn_registration);
        btn_rojgar_alert = findViewById(R.id.btn_rojgar_alert);
        job_renewal = findViewById(R.id.job_renewal);
        txtname = findViewById(R.id.txtname);
        txtmobile = findViewById(R.id.txtmobile);
        log_out_sv_db = findViewById(R.id.log_out_sv_db);
        feedback = findViewById(R.id.feedback);
        registration_card = findViewById(R.id.registration_card);
        renewal_card = findViewById(R.id.renewal_card);
        lost_login_tv = findViewById(R.id.lost_login_tv);


      // AllCertificatesAndHostsTruster.apply();



        //////////////////////////////////////////////////////////////////////
        dialog = new Dialog(dashboard.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_bar);
        dialog.setCanceledOnTouchOutside(false);
        ///////////////////////////////////////////////////////////////

        SharedPreferences sp = getSharedPreferences("credentialsuser", MODE_PRIVATE);
        String userName = sp.getString("userName", "wellcome");//"No name defined" is the default value.
        String mobileno = sp.getString("mobile", "9098988002");//"No name defined" is the default value.


        txtname.setText(userName);
        txtmobile.setText(mobileno);

        registration_card.setVisibility(View.GONE);
        renewal_card.setVisibility(View.GONE);

        ErojgarAppTileActiveAndDeactives();
        GetLastLoginTime();

        btn_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), registration_form.class));
                finish();
            }
        });
        job_renewal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             /*   AlertDialog.Builder builder =
                        new AlertDialog.Builder
                                (dashboard.this);
                View view1 = LayoutInflater.from(dashboard.this).inflate(
                        R.layout.layout_success_dialog,
                        findViewById(R.id.layoutDialogContainer)
                );
                builder.setCancelable(false);
                builder.setView(view1);
                ((TextView) view1.findViewById(R.id.textTitle))
                        .setText("सफल");


                ((TextView) view1.findViewById(R.id.textMessage))
                        .setText("जल्द आ रहा है....");

                ((Button) view1.findViewById(R.id.buttonAction))
                        .setText("ओके");

                ((ImageView) view1.findViewById(R.id.imageIcon))
                        .setImageResource(R.drawable.correct);

                final AlertDialog alertDialog = builder.create();
                view1.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();

                    }
                });
                if (alertDialog.getWindow() != null) {
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                alertDialog.show(); */


                startActivity(new Intent(getApplicationContext(), job_renewal_form.class));
                finish();
            }
        });

        btn_rojgar_alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), btn_rojgar_alert_form.class));
                finish();
            }
        });


        log_out_sv_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), mpin_login.class));
                finish();
            }
        });

        navigationDrawer();

        navigationDrawer();
        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
        SliderView sliderView = findViewById(R.id.slider);
        sliderDataArrayList.add(new SliderData(Constants_URL.SliderUrl1));
        sliderDataArrayList.add(new SliderData(Constants_URL.SliderUrl2));
        sliderDataArrayList.add(new SliderData(Constants_URL.SliderUrl3));
        sliderDataArrayList.add(new SliderData(Constants_URL.SliderUrl4));

        SliderAdapter adapter = new SliderAdapter(this, sliderDataArrayList);
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderView.setSliderAdapter(adapter);
        sliderView.setScrollTimeInSec(6);

        // to set it scrollable automatically
        // we use below method.
        sliderView.setAutoCycle(true);

        // to start autocycle below method is used.
        sliderView.startAutoCycle();

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), FeedBack.class));
                finish();
            }
        });

    }

    //////////////////////////navigation Drawer////////////////////////////////////
    private void navigationDrawer() {

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        navigationView.setCheckedItem(R.id.nav_home);
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }


    private boolean onNavigationItemSelected(MenuItem menuItem) {

        //   SharedPreferences sp2 = getSharedPreferences("VHSNDID", MODE_PRIVATE);
        //  String _vhsndId = sp2.getString("_Vhsndid", "999");

        int id = menuItem.getItemId();
        switch (id) {
            case R.id.nav_home:
                Intent home = new Intent(getApplicationContext(), dashboard.class);
                startActivity(home);
                break;
            case R.id.nav_logout:
                Intent logout = new Intent(getApplicationContext(), mpin_login.class);
                startActivity(logout);
                break;
            case R.id.profile:
                Intent profile = new Intent(getApplicationContext(), profile.class);
                startActivity(profile);
                break;
            case R.id.nav_pp:
                String priURL = Constants_URL.PrivacyPolicy;

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(priURL));
                startActivity(intent);
                break;
            case R.id.gotoweb:

                String portlURL = "https://erojgar.cg.gov.in/";

                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(portlURL));
                startActivity(intent1);

                break;


            case R.id.xten:
              /*  SharedPreferences sp = getSharedPreferences("credentialsuser", MODE_PRIVATE);
                String userName = sp.getString("userName", "");//"No name defined" is the default value.
                String mobileno = sp.getString("mobile", "");//"No name defined" is the default value.
                    String url=Constants_URL.FrmUserCardForApp+mobileno;
                String xtenURL=url;

                Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(xtenURL));
                startActivity(urlIntent); */
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                break;


        }


        return true;
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


    public void ErojgarAppTileActiveAndDeactives() {
        dialog.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("key", "ERojgargdvd");


        } catch (JSONException e) {
            e.printStackTrace();
        }


        final String requestBody = jsonObject.toString();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Constants_URL.ErojgarAppTileActiveAndDeactives, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String result = response.getString("statusCode");

                            if (result.equals("200")) {
                                dialog.dismiss();
                                JSONObject output = response.getJSONObject("data");
                                String _registrationTileFlage = output.getString("registrationTileFlage");
                                String _renewalTileFlag = output.getString("renewalTileFlag");

                                //  Toast.makeText(dashboard.this, _renewalTileFlag, Toast.LENGTH_SHORT).show();

                                if (_registrationTileFlage.equals("true")) {
                                    registration_card.setVisibility(View.VISIBLE);

                                } else {
                                    registration_card.setVisibility(View.GONE);
                                }
                                if (_renewalTileFlag.equals("true")) {
                                    renewal_card.setVisibility(View.VISIBLE);
                                } else {
                                    renewal_card.setVisibility(View.GONE);

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
                                AlertDialog.Builder alert = new AlertDialog.Builder(
                                        dashboard.this);
                                alert.setTitle("सन्देश !");
                                alert.setCancelable(false);
                                alert.setMessage(MSG);
                                alert.setPositiveButton("ओके", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.dismiss();
                                    }
                                });
                                alert.show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            dialog.dismiss();
                            AlertDialog.Builder alert = new AlertDialog.Builder(
                                    dashboard.this);
                            alert.setTitle("सन्देश !");
                            alert.setCancelable(false);
                            alert.setMessage(e.toString());
                            alert.setPositiveButton("ओके", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            });
                            alert.show();

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                dialog.dismiss();

                AlertDialog.Builder alert = new AlertDialog.Builder(
                        dashboard.this);
                alert.setTitle("सन्देश !");
                alert.setCancelable(false);
                alert.setMessage(error.toString());
                alert.setPositiveButton("ओके", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
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

    public void GetLastLoginTime() {
        dialog.show();

        SharedPreferences sp = getSharedPreferences("credentialsuser", MODE_PRIVATE);
        String userName = sp.getString("userName", "");//"No name defined" is the default value.
        String mobileno = sp.getString("mobile", "");//"No name defined" is the default value.

        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("userName", mobileno);
            jsonObject.put("key", "ERojgargdvd");


        } catch (JSONException e) {
            e.printStackTrace();
        }


        final String requestBody = jsonObject.toString();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Constants_URL.GetLastLoginTime, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String result = response.getString("statusCode");

                            if (result.equals("200")) {
                                dialog.dismiss();

                                JSONObject output = response.getJSONObject("data");
                                String _lastLoginTime = output.getString("lastLoginTime");


                                lost_login_tv.setText("Last Login : " + _lastLoginTime);


                            } else {

                                dialog.dismiss();

                                String MSG = null;
                                try {
                                    MSG = response.getString("msg");
                                    ;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                AlertDialog.Builder alert = new AlertDialog.Builder(
                                        dashboard.this);
                                alert.setTitle("सन्देश !");
                                alert.setCancelable(false);
                                alert.setMessage(MSG);
                                alert.setPositiveButton("ओके", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.dismiss();
                                    }
                                });
                                alert.show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            dialog.dismiss();
                            AlertDialog.Builder alert = new AlertDialog.Builder(
                                    dashboard.this);
                            alert.setTitle("सन्देश !");
                            alert.setCancelable(false);
                            alert.setMessage(e.toString());
                            alert.setPositiveButton("ओके", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            });
                            alert.show();

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                dialog.dismiss();

                AlertDialog.Builder alert = new AlertDialog.Builder(
                        dashboard.this);
                alert.setTitle("सन्देश !");
                alert.setCancelable(false);
                alert.setMessage(error.toString());
                alert.setPositiveButton("ओके", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
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
    ////////////////network check/////////////////////////////////
    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();


    }
}