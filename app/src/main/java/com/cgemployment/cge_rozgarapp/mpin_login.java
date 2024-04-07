package com.cgemployment.cge_rozgarapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

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
import android.hardware.biometrics.BiometricManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import de.hdodenhof.circleimageview.CircleImageView;

public class mpin_login extends AppCompatActivity {

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    LinearLayout login_btn;
    ImageView fingerprint;
    TextView msg_text, msgtxt, fingertxtmsg, employeename;

    PinView loginPinView;
    LinearLayout over_all_log_out;
    CircleImageView cm_image, dcm_image;
    TextView cm_name, cmpost, dcm_name, dcm_post, dcm_dept;
    Dialog dialog;
    CheckBox cpatch_checkbox;


    String SITE_KEY = "6Lew3HMpAAAAAPA9IwcWmCom_b6BYtE9_Jk3QrKX";
    String SECRET_KEY = "6Lew3HMpAAAAAE21yeA_nSV18briNrIjmlEpjzoi";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpin_login);

        login_btn = findViewById(R.id.login_btn);
        fingerprint = findViewById(R.id.finger_print);
        msg_text = findViewById(R.id.msg_text);
        msgtxt = findViewById(R.id.msgtxt);
        fingertxtmsg = findViewById(R.id.fingertxtmsg);
        employeename = findViewById(R.id.employeename);
        loginPinView = findViewById(R.id.loginPinView);
        over_all_log_out = findViewById(R.id.over_all_log_out);

        cm_image = findViewById(R.id.cm_image);
        dcm_image = findViewById(R.id.dcm_image);
        cm_name = findViewById(R.id.cm_name);
        cmpost = findViewById(R.id.cmpost);
        dcm_name = findViewById(R.id.dcm_name);
        dcm_post = findViewById(R.id.dcm_post);
        dcm_dept = findViewById(R.id.dcm_dept);

        cpatch_checkbox = findViewById(R.id.cpatch_checkbox);

      //   AllCertificatesAndHostsTruster.apply();

        //////////////////////////////////////////////////////////////////////
        dialog = new Dialog(mpin_login.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_bar);
        dialog.setCanceledOnTouchOutside(false);

        login_btn.setVisibility(View.GONE);

        RojgarAppAssests();

        SharedPreferences sp = getSharedPreferences("credentialsuser", MODE_PRIVATE);
        String userName = sp.getString("userName", "wellcome");//"No name defined" is the default value.
        String mobileno = sp.getString("mobile", "9098988002");//"No name defined" is the default value.
        employeename.setText(userName);


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validatempinLogin()) {
                    return;
                }

                SharedPreferences sp = getSharedPreferences("empmpin", MODE_PRIVATE);
                String Mpin = sp.getString("empmpin", "");//"No name defined" is the default value.


                if (Mpin.equals(loginPinView.getText().toString().trim())) {
///////////////////////////////////log////////////////////////////////////////////
                    Apploginlog();



                } else {


                    AlertDialog.Builder builder =
                            new AlertDialog.Builder
                                    (mpin_login.this);
                    View view1 = LayoutInflater.from(mpin_login.this).inflate(
                            R.layout.layout_error_dialog,
                            findViewById(R.id.layoutDialogContainer)
                    );
                    builder.setCancelable(false);
                    builder.setView(view1);
                    ((TextView) view1.findViewById(R.id.textTitle))
                            .setText("असफल");

                    ((TextView) view1.findViewById(R.id.textMessage))
                            .setText("दर्ज एम पिन गलत है,कृपया सही एम पिन दर्ज करें");
                    ((Button) view1.findViewById(R.id.buttonAction))
                            .setText("बंद करें");
                    ((ImageView) view1.findViewById(R.id.imageIcon))
                            .setImageResource(R.drawable.file);
                    final AlertDialog alertDialog = builder.create();
                    view1.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view1) {
                            loginPinView.setText("");
                            cpatch_checkbox.setChecked(false);
                            login_btn.setVisibility(View.GONE);
                            alertDialog.dismiss();

                        }
                    });
                    if (alertDialog.getWindow() != null) {
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    }
                    alertDialog.show();

                }

            }
        });

        over_all_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSharedPreferences("empmpin", 0).edit().clear().commit();
                startActivity(new Intent(getApplicationContext(), user_signup.class));
                finish();

            }
        });


        ////////////////////////////biometrics/////////////////////////////////
        androidx.biometric.BiometricManager biometricManager = androidx.biometric.BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {

            case BiometricManager.BIOMETRIC_SUCCESS:
                msg_text.setText("आप लॉगिन करने के लिए फ़िंगरप्रिंट व एम पिन दोनों में किसी एक का उपयोग कर सकते हैं");
                msg_text.setTextColor(Color.parseColor("#ffffffff"));

                break;


            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:

                msg_text.setText("डिवाइस में फिंगरप्रिंट सेंसर नहीं है! एम पिन से लॉगिन करें ");
                msg_text.setTextColor(Color.parseColor("#ffffffff"));
                fingerprint.setVisibility(View.GONE);
                fingertxtmsg.setVisibility(View.GONE);
                break;

            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:

                msg_text.setText("फिंगरप्रिंट सेंसर काम नहीं कर रहा है एम पिन से लॉगिन करें");
                msg_text.setTextColor(Color.parseColor("#ffffffff"));

                fingerprint.setVisibility(View.GONE);
                fingertxtmsg.setVisibility(View.GONE);


                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:

                msg_text.setText("आपके डिवाइस में कोई फ़िंगरप्रिंट सहेजा नहीं गया है! कृपया अपनी सुरक्षा सेटिंग्स में फ़िंगरप्रिंट जोड़ें या mPIN से लॉगिन करें! ");
                msg_text.setTextColor(Color.parseColor("#ffffffff"));
                fingerprint.setVisibility(View.GONE);
                fingertxtmsg.setVisibility(View.GONE);

                break;
        }


        Executor executor = ContextCompat.getMainExecutor(this);

        BiometricPrompt biometricPrompt = new BiometricPrompt(mpin_login.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);

                Toast.makeText(getApplicationContext(),
                        "authentication error", Toast.LENGTH_LONG).show();
            }

            ///////////////////////////////////////authentication succeeded/////////////////////////////
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                //////////////////////////////log/////////////////////////////////////////////
                Apploginlog();

              //  startActivity(new Intent(getApplicationContext(), dashboard.class));

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "बायोमेट्रिक प्रमाणीकरण विफल रहा", Toast.LENGTH_LONG).show();
            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("लॉग इन")
                .setDescription("लॉग इन करने के लिए फिंगर प्रिंट दर्ज करें")
                .setNegativeButtonText("रद्द करें")
                .build();
        ////////////////////////////finger print////////////////////////////
        fingerprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biometricPrompt.authenticate(promptInfo);

            }
        });
    }

    private boolean validatempinLogin() {

        String val = loginPinView.getText().toString().trim();
        if (val.equals("") || val.length() != 4) {

            AlertDialog.Builder builder =
                    new AlertDialog.Builder
                            (mpin_login.this);
            View view1 = LayoutInflater.from(mpin_login.this).inflate(
                    R.layout.layout_error_dialog,
                    findViewById(R.id.layoutDialogContainer)
            );
            builder.setCancelable(false);
            builder.setView(view1);
            ((TextView) view1.findViewById(R.id.textTitle))
                    .setText("असफल");

            ((TextView) view1.findViewById(R.id.textMessage))
                    .setText("4 अंको का एम पिन दर्ज करें");
            ((Button) view1.findViewById(R.id.buttonAction))
                    .setText("बंद करें");
            ((ImageView) view1.findViewById(R.id.imageIcon))
                    .setImageResource(R.drawable.file);
            final AlertDialog alertDialog = builder.create();
            view1.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view1) {
                    loginPinView.setText("");
                    cpatch_checkbox.setChecked(false);
                    alertDialog.dismiss();

                }
            });
            if (alertDialog.getWindow() != null) {
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            alertDialog.show();


            return false;
        } else {
            loginPinView.setError(null);
            return true;

        }
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


                            } else if (result.equals("404")) {
                                dialog.dismiss();




                            } else {

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


    public void Check_checkBox(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {

            case R.id.cpatch_checkbox:
                if (checked) {

                    if (!validatempinLogin()) {
                        return;
                    }
                    verifyGoogleReCAPTCHA();


                    //  send_otp.setVisibility(View.VISIBLE);

                } else {
                    login_btn.setVisibility(View.GONE);
                    loginPinView.setText("");


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
                            Toast.makeText(mpin_login.this, "Error found is : " + e, Toast.LENGTH_SHORT).show();
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
                        login_btn.setVisibility(View.VISIBLE);
                        cpatch_checkbox.setTextColor(Color.GREEN);

                        //  Toast.makeText(user_signup.this, "User verified with reCAPTCHA", Toast.LENGTH_SHORT).show();
                    } else {
                        // if the response if failure we are displaying
                        // a below toast message.
                        login_btn.setVisibility(View.GONE);
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

    public void Apploginlog() {
        dialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();
        String _versionname = "V-" + BuildConfig.VERSION_NAME;

        SharedPreferences sp = getSharedPreferences("credentialsuser", MODE_PRIVATE);
        String userName = sp.getString("userName", "");//"No name defined" is the default value.
        String mobileno = sp.getString("mobile", "");//"No name defined" is the default value.
        try {
            jsonObject.put("userName", mobileno);
            jsonObject.put("versionName", _versionname);
            jsonObject.put("key", "ERojgargdvd");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String requestBody = jsonObject.toString();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Constants_URL.InserTLogin, jsonObject,
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
                            startActivity(new Intent(getApplicationContext(), dashboard.class));
                            finish();


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
                                            (mpin_login.this);
                            View view = LayoutInflater.from(mpin_login.this).inflate(
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
                        mpin_login.this);
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
                params.put("Content-Type", "application/json; charset=utf-8");
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